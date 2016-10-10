package com.coolpeng.cloud.reply.service;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.cloud.reply.entity.CloudReply;
import com.coolpeng.cloud.reply.entity.CloudReplyPage;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.QueryCondition;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.StringUtils;
import com.coolpeng.framework.utils.ipaddr.IPAddrCallback;
import com.coolpeng.framework.utils.ipaddr.IPAddrParse;
import com.coolpeng.framework.utils.ipaddr.IPAddrResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/7/5.
 */
@Service
public class CloudReplyService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int MAX_REPLY_COUNT = 1000;
    public static final int MAX_REPLY_REPLY_COUNT = 20;

    public CloudReplyPage getReplyPageSummary(String pageId) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageId", pageId);
        try {
            CloudReplyPage replyPage = CloudReplyPage.DAO.queryByAndParams(params);
            return replyPage;
        } catch (FieldNotFoundException e) {
            logger.error("", e);
            return null;
        }
    }


    /**
     * @param pageId
     * @param pageSize
     * @param pageNumber
     * @param orderType  最新1，最早2，最热3
     * @return
     */
    public PageResult<CloudReply> getReplyPageList(String pageId, int pageSize, int pageNumber, int orderType) {

        QueryCondition qc = new QueryCondition();
        qc.addEqualCondition("pageId", pageId);
        if (1 == orderType) {
            qc.setOrderDesc("createTime");
        }
        if (2 == orderType) {
            qc.setOrderAsc("createTime");
        }
        if (3 == orderType) {
            qc.setOrderDesc("hot");
        }


        try {
            PageResult<CloudReply> result = CloudReply.DAO.queryForPage(qc, pageNumber, pageSize, null);
            return result;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("", e);
        } catch (FieldNotFoundException e) {
            e.printStackTrace();
            logger.error("", e);
        }

        return new PageResult(0, 0, 0, new ArrayList<>());
    }


    public CloudReply createReply(JSONObject jsonObject) throws UpdateErrorException, TMSMsgException {

        String pageId = jsonObject.getString("pageId");

        //1、更新Summary
        CloudReplyPage replyPage = getReplyPageSummary(pageId);
        if (replyPage == null) {
            replyPage = new CloudReplyPage(pageId, 0, 0);
        }
        else {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("pageId", pageId);
                int totalCount = CloudReply.DAO.count(params);
                replyPage.setTotalCount(totalCount);
            } catch (FieldNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (replyPage.getMaxFloorNumber() > MAX_REPLY_COUNT) {
            throw new TMSMsgException("评论数量达到最大值，已经关闭评论");
        }

        replyPage.setTotalCount(replyPage.getTotalCount() + 1);
        replyPage.setMaxFloorNumber(replyPage.getMaxFloorNumber() + 1);
        CloudReplyPage.DAO.insertOrUpdate(replyPage);


        //2、插入新回复
        final CloudReply replyEntity = new CloudReply(jsonObject);
        replyEntity.setCreateIpAddr(TmsCurrentRequest.getClientIpAddr());
        replyEntity.setFloorNumber("" + replyPage.getMaxFloorNumber());
        CloudReply.DAO.save(replyEntity);



        //3、IP地址解析
        IPAddrParse.parseIpAddr(replyEntity.getCreateIpAddr(), new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult) {
                if (ipAddrResult.isOk()){
                    replyEntity.setCreateIpStr(ipAddrResult.toDisplayString());
                    try {
                        CloudReply.DAO.insertOrUpdate(replyEntity);
                    } catch (Exception e) {
                        logger.error("",e);
                    }
                }
            }
        });

        return replyEntity;
    }

    public CloudReply createReplyReply(JSONObject jsonObject, String replyId) throws TMSMsgException, UpdateErrorException, ParameterErrorException, FieldNotFoundException {

        CloudReply replyEntity = CloudReply.DAO.queryById(replyId);
        if (replyEntity == null) {
            throw new TMSMsgException("没有根据Id找到此条回复记录");
        }

        if (replyEntity.getMaxFloorNumber() > MAX_REPLY_REPLY_COUNT) {
            throw new TMSMsgException("评论数量达到最大值，已经关闭评论");
        }

        replyEntity.setMaxFloorNumber(replyEntity.getMaxFloorNumber() + 1);

        List<CloudReply> replyList = replyEntity.getReplyList();
        if (replyList == null) {
            replyList = new ArrayList<>();
        }


        CloudReply replyReplyEntity = new CloudReply(jsonObject);
        replyReplyEntity.setCreateIpAddr(TmsCurrentRequest.getClientIpAddr());
        replyReplyEntity.setFloorNumber("" + (replyEntity.getMaxFloorNumber()));
        replyList.add(replyReplyEntity);
        replyEntity.setReplyList(replyList);
        CloudReply.DAO.update(replyEntity);

        return replyEntity;
    }

    public CloudReply deleteReplyReply(String replyId, String floorNumber) throws TMSMsgException, ParameterErrorException, FieldNotFoundException, UpdateErrorException {

        CloudReply replyEntity = CloudReply.DAO.queryById(replyId);
        if (replyEntity == null) {
            throw new TMSMsgException("没有根据Id找到此条回复记录");
        }
        if (CollectionUtil.isEmpty(replyEntity.getReplyList())) {
            throw new TMSMsgException("没有需要删除的回复，可能已经被其他人删除了");
        }
        if (StringUtils.isBlank(floorNumber)) {
            throw new TMSMsgException("floorNumber请求参数不能为空");
        }


        //过滤掉要删除的那一个
        List<CloudReply> replyList0 = replyEntity.getReplyList();
        List<CloudReply> replyResultList = new ArrayList<>();
        for (CloudReply replyReply : replyList0) {
            if (!floorNumber.equals(replyReply.getFloorNumber())) {
                replyResultList.add(replyReply);
            }
        }

        replyEntity.setReplyList(replyResultList);
        CloudReply.DAO.update(replyEntity);

        return replyEntity;
    }
}

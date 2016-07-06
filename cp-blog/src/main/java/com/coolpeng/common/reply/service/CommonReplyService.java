package com.coolpeng.common.reply.service;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.common.reply.entity.CommonReply;
import com.coolpeng.common.reply.entity.CommonReplyPage;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.QueryCondition;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.StringUtils;
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
public class CommonReplyService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public CommonReplyPage getReplyPageSummary(String pageId) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageId", pageId);
        try {
            CommonReplyPage replyPage = CommonReplyPage.DAO.queryForObject(params);
            return replyPage;
        } catch (FieldNotFoundException e) {
            logger.error("", e);
            return null;
        }
    }

    public PageResult<CommonReply> getReplyPageList(String pageId, int pageSize, int pageNumber) {

        QueryCondition qc = new QueryCondition();
        qc.addEqualCondition("pageId", pageId);
        qc.setOrderDesc("createTime");

        try {
            PageResult<CommonReply> result = CommonReply.DAO.queryForPage(qc, pageNumber, pageSize, null);
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


    public void createReply(JSONObject jsonObject) throws UpdateErrorException {

        String pageId = jsonObject.getString("pageId");

        //1、更新Summary
        CommonReplyPage replyPage = getReplyPageSummary(pageId);
        if (replyPage == null) {
            replyPage = new CommonReplyPage(pageId, 0, 0);
        }
        replyPage.setTotalCount(replyPage.getTotalCount() + 1);
        replyPage.setMaxFloorNumber(replyPage.getMaxFloorNumber() + 1);
        CommonReplyPage.DAO.insertOrUpdate(replyPage);


        //2、插入新回复
        CommonReply replyEntity = new CommonReply(jsonObject);
        replyEntity.setCreateIpAddr(TmsCurrentRequest.getClientIpAddr());
        replyEntity.setFloorNumber("" + replyPage.getMaxFloorNumber());
        CommonReply.DAO.save(replyEntity);
    }

    public void createReplyReply(JSONObject jsonObject, String replyId) throws TMSMsgException, UpdateErrorException, ParameterErrorException, FieldNotFoundException {

        CommonReply replyEntity = CommonReply.DAO.queryById(replyId);
        if (replyEntity == null) {
            throw new TMSMsgException("没有根据Id找到此条回复记录");
        }

        replyEntity.setMaxFloorNumber(replyEntity.getMaxFloorNumber() + 1);

        List<CommonReply> replyList = replyEntity.getReplyList();
        if (replyList == null) {
            replyList = new ArrayList<>();
        }


        CommonReply replyReplyEntity = new CommonReply(jsonObject);
        replyReplyEntity.setCreateIpAddr(TmsCurrentRequest.getClientIpAddr());
        replyReplyEntity.setFloorNumber("" + (replyEntity.getMaxFloorNumber()));
        replyList.add(replyReplyEntity);
        replyEntity.setReplyList(replyList);

        CommonReply.DAO.update(replyEntity);
    }

    public void deleteReplyReply(String replyId, String floorNumber) throws TMSMsgException, ParameterErrorException, FieldNotFoundException {

        CommonReply replyEntity = CommonReply.DAO.queryById(replyId);
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
        List<CommonReply> replyList0 = replyEntity.getReplyList();
        List<CommonReply> replyResultList = new ArrayList<>();
        for (CommonReply replyReply : replyList0) {
            if (!floorNumber.equals(replyReply.getFloorNumber())) {
                replyResultList.add(replyReply);
            }
        }


        replyEntity.setReplyList(replyResultList);
        CommonReply.DAO.save(replyEntity);
    }
}

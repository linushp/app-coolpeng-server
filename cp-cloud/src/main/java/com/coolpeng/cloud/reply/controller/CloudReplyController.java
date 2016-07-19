package com.coolpeng.cloud.reply.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.cloud.common.base.RestBaseController;
import com.coolpeng.cloud.reply.entity.CloudReply;
import com.coolpeng.cloud.reply.entity.CloudReplyPage;
import com.coolpeng.cloud.reply.service.CloudReplyService;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/7/5.
 */

@Controller
@RequestMapping(value = "/cloud/reply", produces = "application/json; charset=UTF-8")
public class CloudReplyController extends RestBaseController {


    @Autowired
    private CloudReplyService cloudReplyService;

    @ResponseBody
    @RequestMapping({"/getReplyList"})
    public TMSResponse getReplyList(@RequestBody JSONObject jsonObject) {
        String pageId = jsonObject.getString("pageId");
        int pageSize = jsonObject.getInteger("pageSize");
        int pageNumber = jsonObject.getInteger("pageNumber");

        //最新1，最早2，最热3
        int orderType = jsonObject.getInteger("orderType");


        /******************************/
        CloudReplyPage replyPage = cloudReplyService.getReplyPageSummary(pageId);
        PageResult<CloudReply> replyListPage = cloudReplyService.getReplyPageList(pageId, pageSize, pageNumber, orderType);
        TMSResponse response = new TMSResponse();
        response.setData(replyListPage.getPageData());
        response.setTotalCount(replyListPage.getTotalCount());
        response.setPageNo(pageNumber);
        response.setPageSize(pageSize);

        response.addExtendData("CloudReplyPage", replyPage);
        return response;

    }


    @ResponseBody
    @RequestMapping({"/getReply"})
    public TMSResponse getReply(@RequestBody JSONObject jsonObject) throws ParameterErrorException, FieldNotFoundException {
        String replyId = jsonObject.getString("replyId");
        /******************************/

        Map<String, Object> params = new HashMap<>();
        params.put("id", replyId);
        CloudReply obj = CloudReply.DAO.queryById(replyId);
        return TMSResponse.success(obj);
    }

    @ResponseBody
    @RequestMapping({"/createReply"})
    public TMSResponse createReply(@RequestBody JSONObject jsonObject) throws UpdateErrorException, TMSMsgException {
        /**
         * this.pageId = jsonObject.getString("pageId");
         * this.replyContent = jsonObject.getString("replyContent");
         * this.replySummary = jsonObject.getString("replySummary");
         * this.createNickname = jsonObject.getString("createNickname");
         * this.createAvatar = jsonObject.getString("createAvatar");
         * this.createMail = jsonObject.getString("createMail");
         */

        String pageId = jsonObject.getString("pageId");
        //防止操作太频繁
        assertTimeRestriction(CloudReplyController.class, "createReply", pageId);

        cloudReplyService.createReply(jsonObject);
        return TMSResponse.success(jsonObject);
    }


    @ResponseBody
    @RequestMapping({"/deleteReply"})
    public TMSResponse deleteReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, TMSMsgException {
        assertIsAdmin(jsonObject);

        String replyId = jsonObject.getString("replyId");
        /******************************/

        Map<String, Object> params = new HashMap<>();
        params.put("id", replyId);
        CloudReply.DAO.delete(params);
        return TMSResponse.success();
    }


    @ResponseBody
    @RequestMapping({"/likeReply"})
    public TMSResponse likeReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, TMSMsgException {
        String replyId = jsonObject.getString("replyId");
        Boolean isLike = jsonObject.getBoolean("isLike");

        //防止操作太频繁
        assertTimeRestriction(CloudReplyController.class, "likeReply", replyId);

        /******************************/
        CloudReply reply = CloudReply.DAO.queryById(replyId);
        if (reply != null) {
            int countChange = Boolean.TRUE.equals(isLike) ? 1 : -1;
            reply.setLikeCount(reply.getLikeCount() + countChange);
            CloudReply.DAO.update(reply);
        }
        return TMSResponse.success(reply);
    }


    @ResponseBody
    @RequestMapping({"/createReplyReply"})
    public TMSResponse createReplyReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, TMSMsgException {

        /**
         * String replyId = jsonObject.getString("replyId");
         *
         *
         * this.pageId = jsonObject.getString("pageId");
         * this.replyContent = jsonObject.getString("replyContent");
         * this.replySummary = jsonObject.getString("replySummary");
         * this.createNickname = jsonObject.getString("createNickname");
         * this.createAvatar = jsonObject.getString("createAvatar");
         * this.createMail = jsonObject.getString("createMail");
         */
        String replyId = jsonObject.getString("replyId");

        //防止操作太频繁
        assertTimeRestriction(CloudReplyController.class, "createReplyReply", replyId);

        CloudReply replyObject = cloudReplyService.createReplyReply(jsonObject, replyId);
        return TMSResponse.success(replyObject);
    }


    @ResponseBody
    @RequestMapping({"/deleteReplyReply"})
    public TMSResponse deleteReplyReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, TMSMsgException {
        assertIsAdmin(jsonObject);

        String replyId = jsonObject.getString("replyId");
        String floorNumber = jsonObject.getString("floorNumber");

        /******************************/
        CloudReply replyObject = cloudReplyService.deleteReplyReply(replyId, floorNumber);

        return TMSResponse.success(replyObject);
    }


}

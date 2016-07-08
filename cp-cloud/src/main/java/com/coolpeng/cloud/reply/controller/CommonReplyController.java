package com.coolpeng.cloud.reply.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.cloud.common.base.RestBaseController;
import com.coolpeng.cloud.reply.entity.CommonReply;
import com.coolpeng.cloud.reply.entity.CommonReplyPage;
import com.coolpeng.cloud.reply.service.CommonReplyService;
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
public class CommonReplyController extends RestBaseController {


    @Autowired
    private CommonReplyService commonReplyService;

    @ResponseBody
    @RequestMapping({"/getReplyList"})
    public TMSResponse getReplyList(@RequestBody JSONObject jsonObject) {
        String pageId = jsonObject.getString("pageId");
        int pageSize = jsonObject.getInteger("pageSize");
        int pageNumber = jsonObject.getInteger("pageNumber");

        //最新1，最早2，最热3
        int orderType = jsonObject.getInteger("orderType");


        /******************************/
        CommonReplyPage replyPage = commonReplyService.getReplyPageSummary(pageId);
        PageResult<CommonReply> replyListPage = commonReplyService.getReplyPageList(pageId, pageSize, pageNumber, orderType);
        TMSResponse response = new TMSResponse();
        response.setData(replyListPage.getPageData());
        response.setTotalCount(replyListPage.getTotalCount());
        response.setPageNo(pageNumber);
        response.setPageSize(pageSize);

        response.addExtendData("CommonReplyPage", replyPage);
        return response;

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
        assertTimeRestriction(CommonReplyController.class, "createReply", pageId);

        commonReplyService.createReply(jsonObject);
        return TMSResponse.success();
    }


    @ResponseBody
    @RequestMapping({"/deleteReply"})
    public TMSResponse deleteReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, TMSMsgException {
        assertIsAdmin(jsonObject);

        String replyId = jsonObject.getString("replyId");
        /******************************/

        Map<String, Object> params = new HashMap<>();
        params.put("id", replyId);
        CommonReply.DAO.delete(params);
        return TMSResponse.success();
    }


    @ResponseBody
    @RequestMapping({"/likeReply"})
    public TMSResponse likeReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, TMSMsgException {
        String replyId = jsonObject.getString("replyId");
        Boolean isLike = jsonObject.getBoolean("isLike");

        //防止操作太频繁
        assertTimeRestriction(CommonReplyController.class, "likeReply", replyId);

        /******************************/
        CommonReply reply = CommonReply.DAO.queryById(replyId);
        if (reply != null) {
            int countChange = Boolean.TRUE.equals(isLike) ? 1 : -1;
            reply.setLikeCount(reply.getLikeCount() + countChange);
            CommonReply.DAO.update(reply);
        }
        return TMSResponse.success();
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
        commonReplyService.createReplyReply(jsonObject, replyId);
        return TMSResponse.success();
    }


    @ResponseBody
    @RequestMapping({"/deleteReplyReply"})
    public TMSResponse deleteReplyReply(@RequestBody JSONObject jsonObject) throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, TMSMsgException {
        assertIsAdmin(jsonObject);

        String replyId = jsonObject.getString("replyId");
        String floorNumber = jsonObject.getString("floorNumber");

        /******************************/
        commonReplyService.deleteReplyReply(replyId, floorNumber);

        return TMSResponse.success();
    }


}

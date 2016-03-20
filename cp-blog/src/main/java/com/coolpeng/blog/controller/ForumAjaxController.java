package com.coolpeng.blog.controller;

import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.service.ForumModuleService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.blog.service.ModelMapService;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ForumAjaxController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ForumService forumService;

    @Autowired
    private ModelMapService modelMapService;

    @Autowired
    private ForumModuleService forumModuleService;

    @ResponseBody
    @RequestMapping(value = {"/forum/ajax/createPost"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public TMSResponse ajaxCreatePost(@RequestParam(value = "method", required = false, defaultValue = "") String method, @RequestParam(value = "parentId", required = false, defaultValue = "") String parentId, @RequestParam(value = "postTitle", required = false, defaultValue = "") String postTitle, @RequestParam(value = "orderBy", required = false, defaultValue = "time") String orderBy, @RequestParam(value = "postContent", required = false, defaultValue = "") String postContent)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException {
        if ("post".equals(method)) {
            String moduleId = parentId;
            ForumPost post = this.forumService.createPost(moduleId, postTitle, postContent);
            return TMSResponse.success(post);
        }

        if ("reply".equals(method)) {
            String postId = parentId;
            ForumPostReply reply = this.forumService.createPostReply(postId, postContent);
            return TMSResponse.success(reply);
        }

        return TMSResponse.error("");
    }

    @ResponseBody
    @RequestMapping(value = {"/forum/ajax/setPostLikeCount"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String ajaxSetPostLikeCount(@RequestParam(value = "postId", required = false, defaultValue = "") String postId) throws ParameterErrorException, FieldNotFoundException, UpdateErrorException {
        ForumPost post = this.forumService.getForumPost(postId);

        if (post != null) {
            Object hasLike = TmsCurrentRequest.getSessionAttribute(getClass().getName() + "_addPostLikeCount_" + postId);
            if (hasLike != null) {
                return "您已赞过";
            }

            TmsCurrentRequest.setSessionAttribute(getClass().getName() + "_addPostLikeCount_" + postId, "hasLike");

            int c = post.getLikeCount();
            this.forumService.updatePostLikeCount(postId, c + 1);
            return "" + (c + 1);
        }

        return "error";
    }
}
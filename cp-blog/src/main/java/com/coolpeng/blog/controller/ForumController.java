package com.coolpeng.blog.controller;

import com.alibaba.fastjson.JSON;
import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.enums.AccessControl;
import com.coolpeng.blog.entity.enums.ModuleTypeEnum;
import com.coolpeng.blog.service.ForumCategoryService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.blog.utils.ForumUrlUtils;
import com.coolpeng.blog.vo.ForumCategoryTree;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.HtmlUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ForumController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ForumService forumService;


    @Autowired
    private ForumCategoryService forumCategoryService;

    @RequestMapping(value = {"/forum/post-list"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView getPostList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                    @RequestParam(value = "moduleId", required = false, defaultValue = "1") String categoryId,
                                    @RequestParam(value = "orderBy", required = false, defaultValue = "time") String orderBy)
            throws FieldNotFoundException, ParameterErrorException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ModelMap modelMap = new ModelMap();
//        ForumModule module = this.modelMapService.addBelongModuleAndGroup(modelMap, moduleId);
        ForumCategoryTree categories = forumCategoryService.getPublicForumCategory();
        ForumCategory module = categories.getById(categoryId);
        List<ForumCategory> categoryPath = categories.getByIdWidthParents(categoryId);
        Collections.reverse(categoryPath);

        PageResult postList = this.forumService.getPostListByCategoryIdPath(pageNumber, 30, categoryId, orderBy,AccessControl.PUBLIC);
        modelMap.put("postList", postList);
        modelMap.put("orderBy", orderBy);
        modelMap.put("belongCategory",module);
        modelMap.put("belongCategoryPath",categoryPath);

        if (module.getType() == ModuleTypeEnum.GOSSIP.getValue())
            return new ModelAndView("forum/jsp/leave-msg", modelMap);
        if (module.getType() == ModuleTypeEnum.BLOG.getValue()) {
            List<ForumPostReply> lastReplyList = this.forumService.getLastPostReplyByModuleId(5,categoryId);
            modelMap.put("lastReplyList",lastReplyList);
            return new ModelAndView("forum/jsp/blog-list", modelMap);
        }

        return new ModelAndView("forum/jsp/post-list", modelMap);
    }

    @RequestMapping(value = {"/forum/post-content"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView getPostContent(@RequestParam("postId") String postId, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber)
            throws FieldNotFoundException, ParameterErrorException, UpdateErrorException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {



        ModelMap modelMap = new ModelMap();

        ForumPost postContent = this.forumService.getPostWithReply(postId, pageNumber, 10,AccessControl.PUBLIC);
        String moduleId = postContent.getCategoryId();
        ForumCategoryTree categories = forumCategoryService.getPublicForumCategory();
        ForumCategory module = categories.getById(moduleId);
        List<ForumCategory> categoryPath = categories.getByIdWidthParents(moduleId);
        Collections.reverse(categoryPath);

        modelMap.put("belongCategory",module);
        modelMap.put("belongCategoryPath",categoryPath);
        modelMap.put("postContent", postContent);

        if (module.getType() == ModuleTypeEnum.BLOG.getValue()) {
            return new ModelAndView("forum/jsp/blog-content", modelMap);
        }
        return new ModelAndView("forum/jsp/post-content", modelMap);
    }

    @RequestMapping(value = {"/forum/createPost"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String createPost(@RequestParam(value = "method", required = false, defaultValue = "") String method,
                             @RequestParam(value = "parentId", required = false, defaultValue = "") String parentId,
                             @RequestParam(value = "postTitle", required = false, defaultValue = "") String postTitle,
                             @RequestParam(value = "orderBy", required = false, defaultValue = "time") String orderBy,
                             @RequestParam(value = "postContent", required = false, defaultValue = "") String postContent,
                             @RequestParam(value = "imageList", required = false, defaultValue = "") String imageList )
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {


        List<String> images = new ArrayList<>();

        if (StringUtils.isNotBlank(imageList)){
            images = JSON.parseArray(imageList, String.class);
        }

        if ("post".equals(method)) {
            String moduleId = parentId;
            this.forumService.createPost(moduleId, postTitle, postContent, images,AccessControl.PUBLIC);
            return "redirect:/" + ForumUrlUtils.toPostListURLNoCtx(moduleId, orderBy);
        }

        if ("reply".equals(method)) {
            String postId = parentId;
            this.forumService.createPostReply(postId, postContent,images);
            return "redirect:/" + ForumUrlUtils.toPostContentURLNoCtx(postId);
        }

        return "redirect:/forum/post-list.shtml";
    }

    @RequestMapping(value = {"/forum/createLeaveMessage"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String createLeaveMessage(@RequestParam(value = "method", required = false, defaultValue = "") String method, @RequestParam(value = "parentId", required = false, defaultValue = "") String parentId, @RequestParam(value = "postTitle", required = false, defaultValue = "") String postTitle, @RequestParam(value = "orderBy", required = false, defaultValue = "time") String orderBy, @RequestParam(value = "nickname", required = false, defaultValue = "time") String nickname, @RequestParam(value = "avatar", required = false, defaultValue = "time") String avatar, @RequestParam(value = "captcha", required = false, defaultValue = "time") String captcha, @RequestParam(value = "postContent", required = false, defaultValue = "") String postContent)
            throws FieldNotFoundException, UpdateErrorException, ParameterErrorException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        ForumCategoryTree categories = forumCategoryService.getPublicForumCategory();

        if ("leaveMsg".equals(method)) {
            String moduleId = parentId;

            postContent = postContent.replaceAll("<", "$lt;");
            postContent = postContent.replaceAll(">", "$gt;");

            ForumPost forumPost = new ForumPost();
            forumPost.setCreateUserId("");
            forumPost.setCreateAvatar(avatar);
            forumPost.setCreateMail("");
            forumPost.setCreateNickname(nickname);
            forumPost.setLikeCount(0);
            forumPost.setLastReplyMsg("");
            forumPost.setPostContent(postContent);
            forumPost.setPostTitle("留言板");
            forumPost.setCategoryId(moduleId);
            forumPost.setCategoryType(ModuleTypeEnum.GOSSIP.getValue());
            forumPost.setReplyCount(0);

            String summary = postContent.length() > 501 ? postContent.substring(0, 500) : postContent;
            summary = HtmlUtil.getTextFromHtml2(summary);
            if ((!summary.isEmpty()) && (summary.length() > 151)) {
                summary = summary.substring(0, 150);
                summary = summary + "......";
            }
            forumPost.setSummary(summary);

            ForumCategory module = categories.getById(moduleId);
            if (module != null) {
                ForumPost.DAO.save(forumPost);
                this.forumCategoryService.incrementPostCount(module);
            }
            return "redirect:/" + ForumUrlUtils.toPostListURLNoCtx(moduleId, orderBy);
        }

        return "redirect:/forum/post-list.shtml";
    }





}
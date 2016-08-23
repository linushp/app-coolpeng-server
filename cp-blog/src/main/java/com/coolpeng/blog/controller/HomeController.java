package com.coolpeng.blog.controller;

import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.enums.AccessControl;
import com.coolpeng.blog.entity.enums.ModuleTypeEnum;
import com.coolpeng.blog.service.ForumCategoryService;
import com.coolpeng.blog.service.ForumService;
import com.coolpeng.blog.vo.ForumCategoryTree;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ForumService forumService;


    @Autowired
    private ForumCategoryService forumCategoryService;

    @RequestMapping(value = {"/home"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView showHome()
            throws FieldNotFoundException, ParameterErrorException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PageResult postList = this.forumService.getPostList(1, 30, null, null, ModuleTypeEnum.BLOG, AccessControl.PUBLIC);

        ForumCategoryTree publicCategories = forumCategoryService.getPublicForumCategory();

        List<ForumCategory>  moduleList = publicCategories.getOriginNodeList();


        List<ForumPostReply> lastReplyList = this.forumService.getLastPostReply(5);

        ModelMap modelMap = new ModelMap();
        modelMap.put("postList", postList);
        modelMap.put("categoryList", moduleList);
        modelMap.put("lastReplyList", lastReplyList);

        return new ModelAndView("home/jsp/index", modelMap);
    }
}
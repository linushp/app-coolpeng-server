package com.coolpeng.blog.utils;

import com.coolpeng.blog.service.ForumModuleService;
import com.coolpeng.framework.utils.SpringBeanFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HeaderUtils {
    public static void setHeaderMenuGroup(HttpServletRequest context) {
        Object headerMenuGroup = context.getAttribute("headerMenuGroup");
        if (headerMenuGroup == null) {
            ForumModuleService forumModuleService = (ForumModuleService) SpringBeanFactory.getBean("forumModuleService");
            List groupList = forumModuleService.getForumGroupList(true);
            context.setAttribute("headerMenuGroup", groupList);
        }
    }
}
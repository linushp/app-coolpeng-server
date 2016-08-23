package com.coolpeng.blog.utils;

import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.service.ForumCategoryService;
import com.coolpeng.blog.vo.ForumCategoryTree;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class HeaderUtils {

    private static Logger logger = LoggerFactory.getLogger(HeaderUtils.class);

    public static void setHeaderMenuGroup(HttpServletRequest context) {
        Object headerMenuGroup = context.getAttribute("headerMenuGroup");
        if (headerMenuGroup == null) {
            ForumCategoryService forumCategoryService = (ForumCategoryService) ServiceUtils.getBean("forumCategoryService");
            ForumCategoryTree mm = null;
            try {
                mm = forumCategoryService.getPublicForumCategory();
            } catch (Throwable e) {
                e.printStackTrace();
                logger.error("",e);
            }
            List<ForumCategory> groupList = mm.getTreeNodeList();
            context.setAttribute("headerMenuGroup", groupList);
        }
    }
}
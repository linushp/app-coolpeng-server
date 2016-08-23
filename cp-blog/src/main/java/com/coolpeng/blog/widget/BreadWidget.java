
package com.coolpeng.blog.widget;


import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.service.ForumCategoryService;
import com.coolpeng.blog.utils.ForumUrlUtils;
import com.coolpeng.blog.vo.ForumCategoryTree;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.utils.ServiceUtils;
import com.coolpeng.framework.utils.StringUtils;
import com.coolpeng.framework.utils.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BreadWidget extends TagSupport {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Object pageMainObject;

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            String html = toHtml();
            out.print(html);
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("",e);
        }
        return 0;
    }


    public int doEndTag() throws JspException {
        return 6;
    }


    private String toHtml() throws FieldNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Tuple> breads = getBreadList();
        StringBuffer sb = new StringBuffer();
        sb.append("<div class='cp-breadcrumb'>");

        for (Tuple t : breads) {
            String text = (String) t.first();
            String url = (String) t.second();
            String clazz = (String) t.third();

            sb.append("<div class='cp-breadcrumb-i " + clazz + "'>");
            sb.append("<i></i>");
            if (StringUtils.isNotBlank(url))
                sb.append("<a href='" + url + "'>" + text + "</a>");
            else {
                sb.append("<span>" + text + "</span>");
            }
            sb.append("<em></em>");
            sb.append("</div>");
        }

        sb.append("</div>");
        return sb.toString();
    }

    private List<Tuple> getBreadList() throws FieldNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ForumCategoryService forumCategoryService = (ForumCategoryService) ServiceUtils.getBean("forumCategoryService");
        ForumCategoryTree publicCategories = forumCategoryService.getPublicForumCategory();


        if ((this.pageMainObject instanceof ForumPost)) {
            ForumPost forumPost = (ForumPost) this.pageMainObject;
            List<ForumCategory> pathList = publicCategories.getByIdWidthParents(forumPost.getCategoryId());
            Collections.reverse(pathList);

            String ctx = TmsCurrentRequest.getContext();
            String postTitle = forumPost.getPostTitle();
            String postUrl = ForumUrlUtils.toPostContentHttpURL(forumPost.getId());

            List bread = new ArrayList();
            bread.add(new Tuple(new Object[]{"您的位置：", "", "c"}));
            bread.add(new Tuple(new Object[]{"首页", ctx + "/home.shtml", "a"}));
            for (ForumCategory category:pathList){
                String moduleUrl = ForumUrlUtils.toPostListHttpURL(category.getId(), "new");
                bread.add(new Tuple(new Object[]{category.getName(), moduleUrl, "a"}));
            }


            postTitle = StringUtils.maxSize(postTitle, 26);
            bread.add(new Tuple(new Object[]{postTitle, postUrl, "b"}));

            return bread;
        }
        if ((this.pageMainObject instanceof ForumCategory)) {
            ForumCategory forumModule = (ForumCategory) this.pageMainObject;
            List<ForumCategory> pathList = publicCategories.getByIdWidthParents(forumModule.getId());
            Collections.reverse(pathList);

            String ctx = TmsCurrentRequest.getContext();

            List bread = new ArrayList();
            bread.add(new Tuple(new Object[]{"您的位置：", "", "c"}));
            bread.add(new Tuple(new Object[]{"首页", ctx + "/home.shtml", "a"}));

            for (ForumCategory category:pathList){
                String moduleUrl = ForumUrlUtils.toPostListHttpURL(category.getId(), "new");
                bread.add(new Tuple(new Object[]{category.getName(), moduleUrl, "a"}));
            }

            return bread;
        }
        return new ArrayList();
    }

    public Object getPageMainObject() {
        return this.pageMainObject;
    }

    public void setPageMainObject(Object pageMainObject) {
        this.pageMainObject = pageMainObject;
    }
}
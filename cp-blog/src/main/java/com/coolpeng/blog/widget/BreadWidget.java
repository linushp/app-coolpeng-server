
package com.coolpeng.blog.widget;

import com.coolpeng.blog.entity.ForumGroup;
import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.service.ForumModuleService;
import com.coolpeng.blog.utils.ForumUrlUtils;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.utils.SpringBeanFactory;
import com.coolpeng.framework.utils.StringUtils;
import com.coolpeng.framework.utils.Tuple;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BreadWidget extends TagSupport {
    private Object pageMainObject;

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            String html = toHtml();
            out.print(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int doEndTag() throws JspException {
        return 6;
    }

    private String toHtml() {
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

    private List<Tuple> getBreadList() {
        if ((this.pageMainObject instanceof ForumPost)) {
            ForumModuleService forumModuleService = (ForumModuleService) SpringBeanFactory.getBean("forumModuleService");

            ForumPost forumPost = (ForumPost) this.pageMainObject;
            ForumModule forumModule = forumModuleService.getForumModule(forumPost.getForumModuleId(), true);
            ForumGroup forumGroup = forumModule.getForumGroup();
            String ctx = TmsCurrentRequest.getContext();
            String moduleUrl = ForumUrlUtils.toPostListHttpURL(forumModule.getId(), "new");
            String postTitle = forumPost.getPostTitle();
            String postUrl = ForumUrlUtils.toPostContentHttpURL(forumPost.getId());

            List bread = new ArrayList();
            bread.add(new Tuple(new Object[]{"您的位置：", "", "c"}));
            bread.add(new Tuple(new Object[]{"首页", ctx + "/home.shtml", "a"}));
            bread.add(new Tuple(new Object[]{forumGroup.getGroupName(), "", "a"}));
            bread.add(new Tuple(new Object[]{forumModule.getModuleName(), moduleUrl, "a"}));

            postTitle = StringUtils.maxSize(postTitle, 26);
            bread.add(new Tuple(new Object[]{postTitle, postUrl, "b"}));

            return bread;
        }
        if ((this.pageMainObject instanceof ForumModule)) {
            ForumModuleService forumModuleService = (ForumModuleService) SpringBeanFactory.getBean("forumModuleService");

            ForumModule forumModule = (ForumModule) this.pageMainObject;
            ForumGroup forumGroup = forumModuleService.getForumGroup(forumModule.getForumGroupId());
            String ctx = TmsCurrentRequest.getContext();
            String moduleUrl = ForumUrlUtils.toPostListHttpURL(forumModule.getId(), "new");

            List bread = new ArrayList();
            bread.add(new Tuple(new Object[]{"您的位置：", "", "c"}));
            bread.add(new Tuple(new Object[]{"首页", ctx + "/home.shtml", "a"}));
            bread.add(new Tuple(new Object[]{forumGroup.getGroupName(), "", "a"}));
            bread.add(new Tuple(new Object[]{forumModule.getModuleName(), moduleUrl, "b"}));

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
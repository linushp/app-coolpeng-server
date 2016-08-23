package com.coolpeng.blog.widget;

import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.utils.ForumUrlUtils;
import com.coolpeng.framework.utils.CollectionUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ModuleListWidget extends TagSupport {
    private List<ForumCategory>  moduleList;

    private String toHtml() {
        if (!CollectionUtil.isEmpty(this.moduleList)) {
            StringBuffer sb = new StringBuffer();
            sb.append("<div class='module-list'>");
            for (ForumCategory m : this.moduleList) {
                appendHtml(sb, m);
            }
            sb.append("<div class='clear'></div>");
            sb.append("</div>");
            return sb.toString();
        }
        return "<div class='module-list-empty'><span>没有内容</span></div>";
    }

    private void appendHtml(StringBuffer sb, ForumCategory m) {
        String name = m.getName();
        String url = ForumUrlUtils.toPostListHttpURL(m.getId(), "time");
        String img = m.getIcon();
        String moduleType = "" + m.getType();
        String postCount = "" + m.getPostCount();
        String groupName = "";
        String template = "" +
                "<div class='module-list-item'>   <a href='" + url + "'> " +
                "       <div class='name'>" + name + "</div>" +
                "       <div class='type'>" + moduleType + "</div>" +
                "       <div class='group'>" + groupName + "</div>" +
                "       <div class='postCount'>" + postCount + "</div>" +
                "       <div class='img'>" +
                "           <img src='" + img + "' >" +
                "       </div>" +
                "   </a>" +
                "</div>";
        sb.append(template);
    }

    public int doStartTag() throws JspException {
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

    public List<ForumCategory> getModuleList() {
        return this.moduleList;
    }

    public void setModuleList(List<ForumCategory> moduleList) {
        this.moduleList = moduleList;
    }
}
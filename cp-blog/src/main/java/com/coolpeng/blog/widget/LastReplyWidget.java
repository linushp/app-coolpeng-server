package com.coolpeng.blog.widget;

import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.utils.ForumUrlUtils;
import com.coolpeng.framework.mvc.jsptag.TmsFunctions;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class LastReplyWidget extends TagSupport {
    private List<ForumPostReply> replyList;

    private void appendHtml(StringBuffer sb, ForumPostReply m) {
        String avatar = m.getCreateAvatar();
        avatar = TmsFunctions.toFullUrl(avatar);
        String createName = m.getCreateNickname();
        String title = m.getForumPostTitle();
        String replyMsg = m.getReplySummary();
        replyMsg = StringUtils.maxSize(replyMsg, 70);

        String url = ForumUrlUtils.toPostContentURL(m.getForumPostId());

        String x = "" +
                "<li class=\"cpw-msg-item\">\n" +
                "    <div class=\"cpw-msg-pic\">" +
                "       <img src=\"" + avatar + "\" width=\"40\" height=\"40\">" +
                "    </div>\n" + "" +
                "    <div class=\"cpw-msg-info\">\n" +
                "        <h3>" +
                "           <span>" + createName + "</span>" +
                "           <a href='" + url + "' target=\"_blank\">" + title + "</a>\n" +
                "        </h3>\n" +
                "        <p>" + replyMsg + "<i></i></p>\n" +
                "    </div>\n" +
                "</li>";

        sb.append(x);
    }

    private String toHtml() {
        if (!CollectionUtil.isEmpty(this.replyList)) {
            StringBuffer sb = new StringBuffer();
            sb.append("<div class='cpw-msg-clear'><div class=\"cpw-msg-block\"><ul>");
            for (ForumPostReply m : this.replyList) {
                appendHtml(sb, m);
            }
            sb.append("</ul></div></div>");
            return sb.toString();
        }
        return "<div>没有内容</div>";
    }

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

    public int doEndTag()
            throws JspException {
        return 6;
    }

    public List<ForumPostReply> getReplyList() {
        return this.replyList;
    }

    public void setReplyList(List<ForumPostReply> replyList) {
        this.replyList = replyList;
    }
}
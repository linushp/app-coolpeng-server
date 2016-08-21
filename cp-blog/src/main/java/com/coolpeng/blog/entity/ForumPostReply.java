package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

public class ForumPostReply extends BlogBaseEntity {
    public static SimpleDAO<ForumPostReply> DAO = new SimpleDAO(ForumPostReply.class);
    private String forumPostId;
    private ForumPost forumPost;

    private String forumPostTitle;
    private String replyTitle;
    private String floorNumber;

    @FieldDef(dbType = "longtext")
    private String replyContent;
    private String replySummary;
    private String createNickname;
    private String createAvatar;
    private String createMail;
    private String createIpAddr;

    public String getForumPostId() {
        return this.forumPostId;
    }

    public void setForumPostId(String forumPostId) {
        this.forumPostId = forumPostId;
    }

    public ForumPost getForumPost() {
        return this.forumPost;
    }

    public void setForumPost(ForumPost forumPost) {
        this.forumPost = forumPost;
    }

    public String getForumPostTitle() {
        return this.forumPostTitle;
    }

    public void setForumPostTitle(String forumPostTitle) {
        this.forumPostTitle = forumPostTitle;
    }

    public String getReplyTitle() {
        return this.replyTitle;
    }

    public void setReplyTitle(String replyTitle) {
        this.replyTitle = replyTitle;
    }

    public String getReplyContent() {
        return this.replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }


    public String getFloorNumber() {
        return this.floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getCreateNickname() {
        return this.createNickname;
    }

    public void setCreateNickname(String createNickname) {
        this.createNickname = createNickname;
    }

    public String getCreateAvatar() {
        return this.createAvatar;
    }

    public void setCreateAvatar(String createAvatar) {
        this.createAvatar = createAvatar;
    }

    public String getCreateMail() {
        return this.createMail;
    }

    public void setCreateMail(String createMail) {
        this.createMail = createMail;
    }

    public String getCreateIpAddr() {
        return this.createIpAddr;
    }

    public void setCreateIpAddr(String createIpAddr) {
        this.createIpAddr = createIpAddr;
    }

    public String getReplySummary() {
        return this.replySummary;
    }

    public void setReplySummary(String replySummary) {
        this.replySummary = replySummary;
    }
}
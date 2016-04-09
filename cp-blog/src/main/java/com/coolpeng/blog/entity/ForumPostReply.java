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

    /**
     * 下面这些字段，暂时没用到
     */
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String image9;

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

    public String getImage1() {
        return this.image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return this.image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return this.image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return this.image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return this.image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return this.image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage7() {
        return this.image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    public String getImage8() {
        return this.image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public String getImage9() {
        return this.image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
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
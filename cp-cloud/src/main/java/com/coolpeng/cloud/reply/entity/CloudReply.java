package com.coolpeng.cloud.reply.entity;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

import java.util.List;

public class CloudReply extends BlogBaseEntity {
    public static SimpleDAO<CloudReply> DAO = new SimpleDAO(CloudReply.class);
    public static final String EMPTY_STRING = "";

    private String pageId = EMPTY_STRING;
    private String replyContent = EMPTY_STRING;
    private String replySummary = EMPTY_STRING;
    private String createNickname = EMPTY_STRING;
    private String createAvatar = EMPTY_STRING;
    private String createMail = EMPTY_STRING;

    private String createIpAddr = EMPTY_STRING;
    private String floorNumber = EMPTY_STRING;

    //热度通过计算得到
    private int hot = 0;
    //被点赞的个数
    private int likeCount = 0;
    private int maxFloorNumber = 0;

    @FieldDef(jsonColumn = {List.class, CloudReply.class})
    private List<CloudReply> replyList;


    public CloudReply() {

    }

    public CloudReply(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.pageId = jsonObject.getString("pageId");
            this.replyContent = jsonObject.getString("replyContent");
            this.replySummary = jsonObject.getString("replySummary");
            this.createNickname = jsonObject.getString("createNickname");
            this.createAvatar = jsonObject.getString("createAvatar");
            this.createMail = jsonObject.getString("createMail");
            this.createIpAddr = jsonObject.getString("createIpAddr");
            this.floorNumber = jsonObject.getString("floorNumber");
            this.maxFloorNumber = 0;
            this.calculateHot();
        }
    }


    private void calculateHot() {
        this.hot = this.likeCount * 1 + this.maxFloorNumber * 5;
    }


    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplySummary() {
        return replySummary;
    }

    public void setReplySummary(String replySummary) {
        this.replySummary = replySummary;
    }

    public String getCreateNickname() {
        return createNickname;
    }

    public void setCreateNickname(String createNickname) {
        this.createNickname = createNickname;
    }

    public String getCreateAvatar() {
        return createAvatar;
    }

    public void setCreateAvatar(String createAvatar) {
        this.createAvatar = createAvatar;
    }

    public String getCreateMail() {
        return createMail;
    }

    public void setCreateMail(String createMail) {
        this.createMail = createMail;
    }

    public String getCreateIpAddr() {
        return createIpAddr;
    }

    public void setCreateIpAddr(String createIpAddr) {
        this.createIpAddr = createIpAddr;
    }

    public List<CloudReply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<CloudReply> replyList) {
        this.replyList = replyList;
    }

    public int getMaxFloorNumber() {
        return maxFloorNumber;
    }

    public void setMaxFloorNumber(int maxFloorNumber) {
        this.maxFloorNumber = maxFloorNumber;
        this.calculateHot();
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
        this.calculateHot();
    }
}
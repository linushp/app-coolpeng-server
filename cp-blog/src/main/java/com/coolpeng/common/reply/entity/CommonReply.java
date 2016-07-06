package com.coolpeng.common.reply.entity;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

import java.util.List;

public class CommonReply extends BlogBaseEntity {
    public static SimpleDAO<CommonReply> DAO = new SimpleDAO(CommonReply.class);

    private String pageId;
    private String replyContent;
    private String replySummary;
    private String createNickname;
    private String createAvatar;
    private String createMail;

    private String createIpAddr;
    private String floorNumber;
    private int maxFloorNumber = 0;

    @FieldDef(jsonColumn={List.class,CommonReply.class})
    private List<CommonReply> replyList;


    public CommonReply() {

    }

    public CommonReply(JSONObject jsonObject) {
        if (jsonObject!=null){
            this.pageId = jsonObject.getString("pageId");
            this.replyContent = jsonObject.getString("replyContent");
            this.replySummary = jsonObject.getString("replySummary");
            this.createNickname = jsonObject.getString("createNickname");
            this.createAvatar = jsonObject.getString("createAvatar");
            this.createMail = jsonObject.getString("createMail");
            this.createIpAddr = jsonObject.getString("createIpAddr");
            this.floorNumber = jsonObject.getString("floorNumber");
            this.maxFloorNumber = 0;
        }
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

    public List<CommonReply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<CommonReply> replyList) {
        this.replyList = replyList;
    }

    public int getMaxFloorNumber() {
        return maxFloorNumber;
    }

    public void setMaxFloorNumber(int maxFloorNumber) {
        this.maxFloorNumber = maxFloorNumber;
    }
}
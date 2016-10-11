package com.coolpeng.chat.model;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.framework.utils.UniqueId;
import com.coolpeng.framework.utils.DateUtil;

/**
 * Created by Administrator on 2016/9/10.
 */
public class ChatMsgVO {

    private String msgId;
    private ChatUserVO sendUser;
    private String msg;
    private String createTime;
    private Long createTimeMillis;
    private String status="sent"; //"sent,pending"

    public ChatMsgVO(UserEntity sendUser, String msg,String msgId) {
        this.sendUser = new ChatUserVO(sendUser);
        this.msg = msg;
        this.msgId = msgId;
        this.createTime = DateUtil.currentTimeFormat();
        this.createTimeMillis = System.currentTimeMillis();
    }

    public ChatMsgVO(ChatUserVO sendUser, String msg,String msgId) {
        this.sendUser = (sendUser);
        this.msg = msg;
        this.msgId = msgId;
        this.createTime = DateUtil.currentTimeFormat();
        this.createTimeMillis = System.currentTimeMillis();
    }

    public ChatMsgVO() {
    }

    public ChatUserVO getSendUser() {
        return sendUser;
    }

    public void setSendUser(ChatUserVO sendUser) {
        this.sendUser = sendUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTimeMillis() {
        return createTimeMillis;
    }

    public void setCreateTimeMillis(Long createTimeMillis) {
        this.createTimeMillis = createTimeMillis;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

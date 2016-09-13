package com.coolpeng.chat.model;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.framework.utils.DateUtil;

/**
 * Created by Administrator on 2016/9/10.
 */
public class ChatMsgVO {

    private UserEntity sendUser;
    private String msg;
    private String createTime;
    private Long createTimeMillis;

    public ChatMsgVO() {
    }

    public ChatMsgVO(UserEntity sendUser, String msg) {
        this.sendUser = new UserEntity(sendUser);
        this.sendUser.setPassword(null);
        this.msg = msg;
        this.createTime = DateUtil.currentTimeFormat();
        this.createTimeMillis = System.currentTimeMillis();
    }

    public UserEntity getSendUser() {
        return sendUser;
    }

    public void setSendUser(UserEntity sendUser) {
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
}

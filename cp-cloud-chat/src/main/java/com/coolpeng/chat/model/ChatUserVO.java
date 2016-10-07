package com.coolpeng.chat.model;

import com.coolpeng.blog.entity.UserEntity;

/**
 * Created by Administrator on 2016/9/15.
 */
public class ChatUserVO {
    private String uid;
    private String username;
    private String nickname;
    private String avatar;

    public ChatUserVO(UserEntity sendUser) {
        this.uid = sendUser.getId();
        this.username  = sendUser.getUsername();
        this.nickname = sendUser.getNickname();
        this.avatar = sendUser.getAvatar();
    }

    public ChatUserVO() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatUserVO that = (ChatUserVO) o;

        return !(uid != null ? !uid.equals(that.uid) : that.uid != null);

    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}

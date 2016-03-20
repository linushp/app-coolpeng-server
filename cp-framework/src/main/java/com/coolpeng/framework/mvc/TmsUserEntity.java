package com.coolpeng.framework.mvc;

import com.coolpeng.framework.db.BaseEntity;

public class TmsUserEntity extends BaseEntity {
    public static String PERMISSION_ADMIN = "admin";
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String mail;
    private String permission;







    /**
     * 最后一次登录产生的token
     */
    private String lastLoginToken;

    /**
     * 最后一次登录设备信息
     */
    private String lastLoginDevPlatform;

    /**
     * 最后一次登录的UUID
     */
    private String lastLoginDevUid;

    /**
     * 最后一次登录的时间
     */
    private String lastLoginTime;


    public TmsUserEntity(TmsUserEntity user) {

        super(user);

        this.avatar=user.avatar;
        this.lastLoginDevPlatform=user.lastLoginDevPlatform;
        this.lastLoginDevUid=user.lastLoginDevUid;
        this.lastLoginTime=user.lastLoginTime;
        this.lastLoginToken=user.lastLoginToken;
        this.mail=user.mail;
        this.nickname=user.nickname;
        this.password=user.password;
        this.permission=user.permission;
        this.username=user.username;
    }

    public TmsUserEntity() {
    }

    public TmsUserEntity(String username, String password, String nickname, String avatar, String permission) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.permission = permission;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getLastLoginDevPlatform() {
        return lastLoginDevPlatform;
    }

    public void setLastLoginDevPlatform(String lastLoginDevPlatform) {
        this.lastLoginDevPlatform = lastLoginDevPlatform;
    }

    public String getLastLoginDevUid() {
        return lastLoginDevUid;
    }

    public void setLastLoginDevUid(String lastLoginDevUid) {
        this.lastLoginDevUid = lastLoginDevUid;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginToken() {
        return lastLoginToken;
    }

    public void setLastLoginToken(String lastLoginToken) {
        this.lastLoginToken = lastLoginToken;
    }
}
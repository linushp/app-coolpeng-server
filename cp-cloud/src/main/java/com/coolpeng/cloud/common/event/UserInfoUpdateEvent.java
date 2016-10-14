package com.coolpeng.cloud.common.event;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.framework.event.TMSEvent;

/**
 * Created by luanhaipeng on 16/10/14.
 */
public class UserInfoUpdateEvent extends TMSEvent{
    private UserEntity user;

    public UserInfoUpdateEvent(UserEntity user) {
        this.setUser(user);
        this.setData(user);
        this.setName("UserInfoUpdateEvent");
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}

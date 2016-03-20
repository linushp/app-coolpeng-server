package com.coolpeng.blog.entity.base;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.framework.db.BaseEntity;
import com.coolpeng.framework.db.annotation.VOTemp;

public class BlogBaseEntity extends BaseEntity {

    @VOTemp
    private UserEntity createUser;

    public BlogBaseEntity() {
    }

    public BlogBaseEntity(BlogBaseEntity e) {
        super(e);
        this.createUser = e.createUser;
    }

    public UserEntity getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(UserEntity createUser) {
        this.createUser = createUser;
    }
}
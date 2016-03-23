package com.coolpeng.blog.entity;

import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.mvc.TmsUserEntity;
import com.coolpeng.framework.utils.CollectionUtil;

import java.util.Collection;

public class UserEntity extends TmsUserEntity {
    public static SimpleDAO<UserEntity> DAO = new SimpleDAO(UserEntity.class);

    public UserEntity() {

    }

    public UserEntity(UserEntity user) {
        super(user);
    }

    public static void clearUserPassword(Collection<UserEntity> users) {
        if (!CollectionUtil.isEmpty(users))
            for (UserEntity u : users)
                u.setPassword(null);
    }
}
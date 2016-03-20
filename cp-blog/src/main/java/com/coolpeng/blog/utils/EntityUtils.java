package com.coolpeng.blog.utils;

import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.utils.StringUtils;

import java.util.Iterator;
import java.util.List;

public class EntityUtils {
    public static UserEntity getDefaultUser() {
        UserEntity defaultUser = new UserEntity();
        defaultUser.setNickname("匿名");
        defaultUser.setId("default");
        defaultUser.setAvatar("/forum/images/sys_avatar.png");
        return defaultUser;
    }

    public static void addDefaultUser(PageResult pageResult) {
        UserEntity defaultUser = getDefaultUser();

        if (pageResult == null) {
            return;
        }
        if (pageResult.getPageData() == null) {
            return;
        }

        List dataList = pageResult.getPageData();

        for (Iterator localIterator = dataList.iterator(); localIterator.hasNext(); ) {
            Object d0 = localIterator.next();
            if ((d0 instanceof BlogBaseEntity)) {
                BlogBaseEntity d = (BlogBaseEntity) d0;
                UserEntity createUser = d.getCreateUser();
                if (createUser == null) {
                    d.setCreateUser(defaultUser);
                }

                if ((d instanceof ForumPost)) {
                    ForumPost p = (ForumPost) d;
                    String nickname = p.getCreateNickname();
                    if (StringUtils.isBlank(nickname)) {
                        p.setCreateNickname(defaultUser.getNickname());
                        p.setCreateAvatar(defaultUser.getAvatar());
                    }
                } else if ((d instanceof ForumPostReply)) {
                    ForumPostReply p = (ForumPostReply) d;
                    String nickname = p.getCreateNickname();
                    if (StringUtils.isBlank(nickname)) {
                        p.setCreateNickname(defaultUser.getNickname());
                        p.setCreateAvatar(defaultUser.getAvatar());
                    }
                }
            }
        }
    }

    public static void setAvatarUrl(PageResult<ForumPost> pageResult, String context) {
        if (pageResult == null) {
            return;
        }
        if (pageResult.getPageData() == null) {
            return;
        }
        UserEntity defaultUser = getDefaultUser();

        List<ForumPost> dataList = pageResult.getPageData();
        for (ForumPost p : dataList) {
            UserEntity user = (p.getCreateUser() != null) ? p.getCreateUser() : defaultUser;

            String avatar = p.getCreateAvatar();
            if (StringUtils.isNotBlank(avatar)) {
                if (!avatar.startsWith("http"))
                    p.setCreateAvatar(context + avatar);
            } else {
                String userAvatar = user.getAvatar();
                if (!userAvatar.startsWith("http")) {
                    p.setCreateAvatar(context + userAvatar);
                }
            }

            String nickname = p.getCreateNickname();
            if (StringUtils.isBlank(nickname))
                p.setCreateNickname(user.getNickname());
        }
    }
}
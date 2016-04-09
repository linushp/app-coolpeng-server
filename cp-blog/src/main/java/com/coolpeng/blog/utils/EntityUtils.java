package com.coolpeng.blog.utils;

import com.coolpeng.blog.entity.ForumModule;
import com.coolpeng.blog.entity.ForumPost;
import com.coolpeng.blog.entity.ForumPostReply;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.StringUtils;

import java.util.Iterator;
import java.util.List;

public class EntityUtils {
    public static UserEntity getDefaultUser(String ctx) {
        UserEntity defaultUser = new UserEntity();
        defaultUser.setNickname("孙悟空");
        defaultUser.setId("default");
        defaultUser.setAvatar("/forum/images/sys_avatar.png");
        return defaultUser;
    }

    public static void addDefaultUser(PageResult pageResult,String ctx) {
        UserEntity defaultUser = getDefaultUser(ctx);

        if (pageResult == null) {
            return;
        }
        if (pageResult.getPageData() == null) {
            return;
        }

        List dataList = pageResult.getPageData();

        addDefaultUser(dataList,ctx);
    }


    public static void addDefaultUser(List dataList,String ctx) {

        if (dataList == null) {
            return;
        }
        UserEntity defaultUser = getDefaultUser(ctx);

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
        UserEntity defaultUser = getDefaultUser(context);

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


    public static void setReplyAvatarUrl(PageResult<ForumPostReply> pageResult, String context) {
        if (pageResult == null) {
            return;
        }
        if (pageResult.getPageData() == null) {
            return;
        }
        UserEntity defaultUser = getDefaultUser(context);

        List<ForumPostReply> dataList = pageResult.getPageData();
        for (ForumPostReply p : dataList) {
            UserEntity user = (p.getCreateUser() != null) ? p.getCreateUser() : defaultUser;

            String avatar = p.getCreateAvatar();
            if (StringUtils.isNotBlank(avatar)) {
                if (!avatar.startsWith("http")){
                    p.setCreateAvatar(context + avatar);
                }
            } else {
                String userAvatar = user.getAvatar();
                if (!userAvatar.startsWith("http")) {
                    p.setCreateAvatar(context + userAvatar);
                }
            }

            String nickname = p.getCreateNickname();
            if (StringUtils.isBlank(nickname)){
                p.setCreateNickname(user.getNickname());
            }
        }
    }

    public static void setReplyAvatarUrl(List<ForumPostReply> dataList, String context) {

        if (dataList == null) {
            return;
        }

        UserEntity defaultUser = getDefaultUser(context);

        for (ForumPostReply p : dataList) {
            UserEntity user = (p.getCreateUser() != null) ? p.getCreateUser() : defaultUser;

            String avatar = p.getCreateAvatar();
            if (StringUtils.isNotBlank(avatar)) {
                if (!avatar.startsWith("http")){
                    p.setCreateAvatar(context + avatar);
                }
            } else {
                String userAvatar = user.getAvatar();
                if (!userAvatar.startsWith("http")) {
                    p.setCreateAvatar(context + userAvatar);
                }
            }

            String nickname = p.getCreateNickname();
            if (StringUtils.isBlank(nickname)){
                p.setCreateNickname(user.getNickname());
            }
        }
    }


    public static void setModuleDefaultIcon(List<ForumModule> moduleList,String context){
        String [] imgList = new String[]{"001.png","002.png","003.png","004.png","005.png","006.png","007.png","008.jpg","009.jpg","010.jpg","011.png"};
        if (!CollectionUtil.isEmpty(moduleList)){
            int i=0;
            int imgLength = imgList.length;
            for (ForumModule m : moduleList){
                String icon = m.getModuleIcon();
                if (StringUtils.isBlank(icon) || (!icon.endsWith(".png") && !icon.endsWith(".jpg"))){
                    int index = i % imgLength;
                    m.setModuleIcon(context + "/forum/images/modules/"+ imgList[index]);
                }
                i++;
            }
        }
    }
}
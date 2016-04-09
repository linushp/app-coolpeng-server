package com.coolpeng.blog.utils;

import com.coolpeng.framework.mvc.TmsCurrentRequest;

public class ForumUrlUtils {
    public static String toPostContentHttpURL(String postId) {
        return toPostContentURL(postId);
    }

    public static String toPostListHttpURL(String moduleId, String orderBy) {
        return toPostListURL(moduleId, orderBy);
    }

    public static String toPostContentURL(String postId) {
        String ctx = TmsCurrentRequest.getContext();
        return ctx + "/forum/post-content.shtml?postId=" + postId;
    }

    public static String toPostListURL(String moduleId, String orderBy) {
        String ctx = TmsCurrentRequest.getContext();
        return ctx + "/forum/post-list.shtml?orderBy=" + orderBy + "&moduleId=" + moduleId;
    }
}
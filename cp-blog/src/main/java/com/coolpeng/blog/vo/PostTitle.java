package com.coolpeng.blog.vo;

/**
 * Created by Administrator on 2015/9/30.
 */
public class PostTitle {
    private String postTitle;
    private String linkUrl;
    private String createTime;

    public PostTitle() {
    }

    public PostTitle(String postTitle, String linkUrl, String createTime) {
        this.postTitle = postTitle;
        this.linkUrl = linkUrl;
        this.createTime = createTime;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

package com.coolpeng.blog.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/30.
 */
public class ForumHomeVO {

    //帖子数量
    private int postCount;

    //用户数量
    private int userCount;

    //首页幻灯片
    private List<SlideImage> slideImageList = new ArrayList<>();

    //新帖
    private List<PostTitle> newPostList = new ArrayList<>();

    //推荐贴
    private List<PostTitle> recommendPostList = new ArrayList<>();

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<SlideImage> getSlideImageList() {
        return slideImageList;
    }

    public void setSlideImageList(List<SlideImage> slideImageList) {
        this.slideImageList = slideImageList;
    }

    public List<PostTitle> getNewPostList() {
        return newPostList;
    }

    public void setNewPostList(List<PostTitle> newPostList) {
        this.newPostList = newPostList;
    }

    public List<PostTitle> getRecommendPostList() {
        return recommendPostList;
    }

    public void setRecommendPostList(List<PostTitle> recommendPostList) {
        this.recommendPostList = recommendPostList;
    }
}

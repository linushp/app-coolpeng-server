package com.coolpeng.blog.entity;

import com.alibaba.fastjson.JSON;
import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;
import com.coolpeng.framework.db.annotation.VOTemp;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.StringUtils;

import java.util.*;

public class ForumPost extends BlogBaseEntity {
    public static SimpleDAO<ForumPost> DAO = new SimpleDAO(ForumPost.class);
    private String forumModuleId;
    private int moduleType;
    private String postTitle;

    @FieldDef(dbType = "longtext")
    private String postContent;
    private String summary;
    private int viewCount = 0;

    private int replyCount = 0;

    private int likeCount = 0;

    private int recommend = 0;
    private String createNickname;
    private String createAvatar;
    private String createMail;
    private String createIpAddr;
    private String lastReplyUserId;
    private String lastReplyMsg;
    private String lastReplyNickname;
    private String lastReplyAvatar;
    private String lastReplyMail;
    private String lastReplyTime = DateUtil.currentTimeFormat();

    @VOTemp
    private ForumModule forumModule;

    @VOTemp
    private PageResult<ForumPostReply> replyPageResult;

    @VOTemp
    private List<String> imageList;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String image9;
    private String moreImages;

//    @FieldDef(jsonColumn={List.class,ForumGroup.class})
//    private List<ForumGroup> moreImages1;
//
//    @FieldDef(jsonColumn={Set.class,ForumGroup.class})
//    private List<ForumGroup> moreImages2;
//
//    @FieldDef(jsonColumn={Map.class,ForumGroup.class})
//    private Map<String,ForumGroup> moreImages3;
//
//    @FieldDef(jsonColumn={ForumGroup.class})
//    private ForumGroup moreImages4;

    public ForumPost() {
    }

    public int getRecommend() {
        return this.recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getCreateIpAddr() {
        return this.createIpAddr;
    }

    public void setCreateIpAddr(String createIpAddr) {
        this.createIpAddr = createIpAddr;
    }

    public String getLastReplyTime() {
        return this.lastReplyTime;
    }

    public void setLastReplyTime(String lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public String getLastReplyNickname() {
        return this.lastReplyNickname;
    }

    public void setLastReplyNickname(String lastReplyNickname) {
        this.lastReplyNickname = lastReplyNickname;
    }

    public String getLastReplyAvatar() {
        return this.lastReplyAvatar;
    }

    public void setLastReplyAvatar(String lastReplyAvatar) {
        this.lastReplyAvatar = lastReplyAvatar;
    }

    public String getLastReplyMail() {
        return this.lastReplyMail;
    }

    public void setLastReplyMail(String lastReplyMail) {
        this.lastReplyMail = lastReplyMail;
    }

    public int getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public String getMoreImages() {
        return this.moreImages;
    }

    public void setMoreImages(String moreImages) {
        this.moreImages = moreImages;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPostTitle() {
        return this.postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return this.postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getReplyCount() {
        return this.replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getLastReplyUserId() {
        return this.lastReplyUserId;
    }

    public void setLastReplyUserId(String lastReplyUserId) {
        this.lastReplyUserId = lastReplyUserId;
    }

    public String getImage1() {
        return this.image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return this.image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return this.image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return this.image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return this.image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return this.image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage7() {
        return this.image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    public String getImage8() {
        return this.image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public String getImage9() {
        return this.image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    public String getForumModuleId() {
        return this.forumModuleId;
    }

    public void setForumModuleId(String forumModuleId) {
        this.forumModuleId = forumModuleId;
    }

    public ForumModule getForumModule() {
        return this.forumModule;
    }

    public void setForumModule(ForumModule forumModule) {
        this.forumModule = forumModule;
    }

    public PageResult<ForumPostReply> getReplyPageResult() {
        return this.replyPageResult;
    }

    public void setReplyPageResult(PageResult<ForumPostReply> replyPageResult) {
        this.replyPageResult = replyPageResult;
    }

    public List<String> getImageList() {
        return this.imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getLastReplyMsg() {
        return this.lastReplyMsg;
    }

    public void setLastReplyMsg(String lastReplyMsg) {
        this.lastReplyMsg = lastReplyMsg;
    }

    public int getLikeCount() {
        return this.likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCreateNickname() {
        return this.createNickname;
    }

    public void setCreateNickname(String createNickname) {
        this.createNickname = createNickname;
    }

    public String getCreateAvatar() {
        return this.createAvatar;
    }

    public void setCreateAvatar(String createAvatar) {
        this.createAvatar = createAvatar;
    }

    public String getCreateMail() {
        return this.createMail;
    }

    public void setCreateMail(String createMail) {
        this.createMail = createMail;
    }


    public void createTempImageEntity(int maxCount) {
        if (this.imageList == null) {
            this.imageList = new ArrayList();
        }
        this.imageList.clear();

        if (StringUtils.isNotBlank(this.image1)) {
            this.imageList.add(this.image1);
        }
        if (StringUtils.isNotBlank(this.image2)) {
            this.imageList.add(this.image2);
        }
        if (StringUtils.isNotBlank(this.image3)) {
            this.imageList.add(this.image3);
        }
        if (StringUtils.isNotBlank(this.image4)) {
            this.imageList.add(this.image4);
        }
        if (StringUtils.isNotBlank(this.image5)) {
            this.imageList.add(this.image5);
        }
        if (StringUtils.isNotBlank(this.image6)) {
            this.imageList.add(this.image6);
        }
        if (StringUtils.isNotBlank(this.image7)) {
            this.imageList.add(this.image7);
        }
        if (StringUtils.isNotBlank(this.image8)) {
            this.imageList.add(this.image8);
        }
        if (StringUtils.isNotBlank(this.image9)) {
            this.imageList.add(this.image9);
        }
        if (StringUtils.isNotBlank(this.moreImages)) {
            List images = JSON.parseArray(this.moreImages, String.class);
            this.imageList.addAll(images);
        }

        if (this.imageList.size() > maxCount) {
            this.imageList = this.imageList.subList(0, maxCount);
        }

    }

    public void addImageList(List<String> images) {
        String moreImages = this.moreImages;
        List beforeImages;
        if (StringUtils.isNotBlank(moreImages)) {
            beforeImages = JSON.parseArray(moreImages, String.class);
            LinkedHashSet imageSet = new LinkedHashSet(beforeImages);
            imageSet.addAll(images);
            List newImages = new ArrayList(imageSet);
            this.moreImages = JSON.toJSONString(newImages, true);
        } else {
            for (String img : images)
                addImage(img);
        }
    }

    public void addImage(String imageUrl) {
        if (StringUtils.isBlank(this.image1)) {
            this.image1 = imageUrl;
        } else if (StringUtils.isBlank(this.image2)) {
            this.image2 = imageUrl;
        } else if (StringUtils.isBlank(this.image3)) {
            this.image3 = imageUrl;
        } else if (StringUtils.isBlank(this.image4)) {
            this.image4 = imageUrl;
        } else if (StringUtils.isBlank(this.image5)) {
            this.image5 = imageUrl;
        } else if (StringUtils.isBlank(this.image6)) {
            this.image6 = imageUrl;
        } else if (StringUtils.isBlank(this.image7)) {
            this.image7 = imageUrl;
        } else if (StringUtils.isBlank(this.image8)) {
            this.image8 = imageUrl;
        } else if (StringUtils.isBlank(this.image9)) {
            this.image9 = imageUrl;
        } else {
            String moreImages = this.moreImages;
            List images = new ArrayList();
            if (StringUtils.isNotBlank(moreImages)) {
                images = JSON.parseArray(moreImages, String.class);
            }
            images.add(imageUrl);
            this.moreImages = JSON.toJSONString(images, true);
        }
    }


    public ForumPostReply extractLastReply(){
        ForumPost post = this;
        ForumPostReply reply = new ForumPostReply();
        reply.setCreateTime(post.getLastReplyTime());
        reply.setUpdateTime(post.getLastReplyTime());
        reply.setForumPostId(post.getId());
        reply.setForumPostTitle(post.getPostTitle());
        reply.setReplyContent(post.getLastReplyMsg());
        reply.setReplySummary(post.getLastReplyMsg());
        reply.setCreateNickname(post.getLastReplyNickname());
        reply.setCreateAvatar(post.getLastReplyAvatar());
        reply.setCreateMail(post.getLastReplyMail());
        reply.setCreateUserId(post.getLastReplyUserId());
        reply.setUpdateUserId(post.getLastReplyUserId());

        return reply;
    }

}
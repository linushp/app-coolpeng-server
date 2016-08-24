package com.coolpeng.blog.entity;

import com.alibaba.fastjson.JSON;
import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;
import com.coolpeng.framework.db.annotation.VOTemp;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.StringUtils;

import java.util.*;

public class ForumPost extends BlogBaseEntity {
    public static SimpleDAO<ForumPost> DAO = new SimpleDAO(ForumPost.class);
    private String categoryId;///----
    private String categoryIdPath;
    private int categoryType; //----
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
    private String createIpStr;
    private String lastReplyUserId;
    private String lastReplyMsg;
    private String lastReplyNickname;
    private String lastReplyAvatar;
    private String lastReplyMail;
    private String lastReplyTime = DateUtil.currentTimeFormat();
    private String accessControl; //public private
    private String myCategoryId; //-----
    private String myCategoryIdPath;

    @FieldDef(jsonColumn = {List.class, String.class})
    private List<String> imageList; //--

    @FieldDef(jsonColumn = {List.class, String.class})
    private List<String> allImageList;  //--


    @VOTemp
    private ForumCategory category;
    @VOTemp
    private ForumCategory myCategory;

    @VOTemp
    private PageResult<ForumPostReply> replyPageResult;


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


    public ForumPost(ForumPost p) {
        super(p);

        this.categoryId = p.categoryId;
        this.categoryType = p.categoryType;
        this.postTitle = p.postTitle;
        this.postContent = p.postContent;
        this.summary = p.summary;
        this.viewCount = p.viewCount;
        this.replyCount = p.replyCount;
        this.likeCount = p.likeCount;
        this.recommend = p.recommend;
        this.createNickname = p.createNickname;
        this.createAvatar = p.createAvatar;
        this.createMail = p.createMail;
        this.createIpAddr = p.createIpAddr;
        this.lastReplyUserId = p.lastReplyUserId;
        this.lastReplyMsg = p.lastReplyMsg;
        this.lastReplyNickname = p.lastReplyNickname;
        this.lastReplyAvatar = p.lastReplyAvatar;
        this.lastReplyMail = p.lastReplyMail;
        this.lastReplyTime = p.lastReplyTime;
        this.category = p.category;
        this.myCategory = p.myCategory;
        this.replyPageResult = p.replyPageResult;
        this.imageList = p.imageList;
        this.allImageList = p.allImageList;
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


    public PageResult<ForumPostReply> getReplyPageResult() {
        return this.replyPageResult;
    }

    public void setReplyPageResult(PageResult<ForumPostReply> replyPageResult) {
        this.replyPageResult = replyPageResult;
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


    public ForumPostReply extractLastReply() {
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


    public String getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(String accessControl) {
        this.accessControl = accessControl;
    }

    public String getCreateIpStr() {
        return createIpStr;
    }

    public void setCreateIpStr(String createIpStr) {
        this.createIpStr = createIpStr;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public String getMyCategoryId() {
        return myCategoryId;
    }

    public void setMyCategoryId(String myCategoryId) {
        this.myCategoryId = myCategoryId;
    }


    public ForumCategory getCategory() {
        return category;
    }

    public void setCategory(ForumCategory category) {
        this.category = category;
    }

    public ForumCategory getMyCategory() {
        return myCategory;
    }

    public void setMyCategory(ForumCategory myCategory) {
        this.myCategory = myCategory;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getCategoryIdPath() {
        return categoryIdPath;
    }

    public void setCategoryIdPath(String categoryIdPath) {
        this.categoryIdPath = categoryIdPath;
    }

    public String getMyCategoryIdPath() {
        return myCategoryIdPath;
    }

    public void setMyCategoryIdPath(String myCategoryIdPath) {
        this.myCategoryIdPath = myCategoryIdPath;
    }

    public List<String> getAllImageList() {
        if (allImageList == null) {
            allImageList = new ArrayList<>();
        }
        return allImageList;
    }

    public void setAllImageList(List<String> allImageList) {
        this.allImageList = allImageList;
    }

    public void appendToAllImageList(List<String> images) {
        //去重，并保证先后顺序
        if (!CollectionUtil.isEmpty(images)) {
            this.getAllImageList().addAll(images);
            LinkedHashSet<String> mm = new LinkedHashSet<>(this.getAllImageList());
            List<String> mm2 = new ArrayList<>(mm);
            setAllImageList(mm2);
        }
    }


    @Override
    public String toString() {
        return "ForumPost{" +
                "id = '" + this.getId() + "'" +
                ", categoryId='" + categoryId + '\'' +
                ", categoryIdPath='" + categoryIdPath + '\'' +
                ", categoryType=" + categoryType +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", summary='" + summary + '\'' +
                ", viewCount=" + viewCount +
                ", replyCount=" + replyCount +
                ", likeCount=" + likeCount +
                ", recommend=" + recommend +
                ", createNickname='" + createNickname + '\'' +
                ", createAvatar='" + createAvatar + '\'' +
                ", createMail='" + createMail + '\'' +
                ", createIpAddr='" + createIpAddr + '\'' +
                ", createIpStr='" + createIpStr + '\'' +
                ", lastReplyUserId='" + lastReplyUserId + '\'' +
                ", lastReplyMsg='" + lastReplyMsg + '\'' +
                ", lastReplyNickname='" + lastReplyNickname + '\'' +
                ", lastReplyAvatar='" + lastReplyAvatar + '\'' +
                ", lastReplyMail='" + lastReplyMail + '\'' +
                ", lastReplyTime='" + lastReplyTime + '\'' +
                ", accessControl='" + accessControl + '\'' +
                ", myCategoryId='" + myCategoryId + '\'' +
                ", myCategoryIdPath='" + myCategoryIdPath + '\'' +
                '}';
    }
}
package com.coolpeng.cloud.reply.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;

/**
 * Created by luanhaipeng on 16/7/5.
 */
public class CommonReplyPage extends BlogBaseEntity {

    public static SimpleDAO<CommonReplyPage> DAO = new SimpleDAO(CommonReplyPage.class);


    private String pageId;

    /**
     * 最大楼层
     */
    private int maxFloorNumber;

    /**
     * 目前总共有多少个回复
     */
    private int totalCount;


    public CommonReplyPage() {
    }


    public CommonReplyPage(String pageId, int maxFloorNumber, int totalCount) {
        this.pageId = pageId;
        this.maxFloorNumber = maxFloorNumber;
        this.totalCount = totalCount;
    }


    public CommonReplyPage(BlogBaseEntity e, String pageId, int maxFloorNumber, int totalCount) {
        super(e);
        this.pageId = pageId;
        this.maxFloorNumber = maxFloorNumber;
        this.totalCount = totalCount;
    }


    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public int getMaxFloorNumber() {
        return maxFloorNumber;
    }

    public void setMaxFloorNumber(int maxFloorNumber) {
        this.maxFloorNumber = maxFloorNumber;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

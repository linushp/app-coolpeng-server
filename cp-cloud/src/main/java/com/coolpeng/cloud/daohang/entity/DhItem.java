package com.coolpeng.cloud.daohang.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;

/**
 * Created by luanhaipeng on 16/8/3.
 */
public class DhItem extends BlogBaseEntity {

    public static SimpleDAO<DhItem> DAO = new SimpleDAO(DhItem.class);

    private String categoryId;
    private String text;
    private String desc;
    private String link;
    private String icon;
    private int order;
    private int type; //1 按钮， 2图文


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

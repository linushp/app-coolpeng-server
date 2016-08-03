package com.coolpeng.cloud.daohang.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.VOTemp;

import java.util.List;

/**
 * Created by luanhaipeng on 16/8/3.
 */
public class DhCategory extends BlogBaseEntity {
    public static SimpleDAO<DhCategory> DAO = new SimpleDAO(DhCategory.class);

    private String text;
    private String desc;
    private String icon;
    private int order;

    @VOTemp
    private List<DhItem> items;


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

    public List<DhItem> getItems() {
        return items;
    }

    public void setItems(List<DhItem> items) {
        this.items = items;
    }
}

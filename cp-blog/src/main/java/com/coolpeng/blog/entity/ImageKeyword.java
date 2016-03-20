package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleQuery;

import java.util.List;

/**
 * Created by Administrator on 2015/9/19.
 */
public class ImageKeyword extends BlogBaseEntity {

    public static SimpleQuery<ImageKeyword> DAO = new SimpleQuery<>(ImageKeyword.class);

    private String keyword;

    private String desc;

    private String category;

    private String imageListJSON;

    private List<String> imageList;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageListJSON() {
        return imageListJSON;
    }

    public void setImageListJSON(String imageListJSON) {
        this.imageListJSON = imageListJSON;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

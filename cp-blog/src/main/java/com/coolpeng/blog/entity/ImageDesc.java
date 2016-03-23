package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;

/**
 * Created by Administrator on 2015/9/19.
 */
public class ImageDesc extends BlogBaseEntity {
    public static SimpleDAO<ImageEntity> DAO = new SimpleDAO<>(ImageEntity.class);

    private String belongKeywordId;


}

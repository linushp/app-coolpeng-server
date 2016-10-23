package com.coolpeng.cloud.files.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;

/**
 * Created by luanhaipeng on 16/10/21.
 */
public class CloudCode extends BlogBaseEntity {
    public static SimpleDAO<CloudCode> DAO = new SimpleDAO(CloudCode.class);


    private String language;
    private String title;
    private String content;
    private int codeSize;


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCodeSize() {
        return codeSize;
    }

    public void setCodeSize(int codeSize) {
        this.codeSize = codeSize;
    }
}

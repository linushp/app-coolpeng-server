package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleQuery;
import com.coolpeng.framework.db.annotation.FieldDef;

/**
 * Created by Administrator on 2015/9/30.
 */
public class SysSetting extends BlogBaseEntity {

    public static SimpleQuery<SysSetting> DAO = new SimpleQuery<>(SysSetting.class);

    private String name;

    @FieldDef(dbType = FieldDef.DBTYPE_LONGTEXT)
    private String content;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

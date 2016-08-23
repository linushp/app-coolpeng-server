package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.VOTemp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luanhaipeng on 16/8/23.
 */
public class ForumCategory extends BlogBaseEntity {

    public static final SimpleDAO<ForumCategory> DAO = new SimpleDAO(ForumCategory.class);

    private String name;
    private String desc;
    private int postCount = 0;
    private int type = 3;//板块类型：论坛（1），问答（2），博客类型（3），留言板（4）
    private String accessControl; //public private
    private String parentId;
    private String icon;

    @VOTemp
    private List<ForumCategory> children;


    public ForumCategory() {
    }

    public ForumCategory(ForumCategory e) {
        super(e);
        this.name = e.name;
        this.desc =  e.desc;
        this.postCount =  e.postCount;
        this.type =  e.type;
        this.accessControl =  e.accessControl;
        this.parentId =  e.parentId;
        this.icon = e.icon;
//        this.parent =  e.parent;
//        this.children =  e.children;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(String accessControl) {
        this.accessControl = accessControl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<ForumCategory> getChildren() {
        if(this.children==null){
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setChildren(List<ForumCategory> children) {
        this.children = children;
    }
}

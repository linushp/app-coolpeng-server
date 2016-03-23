package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.VOTemp;

import java.util.List;

public class ForumGroup extends BlogBaseEntity {
    public static SimpleDAO<ForumGroup> DAO = new SimpleDAO(ForumGroup.class);
    private String groupName;
    private String groupDesc;

    @VOTemp
    private List<ForumModule> moduleList;

    public ForumGroup(ForumGroup group) {
        super(group);
        this.groupName = group.groupName;
        this.groupDesc = group.groupDesc;
        this.moduleList = group.moduleList;
    }

    public ForumGroup() {
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return this.groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public List<ForumModule> getModuleList() {
        return this.moduleList;
    }

    public void setModuleList(List<ForumModule> moduleList) {
        this.moduleList = moduleList;
    }
}
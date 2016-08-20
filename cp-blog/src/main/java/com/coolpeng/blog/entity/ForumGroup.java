package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.VOTemp;

import java.util.List;

public class ForumGroup extends BlogBaseEntity {
    public static SimpleDAO<ForumGroup> DAO = new SimpleDAO(ForumGroup.class);
    private String groupName;
    private String groupDesc;

    //ALTER TABLE `coolpeng`.`t_forum_group`
    //ADD COLUMN `access_control` VARCHAR(45) NULL DEFAULT 'public' AFTER `status`;
    private String accessControl; //public private


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

    public String getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(String accessControl) {
        this.accessControl = accessControl;
    }
}
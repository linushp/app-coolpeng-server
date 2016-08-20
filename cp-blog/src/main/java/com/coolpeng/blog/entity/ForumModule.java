package com.coolpeng.blog.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.VOTemp;

public class ForumModule extends BlogBaseEntity {
    public static final SimpleDAO<ForumModule> DAO = new SimpleDAO(ForumModule.class);
    private String moduleName;
    private String moduleDesc;
    private String moduleIcon;

    //板块类型：论坛（1），问答（2），博客类型（3），留言板（4）
    private int moduleType = 3;

    //无限层级
    private String forumGroupId;
    private int postCount = 0;

//    ALTER TABLE `coolpeng`.`t_forum_module`
//    ADD COLUMN `accessControl` VARCHAR(45) NULL DEFAULT 'public' AFTER `status`;
    private String accessControl; //public private

    @VOTemp
    private ForumGroup forumGroup;

    public ForumModule(ForumModule m) {
        super(m);
        this.moduleName = m.moduleName;
        this.moduleDesc = m.moduleDesc;
        this.moduleIcon = m.moduleIcon;
        this.moduleType = m.moduleType;
        this.forumGroupId = m.forumGroupId;
        this.postCount = m.postCount;
        this.forumGroup = m.forumGroup;
    }

    public ForumModule() {
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getPostCount() {
        return this.postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getModuleDesc() {
        return this.moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getModuleIcon() {
        return this.moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public int getModuleType() {
        return this.moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public String getForumGroupId() {
        return this.forumGroupId;
    }

    public void setForumGroupId(String forumGroupId) {
        this.forumGroupId = forumGroupId;
    }

    public ForumGroup getForumGroup() {
        return this.forumGroup;
    }

    public void setForumGroup(ForumGroup forumGroup) {
        this.forumGroup = forumGroup;
    }

    public String getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(String accessControl) {
        this.accessControl = accessControl;
    }
}
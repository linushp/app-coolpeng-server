package com.coolpeng.cloud.note.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luanhaipeng on 16/8/17.
 */
public class CategoryVO {

    private String id;
    private String type;
    private String name;
    private String desc;
    private String level;

    private String parentId = null;
    private String parentLevel = null;

    private List<CategoryVO> children = new ArrayList<>();

    public CategoryVO(String id, String type, String name, String desc) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.desc = desc;
    }

    public CategoryVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<CategoryVO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryVO> children) {
        this.children = children;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(String parentLevel) {
        this.parentLevel = parentLevel;
    }
}

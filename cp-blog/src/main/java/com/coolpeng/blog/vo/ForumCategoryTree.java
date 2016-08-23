package com.coolpeng.blog.vo;

import com.coolpeng.blog.entity.ForumCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/8/23.
 */
public class ForumCategoryTree {

    private List<ForumCategory> rootNodeList;

    private List<ForumCategory> originNodeList;

    private Map<String,ForumCategory> map;


    public ForumCategoryTree(List<ForumCategory> rootNodeList, List<ForumCategory> originNodeList,Map<String,ForumCategory> map) {
        this.rootNodeList = rootNodeList;
        this.originNodeList = originNodeList;
        this.map = map;
    }


    public ForumCategory getParent(ForumCategory s){
        String parentId = s.getParentId();
        return map.get(parentId);
    }



    public Map<String, ForumCategory> getMap() {
        return map;
    }

    public void setMap(Map<String, ForumCategory> map) {
        this.map = map;
    }

    public List<ForumCategory> getRootNodeList() {
        return rootNodeList;
    }

    public void setRootNodeList(List<ForumCategory> rootNodeList) {
        this.rootNodeList = rootNodeList;
    }

    public List<ForumCategory> getOriginNodeList() {
        return originNodeList;
    }

    public void setOriginNodeList(List<ForumCategory> originNodeList) {
        this.originNodeList = originNodeList;
    }
}

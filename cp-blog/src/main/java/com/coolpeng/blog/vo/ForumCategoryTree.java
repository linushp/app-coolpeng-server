package com.coolpeng.blog.vo;

import com.coolpeng.blog.entity.ForumCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/8/23.
 */
public class ForumCategoryTree {

    private List<ForumCategory> treeNodeList;

    private List<ForumCategory> originNodeList;

    private Map<String,ForumCategory> map;


    public ForumCategoryTree(List<ForumCategory> rootNodeList, List<ForumCategory> originNodeList,Map<String,ForumCategory> map) {
        this.treeNodeList = rootNodeList;
        this.originNodeList = originNodeList;
        this.map = map;
    }


    public ForumCategory getParent(ForumCategory s){
        String parentId = s.getParentId();
        return map.get(parentId);
    }


    public List<ForumCategory> getByIdWidthParents(String id){
        List<ForumCategory> result = new ArrayList<>();
        ForumCategory m = this.getById(id);
        result.add(m);
        result.addAll(this.getParentList(m));
        return result;
    }


    public ForumCategory getById(String id){
        return map.get(id);
    }


    public List<ForumCategory> getParentList(ForumCategory s){
        List<ForumCategory> result = new ArrayList<>();
        String parentId = s.getParentId();
        ForumCategory m = map.get(parentId);
        while (m!=null){
            result.add(m);
            if (m.getParentId()!=null){
                m = map.get(m.getParentId());
            }else {
                m = null;
            }
        }
        return result;
    }




    public Map<String, ForumCategory> getMap() {
        return map;
    }

    public void setMap(Map<String, ForumCategory> map) {
        this.map = map;
    }

    public List<ForumCategory> getTreeNodeList() {
        return treeNodeList;
    }

    public void setTreeNodeList(List<ForumCategory> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }

    public List<ForumCategory> getOriginNodeList() {
        return originNodeList;
    }

    public void setOriginNodeList(List<ForumCategory> originNodeList) {
        this.originNodeList = originNodeList;
    }
}

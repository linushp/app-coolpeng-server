package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.ForumCategory;
import com.coolpeng.blog.entity.enums.AccessControl;
import com.coolpeng.blog.vo.ForumCategoryTree;
import com.coolpeng.framework.db.QueryCondition;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/8/23.
 */
@Service
public class ForumCategoryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ForumCategoryTree PUBLIC_FORUM_CATEGORY_CACHE = null;
    private static long LAST_UPDATE_TIME = System.currentTimeMillis();


    public ForumCategoryTree getPublicForumCategory() throws FieldNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        long nowTime = System.currentTimeMillis();
        if (PUBLIC_FORUM_CATEGORY_CACHE == null || (nowTime - LAST_UPDATE_TIME > 1000 * 60 * 5)) {
            Map<String, Object> params = new HashMap<>();
            params.put("accessControl", AccessControl.PUBLIC.getValue());
            List<ForumCategory> list = ForumCategory.DAO.queryForList(params);
            PUBLIC_FORUM_CATEGORY_CACHE = buildTree(list);
            LAST_UPDATE_TIME = nowTime;
        }
        return PUBLIC_FORUM_CATEGORY_CACHE;
    }



    public ForumCategoryTree getForumCategoryByCreateUserId(String userId) throws FieldNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String,Object> params = new HashMap<>();
        params.put("createUserId", userId);
        List<ForumCategory> list = ForumCategory.DAO.queryForList(params);
        return buildTree(list);
    }


    public ForumCategory saveOrUpdate(ForumCategory entity) throws UpdateErrorException {
        ForumCategory.DAO.insertOrUpdate(entity);
        return entity;
    }


    public ForumCategory incrementPostCount(ForumCategory entity) throws UpdateErrorException {
        int count = entity.getPostCount();
        entity.setPostCount(count++);
        ForumCategory.DAO.insertOrUpdate(entity);
        return entity;
    }


    public int deleteEntity(String entityId) throws UpdateErrorException {
        return ForumCategory.DAO.deleteById(entityId);
    }




    private ForumCategoryTree buildTree(List<ForumCategory> list) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String,ForumCategory> map = CollectionUtil.toMap(list,"id");

        List<ForumCategory> rootNodeList = new ArrayList<>();

        for (ForumCategory o : list) {

            String id = o.getId();
            String pid = o.getParentId();

            ForumCategory node = map.get(id);
            ForumCategory parentNode = map.get(pid);

            if (id != null && id.equals(pid)) {
                rootNodeList.add(node); //LR算法可能有个bug自己指向自己
                logger.error("构建节点树出现问题， node id={}", id);
            } else if (parentNode == null) {
                rootNodeList.add(node);//没有父节点
            } else {
                parentNode.getChildren().add(node);
            }
        }

        return new ForumCategoryTree(rootNodeList,list,map);
    }


}

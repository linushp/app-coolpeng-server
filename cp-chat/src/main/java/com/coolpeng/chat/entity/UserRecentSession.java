package com.coolpeng.chat.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class UserRecentSession extends BlogBaseEntity {

    public static final SimpleDAO<UserRecentSession> DAO = new SimpleDAO(UserRecentSession.class);

    private String ownerUid;

    @FieldDef(jsonColumn = {List.class, String.class})
    private List<String> recentIdList = new ArrayList<>();


    public UserRecentSession(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public UserRecentSession() {
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public List<String> getRecentIdList() {
        if (recentIdList==null){
            recentIdList = new ArrayList<>();
        }
        return recentIdList;
    }

    public void setRecentIdList(List<String> recentIdList) {
        this.recentIdList = recentIdList;
    }
}

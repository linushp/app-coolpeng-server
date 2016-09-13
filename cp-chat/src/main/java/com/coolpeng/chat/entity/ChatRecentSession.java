package com.coolpeng.chat.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class ChatRecentSession extends BlogBaseEntity {

    public static final SimpleDAO<ChatRecentSession> DAO = new SimpleDAO(ChatRecentSession.class);

    private String ownerUid;

    @FieldDef(jsonColumn = {List.class, String.class})
    private List<String> recentIdList = new ArrayList<>();


    public ChatRecentSession(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public ChatRecentSession() {
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public List<String> getRecentIdList() {
        if (recentIdList == null) {
            recentIdList = new ArrayList<>();
        }
        return recentIdList;
    }

    public void setRecentIdList(List<String> recentIdList) {
        this.recentIdList = recentIdList;
    }
}

package com.coolpeng.chat.entity;

import com.coolpeng.blog.entity.base.BlogBaseEntity;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.framework.db.BaseEntity;
import com.coolpeng.framework.db.SimpleDAO;
import com.coolpeng.framework.db.annotation.FieldDef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 *
 CREATE TABLE `t_chat_recent_session` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `create_time` varchar(256) DEFAULT NULL,
 `update_time` varchar(256) DEFAULT NULL,
 `create_user_id` varchar(256) DEFAULT NULL,
 `update_user_id` varchar(256) DEFAULT NULL,
 `status` int(11) DEFAULT '0',

 `owner_uid` varchar(256) DEFAULT NULL,
 `recent_session_ids` varchar(2000) DEFAULT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

 */
public class ChatRecentSession extends BaseEntity {

    public static final SimpleDAO<ChatRecentSession> DAO = new SimpleDAO(ChatRecentSession.class);

    private String ownerUid;

    @FieldDef(jsonColumn = {List.class, ChatSessionVO.class})
    private List<ChatSessionVO> recentSessions = new ArrayList<>();


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

    public List<ChatSessionVO> getRecentSessions() {
        if (recentSessions==null){
            recentSessions = new ArrayList<>();
        }
        return recentSessions;
    }

    public void setRecentSessions(List<ChatSessionVO> recentSessions) {
        this.recentSessions = recentSessions;
    }
}

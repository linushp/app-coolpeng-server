package com.coolpeng.chat.entity;

import com.coolpeng.framework.db.BaseEntity;
import com.coolpeng.framework.db.SimpleDAO;

/**

 CREATE TABLE `t_chat_peer_session` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `create_time` varchar(256) DEFAULT NULL,
 `update_time` varchar(256) DEFAULT NULL,
 `create_user_id` varchar(256) DEFAULT NULL,
 `update_user_id` varchar(256) DEFAULT NULL,
 `status` int(11) DEFAULT '0',

 `peer1_uid` varchar(256) DEFAULT NULL,
 `peer1_avatar` varchar(256) DEFAULT NULL,
 `peer1_username` varchar(256) DEFAULT NULL,
 `peer1_nickname` varchar(256) DEFAULT NULL,

 `peer2_uid` varchar(256) DEFAULT NULL,
 `peer2_avatar` varchar(256) DEFAULT NULL,
 `peer2_username` varchar(256) DEFAULT NULL,
 `peer2_nickname` varchar(256) DEFAULT NULL,

 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;




 */
public class ChatPeerSession extends BaseEntity {

    public static final SimpleDAO<ChatPeerSession> DAO = new SimpleDAO(ChatPeerSession.class);

    private String sessionTitle;

    //只有p2p聊天才用到这两个字段
    private String peer1Uid;
    private String peer1Avatar;
    private String peer1Username;
    private String peer1Nickname;

    private String peer2Uid;
    private String peer2Avatar;
    private String peer2Username;
    private String peer2Nickname;


    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }
    public String getSessionId() {
        return "peer_" + this.getId();
    }

    public String getPeer1Uid() {
        return peer1Uid;
    }

    public void setPeer1Uid(String peer1Uid) {
        this.peer1Uid = peer1Uid;
    }

    public String getPeer1Avatar() {
        return peer1Avatar;
    }

    public void setPeer1Avatar(String peer1Avatar) {
        this.peer1Avatar = peer1Avatar;
    }

    public String getPeer1Username() {
        return peer1Username;
    }

    public void setPeer1Username(String peer1Username) {
        this.peer1Username = peer1Username;
    }

    public String getPeer1Nickname() {
        return peer1Nickname;
    }

    public void setPeer1Nickname(String peer1Nickname) {
        this.peer1Nickname = peer1Nickname;
    }

    public String getPeer2Uid() {
        return peer2Uid;
    }

    public void setPeer2Uid(String peer2Uid) {
        this.peer2Uid = peer2Uid;
    }

    public String getPeer2Avatar() {
        return peer2Avatar;
    }

    public void setPeer2Avatar(String peer2Avatar) {
        this.peer2Avatar = peer2Avatar;
    }

    public String getPeer2Username() {
        return peer2Username;
    }

    public void setPeer2Username(String peer2Username) {
        this.peer2Username = peer2Username;
    }

    public String getPeer2Nickname() {
        return peer2Nickname;
    }

    public void setPeer2Nickname(String peer2Nickname) {
        this.peer2Nickname = peer2Nickname;
    }
}
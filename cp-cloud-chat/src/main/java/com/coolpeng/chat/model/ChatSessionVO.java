package com.coolpeng.chat.model;

import com.coolpeng.chat.utils.ChatConstant;
import com.coolpeng.framework.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class ChatSessionVO {

    public static final String TYPE_PUBLIC = "public";
    public static final String TYPE_ROBOT = "robot";
    public static final String TYPE_PEER = "peer";

    private String entityId;
    private String sessionType; //
    private String sessionId; // "peer_1", "public_1" 即 type + entityId
    private String sessionTitle;
    private String sessionIcon = ChatConstant.DEFAULT_SESSION_ICON;

    private String lastMsgText;
    private long lastMsgTimeMillis = System.currentTimeMillis();
    private String lastMsgUsername;
    private String lastMsgNickname;
    private String lastMsgAvatar;
    private String lastMsgUid;

    private List<String> participateUidList;

    public ChatSessionVO() {
    }

    public ChatSessionVO(String sessionType, String entityId, String sessionTitle) {
        this.sessionType = sessionType;
        this.entityId = entityId;
        this.sessionId = sessionType + "_" + entityId;
        this.sessionTitle = sessionTitle;
        this.lastMsgTimeMillis = System.currentTimeMillis();
    }

    public ChatSessionVO(String sessionType, String entityId, String sessionTitle,String sessionIcon) {
        this(sessionType, entityId, sessionTitle);
        this.sessionIcon = sessionIcon;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<String> getParticipateUidList() {
        return participateUidList;
    }

    public void setParticipateUidList(List<String> participateUidList) {
        this.participateUidList = participateUidList;
    }

    public String getSessionIcon() {
        if (StringUtils.isBlank(this.sessionIcon)) {
            this.sessionIcon = ChatConstant.DEFAULT_SESSION_ICON;
        }
        return sessionIcon;
    }

    public void setSessionIcon(String sessionIcon) {
        this.sessionIcon = sessionIcon;
    }

    public String getLastMsgUid() {
        return lastMsgUid;
    }

    public void setLastMsgUid(String lastMsgUid) {
        this.lastMsgUid = lastMsgUid;
    }

    public String getLastMsgText() {
        return lastMsgText;
    }

    public void setLastMsgText(String lastMsgText) {
        this.lastMsgText = lastMsgText;
    }

    public long getLastMsgTimeMillis() {
        return lastMsgTimeMillis;
    }

    public void setLastMsgTimeMillis(long lastMsgTimeMillis) {
        this.lastMsgTimeMillis = lastMsgTimeMillis;
    }

    public String getLastMsgUsername() {
        return lastMsgUsername;
    }

    public void setLastMsgUsername(String lastMsgUsername) {
        this.lastMsgUsername = lastMsgUsername;
    }

    public String getLastMsgNickname() {
        return lastMsgNickname;
    }

    public void setLastMsgNickname(String lastMsgNickname) {
        this.lastMsgNickname = lastMsgNickname;
    }

    public String getLastMsgAvatar() {
        return lastMsgAvatar;
    }

    public void setLastMsgAvatar(String lastMsgAvatar) {
        this.lastMsgAvatar = lastMsgAvatar;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatSessionVO sessionVO = (ChatSessionVO) o;

        if (entityId != null ? !entityId.equals(sessionVO.entityId) : sessionVO.entityId != null) return false;
        return !(sessionType != null ? !sessionType.equals(sessionVO.sessionType) : sessionVO.sessionType != null);

    }

    @Override
    public int hashCode() {
        int result = entityId != null ? entityId.hashCode() : 0;
        result = 31 * result + (sessionType != null ? sessionType.hashCode() : 0);
        return result;
    }
}

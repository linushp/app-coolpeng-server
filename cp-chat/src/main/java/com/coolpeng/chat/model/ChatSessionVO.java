package com.coolpeng.chat.model;

/**
 * Created by Administrator on 2016/9/10.
 */
public class ChatSessionVO {

    public static final int TYPE_PUBLIC = 1;
    public static final int TYPE_P2P = 2;

    private int type; //  1 public , 2 p2p
    private String sessionId; // public :sessionId < 10000
    private String name;

    public ChatSessionVO() {
    }

    public ChatSessionVO(int type, String sessionId, String name) {
        this.type = type;
        this.sessionId = sessionId;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

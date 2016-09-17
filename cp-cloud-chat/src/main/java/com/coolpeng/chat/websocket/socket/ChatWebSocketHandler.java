package com.coolpeng.chat.websocket.socket;

import com.coolpeng.chat.websocket.WebsocketContainer;
import com.coolpeng.chat.websocket.event.init.EventListenerInit;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/cloud/chat.websocket")
public class ChatWebSocketHandler {

    static {
        EventListenerInit.init();
    }

    @OnOpen
    public void onOpen(Session session) throws Exception {
        WebsocketContainer.onOpen(session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        WebsocketContainer.onClose(session);
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
    }


    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

}
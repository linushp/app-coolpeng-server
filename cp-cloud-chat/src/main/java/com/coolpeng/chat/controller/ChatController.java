package com.coolpeng.chat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coolpeng.appbase.RestBaseController;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.model.ChatUserVO;
import com.coolpeng.chat.service.PeerChatService;
import com.coolpeng.chat.service.PublicChatService;
import com.coolpeng.chat.service.RecentSessionService;
import com.coolpeng.chat.service.api.IChatMsgService;
import com.coolpeng.chat.websocket.WebsocketContainer;
import com.coolpeng.chat.websocket.event.CreateSessionEvent;
import com.coolpeng.chat.websocket.event.init.EventListenerInit;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventBus;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.mvc.TMSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
@Controller
@RequestMapping(value = "/cloud/chat", produces = "application/json; charset=UTF-8")
public class ChatController extends RestBaseController {

    @Autowired
    private PublicChatService publicChatService;
    @Autowired
    private PeerChatService peerChatService;
    @Autowired
    private RecentSessionService recentSessionService;


    @ResponseBody
    @RequestMapping({"/getAllOnlineUserVO"})
    public TMSResponse getAllOnlineUserVO(@RequestBody JSONObject jsonObject) throws Exception {
        UserEntity user = assertIsUserLoginIfToken(jsonObject);
        Collection<ChatUserVO> userVOList = WebsocketContainer.getAllOnlineUserVO();
        return TMSResponse.success(userVOList);
    }


    @ResponseBody
    @RequestMapping({"/getSessionList"})
    public TMSResponse getSessionList(@RequestBody JSONObject jsonObject) throws Exception {
        UserEntity user = assertIsUserLoginIfToken(jsonObject);
        List<ChatSessionVO> sessionVOList = recentSessionService.getRecentChatSessionVOList(user);
        return TMSResponse.success(sessionVOList);
    }


    @ResponseBody
    @RequestMapping({"/createSession"})
    public TMSResponse createSession(@RequestBody JSONObject jsonObject) throws Exception {

        ChatSessionVO sessionVO = jsonObject.getObject("sessionVO", ChatSessionVO.class);

        /**********************************/
        UserEntity user = assertIsUserLoginIfToken(jsonObject);
        IChatMsgService chatMsgService = getChatMsgService(sessionVO.getSessionType());
        CreateSessionEvent event  = chatMsgService.createSession(user, sessionVO);
        TMSEventBus.sendEvent(event);
        return TMSResponse.success(event.getChatSessionVO());
    }


    @ResponseBody
    @RequestMapping({"/sendMessage"})
    public TMSResponse sendMessage(@RequestBody JSONObject jsonObject) throws Exception {

        ChatSessionVO sessionVO = jsonObject.getObject("sessionVO", ChatSessionVO.class);
        String msg = jsonObject.getString("msg");
        String msgSummary = jsonObject.getString("msgSummary");
        boolean refreshRecent = jsonObject.getBoolean("refreshRecent");

        /**********************/
        UserEntity user = assertIsUserLoginIfToken(jsonObject);
        if (refreshRecent) {
            recentSessionService.saveRecentSession(sessionVO, user,msg,msgSummary);
        }

        IChatMsgService chatMsgService = getChatMsgService(sessionVO.getSessionType());
        TMSEvent event = chatMsgService.saveMessage(user, msg, sessionVO);
        TMSEventBus.sendEvent(event);
        return TMSResponse.success().addExtendData("sessionVO",sessionVO);
    }

    @ResponseBody
    @RequestMapping({"/getChatMsgList"})
    public TMSResponse getChatMsgList(@RequestBody JSONObject jsonObject) throws Exception {
        ChatSessionVO sessionVO = jsonObject.getObject("sessionVO", ChatSessionVO.class);
        /**********************/
        IChatMsgService chatMsgService = getChatMsgService(sessionVO.getSessionType());
        List<ChatMsgVO> msgList = chatMsgService.getChatMsgList(sessionVO);
        return TMSResponse.success(msgList).addExtendData("sessionVO",sessionVO);
    }



    private IChatMsgService getChatMsgService(String sessionType) throws Exception {
        if (ChatSessionVO.TYPE_PUBLIC.equals(sessionType)) {
            return publicChatService;
        } else if (ChatSessionVO.TYPE_PEER.equals(sessionType)) {
            return peerChatService;
        } else {
            throw new TMSMsgException("sessionType error : " + sessionType);
        }
    }


    private List<String> toStringList(JSONArray participateUid) throws TMSMsgException {
        if (participateUid == null) {
            throw new TMSMsgException("participateUid is null");
        }
        String json = JSON.toJSONString(participateUid);
        return JSON.parseArray(json, String.class);
    }

}

package com.coolpeng.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.appbase.RestBaseController;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.chat.model.ChatMsgVO;
import com.coolpeng.chat.model.ChatSessionVO;
import com.coolpeng.chat.service.PublicChatService;
import com.coolpeng.chat.service.RecentSessionService;
import com.coolpeng.chat.websocket.event.PublicMsgEvent;
import com.coolpeng.framework.cache.CacheManager;
import com.coolpeng.framework.cache.ICache;
import com.coolpeng.framework.event.TMSEvent;
import com.coolpeng.framework.event.TMSEventBus;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
@Controller
@RequestMapping(value = "/chat", produces = "application/json; charset=UTF-8")
public class ChatController extends RestBaseController{

    @Autowired
    private PublicChatService publicChatService;

    @Autowired
    private RecentSessionService recentSessionService;

    @ResponseBody
    @RequestMapping({"/getSessionList"})
    public TMSResponse getSessionList(@RequestBody JSONObject jsonObject) throws TMSMsgException, FieldNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        UserEntity user = assertIsUserLoginIfToken(jsonObject);
        List<ChatSessionVO> sessionVOList = new ArrayList<>();
        sessionVOList.add(new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC,"1","公共频道"));
        sessionVOList.add(new ChatSessionVO(ChatSessionVO.TYPE_PUBLIC,"2","技术灌水"));
        sessionVOList = recentSessionService.sortSessionByRecent(sessionVOList,user);
        return TMSResponse.success(sessionVOList);
    }


    @ResponseBody
    @RequestMapping({"/sendPublicMessage"})
    public TMSResponse sendPublicMessage(@RequestBody JSONObject jsonObject) throws TMSMsgException, FieldNotFoundException, UpdateErrorException {
        String msg = jsonObject.getString("msg");
        String sessionId = jsonObject.getString("sessionId");
        boolean refreshRecent = jsonObject.getBoolean("refreshRecent");


        /**********************/
        UserEntity user = assertIsUserLoginIfToken(jsonObject);

        ChatMsgVO chatMsgVO = publicChatService.sendPublicMessage(user, msg, sessionId);
        if(refreshRecent){
            recentSessionService.saveRecentSession(sessionId, user);
        }
        TMSEventBus.sendEvent(new PublicMsgEvent(chatMsgVO));
        return TMSResponse.success();
    }

    @ResponseBody
    @RequestMapping({"/getPublicChatMsgList"})
    public TMSResponse getPublicChatMsgList(@RequestBody JSONObject jsonObject) throws TMSMsgException {
        String sessionId = jsonObject.getString("sessionId");

        /**********************/
        LinkedList<ChatMsgVO> msgList = publicChatService.getPublicChatMsgList(sessionId);
        return TMSResponse.success(msgList);
    }


}

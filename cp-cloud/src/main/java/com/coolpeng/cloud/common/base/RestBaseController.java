package com.coolpeng.cloud.common.base;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.service.UserService;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.utils.ServiceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 栾海鹏 on 2016/3/19.
 * Rest请求的鉴权不仅仅是使用Session,主要是使用tokenId,
 * Session过期了，只要tokenId还存在，则扔认为用户在线。
 */
public class RestBaseController {

    public void assertTimeRestriction(Class clazz, String funcName) throws TMSMsgException {
        assertTimeRestriction(clazz, funcName, "");
    }

    public void assertTimeRestriction(Class clazz, String funcName, String itemId) throws TMSMsgException {
        assertTimeRestriction(clazz, funcName, itemId, 1000 * 30);
    }

    public void assertTimeRestriction(Class clazz, String funcName, String itemId, long timeLimit) throws TMSMsgException {

        //对admin用户法外开恩
        UserEntity user = (UserEntity) TmsCurrentRequest.getCurrentUser();
        if(user!=null && user.isAdmin()){
            return;
        }


        String key = "assertTimeRestriction_" + clazz.getSimpleName() + "_" + funcName + "_" + itemId;
        Long lastTime = (Long) this.getSessionAttribute(key);
        if (lastTime != null) {
            if (System.currentTimeMillis() - lastTime < timeLimit) {
                throw new TMSMsgException("操作太频繁，请休息一下吧");
            }
        }
        this.setSessionAttribute(key, System.currentTimeMillis());
    }


    public UserEntity assertSessionLoginUser(JSONObject jsonObject) throws TMSMsgException {
        UserEntity user = (UserEntity) TmsCurrentRequest.getCurrentUser();

        //Session没有登录,尝试使用tokenid登录
        if (user == null) {
            ReqParams reqUser = ReqParams.parse(jsonObject);
            UserService userService = (UserService) ServiceUtils.getBean("userService");
            try {
                user = userService.getUserEntityByTokenId(reqUser.getTokenId(), reqUser.getDevicePlatform(), reqUser.getUuid());
            } catch (FieldNotFoundException e) {
                e.printStackTrace();
                throw new TMSMsgException("用户查询失败");
            }
            if (user != null) {
                setSessionLoginUser(user);
            }
        }

        if (user == null) {
            throw new TMSMsgException("用户没有登录,请先登录");
        }

        return user;
    }


    /**
     * @param jsonObject
     * @return
     */
    public UserEntity getCurrentUser(JSONObject jsonObject) {
        try {
            return assertSessionLoginUser(jsonObject);
        } catch (TMSMsgException e) {
            return null;
        }
    }


    public UserEntity assertIsAdmin(JSONObject jsonObject) throws TMSMsgException {
        UserEntity user = assertSessionLoginUser(jsonObject);
        if (!user.isAdmin()) {
            throw new TMSMsgException("您不是管理员");
        }
        return user;
    }

    public HttpServletRequest getHttpServletRequest() {
        return TmsCurrentRequest.getHttpServletRequest();
    }

    public HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    public void setSessionLoginUser(UserEntity user) {
        TmsCurrentRequest.setCurrentUser(user);
    }

    public Object getSessionAttribute(String attr) {
        return getHttpSession().getAttribute(attr);
    }

    public void setSessionAttribute(String attr, Object value) {
        getHttpSession().setAttribute(attr, value);
    }

}

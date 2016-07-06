package com.coolpeng.app.base;

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
 */
public class AppBaseController {

    public UserEntity assertSessionLoginUser(JSONObject jsonObject) throws TMSMsgException, FieldNotFoundException {
        UserEntity user = getSessionLoginUser();

        //Session没有登录,尝试使用tokenid登录
        if (user == null) {
            ReqParams reqUser = ReqParams.parse(jsonObject);
            UserService userService = (UserService) ServiceUtils.getBean("userService");
            user = userService.getUserEntityByTokenId(reqUser.getTokenId(), reqUser.getDevicePlatform(), reqUser.getUuid());
            if (user != null) {
                setSessionLoginUser(user);
            }
        }

        if (user == null) {
            throw new TMSMsgException("用户没有登录,请先登录");
        }

        return user;
    }

    public HttpServletRequest getHttpServletRequest() {
        return TmsCurrentRequest.getHttpServletRequest();
    }

    public HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }


    public UserEntity getSessionLoginUser() {
        return (UserEntity) TmsCurrentRequest.getCurrentUser();
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

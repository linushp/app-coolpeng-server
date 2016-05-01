package com.coolpeng.blog.controller;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.blog.service.UserService;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.mvc.TmsUserEntity;
import com.coolpeng.framework.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/blog/user/"}, produces = {"application/json; charset=UTF-8"})
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/login"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView loginPage() {
        return new ModelAndView("user/jsp/login");
    }

    @RequestMapping(value = {"/login/submit"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String loginSubmit(String username, String password, HttpServletRequest request) {
        TMSResponse result = this.userService.doValidate(username, password);
        if (result.getResponseCode() == 0) {
            TmsCurrentRequest.setCurrentUser((TmsUserEntity) result.getData());
            return "redirect:/forum/moduleList.shtml";
        }

        request.setAttribute("error", Boolean.valueOf(true));
        return "user/jsp/login";
    }

    @ResponseBody
    @RequestMapping({"rest/register"})
    public TMSResponse doRegister(String username, String password) {
        Map params = new HashMap();

        params.put("username", username);
        try {
            List list = UserEntity.DAO.queryForList(params);

            if (!CollectionUtil.isEmpty(list)) {
                return TMSResponse.error(1, "此用户名已经存在");
            }
            UserEntity entity = new UserEntity();
            entity.setPassword(password);
            entity.setUsername(username);
            UserEntity.DAO.save(entity);
            return TMSResponse.success();
        } catch (FieldNotFoundException e) {
            TMSResponse.error(2, "系统异常，字段不存在：" + e.getMessage());
        }

        return TMSResponse.error(3, "未知异常");
    }

    @ResponseBody
    @RequestMapping({"rest/login"})
    public TMSResponse doLogin(String username, String password, HttpServletRequest request) {
        TMSResponse result = this.userService.doValidate(username, password);
        if (result.getResponseCode() == 0) {

            //用户的唯一标记
            TmsCurrentRequest.setCurrentUser((TmsUserEntity) result.getData());
        }

        return result;
    }


    @ResponseBody
    @RequestMapping({"rest/logout"})
    public TMSResponse doLogout(HttpServletRequest request) {
        TmsCurrentRequest.setCurrentUser(null);
        return new TMSResponse();
    }
}
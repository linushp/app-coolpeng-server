package com.coolpeng.app.controller;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.framework.db.EntityStatusEnum;
import com.coolpeng.blog.service.UserService;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created by 栾海鹏 on 2016/3/18.
 */
@Controller
@RequestMapping(value = "/app/user/", produces = "application/json; charset=UTF-8")
public class AppUserController extends AppBaseController {

    @Autowired
    private UserService userService;



    @ResponseBody
    @RequestMapping("/login")
    public TMSResponse login(String username, String password) throws TMSMsgException, UpdateErrorException, FieldNotFoundException {

        TMSMsgException e = new TMSMsgException("用户名或密码错误");
        UserEntity user = userService.getUserEntity(username);
        if (user == null) {
            throw e;
        }

        if (user.getStatus() != EntityStatusEnum.OK) {
            throw e;
        }

        if (user.getPassword() == null) {
            throw e;
        }

        if (!user.getPassword().equals(password)) {
            throw e;
        }


        UUID uuid = UUID.randomUUID();
        String tokenId = uuid.toString();
        user.setLastLoginToken(tokenId);
        user.setLastLoginTime(DateUtil.currentTimeFormat());
        user.setLastLoginDevPlatform(getHttpServletRequest().getHeader("tmsApp.device.platform"));
        user.setLastLoginDevUid(getHttpServletRequest().getHeader("tmsApp.device.uuid"));
        userService.saveAndFlush(user);


        setSessionLoginUser(user);

        user = new UserEntity(user);
        user.setPassword(null);//隐藏敏感信息
        return TMSResponse.success(user);
    }



    @ResponseBody
    @RequestMapping("/logout")
    public TMSResponse logout(String tokenId) throws TMSMsgException, UpdateErrorException, FieldNotFoundException {

        UserEntity user = userService.getUserEntityByTokenId(tokenId);
        if (user != null) {
            user.setLastLoginToken(null);
            userService.saveAndFlush(user);
        }

        setSessionLoginUser(null);
        return TMSResponse.success();
    }


    @ResponseBody
    @RequestMapping("/register")
    public TMSResponse register(String username, String password) throws TMSMsgException, FieldNotFoundException, UpdateErrorException {

        if (StringUtils.isBlank(username)) {
            throw new TMSMsgException("用户名不能为空");
        }

        if (StringUtils.isBlank(password)) {
            throw new TMSMsgException("密码不能为空");
        }


        UserEntity user = userService.getUserEntity(username);
        if (user != null) {
            throw new TMSMsgException("此用户名已经被注册过了");
        }


        UserEntity newUser = new UserEntity();
        newUser.setPassword(password);
        newUser.setUsername(username);
        userService.saveAndFlush(newUser);

        return new TMSResponse();
    }


    @ResponseBody
    @RequestMapping("/getCurrentUserInfo")
    public TMSResponse<UserEntity> getCurrentUserInfo() throws TMSMsgException, FieldNotFoundException {
        UserEntity user = assertSessionLoginUser();
        user = new UserEntity(user);
        user.setPassword(null);//隐藏敏感信息
        return TMSResponse.success(user);
    }


}

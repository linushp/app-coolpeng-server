package com.coolpeng.cloud.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.cloud.common.base.RestBaseController;
import com.coolpeng.cloud.common.base.ReqParams;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Created by 栾海鹏 on 2016/3/18.
 */
@Controller
@RequestMapping(value = "/cloud/user/", produces = "application/json; charset=UTF-8")
public class AppUserController extends RestBaseController {

    @Autowired
    private UserService userService;


    /**
     * 登录
     * @return
     * @throws TMSMsgException
     * @throws UpdateErrorException
     * @throws FieldNotFoundException
     */
    @ResponseBody
    @RequestMapping("/login")
    public TMSResponse login(@RequestBody JSONObject jsonObject) throws TMSMsgException, UpdateErrorException, FieldNotFoundException {
        ReqParams reqParams = ReqParams.parse(jsonObject);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        /****************************************************************/

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
        user.setLastLoginDevPlatform(reqParams.getDevicePlatform());
        user.setLastLoginDevUid(reqParams.getUuid());
        userService.saveAndFlush(user);


        setSessionLoginUser(user);

        user = new UserEntity(user);
        user.setPassword(null);//隐藏敏感信息
        return TMSResponse.success(user);
    }


    /**
     * 退出
     * @return
     * @throws TMSMsgException
     * @throws UpdateErrorException
     * @throws FieldNotFoundException
     */
    @ResponseBody
    @RequestMapping("/logout")
    public TMSResponse logout(@RequestBody JSONObject jsonObject) throws TMSMsgException, UpdateErrorException, FieldNotFoundException {
        String tokenId = jsonObject.getString("tokenId");
        /****************************************************************/


        UserEntity user = userService.getUserEntityByTokenId(tokenId);
        if (user != null) {
            user.setLastLoginToken(null);
            userService.saveAndFlush(user);
        }

        setSessionLoginUser(null);
        return TMSResponse.success();
    }


    /**
     * 注册
     * @return
     * @throws TMSMsgException
     * @throws FieldNotFoundException
     * @throws UpdateErrorException
     */
    @ResponseBody
    @RequestMapping("/register")
    public TMSResponse register(@RequestBody JSONObject jsonObject) throws TMSMsgException, FieldNotFoundException, UpdateErrorException {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        /****************************************************************/

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


    /**
     * 获取用户信息
     * @return
     * @throws TMSMsgException
     * @throws FieldNotFoundException
     */
    @ResponseBody
    @RequestMapping("/getCurrentUserInfo")
    public TMSResponse<UserEntity> getCurrentUserInfo(@RequestBody JSONObject jsonObject) throws TMSMsgException, FieldNotFoundException {
        UserEntity user = assertSessionLoginUser(jsonObject);
        user = new UserEntity(user);
        user.setPassword(null);//隐藏敏感信息
        return TMSResponse.success(user);
    }


}

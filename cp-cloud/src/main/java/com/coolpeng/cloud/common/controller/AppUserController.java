package com.coolpeng.cloud.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.appbase.RestBaseController;
import com.coolpeng.appbase.ReqParams;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.cloud.common.event.UserInfoUpdateEvent;
import com.coolpeng.framework.db.EntityStatusEnum;
import com.coolpeng.blog.service.UserService;
import com.coolpeng.framework.event.TMSEventBus;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.ParameterErrorException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.utils.*;
import com.coolpeng.framework.utils.ipaddr.IPAddrCallback;
import com.coolpeng.framework.utils.ipaddr.IPAddrParse;
import com.coolpeng.framework.utils.ipaddr.IPAddrResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 栾海鹏 on 2016/3/18.
 */
@Controller
@RequestMapping(value = "/cloud/user/", produces = "application/json; charset=UTF-8")
public class AppUserController extends RestBaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


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
        String password = jsonObject.getString("password");//md51
        /****************************************************************/

        TMSMsgException e = new TMSMsgException("用户名或密码错误");
        final UserEntity user = userService.getUserEntityByUserName(username);

        if (user == null) {
            throw e;
        }

        if (user.getStatus() != EntityStatusEnum.OK) {
            throw e;
        }

        if (user.getPassword() == null) {
            throw e;
        }


        String md52Password = UbibiPasswordUtils.md51ToMd52(password);

        if (!user.getPassword().equals(md52Password)) {
            throw e;
        }


        String ipAddr = TmsCurrentRequest.getClientIpAddr();

        UUID uuid = UUID.randomUUID();
        String tokenId = uuid.toString();
        user.setLastLoginToken(tokenId);
        user.setLastLoginTime(DateUtil.currentTimeFormat());
        user.setLastLoginDevPlatform(reqParams.getDevicePlatform());
        user.setLastLoginDevUid(reqParams.getUuid());
        user.setLastLoginIpAddr(ipAddr);
        user.setLoginCount(user.getLoginCount()+1);
        userService.saveAndFlush(user);


        setSessionLoginUser(user);


        //3、IP地址解析
        IPAddrParse.parseIpAddr(ipAddr, new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult) {
                if (ipAddrResult.isOk()) {
                    user.setLastLoginIpStr(ipAddrResult.toDisplayString());
                    try {
                        UserEntity.DAO.insertOrUpdate(user);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
        });


        UserEntity userReturn = new UserEntity(user);
        userReturn.setPassword(null);//隐藏敏感信息

        return TMSResponse.success(userReturn);
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

        ReqParams reqParams = ReqParams.parse(jsonObject);
        String tokenId = reqParams.getTokenId();
        /****************************************************************/


        UserEntity user = userService.getUserEntityByTokenId(tokenId);
        if (user != null) {
            user.setLastLoginToken(null);
            userService.saveAndFlush(user);
        }

        setSessionLoginUser(null);
        TmsCurrentRequest.clearSession();
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
        String password = jsonObject.getString("password");//MD51
        String nickname = jsonObject.getString("nickname");
        String mail = jsonObject.getString("mail");
        String avatar = jsonObject.getString("avatar");
        String username = mail;//使用邮箱作为用户名
        /****************************************************************/

        if (StringUtils.isBlank(mail)) {
            throw new TMSMsgException("邮箱不能为空", "mail_empty");
        }

        if (!StringUtils.checkEmail(mail)){
            throw new TMSMsgException("邮箱格式输入不正确", "mail_format_error");
        }

        UserEntity user = userService.getUserEntityByUserNameOrEmail(mail);
        if (user != null) {
            throw new TMSMsgException("此邮箱已经被注册过了", "mail_used");
        }

        if (StringUtils.isBlank(password)) {
            throw new TMSMsgException("密码不能为空", "password_empty");
        }

//        if (!password.equals(password2)){
//            throw new TMSMsgException("两次密码输入的不一致", "password_not_equal");
//        }

        if (StringUtils.isBlank(nickname)) {
            throw new TMSMsgException("昵称不能为空", "nickname_empty");
        }


        String md52Password = UbibiPasswordUtils.md51ToMd52(password);

        UserEntity newUser = new UserEntity();
        newUser.setPassword(md52Password);
        newUser.setUsername(username);
        newUser.setNickname(nickname);
        newUser.setMail(mail);
        newUser.setPermission("");
        newUser.setAvatar(avatar);
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
    public TMSResponse<UserEntity> getCurrentUserInfo(@RequestBody JSONObject jsonObject) throws Exception {
        UserEntity user = assertIsUserLoginIfToken(jsonObject);
        user = new UserEntity(user);
        user.setPassword(null);//隐藏敏感信息


        UserEntity user0 = UserEntity.DAO.queryById(user.getId());
        int viewCount = user0.getViewCount() + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("viewCount", viewCount);
        UserEntity.DAO.updateFields(user.getId(), map);


        user.setViewCount(viewCount);
        return TMSResponse.success(user);
    }


    /**
     * 获取用户信息
     * @return
     * @throws TMSMsgException
     * @throws FieldNotFoundException
     */
    @ResponseBody
    @RequestMapping("/getUserInfoByUid")
    public TMSResponse<UserEntity> getUserInfoByUid(@RequestBody JSONObject jsonObject) throws Exception {

        String uid = jsonObject.getString("uid");

        /****************************/
        assertIsUserLoginIfToken(jsonObject);

        UserEntity user0 = UserEntity.DAO.queryById(uid);
        if (user0!=null){
            //隐藏敏感信息
            user0.hideSecretInfo();
        }
        return TMSResponse.success(user0);
    }




    /**
     * 修改用户信息
     * @return
     * @throws TMSMsgException
     * @throws FieldNotFoundException
     */
    @ResponseBody
    @RequestMapping("/updateUserInfo")
    public TMSResponse<UserEntity> updateUserInfo(@RequestBody JSONObject jsonObject) throws TMSMsgException, FieldNotFoundException, UpdateErrorException, ParameterErrorException {

        String id = jsonObject.getString("id");
        Map<String,Object> map = jsonObject.getObject("UserEntity", Map.class);

        //暂时只允许修改昵称，头像，邮箱
        map = CollectionUtil.pickupMapAttr(map,new String[]{"nickname","avatar","mail"});

        /***************************/
        UserEntity user = assertIsUserLoginIfToken(jsonObject);
        if (!user.isAdmin() && !user.getId().equals(id)){
            //只有Admin和用户本人可以修改
            throw new TMSMsgException("您无权修改");
        }

        boolean isUserSelf = false;
        if (user.getId().equals(id)){
            isUserSelf = true;
        }


        UserEntity.DAO.updateFields(id,map);

        user = UserEntity.DAO.queryById(id);


        //员工本人的修改，刷新下当前Session
        if(isUserSelf){
            setSessionLoginUser(user);
        }

        TMSEventBus.asynSendEvent(new UserInfoUpdateEvent(user));


        if (user!=null){
            //隐藏敏感信息
            user.hideSecretInfo();
        }
        return TMSResponse.success(user);
    }


}

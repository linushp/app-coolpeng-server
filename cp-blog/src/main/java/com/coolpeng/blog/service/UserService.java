package com.coolpeng.blog.service;

import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.utils.CollectionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {


    public TMSResponse<UserEntity> doValidate(String username, String password) {
        if (StringUtils.isBlank(username)) {
            return TMSResponse.error(5, "用户名不能为空");
        }

        if (StringUtils.isBlank(password)) {
            return TMSResponse.error(6, "密码不能为空");
        }

        Map params = new HashMap();

        params.put("username", username);
        try {
            List list = UserEntity.DAO.queryForList(params);

            if (CollectionUtil.isEmpty(list)) {
                return TMSResponse.error(1, "此用户名不存在");
            }

            UserEntity user = (UserEntity) list.get(0);
            if (password.equals(user.getPassword())) {
                user.setPassword(null);
                return TMSResponse.success(user);
            }
            return TMSResponse.error(4, "密码错误");
        } catch (FieldNotFoundException e) {
            TMSResponse.error(2, "系统异常，字段不存在：" + e.getMessage());
        }

        return TMSResponse.error(3, "未知异常");
    }

    public UserEntity getUserEntityByTokenId(String tokenId) throws FieldNotFoundException {

        if (tokenId == null) {
            return null;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("lastLoginToken", tokenId);
        List<UserEntity> userList = UserEntity.DAO.queryForList(params);
        if (CollectionUtil.isEmpty(userList)) {
            return null;
        }

        for (UserEntity userEntity : userList) {
            if (tokenId.equals(userEntity.getLastLoginToken())){
                return userEntity;
            }
        }

        return null;
    }

    public UserEntity getUserEntityByTokenId(String tokenId, String devicePlatform, String deviceUuid) throws FieldNotFoundException {

        if (devicePlatform == null || deviceUuid == null || tokenId == null) {
            return null;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("lastLoginToken", tokenId);
        List<UserEntity> userList = UserEntity.DAO.queryForList(params);
        if (CollectionUtil.isEmpty(userList)) {
            return null;
        }

        for (UserEntity userEntity : userList) {
            if (devicePlatform.equals(userEntity.getLastLoginDevPlatform()) && deviceUuid.equals(userEntity.getLastLoginDevUid())) {
                return userEntity;
            }
        }

        return null;
    }


    public UserEntity getUserEntity(String username) throws FieldNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        return UserEntity.DAO.queryForObject(params);
    }


    public void saveAndFlush(UserEntity user) throws UpdateErrorException {
        UserEntity.DAO.insertOrUpdate(user);
    }
}
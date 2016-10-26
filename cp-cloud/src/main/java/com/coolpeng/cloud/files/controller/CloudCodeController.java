package com.coolpeng.cloud.files.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.appbase.RestBaseController;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.cloud.files.entity.CloudCode;
import com.coolpeng.framework.mvc.TMSResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/cloud/filesCode", produces = "application/json; charset=UTF-8")
public class CloudCodeController extends RestBaseController {


    @ResponseBody
    @RequestMapping({"/saveCloudCode"})
    public TMSResponse saveCloudCode(@RequestBody JSONObject jsonObject) throws Exception {

        CloudCode cloudCode = jsonObject.getObject("CloudCode", CloudCode.class);
        /**************************************************/

        UserEntity currentUser = assertIsUserLoginIfToken(jsonObject);

        cloudCode.setCreateUserId(currentUser.getId());
        cloudCode.setUpdateUserId(currentUser.getId());

        CloudCode.DAO.insert(cloudCode);

        return TMSResponse.success(cloudCode.getId());
    }


    @ResponseBody
    @RequestMapping({"/getCloudCodeById"})
    public TMSResponse getCloudCodeById(@RequestBody JSONObject jsonObject) throws Exception {
        String id = jsonObject.getString("id");
        /**************************************************/

        assertIsUserLoginIfToken(jsonObject);

        CloudCode entity = CloudCode.DAO.queryById(id);

        return TMSResponse.success(entity);
    }


}


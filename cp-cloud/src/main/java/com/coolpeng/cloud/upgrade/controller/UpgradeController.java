package com.coolpeng.cloud.upgrade.controller;

import com.alibaba.fastjson.JSONObject;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.framework.exception.FieldNotFoundException;
import com.coolpeng.framework.exception.TMSMsgException;
import com.coolpeng.framework.exception.UpdateErrorException;
import com.coolpeng.framework.mvc.TMSResponse;
import com.coolpeng.framework.utils.StringUtils;
import com.coolpeng.framework.utils.UbibiPasswordUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luanhaipeng on 16/10/26.
 */

@Controller
@RequestMapping(value = "/cloud/upgrade/", produces = "application/json; charset=UTF-8")
public class UpgradeController {

    private static boolean isDidUpgradePassword = false;

    @ResponseBody
    @RequestMapping("/upgradePassword")
    public TMSResponse upgradePassword() throws TMSMsgException, FieldNotFoundException, UpdateErrorException {

        if (isDidUpgradePassword){
            return TMSResponse.success("already upgrade");
        }




        return new TMSResponse();
    }


}

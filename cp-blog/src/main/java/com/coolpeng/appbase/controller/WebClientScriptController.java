package com.coolpeng.appbase.controller;

import com.alibaba.fastjson.JSON;
import com.coolpeng.appbase.StaticConfigManager;
import com.coolpeng.appbase.controller.vo.WebClientInfoVO;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by luanhaipeng on 16/9/20.
 */
@Controller
@RequestMapping(value = "/appBase/web-client-script/", produces = "application/json; charset=UTF-8")
public class WebClientScriptController {


    @ResponseBody
    @RequestMapping("/WebStaticInfo")
    public String getWebStaticInfo(HttpServletRequest request, @RequestParam(required = false, defaultValue = "", value = "name") String name) {
        WebClientInfoVO vo = new WebClientInfoVO();
        vo.setClientIpAddress(getRemoteAddress(request));
        vo.setConfig(StaticConfigManager.getMap());
        String json = JSON.toJSONString(vo);
        String scriptName = "UBIBI_WebStaticInfo";
        if (StringUtils.isNotBlank(name)) {
            scriptName = name;
        }
        String script = "var  " + scriptName + " = " + json + ";";
        return script;
    }

    private String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}

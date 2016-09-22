package com.coolpeng.cloud.common.controller;

import com.alibaba.fastjson.JSON;
import com.coolpeng.cloud.common.vo.WebClientInfoVO;
import com.coolpeng.framework.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by luanhaipeng on 16/9/20.
 */
@Controller
@RequestMapping(value = "/cloud/web-client-script/", produces = "application/json; charset=UTF-8")
public class WebClientScriptController {


    @ResponseBody
    @RequestMapping("/WebClientInfo")
    public String getWebClientInfo(HttpServletRequest request, @RequestParam(required = false, defaultValue = "", value = "name") String name) {
        WebClientInfoVO vo = new WebClientInfoVO();
        vo.setClientIpAddress(getRemoteAddress(request));
        String json = JSON.toJSONString(vo);
        String scriptName = "UBIBI_WebClientInfo";
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

package com.coolpeng.framework.mvc.jsptag;

import com.alibaba.fastjson.JSON;
import com.coolpeng.framework.mvc.TmsCurrentRequest;
import com.coolpeng.framework.mvc.TmsUserEntity;
import com.coolpeng.framework.utils.CollectionUtil;
import com.coolpeng.framework.utils.DateUtil;
import com.coolpeng.framework.utils.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

public class TmsFunctions {
    public static String prettyDate(String str) {
        return DateUtil.toPrettyString(str);
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    public static Integer randomInt() {
        Random m = new Random();
        return Integer.valueOf(m.nextInt());
    }

    public static Boolean isAdmin() {
        return Boolean.valueOf(TmsCurrentRequest.isAdmin());
    }

    public static Boolean isLogin() {
        return Boolean.valueOf(TmsCurrentRequest.isLogin());
    }

    public static Boolean isNotEmpty(Object object) {
        return Boolean.valueOf(!isEmpty(object).booleanValue());
    }


    public static Boolean isEmpty(Object object) {
        if (object == null) {
            return Boolean.valueOf(true);
        }

        if ((object instanceof String)) {
            String str = (String) object;
            return Boolean.valueOf(StringUtils.isBlank(str));
        }

        if ((object instanceof Collection)) {
            Collection collection = (Collection) object;
            return Boolean.valueOf(CollectionUtil.isEmpty(collection));
        }

        if ((object instanceof Map)) {
            Map map = (Map) object;
            return Boolean.valueOf(CollectionUtil.isEmpty(map));
        }

        return Boolean.valueOf(false);
    }

    public static Boolean isSizeEqual(Object object, Integer size) {
        if ((object == null) && (size.intValue() == 0)) {
            return Boolean.valueOf(true);
        }

        if (object == null) {
            return Boolean.valueOf(false);
        }

        if ((object instanceof Collection)) {
            Collection cc = (Collection) object;
            if (cc.size() == size.intValue()) {
                return Boolean.valueOf(true);
            }
        }

        if ((object instanceof Map)) {
            Map cc = (Map) object;
            if (cc.size() == size.intValue()) {
                return Boolean.valueOf(true);
            }
        }

        if ((object instanceof String)) {
            String cc = (String) object;
            if (cc.length() == size.intValue()) {
                return Boolean.valueOf(true);
            }
        }

        return Boolean.valueOf(false);
    }

    public static String toFullUrl(String u) {
        if (u == null) {
            return "";
        }
        if (!u.startsWith("http")) {
            return TmsCurrentRequest.getContext() + u;
        }
        return u;
    }

    public static String getGlobalConstScript() {
        return TmsScriptUtil.getGlobalConstScript(null);
    }


    public static TmsUserEntity getCurrentUser(){
        TmsUserEntity user = TmsCurrentRequest.getCurrentUser();
        if (user==null){
            return new TmsUserEntity();
        }
        return user;
    }



    private static final String [] supporyImageHostName = {
            "http://coolpeng.bj.bcebos.com",//http://image.coolpeng.cn
            "http://ubibi.coolpeng.cn",
            "http://image.coolpeng.cn"
    };


    private static boolean isImageUploadSupport(String url) {
        if (url == null) {
            return false;
        }
        for (int i = 0; i < supporyImageHostName.length; i++) {
            String hostName = supporyImageHostName[i];
            if (url.startsWith(hostName)) {
                return true;
            }
        }
        return false;
    }

    public static String toImageThumb(String url, Integer width, Integer height) {
        try {
            //http://image.coolpeng.cn/${img}@s_0,w_80,q_90,f_png
            if (url == null) {
                return "";
            }

            String suffix = "@s_0,w_" + width + ",q_" + height + ",f_png";
            if (url.startsWith("http://")) {
                if (isImageUploadSupport(url)) {
                    if (url.startsWith(supporyImageHostName[0])) {
                        url = url.replace(supporyImageHostName[0], "http://image.coolpeng.cn");
                    }
                    return url + suffix;

                }
                return url;
            }

            return "http://image.coolpeng.cn/" + url + "@s_0,w_" + width + ",q_" + height + ",f_png";
        }catch (Throwable e){
            e.printStackTrace();
        }

        return url;
    }
}
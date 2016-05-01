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
}
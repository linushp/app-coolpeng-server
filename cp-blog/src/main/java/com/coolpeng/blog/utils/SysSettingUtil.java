package com.coolpeng.blog.utils;

import com.alibaba.fastjson.JSON;
import com.coolpeng.blog.entity.SysSetting;
import com.coolpeng.framework.exception.FieldNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/30.
 */
public class SysSettingUtil {

    public static final <T> T getSysSetting(Class<T> clazz) {
        return getSysSetting(clazz.getName(), clazz);
    }

    public static final <T> void saveOrUpdate(T object) throws FieldNotFoundException {
        saveOrUpdate(object.getClass().getName(), object);
    }

    private static final <T> T getSysSetting(String name, Class<T> clazz) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("name", name);
            SysSetting setting = SysSetting.DAO.queryByAndParams(params);
            if (setting != null) {
                String content = setting.getContent();
                T vo = JSON.parseObject(content, clazz);
                return vo;
            }
        } catch (FieldNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final <T> void saveOrUpdate(String name, T object) throws FieldNotFoundException {
        String content = JSON.toJSONString(object);
        SysSetting newSetting = new SysSetting();
        newSetting.setName(name);
        newSetting.setContent(content);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);

        SysSetting.DAO.deleteAndInsert(newSetting, params);
    }


}

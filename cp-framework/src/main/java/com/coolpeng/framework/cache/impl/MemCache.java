package com.coolpeng.framework.cache.impl;

import com.coolpeng.framework.cache.ICache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/10.
 */
public class MemCache implements ICache{

    private Map<String,Object> map = new HashMap<>();

    @Override
    public Object getData(String key) {
        return map.get(key);
    }

    @Override
    public void putData(String key, Object data) {
        map.put(key,data);
    }
}
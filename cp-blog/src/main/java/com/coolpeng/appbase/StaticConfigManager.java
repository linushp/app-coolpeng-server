package com.coolpeng.appbase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/10/10.
 */
public class StaticConfigManager {
    private static final Map<String,Object> map = new HashMap<>();
    public static Map<String,Object> getMap(){
        return map;
    }
    public static void pushInfo(String key,Object value){
        map.put(key,value);
    }
}

package com.coolpeng.framework.cache;

import com.coolpeng.framework.cache.impl.MemCache;

/**
 * Created by Administrator on 2016/9/10.
 */
public class CacheManager {

    private static MemCache memCache = new MemCache();

    public static ICache getCache(){
        return memCache;
    }
}

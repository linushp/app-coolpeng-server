package com.coolpeng.framework.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/10.
 */
public interface ICache {

    Object getData(String key);

    void putData(String key,Object data);

}

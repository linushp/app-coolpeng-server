package com.coolpeng.framework.utils.ipaddr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coolpeng.framework.qtask.QueueTask;
import com.coolpeng.framework.qtask.QueueTaskRunner;
import com.coolpeng.framework.utils.RESTUtils;
import com.coolpeng.framework.utils.StringUtils;
import com.coolpeng.framework.utils.data.MaxSizedMap;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by luanhaipeng on 16/8/1.
 */
public class IPAddrParse {

    private static final QueueTaskRunner queueTaskRunner = new QueueTaskRunner();

    private static final Map<String, IPAddrResult> cacheMap = new MaxSizedMap(100);

    public static void parseIpAddr(final String ipAddr, final IPAddrCallback ipAddrCallback) {

        //加了一个小缓存
        IPAddrResult result = cacheMap.get(ipAddr);
        if (result != null) {
            ipAddrCallback.onResult(result);
            return;
        }


        queueTaskRunner.addTask(new QueueTask() {
            @Override
            public void runTask() {

                if (StringUtils.isBlank(ipAddr)) {
                    ipAddrCallback.onResult(new IPAddrResult(false, null));
                } else {

                    try {
                        Thread.sleep(5000);

                        String s = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ipAddr + "&ddwwerfsdfds=" + System.currentTimeMillis();
                        String resultStr = null;
                        try {
                            resultStr = RESTUtils.get(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        JSONObject jsonObject = JSON.parseObject(resultStr);

                        JSONObject data = jsonObject.getJSONObject("data");
                        IPAddrResult ipAddrResult = new IPAddrResult(data, resultStr);
                        ipAddrCallback.onResult(ipAddrResult);


                        //加了一个小缓存
                        cacheMap.put(ipAddr, ipAddrResult);

                    } catch (Throwable e) {
                        ipAddrCallback.onResult(new IPAddrResult(false, e.toString()));
                    }

                }

            }
        });
    }



}

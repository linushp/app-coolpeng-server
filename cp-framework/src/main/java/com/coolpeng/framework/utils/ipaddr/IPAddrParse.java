package com.coolpeng.framework.utils.ipaddr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coolpeng.framework.qtask.QueueTask;
import com.coolpeng.framework.qtask.QueueTaskRunner;
import com.coolpeng.framework.utils.RESTUtils;
import com.coolpeng.framework.utils.StringUtils;

import java.io.IOException;

/**
 * Created by luanhaipeng on 16/8/1.
 */
public class IPAddrParse {

    private static QueueTaskRunner queueTaskRunner = new QueueTaskRunner();

    public static void parseIpAddr(final String ipAddr,final IPAddrCallback ipAddrCallback){

        queueTaskRunner.addTask(new QueueTask() {
            @Override
            public void runTask() {

                if (StringUtils.isBlank(ipAddr)){
                    ipAddrCallback.onResult(new IPAddrResult(false), null);
                }
                else {

                    try {
                        Thread.sleep(5000);

                        String s = "http://ip.taobao.com/service/getIpInfo.php?ip="+ipAddr+"&ddwwerfsdfds=" + System.currentTimeMillis();
                        String resultStr = null;
                        try {
                            resultStr = RESTUtils.get(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        JSONObject jsonObject = JSON.parseObject(resultStr);

                        JSONObject data = jsonObject.getJSONObject("data");

                        ipAddrCallback.onResult(new IPAddrResult(data), resultStr);

                    }catch (Throwable e){

                        ipAddrCallback.onResult(new IPAddrResult(false), e.toString());

                    }

                }

            }
        });
    }

}

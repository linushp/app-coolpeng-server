package com.coolpeng.test;

import com.coolpeng.framework.qtask.QueueTask;
import com.coolpeng.framework.qtask.QueueTaskRunner;
import com.coolpeng.framework.utils.RESTUtils;
import com.coolpeng.framework.utils.ipaddr.IPAddrCallback;
import com.coolpeng.framework.utils.ipaddr.IPAddrParse;
import com.coolpeng.framework.utils.ipaddr.IPAddrResult;

import java.io.IOException;

/**
 * Created by luanhaipeng on 16/8/1.
 */
public class GetIpAddrInfo {


    public static void main(String [] args){

        IPAddrParse.parseIpAddr("60.191.97.50", new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult, String resultStr) {
                System.out.println("111111");
                System.out.println(ipAddrResult);
            }
        });


        IPAddrParse.parseIpAddr("60.191.97.50", new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult, String resultStr) {
                System.out.println("22222");
                System.out.println(ipAddrResult);
            }
        });


        IPAddrParse.parseIpAddr("60.191.97.50", new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult, String resultStr) {
                System.out.println("33333");
                System.out.println(ipAddrResult);
            }
        });


        IPAddrParse.parseIpAddr("60.191.97.50", new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult, String resultStr) {
                System.out.println("44444");
                System.out.println(ipAddrResult);
            }
        });


        IPAddrParse.parseIpAddr("60.191.97.50", new IPAddrCallback() {
            @Override
            public void onResult(IPAddrResult ipAddrResult, String resultStr) {
                System.out.println("55555");
                System.out.println(ipAddrResult);
            }
        });


    }

}

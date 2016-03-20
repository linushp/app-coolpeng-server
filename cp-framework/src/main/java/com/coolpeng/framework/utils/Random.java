package com.coolpeng.framework.utils;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

/**
 * Created by Yan on 2015/8/15.
 */
public class Random {
    public static String random6Bite(){
        long Temp;
        Temp = Math.round(Math.random()*899999+100000);
        return String.valueOf(Temp);
    }
}

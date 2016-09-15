package com.coolpeng.chat.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/9/15.
 */
public class UniqueId {

    private static final long START_TIME_MILLIS = System.currentTimeMillis();

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static String getOne() {
        int num = counter.incrementAndGet();
        return "" + START_TIME_MILLIS + "-" + num;
    }
}

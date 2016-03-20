package com.coolpeng.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final long minute = 60000L;
    private static final long hour = 3600000L;
    private static final long day = 86400000L;
    private static final long month = 2678400000L;
    private static final long year = 32140800000L;
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date d){
        return DATE_FORMAT.format(d);
    }

    public static String currentTimeFormat() {
        return DATE_FORMAT.format(new Date());
    }

    public static String toPrettyString(String date) {
        Date d;
        try {
            d = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return date;
        }
        return toPrettyString(d);
    }

    public static String toPrettyString(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0L;
        if (diff > 32140800000L) {
            r = diff / 32140800000L;
            return r + "年前";
        }
        if (diff > 2678400000L) {
            r = diff / 2678400000L;
            return r + "个月前";
        }
        if (diff > 86400000L) {
            r = diff / 86400000L;
            return r + "天前";
        }
        if (diff > 3600000L) {
            r = diff / 3600000L;
            return r + "个小时前";
        }
        if (diff > 60000L) {
            r = diff / 60000L;
            return r + "分钟前";
        }
        return "刚刚";
    }
}
package com.coolpeng.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

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

        if (StringUtils.isBlank(date)) {
            return "";
        }

        Date d;
        try {
            d = DATE_FORMAT.parse(date);
        } catch (Throwable e) {
            logger.error(e.getMessage() + ", and the date is " + date);
            return "";
        }

        return toPrettyString(d);
    }

    public static String toPrettyString(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0L;
        if (diff > year) {
            r = diff / year;
            return r + "年前";
        }
        if (diff > month) {
            r = diff / month;
            return r + "个月前";
        }
        if (diff > day) {
            r = diff / day;
            return r + "天前";
        }
        if (diff > hour) {
            r = diff / hour;
            return r + "个小时前";
        }
        if (diff > minute) {
            r = diff / minute;
            return r + "分钟前";
        }
        return "刚刚";
    }
}
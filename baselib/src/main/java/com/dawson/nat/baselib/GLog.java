package com.dawson.nat.baselib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 带时间戳的打印日志
 * @author dawson
 */
public class GLog {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private static volatile Date date = new Date();

    public static void println(String msg) {
        date.setTime(System.currentTimeMillis());
        System.out.println(format.format(date) + ": " + msg);
    }
}

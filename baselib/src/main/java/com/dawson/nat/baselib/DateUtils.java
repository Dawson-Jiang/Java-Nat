package com.dawson.nat.baselib;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    /**
     * 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
     */
    public static final String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 年-月-日 显示格式
     */
    public static final String DATE_TO_STRING_Y_M_D_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TO_STRING_H_M_S_PATTERN = "HH:mm:ss";
    public static final String DATE_TO_STRING_M_D_CHINESE = "MM月dd日";


    /**
     * 设置系统时区
     *
     * @param timeZone
     */
    public static void setTimeZone(String timeZone) {
        final Calendar now = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone(timeZone);
        now.setTimeZone(tz);
    }

    /**
     * 获取系统当前的时区
     *
     * @return
     */
    public static String getDefaultTimeZone() {
        return TimeZone.getDefault().getDisplayName();
    }

    private static SimpleDateFormat simpleDateFormat;

    /**
     * Date类型转为指定格式的String类型
     *
     * @param source
     * @param pattern
     * @return
     */
    public static String DateToString(Date source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    /**
     * yyyy-MM-dd 格式的天数递加
     *
     * @param date 开始时间
     * @param days
     * @return
     */
    public static String addDateShort(Date date, int days) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (days > 0) {
            c.add(Calendar.DAY_OF_MONTH, days);
        }
        return sf.format(c.getTime());
    }

    public static Date addHour(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (hours > 0) {
            c.add(Calendar.HOUR_OF_DAY, hours);
        }
        return c.getTime();
    }

    public static Date rollHour(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.roll(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    public static Date addMinute(Date date, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (minute > 0) {
            c.add(Calendar.MINUTE, minute);
        }
        return c.getTime();
    }

    /**
     * yyyyMMdd 格式的天数递加
     *
     * @param date 开始时间
     * @param days
     * @return
     */
    public static String addDateShortFormat(Date date, int days, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (days > 0) {
            c.add(Calendar.DAY_OF_MONTH, days);
        }
        return sf.format(c.getTime());
    }

    public static String rollDateShort(Date date, int days) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.roll(Calendar.DAY_OF_MONTH, days);
        return sf.format(c.getTime());
    }

    public static Date rollDate(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.roll(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * long时间戳转格式化字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String longToFormatString(long time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date(time));
    }

    public static String dateToFormatString(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 将时间字符串转为long
     *
     * @param time 时间
     * @return
     */
    public static long getLongByFormat(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = null;
            date = simpleDateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取 xx:xx:xx格式的时间
     *
     * @param time
     * @return
     */
    public static String secToStingTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                //+ ":" + unitFormat(second);
                timeStr = unitFormat(hour) + ":" + unitFormat(minute);
            }
        }
        return timeStr;
    }

    /**
     * 获取 xx:xx:xx格式的时间
     *
     * @param time
     * @return
     */
    public static String secToStingTimeUP(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                if (second > 0) {
                    minute = minute + 1;
                }
                if (minute == 60) {
                    minute = 0;
                    timeStr = "01:" + unitFormat(minute);
                } else {
                    timeStr = "00:" + unitFormat(minute);
                }
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                if (second > 0) {
                    minute = minute + 1;
                }
                if (minute == 60) {
                    minute = 0;
                    hour = hour + 1;
                    if (hour > 99) {
                        return "99:59";
                    }
                }
                timeStr = unitFormat(hour) + ":" + unitFormat(minute);
            }
        }
        return timeStr;
    }

    /**
     * 单位时间格式化
     *
     * @param i
     * @return
     */
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int hoursBetween(Date smdate, Date bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenHours = (time2 - time1) / (1000 * 3600);

        return Integer.parseInt(String.valueOf(betweenHours));
    }


    public static String getCurrentTime() {
        return formatDate(new Date(), DATE_TO_STRING_DETAIAL_PATTERN);
    }

    public static String getCurrentTime(String format) {
        return formatDate(new Date(), format);
    }

     public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getWeekDay(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static String getWeekEnDay(Date date) {
        String[] weekDays = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }
}

package com.dpad.telematicsclientapp.util.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @创建者 booobdai.
 * @创建时间 2017/9/19  13:43.
 * @描述 ${时间转换工具类}.
 */
public class DateUtil {

    /**
     * 根据时间格式返回特定时间
     *
     * @param timeInMillis
     * @param format
     * @return
     */
    public static String getDateString(long timeInMillis, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 根据格式 获取当前时间
     *
     * @param format
     * @return
     */
    public static String getCurrentDateString(String format) {
        return getDateString(System.currentTimeMillis(), format);
    }

    /**
     * 根据格式化的时间字符串 返回时间毫秒
     *
     * @param time
     * @param format
     * @return
     */
    public static long getTimeMillis(String time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前时间是星期几
     *
     * @param timeInMillis
     * @return 当前日期是星期几
     */
    public static String getWeekString(long timeInMillis) {
        Date date = new Date(timeInMillis);
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 以友好的方式显示时间
     *
     * @param timeInMillis
     * @return
     */
    public static String getFriendlyTime(long timeInMillis) {
        Date date = new Date(timeInMillis);
        if (date == null) {
            return "Unknown";
        }
        String friednlyTime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = getCurrentDateString("yyyy-MM-dd");
        String paramDate = getDateString(timeInMillis, "yyyy-MM-dd");
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0) {
                friednlyTime = Math.max((cal.getTimeInMillis() - date.getTime()) / 60000, 1) + "分钟前";
            } else {
                friednlyTime = hour + "小时前";
            }
            return friednlyTime;
        }

        long lt = date.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0) {
                friednlyTime = Math.max(
                        (cal.getTimeInMillis() - date.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                friednlyTime = hour + "小时前";
            }
        } else if (days == 1) {
            friednlyTime = "昨天";
        } else if (days == 2) {
            friednlyTime = "前天 ";
        } else if (days > 2 && days < 31) {
            friednlyTime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            friednlyTime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            friednlyTime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            friednlyTime = "3个月前";
        } else {
            friednlyTime = getDateString(timeInMillis, "yyyy-MM-dd HH:mm");
        }
        return friednlyTime;
    }

//    /**
//     * 计算第二个时间距离第一个时间的天数
//     *
//     * @param startTime
//     * @param finishTime
//     * @return
//     */
//    public static int calculateDays(long startTime, long finishTime) {
//        long l = finishTime - startTime;
//        return (int) (l / (1000 * 60 * 60 * 24));
//    }

    public static long formatTime(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long time = 0;
        try {
            Date date1 = sdf.parse(s);
            time = date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long formatUserInfoTime(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        long time = 0;
        try {
            Date date1 = sdf.parse(s);
            time = date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * @param strDate
     * @param pattern
     * @return
     * @Title: string2Date
     * @Description: 字符串格式的时间转化成日期格式的时间
     * @author YFB
     */
    public static Date string2Date(String strDate, String pattern) {
        if (strDate == null || strDate.equals("")) {
            throw new RuntimeException("strDate is null");
        }
        pattern = (pattern == null || pattern.equals("")) ? "yyyy-MM-dd" : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * @param date
     * @param pattern
     * @return
     * @Title: date2String
     * @Description: 日期格式的时间转化成字符串格式的时间
     * @author YFB
     */
    public static String date2String(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals("")) ? "yyyy-MM-dd" : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();//将毫秒转换为秒
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 个人中心
     * 获取首页时间
     *
     * @return
     */
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 6) {
            return "凌晨好";
        } else if (hour < 12) {
            return "上午好";

        } else if (hour < 14) {
            return "中午好";

        } else if (hour < 18) {
            return "下午好";
        } else {
            return "晚上好";
        }
    }


    public static Boolean isNight() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour < 6 || hour > 17);
    }


    /**
     * 获取月末最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getDateLastDay(String year, String month) {

        //year="2018" month="2"
        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month));

        System.out.println(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day + "".length() < 2) {
            return "0" + day;
        } else {
            return day + "";
        }
    }

}

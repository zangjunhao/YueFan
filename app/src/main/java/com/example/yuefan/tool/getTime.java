package com.example.yuefan.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 67698 on 2018/8/24.
 */

public class getTime {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @param
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            return dateString ;
        }

        if (diff > day && diff <year) {
            r = (diff / day);
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
            String dateString = formatter.format(date);
            return dateString ;
        }
        if (diff > hour && diff < day) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute && diff < hour) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }
}

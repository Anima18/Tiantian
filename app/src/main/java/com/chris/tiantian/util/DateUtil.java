package com.chris.tiantian.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date getDate(int year, int month, int dayOfMonth,int hourOfDay,int minute,int second){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    public static String getTime(Date date,String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String formatDate(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String formatDate(String dateStr, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);

        try {
            Date date = df.parse(dateStr);
            return df.format(date);
        } catch (ParseException var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public static String formatDate(int year, int month, int day, String format) {
        String dateStr = String.format("%d-%d-%d", year, month + 1, day);
        return formatDate(dateStr, format);
    }

    public static String formatDate(Calendar calendar, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }

    public static String formatDate(long time) {
        DateFormat df = new SimpleDateFormat(DATETIME_FORMAT);
        return df.format(time);
    }

    public static String getTodayTime() {
        return formatDate(Calendar.getInstance(), DATETIME_FORMAT);
    }

    public static Date getTodayBegin(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDay(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取过去第几月的日期
     *
     * @param past
     * @return
     */
    public static Date getPastMonth(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - past);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}

package org.qvit.hybrid.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final String TIME_PATTERN = "HHmmss";
    private static final String DATETIME_PATTERN = "yyyyMMddHHmmss";

    public static String getFormatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String getFormatDate(Date date) {
        return getFormatDate(date, DATETIME_PATTERN);
    }

    public static String getCurrentFormatDate(String pattern) {
        return getFormatDate(new Date(), pattern);
    }

    public static String getCurrentDateStr() {
        return getCurrentFormatDate(DATE_PATTERN);
    }

    public static String getCurrentTimeStr() {
        return getCurrentFormatDate(TIME_PATTERN);
    }

    public static String getCurrentDateTimeStr() {
        return getCurrentFormatDate(DATETIME_PATTERN);
    }

    public static String getDateStr(Date date) {
        return getFormatDate(date, DATE_PATTERN);
    }

    public static String getTimeStr(Date date) {
        return getFormatDate(date, TIME_PATTERN);
    }

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("date:" + date + ",pattern:" + pattern, e);
        }
    }

    public static Date parseDate(String date) {
        return parseDate(date, DATETIME_PATTERN);
    }

    public static String getCnStr(String dateTimeStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(dateTimeStr));
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        int day = cal.get(5);
        int hour = cal.get(11);
        int minute = cal.get(12);
        int second = cal.get(13);
        return year + " 年 " + month + " 月 " + day + " 日 " + hour + " : " + minute + " : " + second;
    }
}
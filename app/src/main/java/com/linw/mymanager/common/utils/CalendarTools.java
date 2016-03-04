package com.linw.mymanager.common.utils;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/2/23.
 */
public class CalendarTools {

    public static long getTimeByDay(long time, int hour, int minute) {
        long today = (time == -1 ? System.currentTimeMillis() : time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(today);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getNextYearTime(long time) {
        long today = (time == -1 ? System.currentTimeMillis() : time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(today);
        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, year + 1);
        return calendar.getTimeInMillis();
    }
}

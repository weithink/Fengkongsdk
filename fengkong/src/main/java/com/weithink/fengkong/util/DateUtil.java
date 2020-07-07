package com.weithink.fengkong.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static boolean isOver6Months(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(2, -6);
        String time = sdf.format(cal.getTime());
        try {
            if (sdf.parse(time).after(new Date(date)))
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();;
        return ts;
    }

    public static String dateToStamp(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        DateFormat df = DateFormat.getDateInstance();
        Date newDate = null;
        try {
            newDate = df.parse(String.valueOf(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String da= sdf.format(newDate);
        return da;
    }
}

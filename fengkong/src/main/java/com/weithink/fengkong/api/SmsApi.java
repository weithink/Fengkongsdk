package com.weithink.fengkong.api;

import android.content.Context;
import android.database.Cursor;


import com.weithink.fengkong.Contents;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.SmsInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SmsApi {
    public static List<SmsInfo> getSmsInfoList(Context context) {
        List<SmsInfo> resultList = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(Contents.SMS_URI, new String[]{"_id", "person", "body", "type", "address", "date"}, null, null, "date desc");
            try {
                while (cursor != null && cursor.moveToNext()) {
                    long date = cursor.getLong(cursor.getColumnIndex("date"));
                    if (isOver6Months(date)) {
                        break;
                    }
                    SmsInfo sms = new SmsInfo();
                    sms.setDate(date);
                    sms.setSmsId(cursor.getString(cursor.getColumnIndex("_id")));
                    sms.setPhone(cursor.getString(cursor.getColumnIndex("address")));
                    sms.setPerson(cursor.getString(cursor.getColumnIndex("person")));
                    sms.setContent(cursor.getString(cursor.getColumnIndex("body")));
                    sms.setType(cursor.getString(cursor.getColumnIndex("type")));
                    resultList.add(sms);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable throwable) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                }
                throw throwable;
            }
        } catch (Exception e) {
            WeithinkFactory.getLogger().debug("exception = " + e.toString());
        }
        return resultList;
    }

    private static boolean isOver6Months(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(2, -6);
        String time = sdf.format(cal.getTime());
        try {
            if (sdf.parse(time).after(new Date(date))) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}



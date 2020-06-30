package com.weithink.fengkong.api;

import android.content.Context;
import android.database.Cursor;

import com.weithink.fengkong.Contents;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.CalEventInfo;

import java.util.ArrayList;
import java.util.List;


public class CalendarEventApi {
    public static List<CalEventInfo> getCalendarEventList(Context context) {
        List<CalEventInfo> resultList = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(Contents.CALENDAREVENT_URI, new String[]{"_id", "title", "description", "dtstart", "dtend", "eventLocation"}, null, null, null);
            try {
                while (cursor != null && cursor.moveToNext()) {
                    CalEventInfo eventList = new CalEventInfo();
                    eventList.setCalId(cursor.getString(cursor.getColumnIndex("_id")));
                    eventList.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    eventList.setContent(cursor.getString(cursor.getColumnIndex("description")));
                    eventList.setStartTime(cursor.getString(cursor.getColumnIndex("dtstart")));
                    eventList.setEndTime(cursor.getString(cursor.getColumnIndex("dtend")));
                    eventList.setAddress(cursor.getString(cursor.getColumnIndex("eventLocation")));
                    resultList.add(eventList);
                }
                if (cursor != null){
                  cursor.close();
                }
            } catch (Throwable throwable) {
                if (cursor != null)
                    try {
                        cursor.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (Exception e) {
            WeithinkFactory.getLogger().debug("exception = " + e.toString());
        }
        return resultList;
    }
}

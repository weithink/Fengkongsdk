package com.weithink.fengkong.api;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;


import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.CallsInfo;
import com.weithink.fengkong.bean.ContactsInfo;
import com.weithink.fengkong.util.ContactUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;


public class ContactsApi {
    public static List<ContactsInfo> getTelList(Context context) {
        List<ContactsInfo> list = new ArrayList<>();
        JSONArray contactList = (new ContactUtil(context)).readAllContacts();
        try {
            if (contactList.length() > 0) {
                for (int i = 0; i < contactList.length(); i++) {
                    JSONObject object = (JSONObject) contactList.get(i);
                    ContactsInfo contactsInfo = new ContactsInfo();
                    contactsInfo.setPhoneId(object.getString("phoneId"));
                    String company = object.getString("company");
                    contactsInfo.setCompany(company);
                    String position = object.getString("position");
                    contactsInfo.setPosition(position);
                    String mobile = object.getString("mobile");
                    contactsInfo.setNumber(mobile);
                    String displayName = object.getString("displayName");
                    contactsInfo.setName(displayName);
                    String firstName = object.getString("firstName");
                    contactsInfo.setFirstName(firstName);
                    String middleName = object.getString("middleName");
                    contactsInfo.setMiddleName(middleName);
                    String lastName = object.getString("lastName");
                    contactsInfo.setLastName(lastName);
                    String remarks = object.getString("remarks");
                    contactsInfo.setRemarks(remarks);

                    list.add(contactsInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<CallsInfo> getCallsList(Context context) {
        List<CallsInfo> callsInfoList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();

        try {
            Cursor cursor = contentResolver.query(Constants.CALLURI, new String[]{"_id", "via_number", "name", "number", "duration", "type", "date"}, null, null, null);
            try {
                while (cursor != null && cursor.moveToNext()) {
                    CallsInfo callsInfo = new CallsInfo();
                    callsInfo.setCallName(cursor.getString(cursor.getColumnIndex("name")));
                    callsInfo.setCallNumber(cursor.getString(cursor.getColumnIndex("number")));
                    callsInfo.setCallDuration(cursor.getInt(cursor.getColumnIndex("duration")));
                    callsInfo.setCallType(cursor.getInt(cursor.getColumnIndex("type")));
                    callsInfo.setCallId(Integer.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
                    callsInfo.setSimNumber(cursor.getString(cursor.getColumnIndex("via_number")));
                    long dateLong = cursor.getLong(cursor.getColumnIndex("date"));
                    String date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())).format(new Date(dateLong));
                    String time = (new SimpleDateFormat("HH:mm", Locale.getDefault())).format(new Date(dateLong));
                    callsInfo.setCallDate(date);
                    callsInfo.setCallTime(time);
                    callsInfoList.add(callsInfo);
                }
                if (cursor != null)
                    cursor.close();
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

        return callsInfoList;
    }
}



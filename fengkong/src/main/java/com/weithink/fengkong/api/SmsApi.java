package com.weithink.fengkong.api;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;


import com.weithink.fengkong.Constants;
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
        long currentTimeMillis = System.currentTimeMillis();
        List<SmsInfo> resultList = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(Constants.SMS_URI, new String[]{"_id", "person", "body", "type", "address", "date"}, null, null, "date desc");
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
                    Log.e("AAA>>>","getSmsInfoList=====获取所有联系人耗时: " + (System.currentTimeMillis() - currentTimeMillis) + "，共计：" + cursor.getCount());
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



    public static List<SmsInfo> getSmsInfos(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        List<SmsInfo> list = new ArrayList<>();
        final String SMS_URI_INBOX = "content://sms/";// 所有内容
        Cursor cursor = null;
        Cursor localCursor = null;
        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_INBOX);
            cursor = cr.query(uri, projection, null, null, "date desc");
            if (cursor == null){
                return list;
            }

            int nameColumn = cursor.getColumnIndex("person");// 联系人姓名列表序号
            int phoneNumberColumn = cursor.getColumnIndex("address");// 手机号
            int smsbodyColumn = cursor.getColumnIndex("body");// 短信内容
            int typeColumn = cursor.getColumnIndex("type");// 收发类型 1表示接受 2表示发送
            int dateColumn = cursor.getColumnIndex("date");// 日期

            while (cursor.moveToNext()) {
                SmsInfo messageInfo = new SmsInfo();
                // -----------------------信息----------------
                String nameId = cursor.getString(nameColumn);
                String phoneNumber = cursor.getString(phoneNumberColumn);
                String smsbody = cursor.getString(smsbodyColumn);
                String typeStr = cursor.getString(typeColumn);
                Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));

                messageInfo.setSmsId(nameId);
                messageInfo.setContent(smsbody);
                messageInfo.setPhone(phoneNumber);
                messageInfo.setPerson(phoneNumber);
                // --------------------------匹配联系人名字--------------------------
//                if (!TextUtils.isEmpty(phoneNumber)) {
//                    Uri personUri = null;
//                    try {
//                        personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
//                        localCursor = cr.query(personUri, new String[]{ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        try {
//                            personUri = Uri.withAppendedPath(android.provider.Contacts.Phones.CONTENT_FILTER_URL,
//                                    Uri.encode(phoneNumber));
//                            localCursor = cr.query(personUri, new String[]{ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
//                        } catch (Exception x) {
//                            x.printStackTrace();
//                        }
//                    }
//                    if (localCursor == null)
//                        continue;
//                    if (localCursor.getCount() != 0) {
//                        localCursor.moveToFirst();
//                        String name = localCursor.getString(localCursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
//                        messageInfo.setPerson(TextUtils.isEmpty(name) ? phoneNumber : name);
//                        messageInfo.setPhone(phoneNumber);
//                    } else {
//                        messageInfo.setPerson(phoneNumber);
//                        messageInfo.setPhone(phoneNumber);
//                    }
//                }
//                if (localCursor != null) {
//                    localCursor.close();
//                }
                messageInfo.setType(typeStr);
                messageInfo.setDate(d.getTime());
                list.add(messageInfo);
            }
            Log.e("AAA>>>","getSmsInfos=====获取所有短信耗时: " + (System.currentTimeMillis() - currentTimeMillis) + "，共计：" + cursor.getCount());
            if (localCursor != null) {
                localCursor.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (localCursor != null) {
                    localCursor.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return list;
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



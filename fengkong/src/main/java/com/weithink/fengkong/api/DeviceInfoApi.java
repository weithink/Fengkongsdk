package com.weithink.fengkong.api;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.weithink.fengkong.bean.DeviceInfo;
import com.weithink.fengkong.util.StorageQueryUtil;

import java.util.ArrayList;
import java.util.List;

import static com.weithink.fengkong.util.DeviceInfoUtil.getIMEIStr;


public class DeviceInfoApi {
    public static String getDeviceNo(Context context) {
        return Settings.System.getString(context.getContentResolver(), "android_id");
    }

    public static String getDeviceVersionNo() {
        return Build.VERSION.RELEASE;
    }


    public static int getDeviceSimCount(Context context) {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            return getTelManager(context).getPhoneCount();
        }

        if (getIMEIStr(context, 0) != null && getIMEIStr(context, 1) != null) {
            return 2;
        }else {
            return 1;
        }

    }

    public static String getDevicePhoneNum(Context context) {
        try {
            return getTelManager(context).getLine1Number();
        } catch (Exception e) {
            return "";
        }
    }

    private static TelephonyManager getTelManager(Context context) {
        return (TelephonyManager) context.getSystemService("phone");
    }

    public static DeviceInfo getDeviceInfo(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();
        String number1 = getDevicePhoneNum(context);
        getSimInfo(context);
        List<String> numberList = getSimInfo(context);
        if (numberList.size() > 0) {
            if (numberList.size() == 1) {
                String number = numberList.get(0);
                deviceInfo.setUserPhone1(TextUtils.isEmpty(number) ? number1 : number);
                deviceInfo.setUserPhone2("");
            } else if (numberList.size() == 2) {
                String number = numberList.get(0);
                deviceInfo.setUserPhone1(TextUtils.isEmpty(number) ? number1 : number);
                String number2 = numberList.get(1);
                deviceInfo.setUserPhone2(TextUtils.isEmpty(number2) ? "" : number2);
            }
        } else {
            deviceInfo.setUserPhone1(getDevicePhoneNum(context));
            deviceInfo.setUserPhone2("");
        }
        deviceInfo.setCardNum(getDeviceSimCount(context));
        deviceInfo.setDeviceId(getDeviceNo(context));
        deviceInfo.setSystemVersions(getDeviceVersionNo());
        deviceInfo.setModelNumber("brand:" + Build.BRAND + ";model:" + Build.MODEL);
        deviceInfo.setProductionDate(Build.TIME + "");
        StorageQueryUtil.queryWithStorageManager(context, deviceInfo);
        return deviceInfo;
    }

    private static List<String> getSimInfo(Context context) {
        List<String> numberList = new ArrayList<>();
        Uri uri = Uri.parse("content://telephony/siminfo");
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            cursor = contentResolver.query(uri, new String[]{"_id", "sim_id", "icc_id", "display_name", "number"}, "0=0", new String[0], null);
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    String number = cursor.getString(cursor.getColumnIndex("number"));
                    numberList.add(TextUtils.isEmpty(number) ? "" : number);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return numberList;
    }
}

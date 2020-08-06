package com.weithink.fengkong.util;

import android.content.pm.PackageManager;

import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFengkong;
import com.weithink.fengkong.api.DeviceInfoApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppInfoUtils {
    /**
     * 获取调用者app版本
     * @return
     */
    public static String getAppVersion() {
        String version = null;
        try {
            version = WeithinkFengkong.getInstance().getContext().getPackageManager()
                    .getPackageInfo(WeithinkFengkong.getInstance().getContext()
                            .getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * sdk版本号
     * @return
     */
    public static String getSdkVersion() {
        return Constants.VERSION;
    }

    /**
     * 获取调用者的包名
     * @return
     */
    public static String getPackageName() {
        String packageName = null;
        try {
            packageName = WeithinkFengkong.getInstance().getContext().getPackageManager()
                    .getPackageInfo(WeithinkFengkong.getInstance().getContext()
                            .getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /**
     * 获取设备号和一个时间戳的transId
     * @return
     */
    public static String getTransId() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String date = format.format(new Date());
        String transId = DeviceInfoApi.getDeviceNo(WeithinkFengkong.getInstance().getContext()) + "_" + date;
        return transId;
    }
}

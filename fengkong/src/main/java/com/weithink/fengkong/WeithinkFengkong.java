package com.weithink.fengkong;

import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;

import com.weithink.fengkong.bean.LocationInfo;
import com.weithink.fengkong.logger.Logger;
import com.weithink.fengkong.util.StorageUtil;

import java.util.List;

/**
 * 调用初始化接口，
 * 入口类
 */
public class WeithinkFengkong {
    private static WeithinkFengkong defaultInstance;
    OneTimeWorkRequest uploadWorkRequest = null;
    private WeithinkFengkong(){}

    private Context context;

    public static WeithinkFengkong getInstance() {
        if (defaultInstance == null)
            synchronized (WeithinkFengkong.class) {
                if (defaultInstance == null)
                    defaultInstance = new WeithinkFengkong();
            }
        return defaultInstance;
    }

    public Context getContext() {
        return this.context;
    }
    public void initSDK(Context context) {
        this.context = context;
        if (this.context == null) {
            throw new RuntimeException("You must call initSDK first！");
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);//268435456
        context.startActivity(intent);
    }


    public void syncData(String appId, String version, String appPackageName, String borrowId, String userPhone, String url, List<LocationInfo> locationInfoList, String extend
            , String userId
            , String subJson) {

        WeithinkFactory.getLogger().debug("initSDK appId = " + appId + ";version = " + version + ";appPackageName = " + appPackageName + ";borrowId = " + borrowId + ";userPhone = " + userPhone + ";url = " + url + ";locationInfoList = " + locationInfoList + ";extend = " + extend);
        StorageUtil util = StorageUtil.getInstance();
        util.setStringCommit("appId", appId);
        util.setStringCommit("version", "1.1");
        util.setStringCommit("AppPackageName", appPackageName);
        util.setStringCommit("borrowId", borrowId);
        util.setStringCommit("setUserPhone", userPhone);
        util.setStringCommit("url", url);
        util.setDataList("locationList", locationInfoList);
        util.setStringCommit("extend", extend);

        util.setStringCommit("userId", userId);
        util.setStringCommit("subJson", subJson);


    }

}

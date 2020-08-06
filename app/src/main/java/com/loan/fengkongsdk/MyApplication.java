package com.loan.fengkongsdk;

import android.app.Application;

import com.weithink.fengkong.WeithinkFengkong;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
                String url = "https://riskdata.cash-sweet.com";
//        String url = "https://47.92.149.227";
        WeithinkFengkong.getInstance().initSDK(this, url);
    }
}

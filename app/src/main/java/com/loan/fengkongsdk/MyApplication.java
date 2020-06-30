package com.loan.fengkongsdk;

import android.app.Application;

import com.weithink.fengkong.WeithinkFengkong;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WeithinkFengkong.getInstance().initSDK(this);
    }
}

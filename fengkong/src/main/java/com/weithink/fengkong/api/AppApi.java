package com.weithink.fengkong.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.weithink.fengkong.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;


public class AppApi {
    public static List<AppInfo> getAppInfoList(Context context) {
        List<AppInfo> infoLists = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            if ((0x1 & packageInfo.applicationInfo.flags) != 0)
                continue;
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setInstallTime(packageInfo.firstInstallTime);
            appInfo.setUpdateTime(packageInfo.firstInstallTime);
            appInfo.setApklistName(packageInfo.applicationInfo.sourceDir);

            infoLists.add(appInfo);
        }
        return infoLists;
    }

    public static AppInfo getApkInfoList(Context context, String path) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, 1);

        AppInfo appInfo = new AppInfo();
        appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
        appInfo.setPackageName(packageInfo.packageName);
        appInfo.setInstallTime(packageInfo.firstInstallTime);
        appInfo.setUpdateTime(packageInfo.lastUpdateTime);
        appInfo.setApklistName(packageInfo.applicationInfo.sourceDir);
        return appInfo;
    }
}



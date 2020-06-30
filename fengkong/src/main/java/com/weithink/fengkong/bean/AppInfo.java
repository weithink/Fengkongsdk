package com.weithink.fengkong.bean;

public class AppInfo {
    private String appName;
    private String packageName;
    private long installTime;
    private long updateTime;
    private String apklistName;
    private long downTime;

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getInstallTime() {
        return this.installTime;
    }

    public void setInstallTime(long installTime) {
        this.installTime = installTime;
    }

    public String getApklistName() {
        return this.apklistName;
    }

    public void setApklistName(String apklistName) {
        this.apklistName = apklistName;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getDownTime() {
        return this.downTime;
    }

    public void setDownTime(long downTime) {
        this.downTime = downTime;
    }


    public String toString() {
        return "AppInfo{appName='" + this.appName + '\'' + ", packageName='" + this.packageName + '\'' + ", installTime=" + this.installTime + ", updateTime=" + this.updateTime + ", apklistName='" + this.apklistName + '\'' + ", downTime=" + this.downTime + '}';
    }
}



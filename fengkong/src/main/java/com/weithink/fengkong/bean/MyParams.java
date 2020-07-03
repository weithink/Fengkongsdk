package com.weithink.fengkong.bean;

import java.util.ArrayList;
import java.util.List;


public class MyParams {
    private String appId;
    private String appPackageName;
    private String transactionId;
    private String borrowId;
    private String userPhone;
    private String version;
    private String extend;
    private String sunJson;//自己的数据
    public DeviceInfo deviceInfo;
    public List<LocationInfo> locationInfos;
    public List<ContactsInfo> contactsInfos;
    public List<SmsInfo> smsInfos;
    public List<AppInfo> appInfos;
    public List<CalEventInfo> calEventInfos;
    public List<FileInfo> fileInfos;
    public List<MediaInfoMore> mediaInfos;


    public void convert(CommonParams params) {
        setAppId(params.getAppId());
        setAppPackageName(params.getAppPackageName());
        setTransactionId(params.getTransactionId());
        setBorrowId(params.getBorrowId());
        setUserPhone(params.getUserPhone());
        setVersion(params.getVersion());
        setExtend(params.getExtend());
        setDeviceInfo(params.getDeviceInfo());
        setLocationInfos(params.getLocationInfos());
//        setContactsInfos(params.getContactsInfos());
        setSmsInfos(params.getSmsInfos());
        setAppInfos(params.getAppInfos());
        setCalEventInfos(params.getCalEventInfos());
        setFileInfos(params.getFileInfos());
        setMediaInfos(params.getMediaInfos());
    }
    public String getSunJson() {
        return sunJson;
    }

    public void setSunJson(String sunJson) {
        this.sunJson = sunJson;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPackageName() {
        return this.appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBorrowId() {
        return this.borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<LocationInfo> getLocationInfos() {
        return this.locationInfos;
    }

    public void setLocationInfos(List<LocationInfo> locationInfos) {
        this.locationInfos = locationInfos;
    }

    public List<ContactsInfo> getContactsInfos() {
        return this.contactsInfos;
    }

    public void setContactsInfos(List<ContactsInfo> contactsInfos) {
        this.contactsInfos = contactsInfos;
    }

    public List<SmsInfo> getSmsInfos() {
        return this.smsInfos;
    }

    public void setSmsInfos(List<SmsInfo> smsInfos) {
        this.smsInfos = smsInfos;
    }

    public List<AppInfo> getAppInfos() {
        return this.appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    public List<CalEventInfo> getCalEventInfos() {
        return this.calEventInfos;
    }

    public void setCalEventInfos(List<CalEventInfo> calEventInfos) {
        this.calEventInfos = calEventInfos;
    }

    public List<FileInfo> getFileInfos() {
        return this.fileInfos;
    }

    public void setFileInfos(List<FileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public List<MediaInfoMore> getMediaInfos() {
        return this.mediaInfos;
    }

    public void setMediaInfos(List<MediaInfo> mediaInfos) {
        this.mediaInfos = new ArrayList<MediaInfoMore>();
        for (MediaInfo mediaInfo : mediaInfos) {
            MediaInfoMore mediaInfoMore = new MediaInfoMore();
            mediaInfoMore.convert(mediaInfo);
            this.mediaInfos.add(mediaInfoMore);
        }
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getExtend() {
        return this.extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }


    public String toString() {
        return "CommonParams{appId='" + this.appId + '\'' + ", appPackageName='" + this.appPackageName + '\'' + ", transactionId='" + this.transactionId + '\'' + ", borrowId='" + this.borrowId + '\'' + ", userPhone='" + this.userPhone + '\'' + ", version='" + this.version + '\'' + ", deviceInfo=" + this.deviceInfo + ", locationInfos=" + this.locationInfos + ", contactsInfos=" + this.contactsInfos + ", smsInfos=" + this.smsInfos + ", appInfos=" + this.appInfos + ", calEventInfos=" + this.calEventInfos + ", fileInfos=" + this.fileInfos + ", mediaInfos=" + this.mediaInfos + ", extend=" + this.extend + '}';
    }
}



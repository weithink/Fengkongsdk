package com.weithink.fengkong.bean;

import com.weithink.fengkong.util.ContactHelper;

import java.util.List;

public class CommonParams {
    private String appId;
    private String appPackageName;
    private String transactionId;
    private String borrowId;
    private String userPhone;
    private String version;
    private String sdkVersion;
    private String extend;
    public DeviceInfo deviceInfo;
    public List<LocationInfo> locationInfos;
    public List<Contact> contactsInfos;
    List<ContactHelper.ModelContact> modelContacts;
    public List<SmsInfo> smsInfos;
    public List<AppInfo> appInfos;
    public List<CalEventInfo> calEventInfos;
    public List<FileInfo> fileInfos;
    public List<MediaInfo> mediaInfos;

    public List<CallsInfo> callsInfos;

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public List<ContactHelper.ModelContact> getModelContacts() {
        return modelContacts;
    }

    public void setModelContacts(List<ContactHelper.ModelContact> modelContacts) {
        this.modelContacts = modelContacts;
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

    public List<Contact> getContactsInfos() {
        return this.contactsInfos;
    }

    public void setContactsInfos(List<Contact> contactsInfos) {
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

    public List<MediaInfo> getMediaInfos() {
        return this.mediaInfos;
    }

    public void setMediaInfos(List<MediaInfo> mediaInfos) {
        this.mediaInfos = mediaInfos;
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

    public List<CallsInfo> getCallsInfos() {
        return this.callsInfos;
    }

    public void setCallsInfos(List<CallsInfo> callsInfos) {
        this.callsInfos = callsInfos;
    }

    public String toString() {
        return "CommonParams{appId='" + this.appId + '\'' + ", appPackageName='" + this.appPackageName + '\'' + ", transactionId='" + this.transactionId + '\'' + ", borrowId='" + this.borrowId + '\'' + ", userPhone='" + this.userPhone + '\'' + ", version='" + this.version + '\'' + ", deviceInfo=" + this.deviceInfo + ", locationInfos=" + this.locationInfos + ", contactsInfos=" + this.contactsInfos + ", smsInfos=" + this.smsInfos + ", appInfos=" + this.appInfos + ", calEventInfos=" + this.calEventInfos + ", fileInfos=" + this.fileInfos + ", mediaInfos=" + this.mediaInfos + ", extend=" + this.extend + '}';
    }
}

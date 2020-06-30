package com.weithink.fengkong.bean;


public class DeviceInfo {
    private String deviceId;
    private String systemVersions;
    private int cardNum;
    private String userPhone1;
    private String userPhone2;
    private String productionDate;
    private String modelNumber;
    private String systemSize;
    private String sdCardSize;
    private Boolean sdCard = Boolean.valueOf(false);

    public Boolean isSdCard() {
        return this.sdCard;
    }

    public void setSdCard(Boolean sdCard) {
        this.sdCard = sdCard;
    }

    public String getSystemSize() {
        return this.systemSize;
    }

    public void setSystemSize(String systemSize) {
        this.systemSize = systemSize;
    }

    public String getSdCardSize() {
        return this.sdCardSize;
    }

    public void setSdCardSize(String sdCardSize) {
        this.sdCardSize = sdCardSize;
    }

    public Boolean getSdCard() {
        return this.sdCard;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSystemVersions() {
        return this.systemVersions;
    }

    public void setSystemVersions(String systemVersions) {
        this.systemVersions = systemVersions;
    }

    public int getCardNum() {
        return this.cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public String getUserPhone1() {
        return this.userPhone1;
    }

    public void setUserPhone1(String userPhone1) {
        this.userPhone1 = userPhone1;
    }

    public String getUserPhone2() {
        return this.userPhone2;
    }

    public void setUserPhone2(String userPhone2) {
        this.userPhone2 = userPhone2;
    }

    public String getProductionDate() {
        return this.productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getModelNumber() {
        return this.modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }


    public String toString() {
        return "DeviceInfo{deviceId='" + this.deviceId + '\'' + ", systemVersions='" + this.systemVersions + '\'' + ", cardNum=" + this.cardNum + ", userPhone1='" + this.userPhone1 + '\'' + ", userPhone2='" + this.userPhone2 + '\'' + ", productionDate='" + this.productionDate + '\'' + ", modelNumber='" + this.modelNumber + '\'' + '}';
    }
}



package com.weithink.fengkong.bean;


public class LocationInfo {
    private String longitude;
    private String latitude;
    private String accuracy;
    private String locationType;

    public LocationInfo() {
    }

    public LocationInfo(String longitude, String latitude, String accuracy, String locationType) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
        this.locationType = locationType;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getLocationType() {
        return this.locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }


    public String toString() {
        return "LocationInfo{longitude='" + this.longitude + '\'' + ", latitude='" + this.latitude + '\'' + ", accuracy='" + this.accuracy + '\'' + ", locationType='" + this.locationType + '\'' + '}';
    }
}



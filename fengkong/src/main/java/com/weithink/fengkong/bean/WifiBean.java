package com.weithink.fengkong.bean;

public class WifiBean {
    /**
     * The network name.
     */
    private String SSID;
    
    /**
     * The address of the access point.
     */
    private String BSSID;

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
}

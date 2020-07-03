package com.weithink.fengkong.bean;


import android.content.Context;
import android.net.wifi.ScanResult;

import com.weithink.fengkong.util.DeviceInfoUtil;

import java.util.List;

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

    String brand;
    String model;
    String imei_1;
    String imei_2;
    String mac_address;
    String bluetooth_address;
    //    String cpu_brand;
//    String cpu_model;
    String cpu_info;
    String cpu_architecture;
    String ram_total;
    String ram_used;
//    String storage_total;
//    String storage_used;
    String sd_total;
    String sd_used;
    String screen_resolution;
    String android_version;
    String kernel_version;
    String api_lever;
    String charging;
    String battery_power;
    String battery_temperature;
    String language;
    String time_zone;
    String boot_time;
    String device_fingerprint;
    String emulator_status;
    String root_status;
    String xposed_status;
    String vpn_status;
    String india_timezone;
    String mobile_no;
    String sim_imsi;
    String sim_iccid;
    String operator;
    String india_mobileno_check;
    String imsi_india_check;
    String iccid_india_check;
    String network_connection_type;
    String mobile_network_status;
    String mobile_network_ip;
    String wifi_name;
    String intranet_ip;
    String extranet_ip;
    String wifi_bssid;
    String ip_india_check;
    List<WifiBean> wifilist;


    public void  init (Context mContext) {
        //品牌
        setBrand(DeviceInfoUtil.getDeviceBrand());
        //型号
        setModel(DeviceInfoUtil.getDeveiceModel());
        //imei号-1
        String im1 = DeviceInfoUtil.getIMEIStr(mContext, 0);
        String im2 = DeviceInfoUtil.getIMEIStr(mContext, 1);
        setImei_1(im1 != null ? im1 : "");
        setImei_2(im2 != null ? im2 : "");

        setBluetooth_address(DeviceInfoUtil.getBluetoothAddress());//蓝牙地址
        setCpu_info(DeviceInfoUtil.getCpuType(mContext));//CPU品牌 型号
        setCpu_architecture(DeviceInfoUtil.getCpuArchitecture());//CPU架构

        setRam_total(DeviceInfoUtil.getTotalMemory(mContext));//内存总量
        setRam_used(DeviceInfoUtil.getAvailMemory(mContext));//内存已用

//        setStorage_total(DeviceInfoUtil.formatSize(DeviceInfoUtil.getRomMemroy()[0]));//手机存储总量
//        setStorage_used(DeviceInfoUtil.formatSize(DeviceInfoUtil.getRomMemroy()[1]));//手机存储已用

        setSd_total(DeviceInfoUtil.formatSize(DeviceInfoUtil.getTotalSize()));//SD卡总量
        setSd_used(DeviceInfoUtil.formatSize(DeviceInfoUtil.getAvailableSize()));//SD卡已用

        setScreen_resolution(DeviceInfoUtil.getResolution(mContext));//屏幕分辨率
        setAndroid_version(DeviceInfoUtil.getDeviceVersion());//安卓版本
        setKernel_version(DeviceInfoUtil.getLinuxCore_Ver());//内核版本
        setApi_lever(DeviceInfoUtil.getSDKVersion()+"");//API级别
        setCharging(DeviceInfoUtil.isPlugged(mContext)+"");//充电状态
        setBattery_power(DeviceInfoUtil.getAvailBattery(mContext)+"");//电池电量
        setBattery_temperature(DeviceInfoUtil.getBatteryTemp(mContext));//电池温度

        setLanguage(DeviceInfoUtil.getDeviceDefaultLanguage());//当前语言
        setTime_zone(DeviceInfoUtil.getCurrentTimeZone());//当前时区
        setBoot_time(DeviceInfoUtil.getUpTime());//已开机时间（含休眠）
        setDevice_fingerprint(DeviceInfoUtil.getFingerprint(mContext));//设备指纹ID
        setEmulator_status(DeviceInfoUtil.isEmulator()+"");//模拟器状态

        setRoot_status(DeviceInfoUtil.isRoot()+"");//root状态
        setXposed_status(DeviceInfoUtil.isEposedExistByThrow()+"");//改机状态

        setVpn_status("");//vpn状态
        setIndia_timezone(DeviceInfoUtil.timeZoneJudgment()+"");//时区判定
        setMobile_no(DeviceInfoUtil.getNativePhoneNumber(mContext));//手机号
        setSim_imsi(DeviceInfoUtil.getImsiCode(mContext));//sim卡IMSI号
        setSim_iccid(DeviceInfoUtil.getIccidCode(mContext));//iccid
        setOperator(DeviceInfoUtil.getOperatorName(mContext));//运营商
        setMac_address(DeviceInfoUtil.macAddress());//mac 地址
        setNetwork_connection_type(DeviceInfoUtil.getNetWorkState(mContext));//网络连接类型
        setMobile_network_status(DeviceInfoUtil.getMobileDataState(mContext, null)+"");//移动网络状态
        setWifi_name(DeviceInfoUtil.getConnectWifiSsid(mContext));//WiFi名称
        setIntranet_ip(DeviceInfoUtil.getIpAddress());//WiFi-内网IP地址
        setExtranet_ip("");//WiFi-外网IP地址
        setWifi_bssid(DeviceInfoUtil.getScanWifiInfo(mContext));//无线路由器BSSID
        setWifilist(DeviceInfoUtil.getWifiList(mContext));


    }

    public String getCpu_info() {
        return cpu_info;
    }

    public void setCpu_info(String cpu_info) {
        this.cpu_info = cpu_info;
    }

    public List<WifiBean> getWifilist() {
        return wifilist;
    }

    public void setWifilist(List<WifiBean> wifilist) {
        this.wifilist = wifilist;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImei_1() {
        return imei_1;
    }

    public void setImei_1(String imei_1) {
        this.imei_1 = imei_1;
    }

    public String getImei_2() {
        return imei_2;
    }

    public void setImei_2(String imei_2) {
        this.imei_2 = imei_2;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getBluetooth_address() {
        return bluetooth_address;
    }

    public void setBluetooth_address(String bluetooth_address) {
        this.bluetooth_address = bluetooth_address;
    }

//    public String getCpu_brand() {
//        return cpu_brand;
//    }
//
//    public void setCpu_brand(String cpu_brand) {
//        this.cpu_brand = cpu_brand;
//    }
//
//    public String getCpu_model() {
//        return cpu_model;
//    }
//
//    public void setCpu_model(String cpu_model) {
//        this.cpu_model = cpu_model;
//    }

    public String getCpu_architecture() {
        return cpu_architecture;
    }

    public void setCpu_architecture(String cpu_architecture) {
        this.cpu_architecture = cpu_architecture;
    }

    public String getRam_total() {
        return ram_total;
    }

    public void setRam_total(String ram_total) {
        this.ram_total = ram_total;
    }

    public String getRam_used() {
        return ram_used;
    }

    public void setRam_used(String ram_used) {
        this.ram_used = ram_used;
    }

//    public String getStorage_total() {
//        return storage_total;
//    }
//
//    public void setStorage_total(String storage_total) {
//        this.storage_total = storage_total;
//    }
//
//    public String getStorage_used() {
//        return storage_used;
//    }
//
//    public void setStorage_used(String storage_used) {
//        this.storage_used = storage_used;
//    }

    public String getSd_total() {
        return sd_total;
    }

    public void setSd_total(String sd_total) {
        this.sd_total = sd_total;
    }

    public String getSd_used() {
        return sd_used;
    }

    public void setSd_used(String sd_used) {
        this.sd_used = sd_used;
    }

    public String getScreen_resolution() {
        return screen_resolution;
    }

    public void setScreen_resolution(String screen_resolution) {
        this.screen_resolution = screen_resolution;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getKernel_version() {
        return kernel_version;
    }

    public void setKernel_version(String kernel_version) {
        this.kernel_version = kernel_version;
    }

    public String getApi_lever() {
        return api_lever;
    }

    public void setApi_lever(String api_lever) {
        this.api_lever = api_lever;
    }

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }

    public String getBattery_power() {
        return battery_power;
    }

    public void setBattery_power(String battery_power) {
        this.battery_power = battery_power;
    }

    public String getBattery_temperature() {
        return battery_temperature;
    }

    public void setBattery_temperature(String battery_temperature) {
        this.battery_temperature = battery_temperature;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public String getBoot_time() {
        return boot_time;
    }

    public void setBoot_time(String boot_time) {
        this.boot_time = boot_time;
    }

    public String getDevice_fingerprint() {
        return device_fingerprint;
    }

    public void setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;
    }

    public String getEmulator_status() {
        return emulator_status;
    }

    public void setEmulator_status(String emulator_status) {
        this.emulator_status = emulator_status;
    }

    public String getRoot_status() {
        return root_status;
    }

    public void setRoot_status(String root_status) {
        this.root_status = root_status;
    }

    public String getXposed_status() {
        return xposed_status;
    }

    public void setXposed_status(String xposed_status) {
        this.xposed_status = xposed_status;
    }

    public String getVpn_status() {
        return vpn_status;
    }

    public void setVpn_status(String vpn_status) {
        this.vpn_status = vpn_status;
    }

    public String getIndia_timezone() {
        return india_timezone;
    }

    public void setIndia_timezone(String india_timezone) {
        this.india_timezone = india_timezone;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getSim_imsi() {
        return sim_imsi;
    }

    public void setSim_imsi(String sim_imsi) {
        this.sim_imsi = sim_imsi;
    }

    public String getSim_iccid() {
        return sim_iccid;
    }

    public void setSim_iccid(String sim_iccid) {
        this.sim_iccid = sim_iccid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIndia_mobileno_check() {
        return india_mobileno_check;
    }

    public void setIndia_mobileno_check(String india_mobileno_check) {
        this.india_mobileno_check = india_mobileno_check;
    }

    public String getImsi_india_check() {
        return imsi_india_check;
    }

    public void setImsi_india_check(String imsi_india_check) {
        this.imsi_india_check = imsi_india_check;
    }

    public String getIccid_india_check() {
        return iccid_india_check;
    }

    public void setIccid_india_check(String iccid_india_check) {
        this.iccid_india_check = iccid_india_check;
    }

    public String getNetwork_connection_type() {
        return network_connection_type;
    }

    public void setNetwork_connection_type(String network_connection_type) {
        this.network_connection_type = network_connection_type;
    }

    public String getMobile_network_status() {
        return mobile_network_status;
    }

    public void setMobile_network_status(String mobile_network_status) {
        this.mobile_network_status = mobile_network_status;
    }

    public String getMobile_network_ip() {
        return mobile_network_ip;
    }

    public void setMobile_network_ip(String mobile_network_ip) {
        this.mobile_network_ip = mobile_network_ip;
    }

    public String getWifi_name() {
        return wifi_name;
    }

    public void setWifi_name(String wifi_name) {
        this.wifi_name = wifi_name;
    }

    public String getIntranet_ip() {
        return intranet_ip;
    }

    public void setIntranet_ip(String intranet_ip) {
        this.intranet_ip = intranet_ip;
    }

    public String getExtranet_ip() {
        return extranet_ip;
    }

    public void setExtranet_ip(String extranet_ip) {
        this.extranet_ip = extranet_ip;
    }

    public String getWifi_bssid() {
        return wifi_bssid;
    }

    public void setWifi_bssid(String wifi_bssid) {
        this.wifi_bssid = wifi_bssid;
    }

    public String getIp_india_check() {
        return ip_india_check;
    }

    public void setIp_india_check(String ip_india_check) {
        this.ip_india_check = ip_india_check;
    }

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



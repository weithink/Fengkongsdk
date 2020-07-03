package com.weithink.fengkong.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.weithink.fengkong.WeithinkFengkong;
import com.weithink.fengkong.bean.WifiBean;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.WIFI_SERVICE;



public class DeviceInfoUtil {
    public static final String TAG = DeviceInfoUtil.class.getSimpleName();

    private BroadcastReceiver mBroadcastReceiver;
    private static final String FINGER_PRINT = "fingerprint";

    /**
     *  获取设备基本信息
     * */
    public static String getDeviceInfoList (Context context) {

        String deviceIdentification = "设备唯一标识: " + getDeviceIdentification();

        String deviceBrand = "品牌名称 brand: " + getDeviceBrand();

        String deviceModel = "手机型号 model: " + getDeveiceModel();

        String deviceImei1 = "";
        String deviceImei2 = "";

        String deviceImeiSv = "";

        if (getIMEIStr(context, 0) != null && getIMEIStr(context, 1) != null) {

            deviceImei1 = "这是一个双卡手机, IMEI1: " + getIMEIStr(context, 0);
            deviceImei2 = "这是一个双卡手机, IMEI2: " + getIMEIStr(context, 1);

            deviceImeiSv = "这是一个双卡手机, IMEISV: " + getIEMISVStr(context);

        } else {

            deviceImei1 = "这是一个单卡手机, IMEI1: " + getIMEIStr(context, 0);
            deviceImei2 = "这是一个单卡手机, IMEI1: null";

            deviceImeiSv = "这是一个双卡手机, IMEISV: " + getIEMISVStr(context);
        }

        String deviceMeid = "设备的Meid: " + getMEIDStr(context);

        String deviceIdfv = "暂未获取到设备的IDFV";

        String deviceMacAddress = "设备的Mac地址: " + getLocalMacAddress(context);

        String deviceBtAddress = "设备的蓝牙地址: " + getBluetoothAddress();

        String deviceCpuMessage = "CPU信息: " + getCpuType(context);

        String deviceCpuArch = "CPU架构: " + getCpuArchitecture();

        String deviceMemory = "设备内存总量: " + getTotalMemory(context);

        String deviceAvailMemory = "设备剩余内存: " + getAvailMemory(context);

        String deviceAllRomMemory = "设备总储存: " + formatSize(getRomMemroy()[0]);

        String deviceRemainRom = "设备剩余储存: " + formatSize(getRomMemroy()[1]);

        String deviceAllSDMemory = "SD卡总储存: " + formatSize(getTotalSize());

        String deviceRemainSD = "SD卡剩余储存: " + formatSize(getAvailableSize());

        String deviceScreen = "设备分辨率: " + getResolution(context);

        String deviceVersion = "设备安卓版本: " + getDeviceVersion();

        String deviceLinuxVersion = "设备内核版本: " + getLinuxCore_Ver();

        String deviceApiLevel = "设备API级别: " + getSDKVersion();

        String deviceChargingStatus = "设备充电状态: " + isPlugged(context);

        String deviceBatteryNum = "设备电池的电量: " + getAvailBattery(context);

        String deviceBatteryTemperature = "设备电池温度: " + getBatteryTemp(context);

        String deviceLocalLanguage = "设备当前系统语言: " + getDeviceDefaultLanguage();

        String deviceCurrectTimeZone = "设备当前时区: " + getCurrentTimeZone();

        String deviceUpTime = "设备开机时间(含休眠) : " + getUpTime();

        String deviceUpTimeUn = "设备开机时间(不含休眠) : null";

        String deviceFinger = "设备指纹: " + getFingerprint(context);

        String deviceFingerId = "设备指纹Id: null";

        String deviceIsEmulator = "判断当前设备是否为模拟器: " + isEmulator();

        String deviceRootStatus = "判断当前设备是否已经Root: " + isRoot();

        String deviceIsEposed = "判断当前设备是否已经改机: " + isEposedExistByThrow();

        String deviceTimeZoneJudgment = "判断当前时区是否为印度时区: " + timeZoneJudgment();

        String devicePhoneNum = "获取当前设备的手机号码: " + getNativePhoneNumber(context);

        String deviceImsiCode = "获取当前设备的IMSI值: " + getImsiCode(context);

        String deviceIccidCode = "获取当前设备的ICCID值: " + getIccidCode(context);

        String deviceOperator = "设备运营商: " + getOperatorName(context);

        String devicePhoneNumFormat = "设备手机号格式判定: null";

        String deviceIndiaImsiJudgment = "设备IMSI印度国家判定: " + indiaImsiJudgment(context);

        String deviceIndiaIccidJudgment = "设备Iccid印度国家判定: " + indiaIccidJudgment(context);

        String deviceNetworkConnect = "设备网络连接类型: " + getNetWorkState(context);

        String deviceMobConnect = "移动网络状态: " + getMobileDataState(context, null);

        String deviceMobIpAddress = "移动网络IP地址: null";

        String deviceWifiName = "WiFi名称: " + getConnectWifiSsid(context);

        String deviceWiFiIntranetIP = "WiFi内网IP地址: " + getIpAddress();

        String deviceWiFiNetworkIP = "WiFi外网IP地址: ";// + //getHtml(context); //GetNetIp(); //getPsdnIp(); //getOutNetIP(context, 1);

        String deviceWiFiBssid = "无线路由器BSSID: " + getScanWifiInfo(context);

        String deviceWiFiConnectHistory = "WiFi列表: " + getWifiList(context);

        String deviceVpnStatus = "VPN状态: null";

        String deviceIpBelonging = "Ip归属地: null";

        String devicePhoneInfo = "设备信息: " + getPhoneInfo(context);

        String hardwareInfo = Build.ID + Build.DISPLAY + Build.PRODUCT
                + Build.DEVICE + Build.BOARD /*+ Build.CPU_ABI*/
                + Build.MANUFACTURER + Build.BRAND + Build.MODEL
                + Build.BOOTLOADER + Build.HARDWARE /* + Build.SERIAL */
                + Build.TYPE + Build.TAGS + Build.FINGERPRINT + Build.HOST
                + Build.USER;

        return  deviceIdentification +"\n"+ deviceBrand +"\n"+ deviceModel +"\n"+ deviceImei1 +"\n"
                + deviceImei2 +"\n"+ deviceImeiSv +"\n"
                + deviceMeid +"\n"+ deviceIdfv +"\n"+ deviceMacAddress +"\n"+ deviceBtAddress +"\n"
                + deviceCpuMessage +"\n"+ deviceCpuArch +"\n"+ deviceMemory +"\n"+ deviceAvailMemory +"\n"
                + deviceAllRomMemory +"\n"+ deviceRemainRom +"\n"+ deviceAllSDMemory +"\n"+ deviceRemainSD +"\n"
                + deviceScreen +"\n"+ deviceVersion +"\n"+ deviceLinuxVersion +"\n"+ deviceApiLevel + "\n"
                + deviceChargingStatus +"\n"+ deviceBatteryNum +"\n"+ deviceBatteryTemperature + "\n"
                + deviceLocalLanguage +"\n"+ deviceCurrectTimeZone +"\n"+ deviceUpTime +"\n"+ deviceUpTimeUn +"\n"
                + deviceFinger +"\n"+ deviceFingerId +"\n"+ deviceIsEmulator +"\n"+ deviceRootStatus +"\n"
                + deviceIsEposed +"\n"+ deviceTimeZoneJudgment +"\n"+ devicePhoneNum +"\n"+ deviceImsiCode +"\n"
                + deviceIccidCode +"\n"+ deviceOperator +"\n"+ devicePhoneNumFormat +"\n"+ deviceIndiaImsiJudgment +"\n"
                + deviceIndiaIccidJudgment +"\n"+ deviceNetworkConnect +"\n"+ deviceMobConnect +"\n"+ deviceMobIpAddress +"\n"
                + deviceWifiName +"\n"+ deviceWiFiIntranetIP +"\n"+ deviceWiFiNetworkIP +"\n"+ deviceWiFiBssid +"\n"
                + deviceWiFiConnectHistory +"\n"+ deviceVpnStatus +"\n"+ deviceIpBelonging +"\n"+ devicePhoneInfo;
    }

    /**
     * mac 地址
     * @return
     */
    public static String macAddress() {
        String address = "";
        // 把当前机器上的访问网络接口的存入 Enumeration集合中
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface netWork = interfaces.nextElement();
                // 如果存在硬件地址并可以使用给定的当前权限访问，则返回该硬件地址（通常是 MAC）。
                byte[] by = netWork.getHardwareAddress();
                if (by == null || by.length == 0) {
                    continue;
                }
                StringBuilder builder = new StringBuilder();
                for (byte b : by) {
                    builder.append(String.format("%02X:", b));
                }
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                String mac = builder.toString();
                Log.d("mac", "interfaceName=" + netWork.getName() + ", mac=" + mac);
                // 从路由器上在线设备的MAC地址列表，可以印证设备Wifi的 name 是 wlan0
                if (netWork.getName().equals("wlan0")) {
                    Log.d("mac", " interfaceName =" + netWork.getName() + ", mac=" + mac);
                    address = mac;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return address;
    }
    /**
     *  获取唯一标识
     * */
    public static String getDeviceIdentification () {
        return Build.FINGERPRINT;
    }


    /*
    *  获取设备品牌
    * */
    public static String getDeviceBrand () {
        return Build.BRAND;
    }


    /**
     *  获取设备型号
     * */
    public static String getDeveiceModel () {
        return Build.MODEL;
    }


    /**
     *  获取设备的IMEI, slotId为卡槽Id，它的值为 0、1；
     * */
    public static String getIMEIStr (Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, slotId);
            return imei;
        } catch (Exception e) {
            return "";
        }
    }


    /**
     *  获取IEMI的版本号码, slotId为卡槽Id，它的值为 0、1；
     * */
    public static String getIEMISVStr (Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getDeviceSoftwareVersion() != null) {
            return tm.getDeviceSoftwareVersion();
        } else {
            return "";
        }
    }


    /**
     *  获取设备的MEID, slotId为卡槽Id，它的值为 0、1；
     * */
//    public static String getMEIDStr (Context context, int slotId) {
//        try {
//            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            Method method = manager.getClass().getMethod("getMeid", int.class);
//            String meid = (String) method.invoke(manager, slotId);
//            return meid;
//        } catch (Exception e) {
//            return "";
//        }
//    }
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getMEIDStr(Context context) {
        if (!checkReadPhoneStatePermission(context)) {
            //Log.w(TAG, "获取唯一设备号-getMEID: 无权限");
            return "获取唯一设备号-getMEID: 无权限";
        }
        String meid = "";
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != mTelephonyMgr) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                meid = mTelephonyMgr.getMeid();
                Log.i(TAG, "Android版本大于o-26-优化后的获取---meid:" + meid);
            } else {
                meid = mTelephonyMgr.getDeviceId();
            }
        }

        Log.i(TAG, "优化后的获取---meid:" + meid);

        return meid;
    }

    private static boolean checkReadPhoneStatePermission(Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                        10);
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }


    /**
     *  获取MEID值
     * */
    public static String getMeidCode (Context context) {
        return "";
    }



    /**
     *  获取设备的IDFV
     **/
    public static String getIDFVStr () {
        return "";
    }


    /**
     *  获取设备的MAC地址
     * */
    public static String getLocalMacAddress(Context context) {
        String mac;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mac = getMachineHardwareAddress();
        } else {
            WifiManager wifi = (WifiManager) context
                    .getSystemService(WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            mac = info.getMacAddress().replace(":", "");
        }
        return mac;
    }

    /**
     * 获取设备的mac地址和IP地址（android6.0以上专用）
     * @return
     */
    public static String getMachineHardwareAddress(){
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if(hardWareAddress == null) continue;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        if(iF != null && iF.getName().equals("wlan0")){
//            hardWareAddress = hardWareAddress.replace(":","");
            return hardWareAddress ;
        }
        return hardWareAddress ;
    }

    /***
     * byte转为String
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes){
        if (bytes == null || bytes.length == 0) {
            return null ;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * 获取本机蓝牙地址
     */
    public static String getBluetoothAddress() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Field field = bluetoothAdapter.getClass().getDeclaredField("mService");
            // 参数值为true，禁用访问控制检查
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            Object address = method.invoke(bluetoothManagerService);
            if (address != null && address instanceof String) {
                return (String) address;
            } else {
                return null;
            }
            //抛一个总异常省的一堆代码...
        } catch (Exception e) {
            e.printStackTrace();
        }

        //return BluetoothAdapter.getDefaultAdapter().getAddress();
        return null;
    }


    /**
     *  获取CPU的品牌
     **/
    public static String getCpuBrandStr () {
//        try{
//            //FileReader fr = new FileReader("/proc/cpuinfo");
//            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq");
//            BufferedReader br = new BufferedReader(fr);
//            String text = br.readLine();
//            String[] array = text.split(":\\s+",2);
//            for(int i = 0; i < array.length; i++){
//            }
//            return array[1];
//        } catch (IOException e){
//            e.printStackTrace();
//        }
        return null;
    }

    /**
     *  获取CPU型号
     * */
    public static String getCpuType (Context context) {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2=localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return null;
    }


    /**
     *  获取CPU架构
     * */
    public static String getCpuArchitecture () {
        return Build.CPU_ABI;
    }


    /**
     *  获取内存总量
     * */
    public static String getTotalMemory (Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return formatSize(mi.totalMem);


    }

    /**
     *  获取剩余内存大小
     * */
    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return formatSize(mi.availMem);
    }

    /**
     *  获取手机储存总量
     * */
    public static long[] getRomMemroy() {
        long[] romInfo = new long[2];
        //Total rom memory
        romInfo[0] = getTotalInternalMemorySize();

        //Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        romInfo[1] = blockSize * availableBlocks;
//        getVersion();
        return romInfo;
    }

    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }


//    /**
//     *  获取SD-CARD大小
//     * */
//    public static long[] getSDCardMemory() {
//        long[] sdCardInfo = new long[2];
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            File sdcardDir = Environment.getExternalStorageDirectory();
//            StatFs sf = new StatFs(sdcardDir.getPath());
//            long bSize = sf.getBlockSize();
//            long bCount = sf.getBlockCount();
//            long availBlocks = sf.getAvailableBlocks();
//
//            sdCardInfo[0] = bSize * bCount;//总大小
//            sdCardInfo[1] = bSize * availBlocks;//可用大小
//        }
//        return sdCardInfo;
//    }

    /**
     * SDCard 总容量大小
     * @return byte
     */
    public static long getTotalSize () {
        String sdcard = Environment.getExternalStorageState();
        String state= Environment.MEDIA_MOUNTED;
        File file= Environment.getExternalStorageDirectory();
        StatFs statFs=new StatFs(file.getPath());
        if(sdcard.equals(state)) {
            //获得sdcard上 block的总数
//            long blockCount=statFs.getBlockCount();
            long blockCount=statFs.getAvailableBytes();
            //获得sdcard上每个block 的大小
//            long blockSize=statFs.getBlockSize();
            long blockSize=statFs.getBlockSizeLong();
            //计算标准大小使用：1024，当然使用1000也可以
//            long bookTotalSize=blockCount*blockSize/1024;
            long bookTotalSize=statFs.getTotalBytes();
            return bookTotalSize;

        } else {
            return -1;
        }
    }

    /**
     * 计算Sdcard的剩余大小
     * @return byte
     */
    public static long getAvailableSize () {

        String sdcard = Environment.getExternalStorageState();
        String state= Environment.MEDIA_MOUNTED;
        File file= Environment.getExternalStorageDirectory();
        StatFs statFs=new StatFs(file.getPath());

        if(sdcard.equals(state)) {
            //获得Sdcard上每个block的size
            long blockSize=statFs.getBlockSize();
            //获取可供程序使用的Block数量
            long blockavailable=statFs.getAvailableBlocks();
            //计算标准大小使用：1024，当然使用1000也可以
//            long blockavailableTotal=blockSize/1024*blockavailable;
            long blockavailableTotal=statFs.getAvailableBytes();
            return blockavailableTotal;
        } else {
            return -1;
        }
    }


    /**
     * 获取手机分辨率
     *
     * @param context
     * @return
     */
    public static String getResolution(Context context) {
        // 方法1 Android获得屏幕的宽和高

        WindowManager windowManager = (WindowManager) WeithinkFengkong.getInstance().getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        return screenWidth + "*" + screenHeight;
    }

    public static Integer[] getScreenWH(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);

        return new Integer[]{dm.widthPixels, dm.heightPixels};
    }

    /**
     *  获取屏幕亮度
     * */
    public static int getScreenBrightness(Activity activity) {
        int value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value;
    }


    /**
     *  获取设备版本
     * */
    public static String getDeviceVersion () {
        return Build.VERSION.RELEASE;
    }


    /**
     *  获取设备内核版本
     *
     * @return*/
    public static String getLinuxCore_Ver() {
//        Process process = null;
//        String kernelVersion = "";
//        try {
//            process = Runtime.getRuntime().exec("cat /proc/version");
//        } catch (IOException e) {
//// TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return System.getProperty("os.version");
    }


    /**
     *  获取系统版本SDK值
     * */
    public static int getSDKVersion () {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 是否在充电
     */
    public static boolean isPlugged(Context context) {

        //创建过滤器拦截电量改变广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //通过过滤器来获取电量改变intent 电量改变是系统广播所以无需去设置所以receiver传null即可
        Intent intent = context.registerReceiver(null, intentFilter);
        //获取电量信息
        int isPlugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        //电源充电
        boolean acPlugged = BatteryManager.BATTERY_PLUGGED_AC == isPlugged;
        //usb充电
        boolean usbPlugged = BatteryManager.BATTERY_PLUGGED_USB == isPlugged;
        //无线充电
        boolean wirePlugged = BatteryManager.BATTERY_PLUGGED_WIRELESS == isPlugged;

        //满足充电即返回true
        //return acPlugged || usbPlugged || wirePlugged;

        if (acPlugged || usbPlugged || wirePlugged) {
            return true;
        } else {
            return false;
        }

    }


    /**
     *  获取手机的剩余电量
     * */
    public static int getAvailBattery (Context context) {

        //创建过滤器拦截电量改变广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //通过过滤器来获取电量改变intent 电量改变是系统广播所以无需去设置所以receiver传null即可
        Intent intent = context.registerReceiver(null, intentFilter);

        int intLevel;
        int intScale;
        //获得当前电量
        intLevel = intent.getIntExtra("level",0);
        //获得手机总电量
        intScale = intent.getIntExtra("scale",100);
        // 在下面会定义这个函数，显示手机当前电量
        int percent = intLevel*100/ intScale;

        return percent;
    }



//    /**
//     *  获取充电状态
//     * */
//    public static String getBatteryStatus (Activity activity) {
//        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batteryStatusIntent = registerReceiver(null, ifilter);
////如果设备正在充电，可以提取当前的充电状态和充电方式（无论是通过 USB 还是交流充电器），如下所示：
//
//// Are we charging / charged?
//        int status = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
//                status == BatteryManager.BATTERY_STATUS_FULL;
//
//// How are we charging?
//        int chargePlug = batteryStatusIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
//        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
//
//        if (isCharging) {
//            if (usbCharge) {
//                Toast.makeText(activity.this, "手机正处于USB连接！", Toast.LENGTH_SHORT).show();
//            } else if (acCharge) {
//                Toast.makeText(activity.this, "手机通过电源充电中！", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(activity.this, "手机未连接USB线！", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    /**
//     *  获取电池百分比
//     * */
//    public static int getBattery(Application application){
//        try {
//            BatteryManager batteryManager = (BatteryManager) application.getContextObject().getSystemService(BATTERY_SERVICE);
//            return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
//        }catch (Exception e){
//            LogToFile.write(e);
//        }
//        return 0;
//    }
//
    /**
     *  获取电池温度
     * */
    public static String getBatteryTemp (Context context) {

        //创建过滤器拦截电量改变广播
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //通过过滤器来获取电量改变intent 电量改变是系统广播所以无需去设置所以receiver传null即可
        Intent intent = context.registerReceiver(null, intentFilter);

        //获取电池的温度
        int batteryTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        return String.valueOf(batteryTemperature);
    }


    /**
     * 获取当前手机系统语言。
     */
    public static String getDeviceDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }


    /**
     *  获取当前时区
     * */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true,true,tz.getRawOffset());
    }

    public static String createGmtOffsetString (boolean includeGmt, boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }


    /**
     *  获取已开机时间(含休眠)
     * */
    public static String getUpTime() {
        long upTime = SystemClock.elapsedRealtime()/1000;
        return convert(upTime);
    }
    private static String convert(long t) {
        int s = (int)(t % 60);
        int m = (int)((t / 60) % 60);
        int h = (int)((t / 3600));

        return h + ":" + pad(m) + ":" + pad(s);
    }
    private static String pad(int n) {
        if (n >= 10) {
            return String.valueOf(n);
        } else {
            return "0" + String.valueOf(n);
        }
    }

    /**
     *  获取设备指纹
     * */

    public static String getFingerprint(Context context) {
        String fingerprint = null;
        fingerprint = readFingerprintFromFile(context);
        if (TextUtils.isEmpty(fingerprint)) {
            fingerprint = createFingerprint(context);
        } else {
            Log.e(TAG, "从文件中获取设备指纹：" + fingerprint);
        }
        return fingerprint;
    }

    /**
     * 从SharedPreferences 文件获取设备指纹
     *
     * @return fingerprint 设备指纹
     */
    private static String readFingerprintFromFile(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(FINGER_PRINT, null);
    }

    /**
     * 生成一个设备指纹（耗时50毫秒以内）：
     * 1.IMEI + 设备硬件信息（主要）+ ANDROID_ID + WIFI MAC组合成的字符串
     * 2.用MessageDigest将以上字符串处理成32位的16进制字符串
     *
     * @param context
     * @return 设备指纹
     */
    public static String createFingerprint(Context context) {
        long startTime = System.currentTimeMillis();

        // 1.IMEI
        TelephonyManager TelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        final String imei = TelephonyMgr.getDeviceId();

        Log.i(TAG, "imei=" + imei);

        //2.android 设备信息（主要是硬件信息）
        final String hardwareInfo = Build.ID + Build.DISPLAY + Build.PRODUCT
                + Build.DEVICE + Build.BOARD /*+ Build.CPU_ABI*/
                + Build.MANUFACTURER + Build.BRAND + Build.MODEL
                + Build.BOOTLOADER + Build.HARDWARE /* + Build.SERIAL */
                + Build.TYPE + Build.TAGS + Build.FINGERPRINT + Build.HOST
                + Build.USER;
        //Build.SERIAL => 需要API 9以上
        Log.i(TAG, "hardward info=" + hardwareInfo);

        /* 3. Android_id 刷机和恢复出厂会变
         * A 64-bit number (as a hex string) that is randomly
         * generated when the user first sets up the device and should remain
         * constant for the lifetime of the user's device. The value may
         * change if a factory reset is performed on the device.
         */
        final String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i(TAG, "android_id=" + androidId);


        /**
         * 4. The WLAN MAC Address string（个别手机刚开机完成后会获取不到，舍去）
         */
        /*WifiManager wifiMgr = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        final String wifiMAC = wifiMgr.getConnectionInfo().getMacAddress();
        Log.i(TAG,"wifi Mac="+wifiMAC);*/


        /*
         *  5. get the bluetooth MAC Address
         *  （有部分手机，如三星GT-S5660 2.3.3，当蓝牙关闭时，获取不到蓝牙MAC;
         *   所以为了保证 device id 的不变，舍去）
         */
        /*BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bt_MAC = null;
        if (bluetoothAdapter == null) {
            Log.e(TAG, "bluetoothAdapter is null");
        } else {
            bt_MAC = bluetoothAdapter.getAddress();
        }
        Log.i(TAG,"m_szBTMAC="+bt_MAC);*/


        // Combined Device ID
        final String deviceId = imei + hardwareInfo + androidId/* + wifiMAC + bt_MAC*/;
        Log.i(TAG, "deviceId=" + deviceId);

        // 创建一个 messageDigest 实例
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //用 MessageDigest 将 deviceId 处理成32位的16进制字符串
        msgDigest.update(deviceId.getBytes(), 0, deviceId.length());
        // get md5 bytes
        byte md5ArrayData[] = msgDigest.digest();

        // create a hex string
        String deviceUniqueId = new String();
        for (int i = 0; i < md5ArrayData.length; i++) {
            int b = (0xFF & md5ArrayData[i]);
            // if it is a single digit, make sure it have 0 in front (proper
            // padding)
            if (b <= 0xF) deviceUniqueId += "0";
            // add number to string
            deviceUniqueId += Integer.toHexString(b);
//          Log.i(TAG,"deviceUniqueId=" + deviceUniqueId);
        } // hex string to uppercase
        deviceUniqueId = deviceUniqueId.toUpperCase();
        Log.d(TAG, "生成的设备指纹：" + deviceUniqueId);

        Log.e(TAG, "生成DeviceId 耗时：" + (System.currentTimeMillis() - startTime));
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(FINGER_PRINT, deviceUniqueId).commit();

        return deviceUniqueId;
    }


    /**
     * 功能描述：判断当前设备是否为模拟器
     * 参数：
     */
    public static boolean isEmulator() {
        //获取手机的Serial码
        String serial = Build.SERIAL;
        if (serial.equalsIgnoreCase("unknown") || serial.equalsIgnoreCase("android")) {
            return true;
        }
        return false;
    }

    /**
     *  判断设备是否Root
     * */
    /** 判断手机是否root，不弹出root请求框<br/> */
    public static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath))
            return true;
        if (new File(xBinPath).exists() && isExecutable(xBinPath))
            return true;
        return false;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            Log.i(TAG, str);
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(p!=null){
                p.destroy();
            }
        }
        return false;
    }


    /**
     *  获取该设备是否改机 (通过Xposed)
     * */
    private static final String XPOSED_HELPERS = "de.robv.android.xposed.XposedHelpers";
    private static final String XPOSED_BRIDGE = "de.robv.android.xposed.XposedBridge";

    //手动抛出异常，检查堆栈信息是否有xp框架包
    public static boolean isEposedExistByThrow() {
        try {
            throw new Exception("gg");
        } catch (Exception e) {
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().contains(XPOSED_BRIDGE)) return true;
            }
            return false;
        }
    }

    //检查xposed包是否存在
    public static boolean isXposedExists() {
        try {
            Object xpHelperObj = ClassLoader
                    .getSystemClassLoader()
                    .loadClass(XPOSED_HELPERS)
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return true;
        } catch (IllegalAccessException e) {
            //实测debug跑到这里报异常
            e.printStackTrace();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try {
            Object xpBridgeObj = ClassLoader
                    .getSystemClassLoader()
                    .loadClass(XPOSED_BRIDGE)
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return true;
        } catch (IllegalAccessException e) {
            //实测debug跑到这里报异常
            e.printStackTrace();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //尝试关闭xp的全局开关，亲测可用
    public static boolean tryShutdownXposed() {
        if (isEposedExistByThrow()) {
            Field xpdisabledHooks = null;
            try {
                xpdisabledHooks = ClassLoader.getSystemClassLoader()
                        .loadClass(XPOSED_BRIDGE)
                        .getDeclaredField("disableHooks");
                xpdisabledHooks.setAccessible(true);
                xpdisabledHooks.set(null, Boolean.TRUE);
                return true;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        } else return true;
    }


    /**
     *  时区判定
     * */
    public static boolean timeZoneJudgment () {
        if (getCurrentTimeZone().equals("GMT+05:30")) {
            return true;
        } else {
            return false;
        }
    }


    private TelephonyManager telephonyManager;

    /**
     * 获取手机服务商信息
     */
    public static String getProvidersName(Context context) {
        String ProvidersName = "N/A";
        String IMSI;
        try{
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            IMSI = manager.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            System.out.println(IMSI);
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ProvidersName;
    }

    /**
     *  获取IMSI码
     * */
    public static String getImsiCode (Context context) {
        String IMSI;
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        IMSI = manager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        return IMSI;
    }

    /**
     *  IMSI印度国家判定
     * */
    public static boolean indiaImsiJudgment (Context context) {
        if ((getImsiCode(context).substring(0, 3).equals("404") || getImsiCode(context).substring(0,3).equals("405")) && getImsiCode(context).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *  获取iccid的值
     * */
    public static String getIccidCode (Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);// 取得相关系统服务
        return telephonyManager.getSimSerialNumber();
    }

    /**
     *  IMSI印度国家判定
     * */
    public static boolean indiaIccidJudgment (Context context) {
        if (getImsiCode(context).substring(3, 5).equals("91") || getImsiCode(context).length() > 0) {
            return true;
        } else  {
            return false;
        }
    }


    /**
     *  获取手机号码
     * */
    public static String getNativePhoneNumber (Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String NativePhoneNumber=null;
        NativePhoneNumber= tm.getLine1Number();
        return NativePhoneNumber;
    }


    public static String getPhoneInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb = new StringBuilder();

        sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
        sb.append("\nLine1Number = " + tm.getLine1Number());
        sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
        sb.append("\nNetworkType = " + tm.getNetworkType());
        sb.append("\nPhoneType = " + tm.getPhoneType());
        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
        sb.append("\nSimOperator = " + tm.getSimOperator());
        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
        sb.append("\nSimState = " + tm.getSimState());
        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
        sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
        return sb.toString();
    }


    /**
     * 获取运营商名字
     * @return int
     */

    public static String getOperatorName (Context content) {
        /*
         * getSimOperatorName()就可以直接获取到运营商的名字
         * 也可以使用IMSI获取，getSimOperator()，然后根据返回值判断，例如"46000"为移动
         * IMSI相关链接：http://baike.baidu.com/item/imsi
         */
        TelephonyManager telephonyManager = (TelephonyManager) content.getSystemService(Context.TELEPHONY_SERVICE);
        // getSimOperatorName就可以直接获取到运营商的名字
        return telephonyManager.getSimOperatorName();
    }

    /**
     * @Title: getNetWorkState
     *
     * @Description: 获取当前网络状态
     *
     * @param context
     * @return int
     */
    public static String getNetWorkState(Context context) {

        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return "无线网络";
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return "移动网络";
            }
        } else {
            return "没有连接网络";
        }
        return "没有连接网络";
    }

    /**
     * 返回手机移动数据的状态**
     @param pContext*
     @param arg      默认填null*
     @return true 连接 false 未连接
     **/

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static boolean getMobileDataState(Context pContext, Object[] arg) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            Class ownerClass = mConnectivityManager.getClass();
            Class[] argsClass = null;
            if (arg != null) {
                argsClass = new Class[1];
                argsClass[0] = arg.getClass();
            }
            Method method = ownerClass.getMethod("getMobileDataEnabled", argsClass);
            Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);
            return isOpen;
        } catch (Exception e) {
            return false;
        }
    }



    /**
     * @Title: getIpAddress
     *
     * @Description: 获取设备ip地址
     *
     * @return String
     */
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces();
                 enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String[] platforms = {
            "http://pv.sohu.com/cityjson",
            "http://pv.sohu.com/cityjson?ie=utf-8",
            "http://ip.chinaz.com/getip.aspx",
            "http://myip.ipip.net"
    };
    public static String getOutNetIP (Context context, int index) {
        if (index < platforms.length) {
            BufferedReader buff = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(platforms[index]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);//读取超时
                urlConnection.setConnectTimeout(5000);//连接超时
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                    InputStream is = urlConnection.getInputStream();
                    buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        builder.append(line);
                    }

                    buff.close();//内部会关闭 InputStream
                    urlConnection.disconnect();

                    if (index == 0 || index == 1) {
                        //截取字符串
                        int satrtIndex = builder.indexOf("{");//包含[
                        int endIndex = builder.indexOf("}");//包含]
                        String json = builder.substring(satrtIndex, endIndex + 1);//包含[satrtIndex,endIndex)
                        JSONObject jo = new JSONObject(json);
                        String ip = jo.getString("cip");

                        return ip;
                    } else if (index == 2) {
                        JSONObject jo = new JSONObject(builder.toString());
                        return jo.getString("ip");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return getInNetIp(context);
        }
        return getOutNetIP(context, ++index);
    }

    public static void getHtml (Context context) {
        String path = "http://myip.ipip.net";

        WebView webView = new WebView(context);
        webView.setVisibility(View.GONE);
        WebSettings settings = webView.getSettings();

        //支持javascript脚本  会 产生 跨站脚本攻击
        settings.setJavaScriptEnabled(true);
        // 设置编码 UTF-8
        settings.setDefaultTextEncodingName("utf-8");
        /**
         * 缓存模式如下：
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存
        settings.setAllowContentAccess(true);   //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true);  //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); // 设置可以自动加载图片

        webView.loadUrl(path);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i(TAG, "onPageStarted..." + url);

            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.i(TAG, "onLoadResource..." + url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i(TAG, "onPageFinished..." + url);
                //view.loadUrl("javascript:window.java_obj.getDes(des);");

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i(TAG, "onReceivedError..." + error);

            }
        });



    }

    public static void getSyn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建OkHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    //创建Request
                    Request request = new Request.Builder()
                            .url("https://myip.ipip.net")//访问连接
                            .get()
                            .build();
                    //创建Call对象
                    Call call = client.newCall(request);
                    //通过execute()方法获得请求响应的Response对象
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        //处理网络请求的响应，处理UI需要在UI线程中处理
                        //...
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 读取输入流，得到html的二进制数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    public static String getPsdnIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }


    public static String getInNetIp(Context context) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);

        return ip;
    }

    //这段是转换成点分式IP的码
    private static String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) &     0xFF) + "." + (ip >> 24 & 0xFF);
    }

    /**
     *  获取WiFi名称
     * */
    public static String getConnectWifiSsid(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID",wifiInfo.getSSID());
        return wifiInfo.getSSID();
    }

    private static final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                List<ScanResult> mScanResults = ((WifiManager)c.getSystemService(WIFI_SERVICE)).getScanResults();
                // add your logic here
                StringBuilder sb = new StringBuilder();
                for (ScanResult sc : mScanResults) {
                    sb.append(sc.BSSID);
                }
                StorageUtil.getInstance().setStringCommit("wifiList", sb.toString());
            }
        }
    };
    public static void scanWifiList(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        context.registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mWifiManager.startScan();
    }

    public static String getScanWifiInfo (Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID",wifiInfo.getSSID());
        return wifiInfo.getBSSID();
    }

    public static List<WifiBean> getWifiList(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        List<ScanResult> scanWifiList = wifiManager.getScanResults();
        List<WifiBean> wifiList = new ArrayList<>();
//        StringBuffer stringBuffer = new StringBuffer();
        if (scanWifiList != null && scanWifiList.size() > 0) {
            for (int i = 0; i < scanWifiList.size(); i++) {
                ScanResult scanResult = scanWifiList.get(i);
                if (!scanResult.SSID.isEmpty()) {
                    WifiBean wb = new WifiBean();
                    wb.setBSSID(scanResult.BSSID);
                    wb.setSSID(scanResult.SSID);
                    wifiList.add(wb);
                }
            }
        }
        return wifiList;
    }

    /**
     *  获取设备VPN的状态
     * */
    public static String getVPNStatus (Context context) {
        return "";
    }


    /**
     *  格式化, 保留两位小数
         * */
    public static String formatSize(long size) {
        String suffix = null;
        float fSize = 0;

        if (size >= 1024) {
            suffix = "KB";
            fSize = size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null){
            resultBuffer.append(suffix);
        }
        return resultBuffer.toString();
    }

}

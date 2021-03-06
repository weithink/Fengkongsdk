package com.weithink.fengkong;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.weithink.fengkong.bean.LocationInfo;
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

    private OneTimeWorkRequest uploadWorkRequest = null;
    private Context context;


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

    public void initSDK(Context context) {
        this.context = context;
        if (this.context == null) {
            throw new RuntimeException("You must call initSDK first！");
        }
        startRequestPermissions();
    }


    public void syncData(String appId, String appPackageName, String borrowId,
                         String userPhone, List<LocationInfo> locationInfoList,
                         String extend, String userId,  String url) {
        StorageUtil util = StorageUtil.getInstance();
        util.setStringCommit("appId", appId);
        try {
            util.setStringCommit("version", context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        util.setStringCommit("AppPackageName", appPackageName);
        util.setStringCommit("borrowId", borrowId);
        util.setStringCommit("setUserPhone", userPhone);
        util.setStringCommit("url", url);
        util.setDataList("locationList", locationInfoList);
        util.setStringCommit("extend", extend);
        util.setStringCommit("userId", userId);
        if (MainActivity.notShowPermissionList.size() == 0 && MainActivity.mPermissionList.size() == 0) {
            startService();
        }else {
            startRequestPermissions();
        }
    }
    private void startService() {
        DeviceInfoUtil.scanWifiList(WeithinkFengkong.getInstance().getContext());
        WeithinkFactory.getLogger().error("AAA>> %s", "startService");
        DeviceInfoUtil.scanWifiList(WeithinkFengkong.getInstance().getContext());
//        startService(new Intent((Context) this, CollectService.class));
        execute();
//        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData(WORK_NAME).observe(MainActivity.this, new Observer<List<WorkInfo>>() {
//            @Override
//            public void onChanged(List<WorkInfo> workInfos) {
//                Log.i("AAA》》》onChanged()->", "workInfo:" + workInfos.get(0));
//            }
//        });

    }
    /**
     * 执行上传操作
     */
    public void execute() {
        WeithinkFengkong.getInstance().uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .build();
        WorkManager.getInstance(WeithinkFengkong.getInstance().getContext()).
                enqueueUniqueWork(WORK_NAME,
                        ExistingWorkPolicy.REPLACE,
                        uploadWorkRequest);
    }

    public static void startRequestPermissions() {
        Intent intent = new Intent(WeithinkFengkong.getInstance().getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        WeithinkFengkong.getInstance().getContext().startActivity(intent);
    }

}

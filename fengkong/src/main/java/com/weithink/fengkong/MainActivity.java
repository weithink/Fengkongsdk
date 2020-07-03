package com.weithink.fengkong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private final int permissionRequestCode = 100;
    private List<String> mPermissionList = new ArrayList<>();
    private String[] permissions = new String[]{"android.permission.READ_SMS", "android.permission.READ_CONTACTS",
            "android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR",
            "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_PHONE_STATE", "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_CALL_LOG"};

    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onCreate(savedInstanceState);
        WeithinkFactory.getLogger().debug("MainActivity onCreate ====== ");
        initPermission();
    }
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initPermission() {
        this.mPermissionList.clear();
        for (String permission : this.permissions) {
            if (ContextCompat.checkSelfPermission( this, permission) != 0)
                this.mPermissionList.add(permission);
        }
        if (this.mPermissionList.size() > 0) {
            ActivityCompat.requestPermissions( this, this.permissions, permissionRequestCode);
        }else {
            startService();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionRequestCode == requestCode) {
            startService();
        }
    }


    private void startService() {
        Log.e("AAA>>>>", "startService");
//        startService(new Intent((Context) this, CollectService.class));
        WeithinkFengkong.getInstance().execute();
        finish();
//        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData(WORK_NAME).observe(MainActivity.this, new Observer<List<WorkInfo>>() {
//            @Override
//            public void onChanged(List<WorkInfo> workInfos) {
//                Log.i("AAA》》》onChanged()->", "workInfo:" + workInfos.get(0));
//            }
//        });

    }
}
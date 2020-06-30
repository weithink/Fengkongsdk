package com.weithink.fengkong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


import com.weithink.fengkong.services.CollectService;
import com.weithink.fengkong.work.UploadWorker;

import java.util.ArrayList;
import java.util.List;

import static com.weithink.fengkong.Constants.WORK_NAME;

public class MainActivity extends AppCompatActivity {
    private final int permissionRequestCode = 100;
    private List<String> mPermissionList = new ArrayList<>();
    private String[] permissions = new String[]{"android.permission.READ_SMS", "android.permission.READ_CONTACTS", "android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

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
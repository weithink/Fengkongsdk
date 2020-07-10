package com.weithink.fengkong;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.weithink.fengkong.util.DeviceInfoUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.PUT;

public class MainActivity extends Activity implements View.OnClickListener {
    private LinearLayout content_box;
    private TextView content;
    private Button button;
    private final int permissionRequestCode = 100;
    public static List<String> mPermissionList = new ArrayList<>();
    public static List<String> notShowPermissionList = new ArrayList<>();//不再提示的权限
    private String[] permissions = new String[]{"android.permission.READ_SMS",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_CALENDAR",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_PHONE_STATE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_CALL_LOG",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.ACCESS_NETWORK_STATE",

    };
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        content_box = findViewById(R.id.content_box);
        content = findViewById(R.id.content);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        WeithinkFactory.getLogger().debug("MainActivity onCreate ====== ");
        initPermission();
    }

    protected void onDestroy() {
        super.onDestroy();
        WeithinkFengkong.startRequestPermissions();
    }

    private void initPermission() {
        mPermissionList.clear();
        notShowPermissionList.clear();
        for (String permission : this.permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != 0) {
                this.mPermissionList.add(permission);
            }
        }
        if (this.mPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, this.permissions, permissionRequestCode);
        } else {
            permissionOk();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                //判断权限的结果，如果有被拒绝，就return
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        notShowPermissionList.add(permissions[i]);
                    }
                }
            }
            if (mPermissionList.size() == 0 && notShowPermissionList.size() == 0) {
                permissionOk();
            } else {
                content_box.setVisibility(View.VISIBLE);
                String tips = "We need these permissions or you will not be able to proceed to the next step\n\n";
                StringBuilder perms = new StringBuilder();
                if (notShowPermissionList.size()>0){
                    for (int i = 0; i < notShowPermissionList.size(); i++) {
                        String ss = notShowPermissionList.get(i);
                        perms.append((i+1)+"."+ss.substring(ss.lastIndexOf(".")+1)+"\n");
                    }
                } else if (mPermissionList.size() > 0) {
                    for (int i = 0; i < mPermissionList.size(); i++) {
                        String ss = mPermissionList.get(i);
                        perms.append((i+1)+"."+ss.substring(ss.lastIndexOf(".")+1)+"\n");
                    }
                }

                perms.append("\nClick the button below, then click permissions, and open all permissions. Then back to this page, we'll refresh the status");
                content.setText(tips+perms);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == permissionRequestCode) {
            initPermission();
        }
    }

    /**
     * 权限获取成果
     */
    private void permissionOk() {
        finish();
    }

    @Override
    public void onClick(View v) {
        //请求所有权限
        if (v.getId() == R.id.button) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, permissionRequestCode);
        }
    }
}
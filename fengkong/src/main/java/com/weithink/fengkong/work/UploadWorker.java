package com.weithink.fengkong.work;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFengkong;
import com.weithink.fengkong.bean.Contact;
import com.weithink.fengkong.network.UtilNetworking;
import com.weithink.fengkong.services.TaskService;
import com.weithink.fengkong.util.StorageUtil;

import java.util.HashMap;

public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            TaskService ts = new TaskService();
            ts.excute();
        } catch (final Exception e) {
            e.printStackTrace();
            new Runnable(){
                @Override
                public void run() {
                    WeithinkFengkong.getInstance().startRequestPermissions();
                    HashMap<String, String> param = new HashMap<>();
                    param.put("userId", StorageUtil.getInstance().getString("userId",""));
                    param.put("message", e.getMessage());
                    UtilNetworking.sendPostI("/data/error", Constants.VERSION, param);
                }
            };
            return Result.retry();
        }
        return Result.success();
    }
}

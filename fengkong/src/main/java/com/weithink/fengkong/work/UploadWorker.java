package com.weithink.fengkong.work;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.weithink.fengkong.services.TaskService;

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
        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }
        return Result.success();
    }
}

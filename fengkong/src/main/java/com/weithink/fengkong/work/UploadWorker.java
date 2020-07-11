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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import static com.weithink.fengkong.Constants.baseUrl;

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
            upErrorMsg(e);
            return Result.retry();
        }
        return Result.success();
    }

    private void upErrorMsg(final Exception e) {
        String url = StorageUtil.getInstance().getString("url", baseUrl);
        String postUrl = url + "/data/error";
        String str="";
        ByteArrayOutputStream bos =null;
        PrintStream ps =null;
        try {
            bos = new ByteArrayOutputStream();
            ps = new PrintStream(bos);
            e.printStackTrace(ps);
            str = new String(bos.toByteArray());
            ps.flush();
            ps.close();
            bos.flush();
            bos.close();
        } catch (Exception e1) {
            if (ps != null) {
                ps.flush();
                ps.close();
            }
            try {
                bos.flush();
                bos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("userId", StorageUtil.getInstance().getString("userId", ""));
        param.put("message", str);
        UtilNetworking.sendPostI(postUrl, Constants.VERSION, param);
    }
}

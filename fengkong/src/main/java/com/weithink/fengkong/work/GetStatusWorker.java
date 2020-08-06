package com.weithink.fengkong.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.UploadStatus;
import com.weithink.fengkong.network.UtilNetworking;
import com.weithink.fengkong.util.StorageUtil;
import com.weithink.fengkong.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class GetStatusWorker extends Worker {
    public GetStatusWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            UploadStatus up = getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }
        return null;
    }

    /**
     * 服务器数据状态
     */
    private UploadStatus getStatus() throws Exception {
        StorageUtil util = StorageUtil.getInstance();
        String status = util.getString("upstatus", "");
        if ("".equals(status)) {
            UtilNetworking.HttpResponse response = UtilNetworking.sendPostI(Constants.ApiType.STATUS_DATA + "?userId=10545");
            WeithinkFactory.getLogger().debug("AAAAAA " + Constants.ApiType.STATUS_DATA + " >>>%s", response.response);
            util.setStringCommit("upstatus", status);
        }
        UploadStatus up = new Gson().fromJson(status, UploadStatus.class);
        return up;
    }
}

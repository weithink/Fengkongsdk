package com.weithink.fengkong.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.UploadStatus;
import com.weithink.fengkong.network.UtilNetworking;
import com.weithink.fengkong.util.StorageUtil;

public class GetMyStatusWorker extends ListenableWorker {
    public GetMyStatusWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver<Result>() {
            @Nullable
            @Override
            public Data attachCompleter(@NonNull CallbackToFutureAdapter.Completer<Result> completer) throws Exception {
                try {
                    String status = getStatus();
                    Data.Builder builder = new Data.Builder();
                    builder.putString("result", status);
                    return builder.build();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
//                return null;
            }
        });
    }


    public Result doWork() {
        try {
            String status = getStatus();
            Data.Builder builder = new Data.Builder();
            builder.putString("result", status);
            return Result.success(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }
    }





    /**
     * 服务器数据状态
     */
    private String getStatus() throws Exception {
        StorageUtil util = StorageUtil.getInstance();
        String status = util.getString("upstatus", "");
        if ("".equals(status)) {
            UtilNetworking.HttpResponse response = UtilNetworking.sendPostI(Constants.ApiType.STATUS_DATA + "?userId=10545");
            WeithinkFactory.getLogger().debug("AAAAAA " + Constants.ApiType.STATUS_DATA + " >>>%s", response.response);
            status = response.response;
            util.setStringCommit("upstatus", status);
        }
        new Gson().fromJson(status, UploadStatus.class);
        return status;
    }
}

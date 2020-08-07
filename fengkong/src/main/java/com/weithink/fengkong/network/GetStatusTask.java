package com.weithink.fengkong.network;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.UploadStatus;
import com.weithink.fengkong.util.Util;

public abstract class GetStatusTask extends AsyncTask<String, Integer, UploadStatus> {
    @Override
    protected UploadStatus doInBackground(String... strings) {

        UploadStatus restr = null;
        do {
            restr = getData(strings[0]);
            if (null==restr) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (null==restr);
        return restr;
    }

    private UploadStatus getData(String userId) {
        try {
            if (Util.isEmpty(userId)) {
                throw new Exception("用户id 不正确,请检查: userId:"+userId);
            }
            UtilNetworking.HttpResponse response = UtilNetworking.sendPostI(Constants.ApiType.STATUS_DATA + "?userId=" + userId);
            WeithinkFactory.getLogger().debug("AAAAAA " + Constants.ApiType.STATUS_DATA + " >>>%s", response.response);
            String status = response.response;
            UploadStatus us = new Gson().fromJson(status, UploadStatus.class);
            return us;
        } catch (Exception e) {
            ErrUtils.upErrorMsg(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(UploadStatus s) {
        super.onPostExecute(s);
        onPostResult(s);
    }

    public abstract void onPostResult(UploadStatus s);
}

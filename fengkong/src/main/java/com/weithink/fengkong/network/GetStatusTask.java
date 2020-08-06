package com.weithink.fengkong.network;

import android.os.AsyncTask;

import com.weithink.fengkong.Constants;
import com.weithink.fengkong.WeithinkFactory;

public abstract class GetStatusTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... strings) {

        String restr = "";
        do {
            restr = getData(strings[0]);
            if ("".equals(restr)) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while ("".equals(restr));
        return restr;
    }

    private String getData(String userId) {
        try {
            UtilNetworking.HttpResponse response = UtilNetworking.sendPostI(Constants.ApiType.STATUS_DATA + "?userId=" + userId);
            WeithinkFactory.getLogger().debug("AAAAAA " + Constants.ApiType.STATUS_DATA + " >>>%s", response.response);
            String status = response.response;
            return status;
        } catch (Exception e) {
            ErrUtils.upErrorMsg(e);
            return "";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onPostResult(s);
    }

    public abstract void onPostResult(String s);
}

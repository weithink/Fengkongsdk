package com.weithink.fengkong.network;

import android.text.TextUtils;

import com.weithink.fengkong.Constants;
import com.weithink.fengkong.util.StorageUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class ErrUtils {
    public static void upErrorMsg(final Exception e) {
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
    public static void upErrorMsg(String  msg) {
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

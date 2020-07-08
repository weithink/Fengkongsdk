package com.weithink.fengkong.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.WeithinkFengkong;
import com.weithink.fengkong.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class StorageUtil {
    private static StorageUtil sInstance;
    private final SharedPreferences mPreferences;

    public static StorageUtil getInstance() {
        if (sInstance == null)
            synchronized (StorageUtil.class) {
                if (sInstance == null)
                    sInstance = new StorageUtil();
            }
        return sInstance;
    }

    private StorageUtil() {
        Context context = WeithinkFengkong.getInstance().getContext();
        WeithinkFactory.getLogger().debug("StorageUtil context ====== %s" , context);
        this.mPreferences = context.getSharedPreferences("WeithinkFengKong", 0);
    }

    public boolean setStringCommit(String key, String value) {
        return this.mPreferences.edit()
                .putString(key, value)
                .commit();
    }

    public boolean setBooleanCommit(String key, boolean value) {
        return this.mPreferences.edit()
                .putBoolean(key, value)
                .commit();
    }

    public boolean setFloatCommit(String key, float value) {
        return this.mPreferences.edit()
                .putFloat(key, value)
                .commit();
    }

    public boolean setIntCommit(String key, int value) {
        return this.mPreferences.edit()
                .putInt(key, value)
                .commit();
    }

    public boolean setLongCommit(String key, long value) {
        return this.mPreferences.edit()
                .putLong(key, value)
                .commit();
    }

    public String getString(String key, String defaultValue) {
        return this.mPreferences.getString(key, defaultValue);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.valueOf(this.mPreferences.getBoolean(key, defaultValue));
    }

    public float getFloat(String key, float defaultValue) {
        return this.mPreferences.getFloat(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return this.mPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return this.mPreferences.getLong(key, defaultValue);
    }

    public <T> boolean setDataList(String key, List<T> value) {
        if (value == null)
            return this.mPreferences.edit()
                    .putString(key, "")
                    .commit();
        Gson gson = new Gson();
        String strJson = gson.toJson(value);
        return this.mPreferences.edit()
                .putString(key, strJson)
                .commit();
    }

    public <T> List<T> getDataList(String key, Class<T> cls) {
        List<T> dataList = new ArrayList<>();
        String strJson = this.mPreferences.getString(key, null);
        if (null == strJson)
            return dataList;
        Gson gson = new Gson();
        JsonArray array = (new JsonParser()).parse(strJson).getAsJsonArray();
        for (JsonElement jsonElement : array)
            dataList.add((T) gson.fromJson(jsonElement, cls));
        return dataList;
    }

    public boolean clearData() {
        return this.mPreferences.edit().clear().commit();
    }
}

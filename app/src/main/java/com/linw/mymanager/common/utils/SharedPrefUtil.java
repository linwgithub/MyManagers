package com.linw.mymanager.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2016/1/5.
 * SharedPrefUtil 是个单例
 */
public class SharedPrefUtil {
    private static Context mContext;
    private static SharedPreferences sPref;

    private static SharedPrefUtil singleton;

    public static SharedPrefUtil getSingleInstance(Context context){
        synchronized (SharedPrefUtil.class) {
            SharedPrefUtil.mContext = context;
            sPref = PreferenceManager.getDefaultSharedPreferences(mContext);
            if (null == singleton) {
                singleton = new SharedPrefUtil();
            }
        }

        return singleton;
    }

    private SharedPrefUtil() { }

    // userId: 标示用户唯一的ID，若不区分用户，可传入null或空字符串
    public boolean getBoolKeyVal(String userId, String key, boolean defaultVal) {
        if (key == null || key.length() == 0) {
            return defaultVal;
        }

        String wholeKey = (userId != null && userId.length() > 0) ? userId + key : key;
        return sPref.getBoolean(wholeKey, defaultVal);
    }

    public boolean setBoolKey(String userId, String key, boolean val) {
        if (key == null || key.length() == 0) {
            return false;
        }

        String wholeKey = (userId != null && userId.length() > 0) ? userId + key : key;
        return sPref.edit().putBoolean(wholeKey, val).commit();
    }

    public int getIntKeyVal(String userId, String key, int defaultVal) {
        if (key == null || key.length() == 0) {
            return defaultVal;
        }

        String wholeKey = (userId != null && userId.length() > 0) ? userId + key : key;
        return sPref.getInt(wholeKey, defaultVal);
    }

    public boolean setIntKey(String userId, String key, int val) {
        if (key == null || key.length() == 0) {
            return false;
        }

        String wholeKey = (userId != null && userId.length() > 0) ? userId + key : key;
        return sPref.edit().putInt(wholeKey, val).commit();
    }

    public String getStringKeyVal(String userId, String key, String defaultVal) {
        if (key == null || key.length() == 0) {
            return "";
        }

        String wholeKey = (userId != null && userId.length() > 0) ? userId + key : key;
        return sPref.getString(wholeKey, defaultVal);
    }

    public boolean setStringKey(String userId, String key, String val) {
        if (key == null || key.length() == 0) {
            return false;
        }

        String wholeKey = (userId != null && userId.length() > 0) ? userId + key : key;
        return sPref.edit().putString(wholeKey, val).commit();
    }
}

package com.khunglong.xanh.utils;

import org.json.JSONObject;

import android.util.Log;

public class MsUtils {
    public static boolean iSJsonValueAvailable(JSONObject ja, String value) {
        if (ja.has(value)) {
            return true;
        } else {
            Log.e("", ">>>ERROR JSON parse value:" + value);
            return false;
        }
    }
}

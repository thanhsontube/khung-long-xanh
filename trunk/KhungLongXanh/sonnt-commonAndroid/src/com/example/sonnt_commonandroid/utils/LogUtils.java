package com.example.sonnt_commonandroid.utils;

import android.util.Log;

public class LogUtils {
	private static final boolean DEBUG = true;
	private static final String TAG = "LogUtils";
	public static void v(String message) {
		if(DEBUG){
			Log.v(TAG, message);
		}
	}
	
	public static void v(String TAG, String message){
		if(DEBUG){
			Log.v(TAG, message);
		}
	}
	
	public static void e(String message) {
		Log.e(TAG, message);
	}

	public static void e(String TAG, String message) {
		Log.e(TAG, message);
	}
}

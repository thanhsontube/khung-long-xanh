package com.example.sonnt_commonandroid.utils;

import android.database.Cursor;

public class CursorUtils {
	private static final String TAG = "CursorUtils";
	static FilterLog log = new FilterLog(TAG);
	public static void getColumns(Cursor cursor){
		if(cursor == null){
			log.d("NECVN>>>" + "cursor == null");
			return;
		}
		int i = 0;
		String []arr = cursor.getColumnNames();
		for (String string : arr) {
			log.i("NECVN>>>" + "Columns " + i++ + ":" + string);
		}
	}
}

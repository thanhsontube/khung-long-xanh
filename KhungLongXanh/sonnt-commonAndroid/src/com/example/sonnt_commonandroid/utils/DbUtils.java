package com.example.sonnt_commonandroid.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUtils {

	public static void close(SQLiteDatabase db){
		if(db != null) {
			db.close();
		}
	}
	
	public static void close(SQLiteDatabase db, Cursor cursor){
		if(cursor != null) {
			cursor.close();
		}
		close(db);
	}

}

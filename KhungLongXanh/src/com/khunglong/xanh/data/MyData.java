package com.khunglong.xanh.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyData {
	MyDataHelper helper;
	SQLiteDatabase db;

	public MyData(Context context) {
		helper = new MyDataHelper(context);
		try {
			db = helper.getWritableDatabase();
		} catch (Exception e) {
			db = helper.getReadableDatabase();
		}
	}

	public boolean insertData(String text) {
		try {
			ContentValues values = new ContentValues();
			values.put("value", text);
			db.insertOrThrow(MyDataHelper.DATABASE_TABLE, null, values);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Cursor getData() {
		return db.query(MyDataHelper.DATABASE_TABLE, null, null, null, null, null, null);
	}
}

package com.example.sonnt_commonandroid.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtils {
	static ProgressDialog progressDialog = null;
	static OnCancelClick callback;
	public static synchronized void dialogStart(Context context, String title, String message) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(title);
		progressDialog.setMessage(message);
//		progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				callback.itemClick();
//			}
//		});
		progressDialog.show();
	}
	
	public static synchronized void dialogStart(Context context, String title, String message, final OnCancelClick callback) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(title);
		progressDialog.setMessage(message);
		progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				callback.itemClick();
			}
		});
		progressDialog.show();
	}
	
	public static void dialogStop() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
	public static interface OnCancelClick {
		public void itemClick();
	}
}

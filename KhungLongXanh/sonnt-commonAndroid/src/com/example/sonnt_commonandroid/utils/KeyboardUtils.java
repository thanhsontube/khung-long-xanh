package com.example.sonnt_commonandroid.utils;

import android.app.Service;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils {
	public static void hide(Context context, EditText ed){
		try {
			InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void show(Context context, EditText ed){
		try {
			InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
			imm.showSoftInput(ed, 0);
			//focous
			 ed.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showSoftwareKeyboard(final View v, final Handler handler) {
		if (v != null && v.getContext() != null) {
			v.requestFocus();
			handler.post(new Runnable() {
				@Override
				public void run() {
					InputMethodManager imm =
							(InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
				}
			});
		}
	}

	public void hideSoftwareKeyboard(Context context, View v) {
		InputMethodManager inputMethodManager =
				(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
}

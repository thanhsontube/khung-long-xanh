package com.khunglong.xanh.utils;

import android.app.ActionBar;

public class ActionBarUtils {
	
	public static void setTitleDefault(ActionBar actionBar, CharSequence title) {
		if (actionBar == null) {
			return;
		}
		actionBar.setTitle(title);
		actionBar.setSubtitle(null);
		actionBar.show();
	}
	
	public static void setIconeDefault(ActionBar actionBar, int  resId) {
		if (actionBar == null) {
			return;
		}
		actionBar.setIcon(resId);
		actionBar.show();
	}
	
	public static void update(ActionBar actionBar, CharSequence title, int  resId ) {
		setTitleDefault (actionBar, title);
		setIconeDefault(actionBar, resId);
	}
}

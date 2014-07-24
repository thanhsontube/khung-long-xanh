package com.khunglong.xanh.main.drawer;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface DrawerItemGenerator<T> {

	abstract public static class DrawerItem<T> {
		public final int icon;
		public final CharSequence title;
		public final CharSequence id;
		public final CharSequence extra;
		private T param;
		protected DrawerItem(int icon, CharSequence title, CharSequence id, CharSequence extra, T param) {
			this.icon = icon;
			this.title = title;
			this.id = id;
			this.extra = extra;
			this.param = param;
		}

		public T getParam() {
			return param;
		}

		abstract public View getView(LayoutInflater inflater, View convertView, ViewGroup parent);
	}

	List<T> generateMain();
}

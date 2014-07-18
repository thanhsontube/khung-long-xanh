package com.example.sonnt_commonandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * {@link SharedPreferences}を使用�?�る際�?�ユーティリティクラス�?��?�。
 */
public class PreferenceUtil {

	private PreferenceUtil() {
	};

	/**
	 * 指定�?��?�KEY�?�該当�?�る{@code String}データをSharedPreferences�?�ら�?�得�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @param defValue
	 *            key�?�該当�?�るプリファレンス�?�存在�?��?��?�場�?��?代�?り�?��?�得�?�る値。{@code null}�?止。
	 * @return プリファレンス�?�値
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static String getPreference(Context context, String key,
			String defValue) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}
		if (defValue == null) {
			throw new IllegalArgumentException("'defValue' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getString(key, defValue);
	}

	/**
	 * 指定�?��?�KEY�?�該当�?�る{@code int}データをSharedPreferences�?�ら�?�得�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @param defValue
	 *            key�?�該当�?�るプリファレンス�?�存在�?��?��?�場�?��?代�?り�?��?�得�?�る値。
	 * @return プリファレンス�?�値
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static int getPreference(Context context, String key, int defValue) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getInt(key, defValue);
	}

	/**
	 * 指定�?��?�KEY�?�該当�?�る{@code boolean}データをSharedPreferences�?�ら�?�得�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @param defValue
	 *            key�?�該当�?�るプリファレンス�?�存在�?��?��?�場�?��?代�?り�?��?�得�?�る値。
	 * @return プリファレンス�?�値
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static boolean getPreference(Context context, String key,
			boolean defValue) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getBoolean(key, defValue);
	}

	/**
	 * 指定�?��?�KEY�?�関連付�?��?�{@code String}データをSharedPreferences�?��?存�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @param value
	 *            プリファレンス�?��?存�?�る文字列。{@code null}�?止。
	 * 
	 * @return �?存�?��?功�?��?��?�を表�?�。{@code true}�?功。{@code false}失敗。
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static boolean setPreference(Context context, String key,
			String value) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}
		if (value == null) {
			throw new IllegalArgumentException("'value' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * 指定�?��?�KEY�?�関連付�?��?�{@code int}データをSharedPreferences�?��?存�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @return �?存�?��?功�?��?��?�を表�?�。{@code true}�?功。{@code false}失敗。
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static boolean setPreference(Context context, String key, int value) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * 指定�?��?�KEY�?�関連付�?��?�{@code boolean}データをSharedPreferences�?��?存�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @return �?存�?��?功�?��?��?�を表�?�。{@code true}�?功。{@code false}失敗。
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static boolean setPreference(Context context, String key,
			boolean value) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	/**
	 * SharedPreferences内�?��?存�?�れ�?��?�る全データを削除�?��?��?�。<br>
	 * 
	 * @param context
	 *            {@code null}�?止。
	 */
	public static void clearPreferences(Context context) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = preferences.edit();
		editor.clear().commit();
	}
	
	
	/**
	 * 指定�?��?�KEY�?�関連付�?��?�{@code long}データをSharedPreferences�?��?存�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @return �?存�?��?功�?��?��?�を表�?�。{@code true}�?功。{@code false}失敗。
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static boolean setPreference(Context context, String key, long value) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	/**
	 * 指定�?��?�KEY�?�該当�?�る{@code long}データをSharedPreferences�?�ら�?�得�?��?��?�。<br>
	 * {@link SharedPreferences#getDefaultSharedPreferences(Context)}を使用�?��?��?��?��?�。
	 * 
	 * @param context
	 *            コンテキスト。{@code null}�?止。
	 * @param key
	 *            プリファレンスを�?�得�?�る�?��?�?�キー。{@code null}�?止。
	 * @param defValue
	 *            key�?�該当�?�るプリファレンス�?�存在�?��?��?�場�?��?代�?り�?��?�得�?�る値。
	 * @return プリファレンス�?�値
	 * @throws IllegalArgumentException
	 *             {@code null} �?止�?�引�??数�?� {@code null} を渡�?��?�場�?�。
	 */
	public static long getPreference(Context context, String key, long defValue) {

		if (context == null) {
			throw new IllegalArgumentException("'context' must not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be null.");
		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences.getLong(key, defValue);
	}
	
	/*
	 * remove shred pre with Key
	 */
	public static Boolean remove(Context context, String key){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor edit = sharedPreferences.edit();
		return edit.remove(key).commit();
	}

	
}

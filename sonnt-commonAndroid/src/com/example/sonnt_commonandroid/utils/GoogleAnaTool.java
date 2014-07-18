package com.example.sonnt_commonandroid.utils;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;

import com.example.sonnt_commonandroid.R;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

public class GoogleAnaTool {
	private static final String TAG = "GoogleAnaTool";
	// private static GoogleAnalyticsTracker tracker = null;
	private static String mGA = "UA-32374846-1";

	public static void activityStart(Context context) {

		EasyTracker.getInstance(context).activityStart((Activity) context);

	}

	public static void activityStop(Context context) {

		EasyTracker.getInstance(context).activityStop((Activity) context);

	}

	public static void trackerSend(Context context, String category, String event, String label, Long value) {
		EasyTracker easyTracker = EasyTracker.getInstance(context);
		easyTracker.send(MapBuilder.createEvent(category, // Event category
															// (required)
				event, // Event action (required)
				label, // Event label
				value) // Event value
				.build());
	}

	public static void trackerView(Context context, String name, String keyGA) {
		EasyTracker easyTracker = EasyTracker.getInstance(context);
		easyTracker.set(Fields.SCREEN_NAME, name);
		easyTracker.send(MapBuilder.createAppView().build());

		GoogleAnalytics ga = GoogleAnalytics.getInstance(context);
		Tracker t = ga.getTracker(keyGA);
		Map<String, String> p = new HashMap<String, String>();
		p.put(Fields.HIT_TYPE, "pageview");
		p.put(Fields.PAGE, name);
		t.send(p);
	}

	// public static void trackPage (Context context, String page, String
	// trackingId) {
	// GoogleAnalytics.getInstance(context).getTracker(trackingId).;
	//
	// }
}

package com.khunglong.xanh.service;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MsConstant;

public class BaseService extends Service {
    private static final String TAG = "BaseService";
    FilterLog log = new FilterLog(TAG);

    private LocalBinder localBinder = new LocalBinder();
    private CommonBroadcastReceiver commonBroadcastReceiver;
    private PendingIntent pendingIntentAlarm;

    public class LocalBinder extends Binder {
        public BaseService getService(Context context) {
            return BaseService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    @Override
    public void onCreate() {
        log.d("log>>> " + "SERVICE onCreate ");
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MsConstant.ACTION_CHECK);
        commonBroadcastReceiver = new CommonBroadcastReceiver();
        registerReceiver(commonBroadcastReceiver, filter);

        Intent intent = new Intent();
        intent.setAction(MsConstant.ACTION_CHECK);
        pendingIntentAlarm = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        setAlarm(getApplicationContext(), MsConstant.ALARM_CHECK, pendingIntentAlarm);

    }

    @Override
    public void onDestroy() {
        log.e("log>>> " + "SERVICE onDestroy ");
        super.onDestroy();
        unregisterReceiver(commonBroadcastReceiver);
    }

    public static Intent getIntentService(Context context) {
        return new Intent().setClass(context, BaseService.class);
    }

    public static void setAlarm(Context context, int distance, PendingIntent pendingIntent) {
        Log.v("", "log>>> " + "setAlarm:" + distance);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, distance);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public class CommonBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            log.d("log>>>" + "onReceive:" + action);
            if (action.equals(MsConstant.ACTION_CHECK)) {
                log.d("log>>> " + "CommonBroadcastReceiver ACTION_CHECK");
                setAlarm(getApplicationContext(), MsConstant.ALARM_CHECK, pendingIntentAlarm);

            }
        }

    }

}

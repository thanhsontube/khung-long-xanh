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
import android.text.TextUtils;
import android.util.Log;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.example.sonnt_commonandroid.utils.PreferenceUtil;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.myfacebook.FbLoaderAlbumsList;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbMyFriendLoader;
import com.khunglong.xanh.myfacebook.object.FbAlbums;
import com.khunglong.xanh.myfacebook.object.FbAlbumsDto;
import com.khunglong.xanh.notification.NotificationUtils;

public class BaseService extends Service {
    private static final String TAG = "BaseService";
    FilterLog log = new FilterLog(TAG);

    private LocalBinder localBinder = new LocalBinder();
    private CommonBroadcastReceiver commonBroadcastReceiver;
    private PendingIntent pendingIntentAlarm;
    ResourceManager resource;
    private Context context;
    private int pagePosition = 0;

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
        context = getApplicationContext();
        resource = ResourceManager.getInstance();
        mFbLoaderManager = resource.getFbLoaderManager();
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
                pagePosition = 0;
                page = resource.getListPageResource().get(pagePosition).getFbName();
                stringNotification.setLength(0);
                controllerAlbums.load();
                // controllerFriend.load();

            }

        }
    }

    private String page;
    private boolean isLoading;
    private FbLoaderManager mFbLoaderManager;

    private StringBuilder stringNotification = new StringBuilder();
    private Controller controllerAlbums = new Controller() {

        @Override
        public void load() {
            isLoading = true;

            mFbLoaderManager.load(new FbLoaderAlbumsList(context, page) {

                @Override
                public void onFbLoaderSuccess(FbAlbums entry) {
                    // get Timeline Photos album;
                    for (final FbAlbumsDto element : entry.listFbAlbumsDto) {
                        if (element.getName().equalsIgnoreCase("Timeline Photos")) {
                            int count = element.getCount();
                            int saveCount;

                            if (page.equals(resource.getListPageResource().get(0).getFbName())) {
                                saveCount = PreferenceUtil.getPreference(context, MsConstant.KEY_PAGE_1,
                                        MsConstant.DEFAULT);

                                if (saveCount == MsConstant.DEFAULT) {
                                    PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_1, count);
                                } else {
                                    if (count != saveCount) {
                                        int iNew = count - saveCount;
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_1, count);
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_NEW_1, iNew);
                                        if (iNew > 0) {
                                            stringNotification.append(page + ":" + iNew + " NEW POST");

                                        }
                                    }
                                }

                            }

                            if (page.equals(resource.getListPageResource().get(1).getFbName())) {
                                saveCount = PreferenceUtil.getPreference(context, MsConstant.KEY_PAGE_2,
                                        MsConstant.DEFAULT);

                                if (saveCount == MsConstant.DEFAULT) {
                                    PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_2, count);
                                } else {
                                    if (count != saveCount) {
                                        int iNew = count - saveCount;
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_2, count);
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_NEW_2, iNew);
                                        if (iNew > 0) {
                                            stringNotification.append(page + ":" + iNew + " NEW POST;");

                                        }
                                    }
                                }
                            }

                            if (page.equals(resource.getListPageResource().get(2).getFbName())) {
                                saveCount = PreferenceUtil.getPreference(context, MsConstant.KEY_PAGE_3,
                                        MsConstant.DEFAULT);

                                if (saveCount == MsConstant.DEFAULT) {
                                    PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_3, count);
                                } else {
                                    if (count != saveCount) {
                                        int iNew = count - saveCount;
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_3, count);
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_NEW_3, iNew);
                                        if (iNew > 0) {
                                            stringNotification.append(page + ":" + iNew + " NEW POST;");

                                        }
                                    }
                                }
                            }

                            if (page.equals(resource.getListPageResource().get(3).getFbName())) {
                                saveCount = PreferenceUtil.getPreference(context, MsConstant.KEY_PAGE_4,
                                        MsConstant.DEFAULT);

                                if (saveCount == MsConstant.DEFAULT) {
                                    PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_4, count);
                                } else {
                                    saveCount--;
                                    if (count != saveCount) {
                                        int iNew = count - saveCount;
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_4, count);
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_NEW_4, iNew);
                                        if (iNew > 0) {
                                            stringNotification.append(page + ":" + iNew + " NEW POST;");

                                        }
                                    }
                                }
                            }
                            if (page.equals(resource.getListPageResource().get(4).getFbName())) {
                                saveCount = PreferenceUtil.getPreference(context, MsConstant.KEY_PAGE_5,
                                        MsConstant.DEFAULT);

                                if (saveCount == MsConstant.DEFAULT) {
                                    PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_5, count);
                                } else {
                                    saveCount--;
                                    if (count != saveCount) {
                                        int iNew = count - saveCount;
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_PAGE_5, count);
                                        PreferenceUtil.setPreference(context, MsConstant.KEY_NEW_5, iNew);
                                        if (iNew > 0) {
                                            stringNotification.append(page + ":" + iNew + " NEW POST");
                                        }
                                    }
                                }
                            }

                            log.d("log>>> " + "page:" + page + ";count:" + count);
                        }
                    }

                    if (pagePosition < 5) {
                        pagePosition++;
                        page = resource.getListPageResource().get(pagePosition).getFbName();
                        controllerAlbums.load();
                    }

                    else if (pagePosition == 5) {

                        Intent intent = new Intent(MsConstant.ACTION_UPDATE_NEWS);
                        sendBroadcast(intent);

                        if (!TextUtils.isEmpty(stringNotification.toString())) {
                            log.e("log>>> " + "NEWS:" + stringNotification.toString());
                            NotificationUtils.notify(context, stringNotification.toString(), R.drawable.ic_launcher);
                        } else {
                            log.e("log>>> " + "NO PAGE HAVE NEW POST");
                        }
                    }

                }

                @Override
                public void onFbLoaderStart() {
                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    log.e("log>>>" + "controllerAlbums onFbLoaderFail:" + e.toString());
                }
            });
        }
    };

    private Controller controllerFriend = new Controller() {

        @Override
        public void load() {
            mFbLoaderManager.load(new FbMyFriendLoader(context, "729372340455086/photos", null) {

                @Override
                public void onFbLoaderSuccess(String entry) {
                    log.d("log>>> " + "entry:" + entry);

                }

                @Override
                public void onFbLoaderStart() {
                    log.d("log>>> " + "entry:");

                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    log.d("log>>> " + "onFbLoaderFail:");

                }
            });
        }
    };

}

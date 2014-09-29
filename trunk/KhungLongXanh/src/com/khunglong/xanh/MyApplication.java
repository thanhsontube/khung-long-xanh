package com.khunglong.xanh;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {

    // private String id = "khunglongxanhvietnam";
    // nhatky
    // hanhphucgiobay
    // VoHoaiLinh
    // HaivlOfficial
    // haivl.com
    // Cristiano
    // AndroidStoreVietnam
    // ba.tung.7
    // LeoMessi
    // private String id = "huyen.dinh.165";

    @Override
    public void onCreate() {
        super.onCreate();

        ResourceManager.createInstance(getApplicationContext());
        try {
            initImageLoader(getApplicationContext());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ResourceManager.destroy(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

}

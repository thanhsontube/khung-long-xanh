package com.khunglong.xanh;

import android.app.Application;
import android.content.Context;

import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {
	
	private DragonData dragonData;
	private FbLoaderManager mFbLoaderManager;
//	private String id = "KhungLongXanhVietNam";
//	nhatky
//	hanhphucgiobay
//	VoHoaiLinh
//	HaivlOfficial
	private String id = "HaivlOfficial";
	private DisplayImageOptions optionsContent;
//	private String id = "huyen.dinh.165";
	@Override
	public void onCreate() {
		super.onCreate();
		mFbLoaderManager = new FbLoaderManager();
		try {
			initImageLoader(getApplicationContext());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		dragonData = new DragonData();
		
		optionsContent = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.iviet_temp)
		.showImageForEmptyUri(R.drawable.iviet_temp)
		.showImageOnFail(R.drawable.iviet_temp)
		.imageScaleType(ImageScaleType.EXACTLY)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.build();
	}
	
	
	
	public DisplayImageOptions getOptionsContent() {
		return optionsContent;
	}



	@Override
	public void onTerminate() {
	    super.onTerminate();
	    dragonData = null;
	}
	
	public FbLoaderManager getmFbLoaderManager() {
		return mFbLoaderManager;
	}



	public void setmFbLoaderManager(FbLoaderManager mFbLoaderManager) {
		this.mFbLoaderManager = mFbLoaderManager;
	}



	public DragonData getDragonData() {
		return dragonData;
	}



	public void setDragonData(DragonData dragonData) {
		this.dragonData = dragonData;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	
}

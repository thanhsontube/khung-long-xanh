package com.khunglong.xanh;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;

import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.main.drawer.MainDrawerItemGenerator;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MyApplication extends Application {
	
	
	
	private List<DragonData> mListDragonDatas;
	private DragonData dragonData;
	private FbLoaderManager mFbLoaderManager;
	private MainDrawerItemGenerator mMainDrawerItemGenerator;
//	private String id = "khunglongxanhvietnam";
//	nhatky
//	hanhphucgiobay
//	VoHoaiLinh
//	HaivlOfficial
	//haivl.com
//	Cristiano
//	AndroidStoreVietnam
//	ba.tung.7
//	LeoMessi
	private String id = "khunglongxanhvietnam";
	private DisplayImageOptions optionsContent;
	private DisplayImageOptions optionsCircle;
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
		mListDragonDatas = new ArrayList<DragonData>();
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
		
		optionsCircle = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.chat_emotion_icon)
		.showImageForEmptyUri(R.drawable.chat_emotion_icon)
		.showImageOnFail(R.drawable.chat_emotion_icon)
		.imageScaleType(ImageScaleType.EXACTLY)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(120))
		.build();
		
		mMainDrawerItemGenerator = new MainDrawerItemGenerator(getApplicationContext());
	}
	
	
	
	public DisplayImageOptions getOptionsContent() {
		return optionsContent;
	}
	



	public DisplayImageOptions getOptionsCircle() {
		return optionsCircle;
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
	
	public MainDrawerItemGenerator getDrawerItemGenerator() {
		return mMainDrawerItemGenerator;
	}



	public List<DragonData> getmListDragonDatas() {
		return mListDragonDatas;
	}



	public void setmListDragonDatas(List<DragonData> mListDragonDatas) {
		this.mListDragonDatas = mListDragonDatas;
	}
	
	
	
	
}

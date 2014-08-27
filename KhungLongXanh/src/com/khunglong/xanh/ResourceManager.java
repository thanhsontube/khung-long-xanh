package com.khunglong.xanh;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.data.MyData;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.object.FbMe;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;

public class ResourceManager {

	private static final String TAG = "ResourceManager";

	static ResourceManager instance = null;

	private DragonData klxData;
	private DragonData haivlData;
	private DragonData nghiemtucvlData;
	private FbMe userInfo;
	private FbLoaderManager fbLoaderManager;
	private ImageLoader imageLoader;
	
	private MyData sqlite;
	private Context context;

	FilterLog log = new FilterLog(TAG);

	public static void createInstance(Context context) {

		instance = new ResourceManager(context);
		instance.startup();

	}

	private void startup() {
		log.d("log>>>" + "startup");
		try {
			fbLoaderManager = new FbLoaderManager();
			klxData = new DragonData();
			klxData.setAlbumTimeLines(MsConstant.ID_KLX_TIME_LINES);
			
			imageLoader = ImageLoader.getInstance();
			
			sqlite = new MyData(context);

		} catch (Exception e) {
			log.e("log>>>" + "error startup:" + e.toString());
		}

	}

	public static ResourceManager getInstance() {
		return instance;
	}

	public DragonData getKlxData() {
		return klxData;
	}

	public void setKlxData(DragonData klxData) {
		this.klxData = klxData;
	}

	public DragonData getHaivlData() {
		return haivlData;
	}

	public void setHaivlData(DragonData haivlData) {
		this.haivlData = haivlData;
	}

	public DragonData getNghiemtucvlData() {
		return nghiemtucvlData;
	}

	public void setNghiemtucvlData(DragonData nghiemtucvlData) {
		this.nghiemtucvlData = nghiemtucvlData;
	}

	public ResourceManager(Context context) {
		this.context = context;
	}

	public FbMe getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(FbMe userInfo) {
		this.userInfo = userInfo;
	}

	public FbLoaderManager getFbLoaderManager() {
		return fbLoaderManager;
	}

	public void setFbLoaderManager(FbLoaderManager fbLoaderManager) {
		this.fbLoaderManager = fbLoaderManager;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public MyData getSqlite() {
		return sqlite;
	}

}

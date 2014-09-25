package com.khunglong.xanh;

import java.util.ArrayList;
import java.util.List;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.data.MyData;
import com.khunglong.xanh.dto.PageResourceDto;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.object.FbMe;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;

public class ResourceManager {

    private static final String TAG = "ResourceManager";

    static ResourceManager instance = null;
    private DragonData chandaiData;
    private DragonData klxData;
    private DragonData haivlData;
    private DragonData nghiemtucvlData;
    private FbMe userInfo;
    private FbLoaderManager fbLoaderManager;
    private ImageLoader imageLoader;

    private MyData sqlite;
    private Context context;

    FilterLog log = new FilterLog(TAG);

    private DisplayImageOptions optionsContent;
    private DisplayImageOptions optionsCircle;
    private List<PageResourceDto> listPageResource;

    public static void createInstance(Context context) {

        instance = new ResourceManager(context);
        instance.startup();

    }

    private void startup() {
        log.d("log>>>" + "startup");
        try {
            fbLoaderManager = new FbLoaderManager();
            klxData = new DragonData();
            chandaiData = new DragonData();
            klxData.setAlbumTimeLines(MsConstant.ID_KLX_TIME_LINES);

            imageLoader = ImageLoader.getInstance();

            optionsContent = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.iviet_temp)
                    .showImageForEmptyUri(R.drawable.iviet_temp).showImageOnFail(R.drawable.iviet_temp)
                    .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).build();

            optionsCircle = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.chat_emotion_icon)
                    .showImageForEmptyUri(R.drawable.chat_emotion_icon).showImageOnFail(R.drawable.chat_emotion_icon)
                    .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true).displayer(new RoundedBitmapDisplayer(120)).build();

            sqlite = new MyData(context);

            String[] arr;

            listPageResource = new ArrayList<PageResourceDto>();
            arr = context.getResources().getStringArray(R.array.page0);
            listPageResource.add(new PageResourceDto(arr));

            arr = context.getResources().getStringArray(R.array.page1);
            listPageResource.add(new PageResourceDto(arr));
            arr = context.getResources().getStringArray(R.array.page2);
            listPageResource.add(new PageResourceDto(arr));
            arr = context.getResources().getStringArray(R.array.page3);
            listPageResource.add(new PageResourceDto(arr));
            arr = context.getResources().getStringArray(R.array.page4);
            listPageResource.add(new PageResourceDto(arr));
            arr = context.getResources().getStringArray(R.array.page5);
            listPageResource.add(new PageResourceDto(arr));

        } catch (Exception e) {
            log.e("log>>>" + "error startup:" + e.toString());
        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<PageResourceDto> getListPageResource() {
        return listPageResource;
    }

    public void setListPageResource(List<PageResourceDto> listPageResource) {
        this.listPageResource = listPageResource;
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

    public DisplayImageOptions getOptionsContent() {
        return optionsContent;
    }

    public void setOptionsContent(DisplayImageOptions optionsContent) {
        this.optionsContent = optionsContent;
    }

    public DisplayImageOptions getOptionsCircle() {
        return optionsCircle;
    }

    public void setOptionsCircle(DisplayImageOptions optionsCircle) {
        this.optionsCircle = optionsCircle;
    }

    public DragonData getChandaiData() {
        return chandaiData;
    }

    public void setChandaiData(DragonData chandaiData) {
        this.chandaiData = chandaiData;
    }

}

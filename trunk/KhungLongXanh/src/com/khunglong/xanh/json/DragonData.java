package com.khunglong.xanh.json;

import java.util.ArrayList;
import java.util.List;

import com.khunglong.xanh.myfacebook.object.FbAlbums;

public class DragonData {
    private String albumTimeLines;
    private List<PageData> data;
    private Paging paging;
    private FbAlbums albums;
    private int icon;

    public DragonData() {
        super();
        data = new ArrayList<PageData>();
        paging = new Paging();
    }
    
    

    public int getIcon() {
        return icon;
    }



    public void setIcon(int icon) {
        this.icon = icon;
    }



    public List<PageData> getData() {
        return this.data;
    }

    public void setData(List<PageData> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return this.paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public FbAlbums getAlbums() {
        return albums;
    }

    public void setAlbums(FbAlbums albums) {
        this.albums = albums;
    }

    public String getAlbumTimeLines() {
        return albumTimeLines;
    }

    public void setAlbumTimeLines(String albumTimeLines) {
        this.albumTimeLines = albumTimeLines;
    }

}

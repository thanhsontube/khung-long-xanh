package com.khunglong.xanh.json;

import java.util.ArrayList;
import java.util.List;

import com.khunglong.xanh.myfacebook.object.FbAlbums;

public class DragonData {
	private List<Data> data;
	private Paging paging;
	private FbAlbums albums;

	public DragonData() {
		super();
		data = new ArrayList<Data>();
		paging = new Paging();
	}

	public List<Data> getData() {
		return this.data;
	}

	public void setData(List<Data> data) {
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

}

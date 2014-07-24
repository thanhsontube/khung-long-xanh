package com.khunglong.xanh.myfacebook;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.khunglong.xanh.myfacebook.object.FbAlbums;
import com.khunglong.xanh.myfacebook.object.FbAlbumsDto;

public abstract class FbLoaderAlbumsList extends FbLoaderGet<FbAlbums> {
	
	private static final String TAG = "FbLoaderAlbumsList";
	FilterLog log = new FilterLog(TAG);
	
	public FbLoaderAlbumsList(Context context, String id) {
		super(context, id + "/albums", null);
	}

	public FbLoaderAlbumsList(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params);
	}

	@Override
	protected FbAlbums handleResult(Response response) {
		try {

			GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
			JSONObject jsonObject = graphObject.getInnerJSONObject();
			// log.d("log>>>" + "jsonObject:" + jsonObject.toString());
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			FbAlbumsDto dto;
			FbAlbums fbAlbums = new FbAlbums();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject ja = (JSONObject) jsonArray.get(i);
				String id = ja.getString("id");
				String name = ja.getString("name");
				String type = ja.getString("type");
				int count = 0;
				if (ja.has("count")) {
					count = ja.getInt("count");
					
				}
				dto = new FbAlbumsDto(id, name, type, count);
				fbAlbums.listFbAlbumsDto.add(dto);
			}
			return fbAlbums;
		} catch (Exception e) {
			log.e("log>>>" + "error FbAlbums handleResult:" + e.toString());
		}

		return null;
	}
}

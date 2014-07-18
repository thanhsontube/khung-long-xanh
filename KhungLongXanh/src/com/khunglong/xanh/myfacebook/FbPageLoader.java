package com.khunglong.xanh.myfacebook;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.khunglong.xanh.json.Data;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.json.FbPageCursor;
import com.khunglong.xanh.json.Paging;

/**
 * load a page from facebook
 * 
 * @author test02
 * 
 */

public abstract class FbPageLoader extends FbLoaderGet<DragonData> {

	public FbPageLoader(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params);
	}
	
	@Override
	protected DragonData handleResult(Response response) {
		try {
			GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
			JSONObject jsonObject = graphObject.getInnerJSONObject();
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			
			DragonData dragonData = new DragonData();
			Data data;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject ja = (JSONObject) jsonArray.get(i);
				String id = ja.getString("id");
				String name = ja.getString("name");
				String source = ja.getString("source");
				String created_time = ja.getString("created_time");
				data = new Data(id, name, source, created_time);
				
				dragonData.getData().add(data);
			}
			Log.v("", "log>>>" + "handleResult page");
			JSONObject paging = (JSONObject) jsonObject.get("paging");
			JSONObject jCursors = (JSONObject) paging.get("cursors");
			String after = jCursors.getString("after");
			String before = jCursors.getString("before");
			FbPageCursor fbPageCursor = new FbPageCursor(before, after);
			Paging fbPaging = new Paging();
			fbPaging.setCursors(fbPageCursor);
			dragonData.setPaging(fbPaging);
			return dragonData;
        } catch (Exception e) {
        	Log.e("", "log>>>" + "error  DragonData handleResult:" + e.toString());
        }
		return null;
	}

}

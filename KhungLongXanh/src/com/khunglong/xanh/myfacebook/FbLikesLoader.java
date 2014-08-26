package com.khunglong.xanh.myfacebook;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.khunglong.xanh.myfacebook.object.FbLikes;
import com.khunglong.xanh.myfacebook.object.FbLikesData;
import com.khunglong.xanh.myfacebook.object.FbLikesSummary;

public abstract class FbLikesLoader extends FbLoaderGet<FbLikes> {

	public FbLikesLoader(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params);
	}
	
	@Override
	protected FbLikes handleResult(Response response) {
		try {
			GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
			JSONObject jsonObject = graphObject.getInnerJSONObject();
//			JSONArray jsonArray = jsonObject.getJSONArray("data");
			FbLikes likes = new FbLikes();
//			FbLikesData dto;
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject ja = (JSONObject) jsonArray.get(i);
//				String id = ja.getString("id");
//				String name = ja.getString("name");
//				dto = new FbLikesData(id, name);
//				likes.getData().add(dto);
//			}
			
			//get summaty
			
			JSONObject jSummary = (JSONObject) jsonObject.get("summary");
			int total_count = jSummary.getInt("total_count");
			FbLikesSummary fbLikesSummary = new FbLikesSummary();
			fbLikesSummary.setTotal_count(total_count);
			likes.setSummary(fbLikesSummary);
			return likes;
		} catch (Exception e) {
			Log.e("", "log>>>" + "error  Comments handleResult:" + e.toString());
		}
		return null;
	}
	
}

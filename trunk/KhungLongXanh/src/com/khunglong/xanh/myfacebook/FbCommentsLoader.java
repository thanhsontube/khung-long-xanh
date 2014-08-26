package com.khunglong.xanh.myfacebook;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbCmtSummary;
import com.khunglong.xanh.myfacebook.object.FbComments;

public abstract class FbCommentsLoader extends FbLoaderGet<FbComments> {
	
	private static final String TAG = "FbCommentsLoader";
	FilterLog log = new FilterLog(TAG);

	public FbCommentsLoader(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params);
	}
	
	@Override
	protected FbComments handleResult(Response response) {
		try {
			GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
			JSONObject jsonObject = graphObject.getInnerJSONObject();
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			FbComments comments = new FbComments();
			FbCmtData dto;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject ja = (JSONObject) jsonArray.get(i);
				String id = ja.getString("id");
				String created_time = ja.getString("created_time");
				String message = ja.getString("message");
				int like_count = ja.getInt("like_count");
				
				//get commenter
				JSONObject jFrom = ja.getJSONObject("from");
//				log.d("log>>>" + "bbb:" + jFrom.toString());
				String fromId = jFrom.getString("id");
				String fromName = jFrom.getString("name");
				FbCmtFrom from = new FbCmtFrom(fromId,fromName);
				dto = new FbCmtData(id, created_time, message, like_count, from);
				comments.getData().add(dto);
			}
			
			//get summaty
			
			JSONObject jSummary = (JSONObject) jsonObject.get("summary");
			int total_count = jSummary.getInt("total_count");
			comments.setSummary(new FbCmtSummary(total_count));
			return comments;
		} catch (Exception e) {
			Log.e("", "log>>>" + "error  Comments handleResult:" + e.toString());
		}
		return null;
	}
	
}

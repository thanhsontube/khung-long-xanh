package com.khunglong.xanh.myfacebook;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.khunglong.xanh.myfacebook.object.FbMe;

public abstract class FbMeLoader extends FbLoaderGet<FbMe> {

	public FbMeLoader(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params);
	}
	
	@Override
	protected FbMe handleResult(Response response) {
		try {
			GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
			JSONObject jsonObject = graphObject.getInnerJSONObject();
			String id = jsonObject.getString("id");
//			String bio = jsonObject.getString("bio");
			String email = jsonObject.getString("email");
			String name = jsonObject.getString("name");
			String quotes = jsonObject.getString("quotes");
			FbMe f = new FbMe(id, null, email, name, quotes);
			return f;
		} catch (Exception e) {
			Log.e("", "log>>>" + "error  FbMeLoader handleResult:" + e.toString());
		}
		return null;
	}
	
}

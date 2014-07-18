package com.khunglong.xanh.myfacebook;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.khunglong.xanh.json.Data;

/**
 * load a page from facebook
 * 
 * @author test02
 * 
 */

public abstract class FbPageDetailLoader extends FbLoaderGet<Data> {

	public FbPageDetailLoader(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params);
	}

	@Override
	protected Data handleResult(Response response) {
		try {
			GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
			JSONObject ja = graphObject.getInnerJSONObject();
			String id = ja.getString("id");
			String name = ja.getString("name");
			String source = ja.getString("source");
			String created_time = ja.getString("created_time");
			Data data = new Data(id, name, source, created_time);
			return data;
		} catch (Exception e) {
			Log.e("", "log>>>" + "error  FbPageDetailLoader handleResult:" + e.toString());
		}
		return null;
	}

}

package com.khunglong.xanh.myfacebook;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;

public abstract class FbUserLoader extends FbLoaderGet<FbCmtFrom> {

	public FbUserLoader(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params);
	}
	
	@Override
	protected FbCmtFrom handleResult(Response response) {
		try {
			GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
			JSONObject jsonObject = graphObject.getInnerJSONObject();
			JSONObject jData = jsonObject.getJSONObject("data");
			String source = jData.getString("url");
			FbCmtFrom f = new FbCmtFrom(source);
			return f;
		} catch (Exception e) {
			Log.e("", "log>>>" + "error  Comments handleResult:" + e.toString());
		}
		return null;
	}
	
}

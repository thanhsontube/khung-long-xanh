package com.khunglong.xanh.myfacebook;

import com.facebook.Response;

import android.content.Context;
import android.os.Bundle;

public abstract class FbPostCommentsLoader extends FbLoaderPost<String> {

	public FbPostCommentsLoader(Context context, String graphpath, Bundle params) {
	    super(context, graphpath, params);
    }
	@Override
	protected String handleResult(Response response) {
	    return response.toString();
	}
}

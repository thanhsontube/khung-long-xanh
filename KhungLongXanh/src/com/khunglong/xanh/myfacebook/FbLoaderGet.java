package com.khunglong.xanh.myfacebook;

import com.facebook.HttpMethod;

import android.content.Context;
import android.os.Bundle;

public abstract class FbLoaderGet<T> extends FbLoader<T>{
	public FbLoaderGet(Context context, String graphpath, Bundle params) {
		super(context, graphpath, params, HttpMethod.GET);
	}
}

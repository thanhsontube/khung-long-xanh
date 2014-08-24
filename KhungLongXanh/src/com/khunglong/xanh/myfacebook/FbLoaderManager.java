package com.khunglong.xanh.myfacebook;


public class FbLoaderManager {
	public void load(final FbLoader<?> loader) {
		loader.request(this);
	}
}

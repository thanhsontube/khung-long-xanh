package com.khunglong.xanh.myfacebook;


public class FbLoaderManager {
//	public void load(final FbLoader<?> loader) {
//		loader.execute(this);
//	}
//	
//	public void cancel(FbLoader<?> loader) {
//		loader.cancel();
//	}
	
	public void load(final FbLoader<?> loader) {
		loader.request(this);
	}
}

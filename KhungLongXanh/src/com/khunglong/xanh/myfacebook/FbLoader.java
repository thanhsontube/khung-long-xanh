package com.khunglong.xanh.myfacebook;

import java.util.concurrent.Executor;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

public abstract class FbLoader<T> {
	public abstract void onFbLoaderStart();
	public abstract void onFbLoaderSuccess(T entry);
	public abstract void onFbLoaderFail(Throwable e);
	
	private String grathPath;
	private Bundle params;
	private HttpMethod httpMethod;
	
	public FbLoader (Context context, String graphpath, Bundle params, HttpMethod httpMethod) {
		this.grathPath = graphpath;
		this.params = params;
		this.httpMethod = httpMethod;
	}
	protected Executor getExecutor() {
//		return AsyncTask.SERIAL_EXECUTOR;
		return AsyncTask.THREAD_POOL_EXECUTOR;
	}
	public void execute(FbLoaderManager fbLoaderManager) {
		task.executeOnExecutor(getExecutor(),fbLoaderManager);
	}
	
	public void cancel(){
		task.cancel(true);
	}
	
	protected abstract T handleResult(Response response);
	
	
	public void request(FbLoaderManager fbLoaderManager) {
		onFbLoaderStart();
		Request request = new Request(Session.getActiveSession(), grathPath, params, httpMethod, new Request.Callback() {
			
			@Override
			public void onCompleted(Response response) {
				try {
					GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
					T result = handleResult(response);
					onFbLoaderSuccess(result);
				} catch (Exception e) {
					onFbLoaderFail(e);
				}
			}
		});
		request.executeAsync();
	}
	
	private final AsyncTask<FbLoaderManager, Void, T> task = new AsyncTask<FbLoaderManager, Void, T> (){
		protected void onPreExecute() {
			if(isCancelled()) {
				return;
			}
			super.onPreExecute();
			onFbLoaderStart();
		};
		protected  T doInBackground(FbLoaderManager[] value) {
			if(isCancelled()) {
				return null;
			}
			
//			new Request(Session.getActiveSession(), grathPath, params, HttpMethod.POST, new Request.Callback() {
//				
//				@Override
//				public void onCompleted(Response response) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			final T result;
			
			Request request = new Request(Session.getActiveSession(), grathPath, params, HttpMethod.POST, new Request.Callback() {
				
				@Override
				public void onCompleted(Response response) {
					try {
						GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
					} catch (Exception e) {
						onFbLoaderFail(e);
					}
				}
			});
			request.executeAsync();
			return null;
			
		};
		protected void onPostExecute(T result) {};
	};
}

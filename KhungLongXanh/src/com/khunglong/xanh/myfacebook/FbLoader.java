package com.khunglong.xanh.myfacebook;

import android.content.Context;
import android.os.Bundle;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

public abstract class FbLoader<T> {
    private static final String TAG = "FbLoader";

    public abstract void onFbLoaderStart();

    public abstract void onFbLoaderSuccess(T entry);

    public abstract void onFbLoaderFail(Throwable e);

    FilterLog log = new FilterLog(TAG);

    private String grathPath;
    private Bundle params;
    private HttpMethod httpMethod;

    public FbLoader(Context context, String graphpath, Bundle params, HttpMethod httpMethod) {
        log.d("log>>>" + "graphpath:" + graphpath);
        this.grathPath = graphpath;
        this.params = params;
        this.httpMethod = httpMethod;
    }

    protected abstract T handleResult(Response response);

    public void request(FbLoaderManager fbLoaderManager) {
        onFbLoaderStart();
        Request request = new Request(Session.getActiveSession(), grathPath, params, httpMethod,
                new Request.Callback() {

                    @Override
                    public void onCompleted(Response response) {
                        try {
                            T result = handleResult(response);
                            onFbLoaderSuccess(result);
                        } catch (Exception e) {
                            onFbLoaderFail(e);
                        }
                    }
                });
        request.executeAsync();
    }

}

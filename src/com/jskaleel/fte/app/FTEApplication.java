package com.jskaleel.fte.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.jskaleel.fte.common.LruBitmapCache;

public class FTEApplication extends Application {
	
	 public static final String TAG = FTEApplication.class.getSimpleName();
	
	private static FTEApplication mInstance;
	private static Context mAppContext;

	private RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		this.setAppContext(getApplicationContext());
	}

	public static FTEApplication getInstance(){
		return mInstance;
	}

	public static Context getAppContext() {
		return mAppContext;
	}

	public void setAppContext(Context mAppContext) {
		FTEApplication.mAppContext = mAppContext;
	}

	public RequestQueue getRequestQueue(){
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return this.mRequestQueue;
	}

	public ImageLoader getImageLoader(){
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
		}
		return FTEApplication.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}

package com.jskaleel.fte.common;

import android.app.Activity;

public class UIProgressLoading {
	
	public  ProgressLoading progressLoading;
	public Activity activity;
	public  void init(Activity context){
		progressLoading = new ProgressLoading(context);
		activity = context;
	}
	public  void showProgressLoading(String msg){
		progressLoading.getDialog(msg, true);
		if(progressLoading != null && progressLoading.isShowing()){
			progressLoading.dismiss();
		}
			progressLoading.show();
	}
	public  void stop() {
		if(progressLoading != null && progressLoading.isShowing()){
			progressLoading.dismiss();
		}
	}
}

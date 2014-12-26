package com.jskaleel.fte;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference extends Application {
	

	public static final String PREFS_NAME 		= "app_preference";	
	public static final String IS_OPEN_FIRSTTIME 	= "open firsttime";
	
	private SharedPreferences mPrefrence;
	private SharedPreferences.Editor mEditor;
	
	public AppPreference(Context mContext){
		String packageName = mContext.getPackageName();
		mPrefrence = mContext.getSharedPreferences(packageName + "." + PREFS_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefrence.edit();
	}
	
	public void setIsOpenFirstTime(boolean isPosteredInterview){
		mEditor.putBoolean(IS_OPEN_FIRSTTIME, isPosteredInterview);
		mEditor.commit();
	}
	
	public boolean getIsOpenFirstTime() {
		return mPrefrence.getBoolean(IS_OPEN_FIRSTTIME, true);
	}
}

package com.jskaleel.fte.common;

import android.util.Log;

public class PrintLog {

	public static void debug(String TAG,String str) {
		if(str.length() > 4000) 
		{
			Log.d("FTE",TAG+"  ---> " + str.substring(0, 4000));
			debug(TAG,str.substring(4000));
		} else
		{
			Log.d("FTE",TAG+ " ---->" +str);
		}
	}
	public static void error(String TAG,String str) {
		if(str.length() > 4000) 
		{
			Log.e("FTE",TAG +"  ---> " + str.substring(0, 4000));
			error(TAG,str.substring(4000));
		}
		else
		{
			Log.e("FTE",TAG+ " ---->" +str);
		}
	}
	
}

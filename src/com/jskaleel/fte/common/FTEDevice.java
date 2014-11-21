package com.jskaleel.fte.common;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.util.TypedValue;

public class FTEDevice {

	public static boolean GTE_GB_9 		= false;
	public static boolean GTE_GB_10		= false;
	
	public static boolean GTE_HC_11		= false;
	public static boolean GTE_HC_12		= false;
	public static boolean GTE_HC_13		= false;
	
	public static boolean GTE_ICS_14	= false;
	public static boolean GTE_ICS_15	= false;
	
	public static boolean GTE_JB_16		= false;
	public static boolean GTE_JB_17		= false;
	public static boolean GTE_JB_18		= false;

	
	public static boolean PRE_GB_9 		= false;
	public static boolean PRE_GB_10		= false;
	
	public static boolean PRE_HC_11		= false;
	public static boolean PRE_HC_12		= false;
	public static boolean PRE_HC_13		= false;
	
	public static boolean PRE_ICS_14	= false;
	public static boolean PRE_ICS_15	= false;
	
	public static boolean PRE_JB_16		= false;
	public static boolean PRE_JB_17		= false;
	public static boolean PRE_JB_18		= false;
	
	static {
		int version = Build.VERSION.SDK_INT;
		
		//2.3.3 to 2.3.7  api level 9 mr1 = 10
		if(version >= VERSION_CODES.GINGERBREAD)
			GTE_GB_9 = true;
		else 
			PRE_GB_9 = true;

		if(version >= VERSION_CODES.GINGERBREAD_MR1)
			GTE_GB_10 = true;
		else 
			PRE_GB_10 = true;
		
		//3.2 api level 11, mr1=12,mr2=13
		if(version >= VERSION_CODES.HONEYCOMB)
			GTE_HC_11 = true;
		else
			PRE_HC_11 = true;
		
		if(version >= VERSION_CODES.HONEYCOMB_MR1)
			GTE_HC_12 = true;
		else
			PRE_HC_12 = true;

		if(version >= VERSION_CODES.HONEYCOMB_MR2)
			GTE_HC_13 = true;
		else
			PRE_HC_13 = true;
	
		//4.0.3 to 4.0.4 api level 14, mr1=15
		if(version >= VERSION_CODES.ICE_CREAM_SANDWICH)
			GTE_ICS_14 = true;
		else
			PRE_ICS_14 = true;
		
		if(version >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
			GTE_ICS_15 = true;
		else
			PRE_ICS_15 = true;
		
		//4.1.x ,4.2.x ,4.3.x api level 16, mr1 =17, mr2 =18
		if(version>= VERSION_CODES.JELLY_BEAN)
			GTE_JB_16 = true;
		else
			PRE_JB_16 = true;
		
		if(version>= VERSION_CODES.JELLY_BEAN_MR1)
			GTE_JB_17 = true;
		else
			PRE_JB_17 = true;
		
		if(version>= VERSION_CODES.JELLY_BEAN_MR2)
			GTE_JB_18 = true;
		else
			PRE_JB_18 = true;
		
		
		//4.4 Kitkat
	}
	
	public static int convertDpToPx(int dp,Context context)
	{
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  context.getResources().getDisplayMetrics());
		return (int)px;
	}
}

package com.jskaleel.fte.common;

import android.util.Base64;
import android.widget.TextView;

public class TextUtils {

	public static final String encodeToBase64(CharSequence content){
		byte[] bytes = Base64.encode(content.toString().getBytes(), Base64.DEFAULT);
		return new String(bytes);		
	}
	
	public static final String decodeBase64(String base64String){
		try {
			return new String(Base64.decode(base64String, Base64.DEFAULT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return base64String;
	}
	
	public static boolean isNullOrEmpty(String value){
		return value == null || value.trim().equals("");
	}
	
	public static boolean isNullOrEmpty(CharSequence value){
		return value == null || value.toString().equals("");
	}
	
	public static boolean isEmpty(TextView txt){
		String text = txt.getText().toString().trim();
		return text.length() <= 0;
	}
	
}

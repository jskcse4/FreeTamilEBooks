package com.jskaleel.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpStatus;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.jskaleel.fte.common.TextUtils;


public class HttpUtils {

	public static String getErrorMsg(String content){
		String msg = "";
		if(content != null){
			try {
				JSONObject object = new JSONObject(content);
				object = object.getJSONObject("meta");
				if(!object.isNull("errorMessage")){
					msg = object.getString("errorMessage");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}
		return msg;
	}

	public static boolean isError(String content){
		boolean isError = true;

		if(content != null){
			try {
				JSONObject object = new JSONObject(content);
				object = object.getJSONObject("meta");
				if(object.isNull("errorMessage")){
					isError = false;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return isError;
	}
	
	public static int getErrorCode(String content){
		int isErrorcode = -1;

		if(content != null){
			try {
				JSONObject object = new JSONObject(content);
				object = object.getJSONObject("meta");
				if(!object.isNull("errors")){
					isErrorcode = object.optInt("errors", -1);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return isErrorcode;
	}

	public static UrlResponse getResponse(int responseCode, String content){
		UrlResponse response = new UrlResponse();		
		response.setResposeCode(responseCode);

		String errorMsg = HttpUtils.getErrorMsg(content);

		if(responseCode == HttpStatus.SC_CREATED || responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_MOVED_TEMPORARILY){
			if(HttpUtils.isError(content)){
				response.setErrorMsg(errorMsg);
				response.setErrorCode(getErrorCode(content));
			}
			else{
				response.setContent(content);
			}				
		}
		else {				
			response.setErrorMsg(errorMsg);
		}

		try {
			int errorCode = -1;
			if(!TextUtils.isNullOrEmpty(content)){
				JSONObject object = new JSONObject(content);
				if(!object.isNull("meta") && 
						!object.getJSONObject("meta").isNull("errors")){
					errorCode = object.getJSONObject("meta").optInt("errors", -1);
				}
				response.setErrorCode(errorCode);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	public static String convertStreamToString(InputStream is){
		BufferedReader buf;
		StringBuilder builder = new StringBuilder();
		try {
			buf = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line;

			while((line = buf.readLine()) != null){
				builder.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static BasicNameValuePair getValuePair(String key, String value){
		return new BasicNameValuePair(key, value);
	}

}

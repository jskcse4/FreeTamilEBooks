package com.jskaleel.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jskaleel.fte.common.PrintLog;

public class HttpGetUrlConnection {

	public HttpGetUrlConnection(){

	}

	public static String getContentFromUrl(String api_url) throws UnsupportedEncodingException{
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		UrlResponse response = new UrlResponse();
		String content = null;

		try {
			if(api_url != null){
				api_url += (api_url.contains("?") ? "&" : "?") + "platform=android";
			}
			
			StringBuilder sb = new StringBuilder(api_url);

			HttpURLConnection.setFollowRedirects(true);

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");


			InputStreamReader in = new InputStreamReader(conn.getInputStream());	        
			int statusCode = conn.getResponseCode();

			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}	

			content = jsonResults.toString();

			response = HttpUtils.getResponse(statusCode, content);
			
			PrintLog.debug("HttpGerUrl", "Response: "+content);

		} catch (MalformedURLException e) {
			PrintLog.error("HttpGerUrl", "ExceptioN: "+e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			PrintLog.error("HttpGerUrl", "ExceptioN: "+e);
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
		}		
		return content.toString();
	}
	
	public static UrlResponse getContentFromUrl(String api_url,HttpListener listener) throws UnsupportedEncodingException{
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		UrlResponse response = new UrlResponse();
		String content = null;
		try {
			if(api_url != null){
				api_url += (api_url.contains("?") ? "&" : "?") + "platform=android";
			}

			StringBuilder sb = new StringBuilder(api_url);

			HttpURLConnection.setFollowRedirects(true);

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");


			InputStreamReader in = new InputStreamReader(conn.getInputStream());	        
			int statusCode = conn.getResponseCode();

			int read;
			char[] buff = new char[1024];
			
			long total=0;
			int fileLength = conn.getContentLength();
			
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
				total += read;
				if (fileLength > 0)
				{
					if(listener!=null)
						listener.processCompletion(((int) (total * 100 / fileLength)));
				}
			}	

			content = jsonResults.toString();

			response = HttpUtils.getResponse(statusCode, content);
			
			PrintLog.debug("HttpGerUrl", "Response: "+content);

		} catch (MalformedURLException e) {
			PrintLog.error("HttpGerUrl", "Exception: "+e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			PrintLog.error("HttpGerUrl", "Exception: "+e);
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
		}		
		return response;
	}

}

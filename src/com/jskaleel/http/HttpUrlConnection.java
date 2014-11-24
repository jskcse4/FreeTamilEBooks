package com.jskaleel.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.jskaleel.fte.common.PrintLog;

public class HttpUrlConnection {
	
	private static final String TAG = "HttpUrlConnection";
	
	public HttpUrlConnection(){
		
	}
	
	public static String getContentFromUrl(String api_url) throws UnsupportedEncodingException{
		HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(api_url);
	        
	        PrintLog.debug("HttpUrlConnection", "getContentFromUrl Url: "+api_url);
	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());
	       	        
	        
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }	 	     
	        PrintLog.debug("HttpUrlConnection", "Response: "+jsonResults.toString());
	        
		} catch (MalformedURLException e) {
			PrintLog.error("HttpUrlConnection", "ExceptioN: "+e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			PrintLog.error("HttpUrlConnection", "ExceptioN: "+e);
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
		}		
		return jsonResults.toString();
	}
	
	public static String getContentFromUrl(String api_url,HttpListener listener) throws UnsupportedEncodingException{

		HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    
	    try {
	        StringBuilder sb = new StringBuilder(api_url);
	        PrintLog.debug("HttpUrlConnection", "getContentFromUrl Url: "+api_url);
	        
	        URL url = new URL(sb.toString());
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());
	       	        
	        int read;
			char[] buff = new char[1024];
			
			long total=0;
			int fileLength = conn.getContentLength();
			
	        while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
				
				total += read;
				//for temp use
				if(listener!=null)
				{
					int x = (int)total/read;
					if(x>100)
						x = 100;
					if(x<0)
						x=0;
					listener.processCompletion(x);
				}
			}	
	        PrintLog.debug("HttpUrlConnection", "Response: "+jsonResults.toString());
            
		} catch (MalformedURLException e) {
			PrintLog.error("HttpUrlConnection", "ExceptioN: "+e);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			PrintLog.error("HttpUrlConnection", "ExceptioN: "+e);
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
		}		
		return jsonResults.toString();
	}

}

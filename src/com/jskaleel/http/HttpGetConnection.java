package com.jskaleel.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.jskaleel.fte.common.PrintLog;



public class HttpGetConnection {


	public static String HTTP_LOG				= "HttpConnection";
	
	public static UrlResponse getRequest(String url) {
		UrlResponse response = new UrlResponse();
		HttpClient httpclient = new DefaultHttpClient();
				
		PrintLog.debug("URL", "---->"+url);
		
		HttpGet httppost = new HttpGet(url);
		String content = null;

		try {			
			
			PrintLog.debug(HTTP_LOG, "getRequest Url:" + url);
			
			HttpResponse httpResponse = httpclient.execute(httppost);
			int responseCode = httpResponse.getStatusLine().getStatusCode();
			InputStream ips  = httpResponse.getEntity().getContent();
			
			content = HttpUtils.convertStreamToString(ips);
			
			response = HttpUtils.getResponse(responseCode, content);

			PrintLog.debug(HTTP_LOG, "Response: "+response);
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			PrintLog.error(HTTP_LOG, "ExceptioN: "+e);
		} catch (IOException e) {
			e.printStackTrace();
			PrintLog.error(HTTP_LOG, "ExceptioN: "+e);
		} 

		return response;		
	}
	
}

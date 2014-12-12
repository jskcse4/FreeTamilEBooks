package com.jskaleel.fte.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;

public class FragmentContributors extends BasicFragment {
    
	private WebView webView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, null);
    
        init(view);
        setupDefaults();
        setupEvents();
        
        return view;
    }

	private void init(View view) {
		// TODO Auto-generated method stub
		webView = (WebView) view.findViewById(R.id.fragment_webview);
	}

	private void setupDefaults() {
		// TODO Auto-generated method stub
		
		WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                dismissProgressDialog();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            	showAlertDialog(description);
            }
        });
        
        webView.loadUrl("file:///android_asset/htmlfiles/team.html");
//        webView.loadDataWithBaseURL("file:///android_asset/htmlfiles/team.html", null, "text/html", "utf-8", null);
	}

	private void setupEvents() {
		// TODO Auto-generated method stub
		webView.setOnTouchListener(new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		       return true;
		    }  
		});
	}
    
	
}

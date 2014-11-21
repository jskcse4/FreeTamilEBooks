package com.jskaleel.fte.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;

public class FragmentWebView extends BasicFragment {
    WebView webView;
    
    public FragmentWebView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, null);
    
        webView = (WebView) view.findViewById(R.id.fragment_webview_webview);
        String data = "<html><body><h1>Welcome to WebView</h1></body></html>";
        webView.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
        
        return view;
    }
}

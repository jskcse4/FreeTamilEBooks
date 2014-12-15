package com.jskaleel.fte.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jskaleel.fte.MainActivity;
import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;

public class FragmentAboutUs extends BasicFragment {

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

	@SuppressLint("SetJavaScriptEnabled")
	private void setupDefaults() {
		// TODO Auto-generated method stub

		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("utf-8");
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

		switch(MainActivity.getFragmentType()) {
		case 1:
			webView.loadUrl("file:///android_asset/htmlfiles/about_us.html");
			break;
		case 2:
			webView.loadUrl("file:///android_asset/htmlfiles/team.html");
			break;
		case 3:
			webView.loadUrl("file:///android_asset/htmlfiles/how_to_publish.html");
			break;
		default:
			webView.loadData("", "text/html", "utf-8");
			break;
		}
		//        webView.loadDataWithBaseURL("file:///android_asset/htmlfiles/team.html", null, "text/html", "utf-8", null);
	}

	private void setupEvents() {
		// TODO Auto-generated method stub
		
	}
}

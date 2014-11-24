package com.jskaleel.fte.splash;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.jskaleel.fte.MainActivity;
import com.jskaleel.fte.R;
import com.jskaleel.fte.common.ConnectionDetector;
import com.jskaleel.fte.common.PrintLog;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 5000;
	
	//Connection Detector
	private Boolean isInternetAvailable = false;
	private ConnectionDetector connectionDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Toast.makeText(getApplicationContext(),"Application Under Development", Toast.LENGTH_LONG).show();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				connectionDetector = new ConnectionDetector(getApplicationContext());
				isInternetAvailable = connectionDetector.isConnectingToInternet();
				
				if(isInternetAvailable) {
					File path = new File(Environment.getExternalStorageDirectory(),"/Free_Tamil_Ebooks");

					if(!(path.exists()))
						path.mkdir();
					Intent i = new Intent(SplashScreen.this, MainActivity.class);
					PrintLog.debug("TamilFreeEbooks","Network Connected");
					startActivity(i);
				}
				else {
					Toast.makeText(getApplicationContext(), "Network Error : Please " +
							"Check your Network Connection", Toast.LENGTH_LONG).show();
					PrintLog.debug("TamilFreeEbooks","Network Not Connected");
					finish();
				}
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}

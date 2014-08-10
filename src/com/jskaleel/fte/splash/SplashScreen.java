package com.jskaleel.fte.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.jskaleel.fte.R;
import com.jskaleel.fte.parse.MainActivity;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 5000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		Toast.makeText(getApplicationContext(),"Application Under Development", Toast.LENGTH_LONG).show();
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = cm.getActiveNetworkInfo();
				if(info != null && info.isConnectedOrConnecting()){
					Intent i = new Intent(SplashScreen.this, MainActivity.class);
					Log.d("TamilFreeEbooks","Network Connected");
					startActivity(i);
				}
				else {
					Toast.makeText(getApplicationContext(), "Network Error : Please " +
							"Check your Network Connection", Toast.LENGTH_LONG).show();
					Log.d("TamilFreeEbooks","Network Not Connected");
					finish();
				}

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}

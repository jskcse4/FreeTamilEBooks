package com.jskaleel.fte.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public class BasicActivity extends FragmentActivity{

	public AlertDialog alertDialog;
	//	public ProgressDialog progressDialog;
	public UIProgressLoading uiProgressLoading;
	public static Boolean isAppRunningBackground = false;
	public static String TAG = "BasicActivity";
	public boolean doubleBackToExitPressedOnce;

	//Connection Detectors
	public Boolean isInternetAvailable = false;
	public ConnectionDetector connectionDetector;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		progressDialog = new ProgressDialog(this);
		uiProgressLoading = new UIProgressLoading();
		uiProgressLoading.init(this);

		connectionDetector = new ConnectionDetector(getApplicationContext());
		isInternetAvailable = connectionDetector.isConnectingToInternet();
	}

	public FragmentActivity getFragmentActivity(){
		return BasicActivity.this;
	}

	public void dismissProgressDialog()
	{
		uiProgressLoading.stop();
	}
	public void showProgressDialog(String message){
		uiProgressLoading.showProgressLoading(message);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(alertDialog != null){
			if(alertDialog.isShowing()){
				alertDialog.dismiss();
			}
		}

		if(uiProgressLoading != null){
			uiProgressLoading.stop();
		}
	}

	public void showAlertDialog(String message){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Free Tamil Ebooks");
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK){
					dialog.dismiss();
				}
				return false;
			}
		});

		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void showAlertDialogAndClose(String message){


		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("TiLT");
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK){
					dialog.dismiss();
					finish();
				}
				return false;
			}
		});

		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	public void showInternetAvailabilityAlertDialog(String message){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("TiLT");
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();

			}
		});
		alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK){
					dialog.dismiss();
					finish();

				}
				return false;
			}
		});

		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	protected void onPause() {
		isAppRunningBackground = true;
		PrintLog.debug(TAG, "isAppRunningBackground onPause: "+isAppRunningBackground);
		super.onPause();
	}

	@Override
	protected void onResume() {
		isAppRunningBackground = false;
		PrintLog.debug(TAG, "isAppRunningBackground onResume: "+isAppRunningBackground);
		super.onResume();
	}

}

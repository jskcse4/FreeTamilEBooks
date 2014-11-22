package com.jskaleel.fte.common;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public class BasicFragment extends Fragment{

	public AlertDialog alertDialog;
	public UIProgressLoading uiProgressLoading;
	public static Boolean isAppRunningBackground = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiProgressLoading = new UIProgressLoading();
		uiProgressLoading.init(getActivity());
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

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle("Free Tamil Ebooks");
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				getActivity().finish();
			}
		});
		alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK){
					dialog.dismiss();
					getActivity().finish();
				}
				return false;
			}
		});

		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	public void showInternetAvailabilityAlertDialog(String message){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle("Free Tamil Ebooks");
		alertDialogBuilder.setMessage(message);
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				getActivity().finish();

			}
		});
		alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_BACK){
					dialog.dismiss();
					getActivity().finish();

				}
				return false;
			}
		});

		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	public void onPause() {
		isAppRunningBackground = true;
		super.onPause();
	}

	@Override
	public void onResume() {
		isAppRunningBackground = false;
		super.onResume();
	}
}

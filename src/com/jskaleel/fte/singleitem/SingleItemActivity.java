package com.jskaleel.fte.singleitem;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jskaleel.fte.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class SingleItemActivity  extends Activity {

	static final String KEY_IMAGE = "image";
	static final String KEY_TITLE = "title";
	static final String KEY_AUTHOR = "author";
	static final String KEY_EPUB = "epub";
	protected static final String Download_ID = "DOWNLOAD_ID";

	private Button dwnldButton;	
	private ImageView lblImage;
	private ProgressBar ivProgress;
	private ImageView ivTwitter, ivFacebook, ivGplus;
	private ImageView ivListButton, ivMenuButton, ivGridButton;
	
	private String title, imageURL, epub;
	private Typeface tf;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_list_item);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
		Intent in = getIntent();

		title = in.getStringExtra(KEY_TITLE);
		imageURL = in.getStringExtra(KEY_IMAGE);
		epub = in.getStringExtra(KEY_EPUB);
		tf = Typeface.createFromAsset(this.getAssets(), "fonts/TMOTNMBI_SHIP.TTF");
		
		init();
		setupDefaults();
		setupEvents();

	}
	
	private void init() {
		// TODO Auto-generated method stub
		lblImage = (ImageView) findViewById(R.id.list_image);
		dwnldButton = (Button) findViewById(R.id.dwldbutton);
		ivProgress = (ProgressBar) findViewById(R.id.ivProgress);
		ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
		ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
		ivGplus = (ImageView) findViewById(R.id.ivGplus);
		
		ivGridButton = (ImageView) findViewById(R.id.ivGridButton);
		ivListButton = (ImageView) findViewById(R.id.ivListButton);
		ivMenuButton = (ImageView) findViewById(R.id.ivMenuButton);
		ivListButton.setEnabled(false);
		ivGridButton.setEnabled(false);
	}
	
	private void setupDefaults() {
		// TODO Auto-generated method stub
		Picasso.with(getApplicationContext()).load(imageURL).into(lblImage, new Callback(){
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				ivProgress.setVisibility(View.GONE);
			}
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ivProgress.setVisibility(View.GONE);
			}
		});
		dwnldButton.setTypeface(tf);
		dwnldButton.setText("Download \"" + title + "\" epub");
		final String url = Uri.parse(epub).toString();
		Log.d("DownloadLink",url);
	}
	
	private void setupEvents() {
		// TODO Auto-generated method stub
		dwnldButton.setOnClickListener(downloadTask);
		ivTwitter.setOnClickListener(share);
		ivFacebook.setOnClickListener(share);
		ivGplus.setOnClickListener(share);
	}
	
	public OnClickListener share = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.ivTwitter:
				Toast();
				break;
			case R.id.ivFacebook:
				Toast();
				break;
			case R.id.ivGplus:
				Toast();
				break;
			}
		}

		private void Toast() {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "Not Implemented yet!!!", Toast.LENGTH_LONG).show();
		}
	};
	
	public OnClickListener downloadTask = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "Not Implemented yet!!!", Toast.LENGTH_LONG).show();
		}
	};
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

//		IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//		registerReceiver(downloadReceiver, intentFilter);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

//		unregisterReceiver(downloadReceiver);
	}
//	private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
//
//		public void onReceive(Context arg0, Intent arg1) {
//			Query query = new Query();
//			query.setFilterById(preferenceManager.getLong(Download_ID, 0));
//			Cursor cursor = downloadManager.query(query);
//
//			if(cursor.moveToFirst()){
//				int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
//				int status = cursor.getInt(columnIndex);
//
//
//				if(status == DownloadManager.STATUS_SUCCESSFUL){
//					//Retrieve the saved download id
//					long downloadID = preferenceManager.getLong(Download_ID, 0);
//
//					ParcelFileDescriptor file;
//					try {
//						file = downloadManager.openDownloadedFile(downloadID);
//						Toast.makeText(SingleItemActivity.this, "File Downloaded", Toast.LENGTH_LONG).show();
//					} catch (FileNotFoundException e) {
//						e.printStackTrace();
//						Toast.makeText(SingleItemActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//					}
//
//				}else if(status == DownloadManager.STATUS_FAILED){
//					Toast.makeText(SingleItemActivity.this, "FAILED!", Toast.LENGTH_LONG).show();
//				}else if(status == DownloadManager.STATUS_PAUSED){
//					Toast.makeText(SingleItemActivity.this, "PAUSED!", Toast.LENGTH_LONG).show();
//				}else if(status == DownloadManager.STATUS_PENDING){
//					Toast.makeText(SingleItemActivity.this, "PENDING!", Toast.LENGTH_LONG).show();
//				}else if(status == DownloadManager.STATUS_RUNNING){
//					Toast.makeText(SingleItemActivity.this,	"RUNNING!", Toast.LENGTH_LONG).show();
//				}
//			}
//		}
//	};
}

package com.jskaleel.fte.downloads;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;
import com.jskaleel.fte.common.PrintLog;
import com.jskaleel.fte.home.BooksHomeListItems;

public class FragmentDownloads extends BasicFragment {

	private static final String TAG="FragmentDownloads";

	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath, txtEmpty;
	private ListView downloadsList;

	public FragmentDownloads() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_downloads, null);

		init(view);
		setupDefaults();
		setupEvents();

		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		txtEmpty = (TextView) view.findViewById(R.id.txtempty);
		myPath = (TextView) view.findViewById(R.id.path);
		downloadsList	=	(ListView) view.findViewById(R.id.downloadList);
	}

	private void setupDefaults() {
		// TODO Auto-generated method stub
		txtEmpty.setVisibility(View.GONE);
		root = Environment.getExternalStorageDirectory().getPath()+"/Free_Tamil_Ebooks/";
		getDir(root);
	}

	private void setupEvents() {
		// TODO Auto-generated method stub

	}

	private void getDir(String dirPath) {

		myPath.setText("Location: " + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();

		if(!dirPath.equals(root))
		{
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent());	
		}

		for(int i=0; i < files.length; i++)
		{
			File file = files[i];

			if(!file.isHidden() && file.canRead()){
				path.add(file.getPath());
				if(file.isDirectory()){
					item.add(file.getName() + "/");
				}else{
					item.add(file.getName());
				}
			}	
		}
		if(item.size() != 0) {
			txtEmpty.setVisibility(View.GONE);
			DownloadsAdapter adapter = new DownloadsAdapter(getActivity(), item, FragmentDownloads.this);
			downloadsList.setAdapter(adapter);
		}else {
			txtEmpty.setVisibility(View.VISIBLE);
		}
	}

	public void OpenBook(String path) {
		// TODO Auto-generated method stub
		File file = new File(root+path);

		PrintLog.debug(TAG, "jsk-->Path-->"+path+"-->File-->"+file);

		if (file.isDirectory()) {
			if(file.canRead()){
				getDir(file.toString());
			} else{
				new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher).setTitle("[" + file.getName() + "] folder can't be read!")
				.setPositiveButton("Ok", null).show();	
			}	
		}else {
			String extension = (file.toString()).substring(((file.toString()).lastIndexOf(".") + 1), (file.toString()).length());

			if (extension.equals("epub")) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setDataAndType(Uri.fromFile(file), "application/epub");
				ComponentName cn = new ComponentName("org.geometerplus.zlibrary.ui.android", "org.geometerplus.android.fbreader.FBReader");
				intent.setComponent(cn);
				try {
					startActivity(intent);
				}catch(ActivityNotFoundException e) {
					showOkCancel();
				}
			}else {
				showAlertDialog("Invalid file format!!!");
			}
		}
	}

	public void showOkCancel() {	
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

		alertDialog.setTitle(getResources().getString(R.string.app_name));
		alertDialog.setMessage(getResources().getString(R.string.down_epub));
		alertDialog.setIcon(R.drawable.ic_launcher);

		alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				downloadIt("org.geometerplus.zlibrary.ui.android");
			}
		});

		alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alertDialog.show();
	}
	
	private void downloadIt(String packageName) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse("market://search?q=" + packageName);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);

		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			String url = "https://play.google.com/store/apps/details?id="+packageName;
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		}
	}
}

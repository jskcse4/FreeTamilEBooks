package com.jskaleel.fte.parse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jskaleel.fte.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MyListAdapter extends BaseAdapter {

	public Vector<PostValue> vecPostValue;
	public Activity activity;
	private static LayoutInflater inflater=null;
	public MyListAdapter(Vector<PostValue> vecCom,Activity a) {
		// TODO Auto-generated constructor stub
		vecPostValue=vecCom;
		activity=a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(vecPostValue != null && vecPostValue.size()>0)
		return vecPostValue.size();
		else return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi=convertView;
		PostValue postvalue=vecPostValue.get(position);
        if(convertView==null) {
            vi = inflater.inflate(R.layout.list_row, null);
        }
				
        Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/TMOTNMBI_SHIP.TTF");
        
		TextView tv1 = (TextView)vi.findViewById(R.id.txtTitle);
		TextView tv2 = (TextView)vi.findViewById(R.id.txtAuthor);
		ImageView ivArrow = (ImageView)vi.findViewById(R.id.ivLefticon);
		ImageView ivbookImage = (ImageView)vi.findViewById(R.id.ivBookImage);
		final ProgressBar ivProgress = (ProgressBar) vi.findViewById(R.id.ivProgress);
		
		tv1.setTypeface(tf);
		tv1.setText(postvalue.title);
		tv2.setTypeface(tf);
		tv2.setText(postvalue.author);
		ivbookImage.setTag(postvalue.image);
		ivArrow.setTag(postvalue.epub);
		
		Log.d("FTE","Book URL------------------->"+postvalue.image);
		try {
			String img_url = new URL(postvalue.image).toString();
			Picasso.with(activity).load(img_url).into(ivbookImage, new Callback(){

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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vi;
	}

	
}

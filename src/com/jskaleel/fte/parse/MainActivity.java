package com.jskaleel.fte.parse;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jskaleel.fte.R;
import com.jskaleel.fte.singleitem.SingleItemActivity;

public class MainActivity extends Activity {

	private ProgressBar progressBar;
	InputStream is;
	SAXParser saxParser;
	private ListView list;
	private GridView gridView;
	private ImageView ivMenuButton, ivListButton, ivGridButton;
	String xmlContent = "https://raw.githubusercontent.com/kishorek/Free-Tamil-Ebooks/master/booksdb.xml";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		init();
		setupDefaults();
		setupEvents();
	}

	private void init() {
		// TODO Auto-generated method stub
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		list = (ListView) findViewById(R.id.list);
		gridView = (GridView) findViewById(R.id.gridView);
		ivMenuButton = (ImageView) findViewById(R.id.ivMenuButton);
		ivListButton = (ImageView) findViewById(R.id.ivListButton);
		ivGridButton = (ImageView) findViewById(R.id.ivGridButton);
		list.setVisibility(View.VISIBLE);
		gridView.setVisibility(View.GONE);
	}

	private void setupDefaults() {
		// TODO Auto-generated method stub
		new listUrlParseTask().execute();
		Log.d("FTE", "List updated..."+list);
	}

	private void setupEvents() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(gridviewListener);
		list.setOnItemClickListener(viewListener);
		ivGridButton.setOnClickListener(layoutView);
		ivListButton.setOnClickListener(layoutView);
	}
	private OnItemClickListener gridviewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			String title = ((TextView) view.findViewById(R.id.txtTitle)).getText().toString();
			Log.d("FTE","Title------>"+title);

			String img_url = ((ImageView) view.findViewById(R.id.ivBookImage)).getTag().toString();
			Log.d("FTE","Img-URL------>"+img_url);

			String epub_url = ((ImageView) view.findViewById(R.id.ivLefticon)).getTag().toString();
			Log.d("FTE","Epub-URL------>"+epub_url);

			Log.d("FreeTamilEbooks","Values-----> "+title+"--"+img_url+"--"+epub_url);

			Intent in = new Intent(getApplicationContext(), SingleItemActivity.class);
			in.putExtra("title", title);
			in.putExtra("image", img_url);
			in.putExtra("epub", epub_url);
			startActivity(in);
		}
	};

	private OnItemClickListener viewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			String title = ((TextView) view.findViewById(R.id.txtTitle)).getText().toString();
			Log.d("FTE","Title------>"+title);

			String author = ((TextView) view.findViewById(R.id.txtAuthor)).getText().toString();
			Log.d("FTE","Author------>"+author);

			String img_url = ((ImageView) view.findViewById(R.id.ivBookImage)).getTag().toString();
			Log.d("FTE","Img-URL------>"+img_url);

			String epub_url = ((ImageView) view.findViewById(R.id.ivLefticon)).getTag().toString();
			Log.d("FTE","Epub-URL------>"+epub_url);

			Log.d("FreeTamilEbooks","Values-----> "+title+"--"+author+"--"+img_url+"--"+epub_url);

			Intent in = new Intent(getApplicationContext(), SingleItemActivity.class);
			in.putExtra("title", title);
			in.putExtra("author", author);
			in.putExtra("image", img_url);
			in.putExtra("epub", epub_url);
			startActivity(in);
		}
	};

	private OnClickListener layoutView = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.ivListButton:
			{
				gridView.setVisibility(View.GONE);
				new listUrlParseTask().execute();
				ivListButton.setClickable(false);
				ivGridButton.setClickable(true);	
			}
			break;
			case R.id.ivGridButton:
			{
				list.setVisibility(View.GONE);
				new gridUrlParseTask().execute();
				ivGridButton.setClickable(false);
				ivListButton.setClickable(true);
			}
			break;
			}
		}
	};

	class gridUrlParseTask extends AsyncTask<Void, Void, Void> {
		boolean success = false;
		protected void onPreExecute()
		{
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			ivMenuButton.setEnabled(false);
			ivListButton.setEnabled(false);
			ivGridButton.setEnabled(false);
		}

		protected Void doInBackground(Void... params) {

			try {
				saxParser=SAXParserFactory.newInstance().newSAXParser();
				is=new DefaultHttpClient().execute(
						new HttpGet(xmlContent)).getEntity().getContent();
				saxParser.parse(is, new XMLHelper());
				success = true;
			} catch (IllegalStateException e) {
				e.printStackTrace();
				Log.d("FTE","1------>"+e);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.d("FTE","2------>"+e);
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("FTE","3------>"+e);
			} catch (SAXException e) {
				e.printStackTrace();
				Log.d("FTE","4------>"+e);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				Log.d("FTE","5------>"+e);
			}
			if(!success) {
				Toast.makeText(getApplicationContext(), "Unable to fetch Data", Toast.LENGTH_LONG).show();
			}
			Log.d("FTE", "Parsing Done..."+is);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			gridView.setAdapter(new MyGridAdapter(new XMLHelper().getData(),MainActivity.this));
			gridView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			ivMenuButton.setEnabled(true);
			ivListButton.setEnabled(true);
			ivGridButton.setEnabled(true);
		}
	}

	class listUrlParseTask extends AsyncTask<Void, Void, Void> {
		boolean success = false;
		protected void onPreExecute()
		{
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			ivMenuButton.setEnabled(false);
			ivListButton.setEnabled(false);
			ivGridButton.setEnabled(false);
		}

		protected Void doInBackground(Void... params) {

			try {
				saxParser=SAXParserFactory.newInstance().newSAXParser();
				is=new DefaultHttpClient().execute(
						new HttpGet(xmlContent)).getEntity().getContent();
				saxParser.parse(is, new XMLHelper());
				success = true;
			} catch (IllegalStateException e) {
				e.printStackTrace();
				Log.d("FTE","1------>"+e);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.d("FTE","2------>"+e);
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("FTE","3------>"+e);
			} catch (SAXException e) {
				e.printStackTrace();
				Log.d("FTE","4------>"+e);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				Log.d("FTE","5------>"+e);
			}
			if(!success) {
				Toast.makeText(MainActivity.this, "Unable to fetch Data", Toast.LENGTH_LONG).show();
				finish();
			}
			Log.d("FTE", "Parsing Done..."+is);
			return null;
		}

		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			list.setAdapter(new MyListAdapter(new XMLHelper().getData(),MainActivity.this));
			list.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			ivMenuButton.setEnabled(true);
			ivListButton.setEnabled(true);
			ivGridButton.setEnabled(true);
		}
	}
}

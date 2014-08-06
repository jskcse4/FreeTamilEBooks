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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	ListView list;
	String xmlContent = "https://raw.githubusercontent.com/kishorek/Free-Tamil-Ebooks/master/booksdb.xml";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		list=(ListView)findViewById(R.id.list);
		/*try {
        	saxParser=SAXParserFactory.newInstance().newSAXParser();
			is=new DefaultHttpClient().execute(
					new HttpGet(xmlContent)).getEntity().getContent();
			saxParser.parse(is, new XMLHelper());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("FTE","1------>"+e);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("FTE","2------>"+e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("FTE","3------>"+e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("FTE","4------>"+e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("FTE","5------>"+e);
		}*/
		new urlparseTask().execute();
		Log.d("FTE", "List updated..."+list);

		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

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
		});

	}

	class urlparseTask extends AsyncTask<Void, Void, Void> {
//		ProgressDialog pg;
		boolean success = false;;
		protected void onPreExecute()
		{
			super.onPreExecute();
//			pg = new ProgressDialog(MainActivity.this);
//			pg.setTitle("Free Tamil Ebooks");
//			pg.setMessage("Loading...");
//			pg.setCanceledOnTouchOutside(false);
//			pg.show();
			progressBar.setVisibility(View.VISIBLE);
		}

		protected Void doInBackground(Void... params) {

			try {
				saxParser=SAXParserFactory.newInstance().newSAXParser();
				is=new DefaultHttpClient().execute(
						new HttpGet(xmlContent)).getEntity().getContent();
				saxParser.parse(is, new XMLHelper());
				success = true;
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("FTE","1------>"+e);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("FTE","2------>"+e);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("FTE","3------>"+e);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("FTE","4------>"+e);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
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
			list.setAdapter(new MyArrayAdapter(new XMLHelper().getData(),MainActivity.this));
//			pg.dismiss();
			progressBar.setVisibility(View.GONE);

		}
	}
}

package com.jskaleel.fte.home;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;
import com.jskaleel.fte.common.ConnectionDetector;
import com.jskaleel.fte.common.PrintLog;
import com.jskaleel.http.HttpGetUrlConnection;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FragmentHome extends BasicFragment implements HomeItemListener{

	private String TAG = "FragmentHome";
	private View rootView;
	private ListView listView;
	private BooksHomeParser booksHomeListParser;
	private BooksHomeListItems booksHomeListItems;
	private ArrayList<BooksHomeListItems> bookListArray; 
	private BooksHomeAdapter booksHomeAdapter;
	private static final String xmlUrl = "https://raw.githubusercontent.com/kishorek/Free-Tamil-Ebooks/master/booksdb.xml";

	//Connection Detector
	private Boolean isInternetAvailable = false;
	private ConnectionDetector connectionDetector;

	private String savedfilePath;

/*    private int mShortAnimationDuration;
    private Animator mCurrentAnimator;*/
	
    private ImageView expandedImageView;
    private RelativeLayout imgLayout;
    private ProgressBar bookProgressBar;
    
	public FragmentHome() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, null);

		connectionDetector = new ConnectionDetector(getActivity());
		isInternetAvailable = connectionDetector.isConnectingToInternet();

		if(isInternetAvailable) {
			init();
			setupDefaults();
			setupEvents();
		} else{
			showInternetAvailabilityAlertDialog(getResources().getString(R.string.check_connection));
		}
		return rootView;
	}

	private void init() {

		listView = (ListView) rootView.findViewById(R.id.fragment_listview);

        // Load the high-resolution "zoomed-in" image.
		imgLayout						=	(RelativeLayout) rootView.findViewById(R.id.imgLayout);
        expandedImageView 	= (ImageView) rootView.findViewById(R.id.expanded_image);
        bookProgressBar			=	(ProgressBar) rootView.findViewById(R.id.bookProgressBar);

		bookListArray			=	new ArrayList<BooksHomeListItems>();
		booksHomeAdapter	=	new BooksHomeAdapter(bookListArray, FragmentHome.this, getActivity());
		booksHomeAdapter.setListItemListener(FragmentHome.this);
		
        // Retrieve and cache the system's default "short" animation time.
//        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
	}

	private void setupDefaults() {
		// TODO Auto-generated method stub
		imgLayout.setVisibility(View.GONE);
		new TaskGetXmlfromUrl().execute();
	}

	private void setupEvents() {
		// TODO Auto-generated method stub
		imgLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imgLayout.setVisibility(View.GONE);
			}
		});
	}
	private class TaskGetXmlfromUrl extends AsyncTask<Void, Void, String>
	{

		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog(getResources().getString(R.string.loading_));
		};

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return HttpGetUrlConnection.getContentFromUrl(xmlUrl);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			PrintLog.debug("XML", ""+result);
			String xmlContent = result;
			JSONObject jsonObj = null;
			try {
				jsonObj = XML.toJSONObject(xmlContent);
			} catch (JSONException e) {
				PrintLog.debug("JSON exception", e.getMessage());
				e.printStackTrace();
			} 
			PrintLog.debug("JSON",jsonObj.toString());
			Gson gson = new Gson();
			booksHomeListParser =gson.fromJson(jsonObj.toString(), BooksHomeParser.class);
			for(int i=0; i<booksHomeListParser.books.book.size(); i++) {
				booksHomeListItems = new BooksHomeListItems(
						booksHomeListParser.books.book.get(i).author,
						booksHomeListParser.books.book.get(i).category,
						booksHomeListParser.books.book.get(i).title,
						booksHomeListParser.books.book.get(i).pdf,
						booksHomeListParser.books.book.get(i).epub,
						booksHomeListParser.books.book.get(i).link,
						booksHomeListParser.books.book.get(i).image,
						booksHomeListParser.books.book.get(i).date);
				bookListArray.add(booksHomeListItems);
			}

			booksHomeAdapter	=	new BooksHomeAdapter(bookListArray, FragmentHome.this, getActivity());
			booksHomeAdapter.setListItemListener(FragmentHome.this);

			PrintLog.debug(TAG, "Adapter Called");
			listView.setAdapter(booksHomeAdapter);
			listView.setOnScrollListener(booksHomeAdapter);
			booksHomeAdapter.setResetPos();

			dismissProgressDialog();
		}
	}
	public void downloadEpub(BooksHomeListItems singleItem) {
		// TODO Auto-generated method stub
		final String url = Uri.parse(singleItem.epub).toString();
		new TaskDownloadEpub(singleItem).execute(url);
	}

	class TaskDownloadEpub extends AsyncTask<String, String, String> {
		BooksHomeListItems singleItem;
		ProgressDialog downDialog;
		
		private boolean isCancelled = false;

		public TaskDownloadEpub(BooksHomeListItems singleItem) {
			// TODO Auto-generated constructor stub
			this.singleItem	=	singleItem;
		}

		@SuppressWarnings("static-access")
		protected void onPreExecute() {
			super.onPreExecute();
			downDialog = new ProgressDialog(getActivity());
			downDialog.setMessage(getResources().getString(R.string.downloading));
			downDialog.setCancelable(false);
			downDialog.setProgress(0);
			downDialog.setProgressStyle(downDialog.STYLE_HORIZONTAL);
			downDialog.setMax(100);
			downDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					isCancelled = true;
					dialog.dismiss();
				}
			});
			downDialog.show();
		}

		@Override
		protected String doInBackground(String... f_url) {
			int count;
			while(!isCancelled) {
				try {
					URL url = new URL(f_url[0]);
					URLConnection conection = url.openConnection();
					conection.connect();

					int lenghtOfFile = conection.getContentLength();
					InputStream input = new BufferedInputStream(url.openStream(), 1024*10);
					File path = new File(Environment.getExternalStorageDirectory(),"/Free_Tamil_Ebooks");

					if(!(path.exists()))
						path.mkdir();

					savedfilePath = path+"/"+singleItem.title+".epub";
					OutputStream output = new FileOutputStream(savedfilePath);
					byte data[] = new byte[1024];

					long total = 0;

					while ((count = input.read(data)) != -1) {
						if(!isCancelled) {
							total += count;
							publishProgress(""+(int)((total*100)/lenghtOfFile));
							output.write(data, 0, count);
						}else {
							File file = new File(savedfilePath);
							file.delete();
							PrintLog.debug(TAG, "File Deleted Successfully...");
							return null;
						}
					}
					output.flush();
					output.close();
					input.close();

				} catch (Exception e) {
					Log.e("Error: ", e.getMessage());
				}

				return savedfilePath;
			}
			return null;
		}
		protected void onProgressUpdate(String... progress) {
			//			 setting progress percentage
			downDialog.setProgress(Integer.parseInt(progress[0]));
		}

		protected void onPostExecute(String filePath){
			super.onPostExecute(filePath);
			if(filePath != null){
				downDialog.dismiss();
				Toast.makeText(getActivity(), "File Saved at "+savedfilePath, Toast.LENGTH_LONG).show();
				showOkCancel(savedfilePath, 1, singleItem);
			}
		}
	}

	@Override
	public void DownloadPressed(BooksHomeListItems singleItem) {
		// TODO Auto-generated method stub
		final String url = Uri.parse(singleItem.epub).toString();
		new TaskDownloadEpub(singleItem).execute(url);
	}

	@Override
	public void OpenPressed(BooksHomeListItems singleItem) {
		// TODO Auto-generated method stub
		File path = new File(Environment.getExternalStorageDirectory(),"/Free_Tamil_Ebooks/"+singleItem.title+".epub");
		PrintLog.debug(TAG, "File Path-->"+path.toString());
		if(path.exists()) {
			openBook(path.toString());
		}else {
			showOkCancel(path.toString(), 2, singleItem);
			//			DownloadPressed(singleItem);
		}
	}

	public void shareOnTwitter(BooksHomeListItems singleItem) {
		// TODO Auto-generated method stub

	}
	public void showOkCancel(final String filePath, final int from, final BooksHomeListItems singleItem) {	// from = 1 --> request to OpenBook
		// TODO Auto-generated method stub																													// from = 2 --> request to start download
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

		alertDialog.setTitle(getResources().getString(R.string.app_name));

		if(from == 1) {
			alertDialog.setMessage(getResources().getString(R.string.open_dialog));
		}else if(from == 2) {
			alertDialog.setMessage(getResources().getString(R.string.book_not_found));
		}else if(from == 3) {
			alertDialog.setMessage(getResources().getString(R.string.down_epub));
		}

		alertDialog.setIcon(R.drawable.ic_launcher);

		alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				if(from == 1) {
					openBook(filePath);
				}else if(from == 2) {
					DownloadPressed(singleItem);
				}else if(from == 3) {
					downloadIt("org.geometerplus.zlibrary.ui.android");
				}
			}
		});

		alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		alertDialog.show();
	}

	protected void openBook(String filePath) {
		// TODO Auto-generated method stub
		File file = new File(filePath);
		String extension = filePath.substring((filePath.lastIndexOf(".") + 1), filePath.length());

		PrintLog.debug(TAG, "FileExtension-->"+extension);
		if (extension.equals("epub")) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(file), "application/epub");
			ComponentName cn = new ComponentName("org.geometerplus.zlibrary.ui.android", "org.geometerplus.android.fbreader.FBReader");
			intent.setComponent(cn);
			try {
				startActivity(intent);
			} catch(ActivityNotFoundException e) {
				showOkCancel("", 3, null);
			}
		}
		PrintLog.debug(TAG, "FilePath-->"+filePath);
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
	
	@Override
	public void BookIconPressed(final View v, String bookImage) {
		imgLayout.setVisibility(View.VISIBLE);
		Picasso.with(getActivity()).load(bookImage).into(expandedImageView, new Callback() {
			@Override
			public void onSuccess() {
				bookProgressBar.setVisibility(View.GONE);
			}
			@Override
			public void onError() {
				expandedImageView.setBackgroundResource(R.drawable.default_img);
				bookProgressBar.setVisibility(View.GONE);
			}
		});
	}

/*	@SuppressLint("NewApi")
	@Override
	public void BookIconPressed(final View v, String bookImage) {
		// TODO Auto-generated method stub

        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        Picasso.with(getActivity()).load(bookImage).into(expandedImageView, new Callback() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
			}
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				expandedImageView.setBackgroundResource(R.drawable.default_img);
			}
		});

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        v.getGlobalVisibleRect(startBounds);
        rootView.findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds.width() / startBounds.height()) {
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        v.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        v.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
	}*/
}

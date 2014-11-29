package com.jskaleel.fte.home;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jskaleel.fte.R;
import com.jskaleel.fte.common.FTEDevice;
import com.jskaleel.fte.common.OnListItemTouchListener;
import com.jskaleel.fte.common.TouchHighlightImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BooksHomeAdapter extends BaseAdapter implements OnScrollListener {

	private Context context;
	private ArrayList<BooksHomeListItems> bookListArray;
	private FragmentHome fragmentHome;
	private Typeface tf;

	private boolean fastAnim,isAnim;
	private int SwipeLength;
	private HomeItemListener homeItemListener;	
	private int selectedPos=0;

	public BooksHomeAdapter(ArrayList<BooksHomeListItems> bookListArray, FragmentHome fragmentHome, Context context) {
		this.bookListArray	=	bookListArray;
		this.fragmentHome	=	fragmentHome;
		this.context				=	context;

		SwipeLength				= FTEDevice.convertDpToPx(100, context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bookListArray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bookListArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setListItemListener(HomeItemListener listener)
	{
		this.homeItemListener = listener;
	}

	public void setResetPos()
	{
		selectedPos = -1;
		fastAnim = true;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View layout = null;
		final ItemViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflator	 	= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout 									= (View) inflator.inflate(R.layout.books_list_row, null);
			holder 								= new ItemViewHolder();

			holder.txtBookTitle			= (TextView) layout.findViewById(R.id.txtTitle);
			holder.txtAuthorName		= (TextView) layout.findViewById(R.id.txtAuthor);
			holder.ivBookIcon				=	(TouchHighlightImageView) layout.findViewById(R.id.ivBookImage);
			holder.ivProgress				=	(ProgressBar) layout.findViewById(R.id.ivProgress);
			holder.btnShare			=	(Button) layout.findViewById(R.id.btnDownload);

			holder.baseLayout			=	(LinearLayout) layout.findViewById(R.id.baseLayout);
			holder.btnDownload				=	(Button) layout.findViewById(R.id.btnDown);
			holder.btnOpen					=	(Button) layout.findViewById(R.id.btnOpen);

			layout.setTag(holder);
		}else {
			layout = (RelativeLayout) convertView;
			holder = (ItemViewHolder) layout.getTag();
		}
		tf = Typeface.createFromAsset(context.getAssets(), "fonts/TMOTNMBI_SHIP.TTF");

		final BooksHomeListItems singleItem	=	bookListArray.get(position);

		if(singleItem.title != null && !singleItem.title.equalsIgnoreCase("")) {
			holder.txtBookTitle.setTypeface(tf);
			holder.txtBookTitle.setText(singleItem.title);
		}else {
			holder.txtBookTitle.setText("");
		}

		if(singleItem.author != null && !singleItem.author.equalsIgnoreCase("")) {
			holder.txtAuthorName.setTypeface(tf);
			holder.txtAuthorName.setText(singleItem.author);
		}else {
			holder.txtAuthorName.setText("");
		}

		if(singleItem.image != null && !singleItem.image.equalsIgnoreCase("")) {
			//			StaticAquery.loadProfileImage(context, holder.ivBookIcon, holder.ivProgress, singleItem.image);
			//			holder.ivProgress.setBackgroundResource(R.drawable.default_img);
//			new ImageDownloaderTask(holder.ivBookIcon, holder.ivProgress).execute(singleItem.image);
			Picasso.with(context).load(singleItem.image).into(holder.ivBookIcon, new Callback() {
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					holder.ivProgress.setVisibility(View.GONE);
				}
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					holder.ivProgress.setBackgroundResource(R.drawable.default_img);
					holder.ivProgress.setVisibility(View.GONE);
				}
			});

		}

		if(!FTEDevice.PRE_HC_11)
		{
			if(selectedPos!=position)
			{
				if(fastAnim)
					ObjectAnimator.ofFloat(holder.baseLayout, "translationX",0).setDuration(0).start();
				else
					ObjectAnimator.ofFloat(holder.baseLayout, "translationX",0).setDuration(500).start();
			}
			else
			{
				if(fastAnim)
					ObjectAnimator.ofFloat(holder.baseLayout, "translationX",0).setDuration(0).start();
			}
			isAnim = false;
		}

		holder.baseLayout.setOnTouchListener(new OnListItemTouchListener(){

			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				return super.onTouch(view, motionEvent);
			}

			@Override
			public void onMove(float difference) {

				if(!FTEDevice.PRE_HC_11)
				{
					if(!isAnim)
					{
						if(difference<=-7)
						{
							isAnim = true;
							ObjectAnimator.ofFloat(holder.baseLayout, "translationX",-SwipeLength).setDuration(500).start();
							selectedPos = position;
							fastAnim = false;
							notifyDataSetChanged();
						}
						else if(difference>=7)
						{
							isAnim = true;
							ObjectAnimator.ofFloat(holder.baseLayout, "translationX",0).setDuration(500).start();
							notifyDataSetChanged();
						}
					}
				}

				super.onMove(difference);
			}

			@Override
			public void onUp(float difference, float touchUpX) {

				if(!FTEDevice.PRE_HC_11)
				{
					if(difference<=-7)
					{
						ObjectAnimator.ofFloat(holder.baseLayout, "translationX",-SwipeLength).setDuration(500).start();
						selectedPos = position;
						fastAnim = false;
					}
					else if(difference>=7)
					{
						ObjectAnimator.ofFloat(holder.baseLayout, "translationX",0).setDuration(500).start();
					}
					notifyDataSetChanged();
				}
				super.onUp(difference, touchUpX);
			}

			@Override
			public void onCancel(float difference) {
				super.onCancel(difference);
			}

			@Override
			public void onSwipeRight() {
				if(!FTEDevice.PRE_HC_11)
				{
					ObjectAnimator.ofFloat(holder.baseLayout, "translationX",0).setDuration(500).start();
					notifyDataSetChanged();
				}
				super.onSwipeRight();
			}

			@Override
			public void onSwipeLeft() {
				if(!FTEDevice.PRE_HC_11){
					ObjectAnimator.ofFloat(holder.baseLayout, "translationX",-SwipeLength).setDuration(500).start();
					fastAnim = false;
					selectedPos = position;
					notifyDataSetChanged();
				}
				super.onSwipeLeft();
			}

			@Override
			public void onSwipeTop() {
				super.onSwipeTop();
			}

			@Override
			public void onSwipeBottom() {
				super.onSwipeBottom();
			}

			@Override
			public void onHover() {
				super.onHover();
			}
		});

		holder.btnDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//				fragmentHome.downloadEpub(singleItem);
				if(homeItemListener != null) {
					homeItemListener.DownloadPressed(singleItem);
				}
			}
		});

		holder.btnOpen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(homeItemListener != null) {
					homeItemListener.OpenPressed(singleItem);
				}
			}
		});
		
		holder.ivBookIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(homeItemListener != null) {
					homeItemListener.BookIconPressed(v, singleItem.image);
				}
			}
		});

		holder.btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentHome.shareOnTwitter(singleItem);
			}
		});
		return layout;
	}

	public static class ItemViewHolder {
		private TextView txtBookTitle, txtAuthorName;
		private TouchHighlightImageView ivBookIcon;
		private ProgressBar ivProgress;
		private Button btnShare;

		private LinearLayout baseLayout;
		private Button btnDownload, btnOpen;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if(scrollState ==2||scrollState ==1)
			fastAnim = true;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {	}
}

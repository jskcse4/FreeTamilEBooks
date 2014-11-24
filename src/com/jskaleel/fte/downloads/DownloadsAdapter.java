package com.jskaleel.fte.downloads;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jskaleel.fte.R;

public class DownloadsAdapter extends BaseAdapter {

	private Context context;
	private List<String> item;
	private FragmentDownloads fragmentDownloads;
	
	public DownloadsAdapter(Context context, List<String> item, FragmentDownloads fragmentDownloads) {
		// TODO Auto-generated constructor stub
		this.context	=	context;
		this.item	=	item;
		this.fragmentDownloads	=	fragmentDownloads;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return item.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View layout = null;
		final ListViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflator	 	= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layout 									= (View) inflator.inflate(R.layout.downloads_list_row, null);
			holder 								= new ListViewHolder();

			holder.txtBookTitle			= (TextView) layout.findViewById(R.id.txtTitle);

			layout.setTag(holder);
		}else {
			layout = (LinearLayout) convertView;
			holder = (ListViewHolder) layout.getTag();
		}
		
		holder.txtBookTitle.setText(item.get(position));
		
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fragmentDownloads.OpenBook(item.get(position));
			}
		});
		
		return layout;
	}
	public static class ListViewHolder {
		private TextView txtBookTitle;
	}
}

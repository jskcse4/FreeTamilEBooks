package com.jskaleel.fte.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.jskaleel.fte.R;

public class StaticAquery {

	public static void loadProfileImage(Context context, ImageView imageView, ProgressBar progressBar, String url) {
		AQuery aQueryUserImage = new AQuery(context);
		aQueryUserImage
		.id(imageView)
		.progress(progressBar)
		.image(url, false, true, 0, R.drawable.default_img, new BitmapAjaxCallback() {
			@Override
			public void callback(String url, ImageView iv,
					Bitmap bm, AjaxStatus status) {
				iv.setImageBitmap(bm);
				iv.setScaleType(ScaleType.CENTER_CROP);
			}
		});
	}
}

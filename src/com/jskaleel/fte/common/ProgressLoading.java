package com.jskaleel.fte.common;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jskaleel.fte.R;

public class ProgressLoading extends Dialog 
{
	Context context;
	TextView txtMessage;
	
	public ProgressLoading(Context context) {
		super(context,R.style.custom_progress_dialog_style);
		this.context = context;
	}

	public ProgressLoading(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}


	public void updateMessage(CharSequence message) {
		if (message != null && message.length() > 0) {
			if(txtMessage!=null)
			{
				txtMessage.setText(message);
				txtMessage.invalidate();
			}
		}
	}

	public ProgressLoading getDialog(CharSequence message, boolean cancelable) 
	{
		setContentView(R.layout.custom_progress_dialog);
		if (message == null || message.length() == 0) {
			findViewById(R.id.message).setVisibility(View.GONE);
		} else {
			txtMessage = (TextView) findViewById(R.id.message);
			txtMessage.setText(message);
			
//			Typeface tf=Typeface.createFromAsset(getContext().getAssets(), "fonts/MGS45_0.TTF");
//			txt.setTypeface(tf);
		}
		
		setCancelable(cancelable);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.dimAmount = 0.2f;
		getWindow().setAttributes(lp);

		return this;
	}
}

package com.jskaleel.fte.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jskaleel.fte.R;
import com.jskaleel.fte.common.BasicFragment;

public class FragmentContact extends BasicFragment {
	
	private static final String TAG = "FragmentContact";

	private EditText edtFName, edtLName, edtMail, edtMessage;
	private Button btnSubmit;
	private String FName, LName, EMail, Message;
	private static final int FROM_EMAIL_CLIENT = 121;

	public FragmentContact() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contact, null);

		init(view);
		setupDefaults();
		setupEvents();

		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		edtFName		=	(EditText) view.findViewById(R.id.edtFName);
		edtLName		=	(EditText) view.findViewById(R.id.edtLName);
		edtMail			=	(EditText) view.findViewById(R.id.edtMail);
		edtMessage	=	(EditText) view.findViewById(R.id.edtMessage);

		btnSubmit		=	(Button) view.findViewById(R.id.btnSubmit);
	}

	private void setupDefaults() {
		// TODO Auto-generated method stub
		setAllFieldsEmpty();
	}

	private void setAllFieldsEmpty() {
		// TODO Auto-generated method stub
		edtFName.setText("");
		edtLName.setText("");
		edtMail.setText("");
		edtMessage.setText("");
	}

	private void setupEvents() {
		// TODO Auto-generated method stub		
		btnSubmit.setOnClickListener(submitListener);
	}

	private OnClickListener submitListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FName	=	edtFName.getText().toString();
			LName	=	edtLName.getText().toString();
			EMail		=	edtMail.getText().toString();
			Message	=	edtMessage.getText().toString();
			if(validateFields()) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.putExtra(Intent.EXTRA_EMAIL, new String[] {"freetamilebooksteam@gmail.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "Review from User[Android-App]");
				i.putExtra(Intent.EXTRA_TEXT, "\nName : "+FName+" "+LName+"\nEmail : "+EMail+"\nMessage : "+Message);
				i.setType("message/rfc822");
				getActivity().startActivityForResult(Intent.createChooser(i, "Choose an Email Client..."), FROM_EMAIL_CLIENT);
			}
		}
	};

	protected boolean validateFields() {
		// TODO Auto-generated method stub
		if(FName != null && !FName.equalsIgnoreCase("")) {
			if(LName != null && !LName.equalsIgnoreCase("")) {
				if(EMail != null && !EMail.equalsIgnoreCase("")) {
					if(Patterns.EMAIL_ADDRESS.matcher(EMail).matches()) {
						if(Message != null && !Message.equalsIgnoreCase("")) {
							return true;
						}else {
							showAlertDialog("Message should not be empty!!!");
						}
					}else {
						showAlertDialog("Not an email!!!");
					}
				}else {
					showAlertDialog("Email should not be empty!!!");
				}
			}else {
				showAlertDialog("LastName should not be empty!!!");
			}
		}else {
			showAlertDialog("FirtName should not be empty!!!");
		}
		return false;
	}
	

	public void showOkCancelAlert() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("Free Tamil Ebooks");
        alertDialog.setMessage("Do you want to clear the Fields???");
 
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	setAllFieldsEmpty();
            }
        });
 
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	}
}

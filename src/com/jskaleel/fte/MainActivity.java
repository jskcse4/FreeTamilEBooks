package com.jskaleel.fte;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jskaleel.fte.common.PrintLog;
import com.jskaleel.fte.downloads.FragmentDownloads;
import com.jskaleel.fte.fragment.FragmentAboutUs;
import com.jskaleel.fte.fragment.FragmentContact;
import com.jskaleel.fte.home.FragmentHome;
import com.jskaleel.fte.layout.MainLayout;

// DK 
// This is my studying about Sliding Menu following Youtube video
public class MainActivity extends FragmentActivity {


	private static final String TAG = "MainActivity";

	private static Context context;

	private static int fragmentType;

	public static int getFragmentType() {
		return fragmentType;
	}

	public static void setFragmentType(int fragmentType) {
		MainActivity.fragmentType = fragmentType;
	}

	// The MainLayout which will hold both the sliding menu and our main content
	// Main content will holds our Fragment respectively
	MainLayout mainLayout;

	// ListView menu
	private ListView lvMenu;
	private String[] lvMenuItems;
	private String[] locMenuItems;

	// Menu button
	ImageView btMenu, btSearch;

	// Title according to fragment
	TextView tvTitle;


	private boolean doubleBackToExitPressedOnce;

	private int FROM_EMAIL_CLIENT = 121;


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		PrintLog.debug(TAG, "RequestCode--->"+requestCode+"-->Result Code-->"+resultCode);
		if(requestCode == FROM_EMAIL_CLIENT) {
			FragmentContact fragment = (FragmentContact) getSupportFragmentManager().findFragmentByTag("Contacts");
			fragment.showOkCancelAlert();
		}		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate the mainLayout
		mainLayout = (MainLayout)this.getLayoutInflater().inflate(R.layout.activity_main, null);
		setContentView(mainLayout);

		// Init menu

		lvMenuItems = getResources().getStringArray(R.array.menu_items);
		locMenuItems	=	getResources().getStringArray(R.array.loc_menu_items);
		PrintLog.debug("jsk", "ArraySize--->"+lvMenuItems.length);
		lvMenu = (ListView) findViewById(R.id.activity_main_menu_listview);
		//        lvMenu.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, lvMenuItems));

		//		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, lvMenuItems){
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, locMenuItems){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view =super.getView(position, convertView, parent);

				TextView textView=(TextView) view.findViewById(android.R.id.text1);
				/*YOUR CHOICE OF COLOR*/
				textView.setTextColor(Color.WHITE);
				textView.setTextSize(20);
				textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

				return view;
			}
		};
		lvMenu.setAdapter(adapter);
		lvMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onMenuItemClick(parent, view, position, id);
			}

		});


		// Get menu button
		btMenu = (ImageView) findViewById(R.id.ivMenuButton);
		btMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show/hide the menu
				toggleMenu(v);
			}
		});
		
		btSearch	=	(ImageView) findViewById(R.id.ivSearchButton);

		// Get title textview
		tvTitle = (TextView) findViewById(R.id.activity_main_content_title);


		// Add FragmentMain as the initial fragment       
		FragmentManager fm = MainActivity.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		FragmentHome fragment = new FragmentHome();
		ft.add(R.id.activity_main_content_fragment, fragment);
		ft.commit();   

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void toggleMenu(View v){
		mainLayout.toggleMenu();
	}

	// Perform action when a menu item is clicked
	private void onMenuItemClick(AdapterView<?> parent, View view, int position, long id) {
		String selectedItem = lvMenuItems[position];
		String locStringItem	=	locMenuItems[position];
		String currentItem = tvTitle.getText().toString();
		PrintLog.debug("jsk", "Position--->"+position+"-->Title-->"+selectedItem+"-->Loc String-->"+locStringItem);

		// Do nothing if selectedItem is currentItem
		if(selectedItem.compareTo(currentItem) == 0) {
			mainLayout.toggleMenu();
			return;
		}

		FragmentManager fm = MainActivity.this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = null;


		if(selectedItem.compareTo("Home") == 0) {
			btSearch.setVisibility(View.VISIBLE);
			fragment = new FragmentHome();
		} else if(selectedItem.compareTo("About Us") == 0) {
			btSearch.setVisibility(View.GONE);
			setFragmentType(1);
			fragment = new FragmentAboutUs();
		} else if(selectedItem.compareTo("Contributors") == 0) {
			btSearch.setVisibility(View.GONE);
			setFragmentType(2);
			fragment = new FragmentAboutUs();
			//			fragment = new FragmentContributors();
		} else if(selectedItem.compareTo("Publish") == 0) {
			btSearch.setVisibility(View.GONE);
			setFragmentType(3);
			fragment = new FragmentAboutUs();
		}else if(selectedItem.compareTo("Contacts") == 0) {
			btSearch.setVisibility(View.GONE);
			fragment = new FragmentContact();
		}else if(selectedItem.compareTo("Downloads") == 0) {
			btSearch.setVisibility(View.GONE);
			fragment = new FragmentDownloads();
		}

		if(fragment != null) {
			// Replace current fragment by this new one
			ft.replace(R.id.activity_main_content_fragment, fragment,selectedItem);
			ft.commit();

			// Set title accordingly
			tvTitle.setText(locStringItem);
		}
		// Hide menu anyway
		mainLayout.toggleMenu();
	}

	@Override
	public void onBackPressed() {
		if (mainLayout.isMenuShown()) {
			mainLayout.toggleMenu();
		}else if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}
		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, getString(R.string.txt_exit), Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				doubleBackToExitPressedOnce=false;   
			}
		}, 2000);
	}

	/**
	 * Hides the soft keyboard
	 */
	public static void hideSoftKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * Shows the soft keyboard
	 */
	public static void showSoftKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
		view.requestFocus();
		inputMethodManager.showSoftInput(view, 0);
	}
}

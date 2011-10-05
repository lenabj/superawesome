package com.lenaogmalin.senior;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SeniorActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	private PhoneStateListener mListener;
	private TelephonyManager mTelMgr;
	private Intent callIntent;

	// Sets up a phonestatelistener that listens to phones call status and
	// sets an intent that goes to SeniorActvity when call goes idle
	private class CallEndedListener extends PhoneStateListener {
		boolean called = false;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			if (called && state == TelephonyManager.CALL_STATE_IDLE) {
				called = false;
				mTelMgr.listen(this, PhoneStateListener.LISTEN_NONE);
				try {
					SeniorActivity.this.finish();
					Intent t = new Intent(SeniorActivity.this,
							SeniorActivity.class);
					t.setAction(Intent.ACTION_MAIN);
					startActivity(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (state == TelephonyManager.CALL_STATE_OFFHOOK)
					called = true;
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Assign the buttons to variables and set onClickListeners.
		Button bGoCal = (Button) findViewById(R.id.bCal);
		bGoCal.setOnClickListener(this);

		Button bGoPic = (Button) findViewById(R.id.bPicture);
		bGoPic.setOnClickListener(this);

		Button bCallPers1 = (Button) findViewById(R.id.bPers1);
		bCallPers1.setOnClickListener(this);

		Button bCallPers2 = (Button) findViewById(R.id.bPers2);
		bCallPers2.setOnClickListener(this);

		Button bCallHomeH = (Button) findViewById(R.id.bHomeH);
		bCallHomeH.setOnClickListener(this);

		mListener = new CallEndedListener();
		mTelMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
	}

	// Gets the phone-number from the preferences,sets it and sends it in to the
	// CALL function, and adds a phonestatelistener
	private void phoneCallP1() {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(SeniorActivity.this);
		mTelMgr.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
		String number = prefs.getString("PhoneNum", "111");
		callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		startActivity(callIntent);
		
	}

	private void phoneCallP2() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(SeniorActivity.this);
		mTelMgr.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
		String number = prefs.getString("PhoneNum2", "222");
		callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		startActivity(callIntent);
		
	}

	public void phoneCallHH() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(SeniorActivity.this);
		mTelMgr.listen(mListener, PhoneStateListener.LISTEN_CALL_STATE);
		String number = prefs.getString("PhoneNumHH", "333");
		callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
			startActivity(callIntent);
		
	}

	// What to do when the buttons over are clicked.
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.bCal:
			Intent q = new Intent("com.lenaogmalin.senior.CALL");
			startActivity(q);
			break;

		case R.id.bPicture:
			Intent ic = new Intent("com.lenaogmalin.senior.CAMERA");
			startActivity(ic);

			break;

		case R.id.bPers1:
			// Checks to see if the user have Skype on their phones
			Intent intent = new Intent();
			intent.setComponent(new ComponentName("com.skype.raider",
					"com.skype.raider.Main"));
			PackageManager pm = getPackageManager();
			List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(SeniorActivity.this);
			String number = prefs.getString("PhoneNum", "111");

			// if user has Skype, do a try and call using Skype, else use the
			// phoneCallP1() method.
			if (activities != null && activities.size() > 0) {
				try {
					Intent sky = new Intent(
							"android.intent.action.CALL_PRIVILEGED");
					sky.setClassName("com.skype.raider",
							"com.skype.raider.contactsync.ContactSkypeOutCallStartActivity");
					sky.setData(Uri.parse("tel:" + number));
					startActivity(sky);
				} catch (Exception e) {
				}
			} else {
				phoneCallP1();
			}

			break;

		case R.id.bPers2:
			Intent intent2 = new Intent();
			intent2.setComponent(new ComponentName("com.skype.raider",
					"com.skype.raider.Main"));
			PackageManager pm2 = getPackageManager();
			List<ResolveInfo> activities2 = pm2.queryIntentActivities(intent2,
					0);

			SharedPreferences prefs2 = PreferenceManager
					.getDefaultSharedPreferences(SeniorActivity.this);
			String number2 = prefs2.getString("PhoneNum2", "222");

			if (activities2 != null && activities2.size() > 0) {
				try {
					Intent sky = new Intent(
							"android.intent.action.CALL_PRIVILEGED");
					sky.setClassName("com.skype.raider",
							"com.skype.raider.contactsync.ContactSkypeOutCallStartActivity");
					sky.setData(Uri.parse("tel:" + number2));
					startActivity(sky);
				} catch (Exception e) {
					e.printStackTrace();  //this 
					Toast.makeText(SeniorActivity.this,
							"Could not connect to Skype", Toast.LENGTH_LONG)
							.show();
				}

			} else {
				phoneCallP2();

				break;
			}

		case R.id.bHomeH:
			phoneCallHH();

			break;

		}
	}

	// Menu and items in menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsActivity.class));
			break;

		case R.id.itemAbout:
			Intent it = new Intent("com.lenaogmalin.senior.ABOUT");
			startActivity(it);

			break;

		}

		return true;
	}

}

package com.lenaogmalin.senior;

import android.os.Bundle;
import android.preference.PreferenceActivity;

//The Preferences class in the pop up menu

public class PrefsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.tPrefs);
		addPreferencesFromResource(R.xml.prefs);
	}

}

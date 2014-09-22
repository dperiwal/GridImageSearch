package com.codepath.gridimagesearch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Utils {
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	static public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
				break; // terminate loop
			}
		}
		spinner.setSelection(index);
	}
	
	static public Boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
}

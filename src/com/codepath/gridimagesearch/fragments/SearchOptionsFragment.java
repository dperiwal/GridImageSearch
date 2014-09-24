package com.codepath.gridimagesearch.fragments;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.activities.GetNewSearchOptions;
import com.codepath.gridimagesearch.activities.SearchActivity;
import com.codepath.gridimagesearch.models.SearchOptions;
import com.codepath.gridimagesearch.utils.Utils;

public class SearchOptionsFragment extends DialogFragment {
	private Spinner spImageSize;
	private Spinner spColorFilter;
	private Spinner spImageType;
	private EditText etSiteFilter;
	private Button btnSaveOptions;
	private Button btnCancelOptions;
	private GetNewSearchOptions callback;
	
	public SearchOptionsFragment() {
	}
	
	public static SearchOptionsFragment newInstance(/*String title,*/ SearchOptions options) {
		SearchOptionsFragment frag = new SearchOptionsFragment();
		Bundle args = new Bundle();
/*		args.putString("title", title);*/
		args.putSerializable(SearchActivity.SEARCH_OPTIONS_KEY, options);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_search_options, container);
/*		String title = getArguments().getString("title", "Specify Search Options");
		getDialog().setTitle(title);*/
		callback = (GetNewSearchOptions) getActivity();
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		SearchOptions currentSearchOptions = (SearchOptions) getArguments().getSerializable(SearchActivity.SEARCH_OPTIONS_KEY);
		setupResources(view, currentSearchOptions);
		// Show soft keyboard automatically
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}
	
	private void setupResources(View view, SearchOptions currentSearchOptions) {	
		// Image size
		spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.image_size_values, R.layout.options_spinners_text);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spImageSize.setAdapter(adapter);
		Utils.setSpinnerToValue(spImageSize, currentSearchOptions.imageSize); 
			
		// Image color
		spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.color_filter_values, R.layout.options_spinners_text);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spColorFilter.setAdapter(adapter);
		Utils.setSpinnerToValue(spColorFilter, currentSearchOptions.colorFilter);
		
		// Image type
		spImageType = (Spinner) view.findViewById(R.id.spImageType);
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.image_type_values, R.layout.options_spinners_text);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spImageType.setAdapter(adapter);
		Utils.setSpinnerToValue(spImageType, currentSearchOptions.imageType);
		
		// Site filter
		etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);	
		etSiteFilter.setText(currentSearchOptions.siteFilterUrl);
		
		// Save button
		btnSaveOptions = (Button) view.findViewById(R.id.saveOptionsButton);
		btnSaveOptions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveSearchOptions(v);
				dismiss();			
			}
		});
		
		// Cancel button
		btnCancelOptions = (Button) view.findViewById(R.id.cancelOptionsButton);
		btnCancelOptions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});	
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	// Collect the latest search option values and send it back to the calling activity
	public void saveSearchOptions(View v) {		
		SearchOptions newSearchOptions = new SearchOptions();
		newSearchOptions.imageSize = spImageSize.getSelectedItem().toString();
		newSearchOptions.colorFilter = spColorFilter.getSelectedItem().toString();
		newSearchOptions.imageType = spImageType.getSelectedItem().toString();
		newSearchOptions.siteFilterUrl = etSiteFilter.getText().toString();
		callback.setNewSearchOptions(newSearchOptions);
	}

}

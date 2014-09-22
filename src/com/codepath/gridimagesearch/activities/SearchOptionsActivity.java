package com.codepath.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.SearchOptions;
import com.codepath.gridimagesearch.utils.Utils;

/**
 * This activity gathers the options for subsequent image searches.
 * 
 * @author Damodar Periwal
 *
 */
public class SearchOptionsActivity extends Activity {
	private Spinner spImageSize;
	private Spinner spColorFilter;
	private Spinner spImageType;
	private EditText etSiteFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		setupResources();
	}
	
	private void setupResources() {
	
		// Collect the existing search options values passed in the intent.
		SearchOptions currentSearchOptions = (SearchOptions) getIntent().getSerializableExtra(SearchActivity.SEARCH_OPTION_KEY);
		// Image size
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_size_values, R.layout.options_spinners_text);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spImageSize.setAdapter(adapter);
		Utils.setSpinnerToValue(spImageSize, currentSearchOptions.imageSize); 
			
		// Image color
		spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = ArrayAdapter.createFromResource(this,
		        R.array.color_filter_values, R.layout.options_spinners_text);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spColorFilter.setAdapter(adapter);
		Utils.setSpinnerToValue(spColorFilter, currentSearchOptions.colorFilter);
		
		// Image type
		spImageType = (Spinner) findViewById(R.id.spImageType);
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = ArrayAdapter.createFromResource(this,
		        R.array.image_type_values, R.layout.options_spinners_text);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spImageType.setAdapter(adapter);
		Utils.setSpinnerToValue(spImageType, currentSearchOptions.imageType);
		
		// Site filter
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);	
		etSiteFilter.setText(currentSearchOptions.siteFilterUrl);
	}
	
	public void saveSearchOptions(View v) {
		// Collect the latest search option values in a new SearchOption object
		SearchOptions newSearchOptions = new SearchOptions();
		newSearchOptions.imageSize = spImageSize.getSelectedItem().toString();
		newSearchOptions.colorFilter = spColorFilter.getSelectedItem().toString();
		newSearchOptions.imageType = spImageType.getSelectedItem().toString();
		newSearchOptions.siteFilterUrl = etSiteFilter.getText().toString();

		// Return the latest search options back to the caller using the new SearchOption object
		Intent i = new Intent();
		i.putExtra(SearchActivity.SEARCH_OPTION_KEY, newSearchOptions);
		setResult(RESULT_OK, i);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

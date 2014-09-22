package com.codepath.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.models.ImageResult;
import com.codepath.gridimagesearch.models.SearchOptions;
import com.codepath.gridimagesearch.utils.EndlessScrollListener;
import com.codepath.gridimagesearch.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * This is the main activity to retrieve and show thumbnails of 
 * filtered images in a scrollable grid view. 
 * 
 * The full view of an image is shown when the user taps on the
 * thumbnail of that particular image.
 * 
 * The user can set options for the query through the Settings action item. 
 * 
 * @author Damodar Periwal
 *
 */
public class SearchActivity extends Activity {
	static final int PAGE_SIZE = 8;
	static final int REQUEST_CODE = 50;
	static final String SEARCH_OPTION_KEY = "search_options";
	static final String NETWORK_UNAVAILABLE_MSG = "Network not available. Aborting.";
	
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private SearchOptions searchOptions = new SearchOptions();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		// Creates the data source
		imageResults = new ArrayList<ImageResult>();
		// Attaches the data source to an adpater
		aImageResults = new ImageResultsAdapter(this, imageResults);
		// Links the adapter to the adpater view (GridView)
		gvResults.setAdapter(aImageResults);
	}
	
	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Get the ImageResult to display
				ImageResult imageResult = imageResults.get(position);
				// Create an intent
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				// Pass image data in the intent
				i.putExtra("result", imageResult);
				// Launch the new activity
				startActivity(i);			
			}
			
		});
	}
	
	/**
	 * Fetches the image data as per the query and search options (if any).
	 * 
	 * @param query The search terms
	 * @param page The page number for the next set of images to be fetched.
	 *             0 => the start of the query. 
	 */
	private void fetchAndLoadData(String query, final int page) {
		if (!Utils.isNetworkAvailable(this)) {
			Log.i("INFO", NETWORK_UNAVAILABLE_MSG);
			Toast.makeText(this, NETWORK_UNAVAILABLE_MSG, Toast.LENGTH_SHORT).show();
			return;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		String searchURL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=" + PAGE_SIZE;
		searchURL = addSearchOptions(searchURL, page * PAGE_SIZE);
		client.get(searchURL, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					if (response.optJSONObject("responseData") == null) {
						// No more data to display
						return;
					}
					JSONArray imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					Log.i("INFO", "Page number in onSuccess=" + page);
					if (page == 0) {
						// clear the existing results from the array (in case where it is a new search).
						// Clearing through the adapter is better because then it automatically refreshes the view also.
						aImageResults.clear(); 
					}
					// imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
					// aImageResults.notifyDataSetChanged();
					// Alternatively, when you make changes to the adapter, it does 
					// change the underlying data automatically				
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.i("Error", "Failure in getting images " + statusCode + ": " + responseString);
			}
			
		});	
		
	}

	// Fired whenever the Search button is pressed (android:onclick property)
	public void onImageSearch(View v) {
		startSearch(etQuery.getText().toString());		
	}
	
	private void startSearch(final String query) {
		// Attach a new ScrollListner every time a fresh query is started.
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				fetchAndLoadData(query, page);
			}
		});
		fetchAndLoadData(query, 0);
	}

	/**
	 * Builds the final search url by appending query parameter string 
	 * based on the search options and the current page number.
	 * 
	 * @param searchURL Base URL
	 * @param page The page number for the next set of images to be fetched
	 * @return
	 */
	private String addSearchOptions(String searchURL, int page) {
		StringBuffer queryOptions = new StringBuffer();
		if (!Utils.isNullOrEmpty(searchOptions.imageSize)) {
			queryOptions.append("&imgsz="+searchOptions.imageSize);			
		}
		if (!Utils.isNullOrEmpty(searchOptions.colorFilter)) {
			queryOptions.append("&imgcolor="+searchOptions.colorFilter);			
		}
		if (!Utils.isNullOrEmpty(searchOptions.imageType)) {
			queryOptions.append("&imgtype="+searchOptions.imageType);			
		}
		if (!Utils.isNullOrEmpty(searchOptions.siteFilterUrl)) {
			queryOptions.append("&as_sitesearch="+searchOptions.siteFilterUrl);
		}
		if (page > 0) {
			queryOptions.append("&start=" + page);
		}
		
		// Log.i("INFO", "query options are: " + queryOptions.toString());
		
		return (searchURL + queryOptions.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				startSearch(query);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// Create an intent for settings activity
			Intent i = new Intent(SearchActivity.this, SearchOptionsActivity.class);
			// Pass image data in the intent
			i.putExtra(SEARCH_OPTION_KEY, searchOptions);
			// Launch the new activity
			startActivityForResult(i, REQUEST_CODE);
					
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Update the search options for subsequent queries
			searchOptions = (SearchOptions) data
					.getSerializableExtra(SEARCH_OPTION_KEY);
			// Log.i("INFO, return value", searchOptions.toString());
		}
	}

}

package com.codepath.gridimagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.codepath.gridimagesearch.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * This Activity retrieves the URL of a full image from the passed ImageResult
 * object, downloads the image, and then shows it.
 * 
 * @author Damodar Periwal
 *
 */
public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// We are making the network check here instead of in SerachActivity because
		// some future implementation of this class may not require a network call 
		// to show the full image as that might have already been pre-fetched. So it 
		// is better to check for the network availability in this class. 
		if (!Utils.isNetworkAvailable(this)) {
			Log.i("INFO", SearchActivity.NETWORK_UNAVAILABLE_MSG);
			Toast.makeText(this, SearchActivity.NETWORK_UNAVAILABLE_MSG, Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		setContentView(R.layout.activity_image_display);
		
		// Hide the action bar from this activity
		// getActionBar().hide();
		// Pull out the url from the intent
		ImageResult imageResult = (ImageResult) getIntent().getSerializableExtra("result");
		// Find the image view
		ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
		// Load the url in the image view
		Picasso.with(this).load(imageResult.fullUrl).into(ivImageResult);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
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

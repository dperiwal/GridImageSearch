package com.codepath.gridimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;


public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, android.R.layout.simple_expandable_list_item_1, images);
		// TODO Auto-generated constructor stub
	}
	
	// View lookup cache
	private static class ViewHolder {
		ImageView ivImage;
		TextView tvTitle;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		ImageResult imageResult = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.item_image_result, parent, false);
			viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			// reset the image from the recycled view
			viewHolder.ivImage.setImageResource(0);
			viewHolder.tvTitle.setText("");
		}
		// Remotely download the image data in the background (with Picasso)
		Picasso.with(getContext()).load(imageResult.thumbUrl).placeholder(R.drawable.ic_launcher).into(viewHolder.ivImage);
		viewHolder.tvTitle.setText(Html.fromHtml(imageResult.title));
		// Return the completed view to be displayed
		return convertView;
	}
}

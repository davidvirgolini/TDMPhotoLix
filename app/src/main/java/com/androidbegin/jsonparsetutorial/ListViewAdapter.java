package com.androidbegin.jsonparsetutorial;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView title;
		TextView description;
		ImageView image;
		String url;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		title = (TextView) itemView.findViewById(R.id.title);
		description = (TextView) itemView.findViewById(R.id.description);

		// Locate the TextViews in listview_item.xml
		image = (ImageView) itemView.findViewById(R.id.primary);

        // Capture position and set results to the TextViews
		title.setText(resultp.get(MainActivity.TITLE));
		description.setText(resultp.get(MainActivity.DESCRIPTION));

		//Passes image url into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(MainActivity.URL), image);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data id
				intent.putExtra(MainActivity.ID, resultp.get(MainActivity.ID));
				// Pass all data title
				intent.putExtra(MainActivity.TITLE, resultp.get(MainActivity.TITLE));
				// Pass all data description
				intent.putExtra(MainActivity.DESCRIPTION, resultp.get(MainActivity.DESCRIPTION));
				// Pass all data population
				context.startActivity(intent);

			}
		});
		return itemView;
	}
}

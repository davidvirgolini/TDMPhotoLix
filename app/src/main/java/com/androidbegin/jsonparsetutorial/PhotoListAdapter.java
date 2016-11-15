package com.androidbegin.jsonparsetutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by David on 14/11/2016.
 */

public class PhotoListAdapter extends BaseAdapter{
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public PhotoListAdapter(Context context,
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
        ImageView image;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.photo_list_item_view, parent, false);
        // Locate the ImageView in listview_item.xml
        image = (ImageView) itemView.findViewById(R.id.primary);
        // Get the position
        resultp = data.get(position);
        //Passes image url into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(MainActivity.URL), image);

        return null;
    }
}

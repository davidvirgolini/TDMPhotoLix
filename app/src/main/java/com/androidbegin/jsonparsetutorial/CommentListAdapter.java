package com.androidbegin.jsonparsetutorial;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Luigi on 15-Nov-16.
 */

public class CommentListAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    //ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public CommentListAdapter(Context context,
                            ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        //imageLoader = new ImageLoader(context);
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
        //ImageView image;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.commentlist_item, parent, false);
        // Locate the ImageView in listview_item.xml
        //image = (ImageView) itemView.findViewById(R.id.photoImage);
        // Get the position
        resultp = data.get(position);
        //Passes image url into ImageLoader.class
        //imageLoader.DisplayImage(resultp.get(MainActivity.URL), image);
/*        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data id
                intent.putExtra(MainActivity.ID, resultp.get(MainActivity.ID));
                context.startActivity(intent);

            }
        });*/
        return itemView;
    }
}

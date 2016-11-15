package com.androidbegin.jsonparsetutorial;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.androidbegin.jsonparsetutorial.R.id.listview;

public class SingleItemView extends Fragment {
	// Declare Variables
	String title;
	String description;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    JSONObject jsonobject;
    JSONArray jsonarray;
    View rootView;
    ListViewAdapter adapter;
    GridView listview;
	String flag;
	//ImageLoader imageLoader = new ImageLoader(this);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
						 Bundle savedInstanceState)  {
		rootView = inflater.inflate(R.layout.singleitemview, container, false);

		Intent i = getActivity().getIntent();
		// Get the result of title
		title = i.getStringExtra("title");
		// Get the result of description
		description = i.getStringExtra("description");

		// Locate the TextViews in singleitemview.xml
		TextView txtrank = (TextView) rootView.findViewById(R.id.title);
		TextView txtcountry = (TextView) rootView.findViewById(R.id.description);

		// Locate the ImageView in singleitemview.xml
		//ImageView imgflag = (ImageView) rootView.findViewById(R.id.flag);

		// Set results to the TextViews
		txtrank.setText(title);
		txtcountry.setText(description);

		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		//imageLoader.DisplayImage(flag, imgflag);
		return rootView;
	}
    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog titlee
            mProgressDialog.setTitle("TDM PhotoLix");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL("https://api.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=0bde4e6810b2f5295df2270bc9ceda8e&user_id=145733563%40N08&format=json&nojsoncallback=1");

            try {
                // Locate the array name in JSON
                jsonobject = jsonobject.getJSONObject("photosets");
                jsonarray =  jsonobject.getJSONArray("photoset");

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);
                    // Retrive JSON Objects
                    JSONObject title = jsonobject.getJSONObject("title");
                    map.put("title", title.getString("_content"));
                    JSONObject description = jsonobject.getJSONObject("description");
                    map.put("description", description.getString("_content"));
                    // Set the JSON Objects into the array
                    arraylist.add(map);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            listview = (GridView) rootView.findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(getActivity(), arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
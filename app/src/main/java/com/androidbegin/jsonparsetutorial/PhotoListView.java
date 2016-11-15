package com.androidbegin.jsonparsetutorial;

import android.app.Fragment;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.data;


/**
 * Created by David on 14/11/2016.
 */

public class PhotoListView extends Fragment {
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList arraylist;
    String id;
    String url;
    View rootView;
    ImageView imageView;
    String imageUrl;
    ImageLoader imageLoader = new ImageLoader(getActivity());
    ProgressDialog mProgressDialog;
    GridView listview;
    PhotoListAdapter adapter;
    static String ID="id";
    static String PRIMARY = "primary";
    static String SECRET = "secret";
    static String SERVER = "server";
    static String FARM = "farm";
    static String URL = "url";

    public static PhotoListView newInstance(Bundle arguments){
        PhotoListView fragment = new PhotoListView();
        if(arguments != null){
            fragment.setArguments(arguments);
        }
        return fragment;

    }
    public  PhotoListView(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        rootView = inflater.inflate(R.layout.listview_main, container, false);//create objet structure
        Intent intent = getActivity().getIntent();
        id=intent.getStringExtra(ID);
        imageView = (ImageView) rootView.findViewById(R.id.photoImage);
        imageLoader.DisplayImage(imageUrl, imageView);
        new DownloadJSON().execute();
       return rootView;
    }
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
                    .getJSONfromURL("https://www.flickr.com/services/api/flickr.photosets.getPhotos.html");

            try {
                // Locate the array name in JSON
                jsonobject =  jsonobject.getJSONObject("photoset");
                jsonarray = jsonobject.getJSONArray("photo");

              if(jsonobject.getString(ID).equals(id)) {
                  for (int i = 0; i < jsonarray.length(); i++) {
                      HashMap<String, String> map = new HashMap<String, String>();
                      jsonobject = jsonarray.getJSONObject(i);
                      //Retrive JSON Objects
                      map.put(ID, jsonobject.getString(ID));
                      String primary = jsonobject.getString(PRIMARY);
                      String secret = jsonobject.getString(SECRET);
                      String server = jsonobject.getString(SERVER);
                      String farm = jsonobject.getString(FARM);
                      //build image url
                      url = "https://farm" + farm + ".staticflickr.com/" + server +
                              "/" + primary + "_" + secret + ".jpg";
                       // Set the JSON Objects into the array
                      map.put(URL, url);
                      arraylist.add(map);
                  }
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
            adapter = new PhotoListAdapter(getActivity(), arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}

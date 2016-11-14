package com.androidbegin.jsonparsetutorial;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class MainActivity extends Activity {
    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    GridView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    String url;
    static String ID = "id";
    static String PRIMARY = "primary";
    static String SECRET = "secret";
    static String SERVER = "server";
    static String FARM = "farm";
    static String TITLE = "title";
    static String DESCRIPTION = "description";
    static String URL = "url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        // Execute DownloadJSON AsyncTask
        new DownloadJSON().execute();
    }

    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
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
                    .getJSONfromURL("https://api.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=32e23b107b0fdf5ce8ca78a78e8d5046&user_id=145733563%40N08&format=json&nojsoncallback=1");

            try {
                // Locate the array name in JSON
                jsonobject = jsonobject.getJSONObject("photosets");
                jsonarray = jsonobject.getJSONArray("photoset");

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);
                    // Retrive JSON Objects
                    map.put(ID, jsonobject.getString(ID));
                    String primary = jsonobject.getString(PRIMARY);
                    String secret = jsonobject.getString(SECRET);
                    String server = jsonobject.getString(SERVER);
                    String farm = jsonobject.getString(FARM);

                    //build image url
                    url = "https://farm" + farm + ".staticflickr.com/" + server +
                            "/" + primary + "_" + secret + ".jpg";

                    map.put(URL, url);
                    JSONObject title = jsonobject.getJSONObject("title");
                    map.put(TITLE, title.getString("_content"));
                    JSONObject description = jsonobject.getJSONObject("description");
                    map.put(DESCRIPTION, description.getString("_content"));
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
            listview = (GridView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
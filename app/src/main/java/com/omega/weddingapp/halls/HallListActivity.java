package com.omega.weddingapp.halls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.omega.addnewhall.AddnewHall;
import com.omega.halldetails.HallActivity;
import com.omega.weddingapp.city.GPSTracker;
import com.omega.weddingapp.mail.NetworkUtils;
import com.omega.weddingapp.main.CustomeProgressDialog;
import com.omega.weddingapp.main.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HallListActivity extends Activity implements OnCancelListener {

    private List<HallRowItem> rowItems;
    ListView lv;
    ProgressHUD mProgressHUD;
    String cityname, hallanme, pname;
    HallLazyAdapter adapter;
    CustomeProgressDialog mProgressDialog;
    GPSTracker gps;
    double latitude, longitude;
    NetworkUtils isNetAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.e("Activity", "HallListActivity");
        cityname = getIntent().getStringExtra("cityname");
        pname = getIntent().getStringExtra("pname");

        isNetAvailable=new NetworkUtils();
        if (isNetAvailable.isNetworkConnectionOn(HallListActivity.this)){getDataFromParse();}else{
            Toast.makeText(HallListActivity.this, R.string.internet_not_available, Toast.LENGTH_SHORT).show();
        }


        gps = new GPSTracker(HallListActivity.this);

//        if (Util.checkConnectivity(HallListActivity.this)) {

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

//                new AppPreferences(HallListActivity.this).saveLat(String
//                        .valueOf(latitude));
//                new AppPreferences(HallListActivity.this).saveLon(String
//                        .valueOf(longitude));

            // Toast.makeText(getApplicationContext(),
            // "Your Location is - \nLat: " + latitude + "\nLong: " +
            // longitude, Toast.LENGTH_LONG).show();
        } else {

            gps.showSettingsAlert();
        }

//        } else {
//            showNetworkDialog("internet");
//
//        }

        // Toast.makeText(HallListActivity.this,
        // "city passed is " + cityname,
        // Toast.LENGTH_LONG).show();
        //
        //
        // Toast.makeText(HallListActivity.this,
        // "calling list: " + cityname,
        // Toast.LENGTH_LONG).show();
        //

        lv = (ListView) findViewById(R.id.myList);
        rowItems = new ArrayList<HallRowItem>();
        //
        // TimeConsumingTask t = new TimeConsumingTask();
        // t.execute();

        // Set the adapter on the ListView
        adapter = new HallLazyAdapter(getApplicationContext(),
                R.layout.list_item_card, rowItems);

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Toast.makeText(HallListActivity.this,
                // "You have chosen the: " + rowItems.get(position),
                // Toast.LENGTH_LONG).show();
                //

                hallanme = rowItems.get(position).toString();

                if (hallanme.equalsIgnoreCase("قم بإضافة قاعة جديدة هنا"))

                {

                    Intent i = new Intent(HallListActivity.this,
                            AddnewHall.class);
                    i.putExtra("hallname", "" + rowItems.get(position));
                    i.putExtra("cityname", cityname);
                    i.putExtra("pname", pname);

                    startActivity(i);

                } else {

                    // if (!(hallanme.equalsIgnoreCase("Add New Hall")))
                    // ;
                    // {

                    Intent i1 = new Intent(HallListActivity.this,
                            HallActivity.class);
                    i1.putExtra("hallname", "" + rowItems.get(position));
                    startActivity(i1);
                }

                // }

            }
        });

    }

    @SuppressWarnings("deprecation")
    public void showNetworkDialog(final String message) {
        // final exit of application
        AlertDialog.Builder builder = new AlertDialog.Builder(
                HallListActivity.this);
        builder.setTitle(getResources().getString(R.string.app_name));
        if (message.equals("GPS")) {
            builder.setMessage(getResources().getString(R.string.gps_message));
        } else if (message.equals("internet")) {
            builder.setMessage(getResources().getString(R.string.net_message));
        }
        AlertDialog alert = builder.create();
        alert.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (message.equals("gps")) {
                    Intent i = new Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(i, 1);
                } else if (message.equals("internet")) {
                    Intent i = new Intent(
                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivityForResult(i, 2);
                    Intent i1 = new Intent(
                            android.provider.Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(i1, 3);
                } else {
                    // do nothing
                }
            }
        });
        // Showing Alert Message
        alert.show();
    }

    private void getDataFromParse() {

        // mProgressDialog = new CustomeProgressDialog(HallListActivity.this);
        // mProgressDialog.showDialog("please Wait",
        // "While We Are Fetching data");
/*
        ProgressDialog progressDialog=new ProgressDialog(HallListActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
 */

        ParseQuery<ParseObject> query = ParseQuery.getQuery("hallList");
        Log.e("Location2", pname + " Province     city" + cityname);
        query.whereEqualTo("city", cityname);
        query.whereEqualTo("state", pname);

//        query.whereEqualTo("IsApproved", true);
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> data, ParseException e) {
                if (e == null) {

                    // Toast.makeText(HallListActivity.this,
                    // "size of data " + data.size(),
                    // Toast.LENGTH_LONG).show();

                    // Log.d("Hall List", "Retrieved " + data.size() +
                    // " param");

                    HallRowItem item;
                    Log.e("Location2", " Size " + data.size());
//                    item = new HallRowItem("قم بإضافة قاعة جديدة هنا");

//                    rowItems.add(item);

                    for (int i = 0; i < data.size(); i++) {
                        ParseObject obj = data.get(i);

                        item = new HallRowItem(obj.getString("hallName"));

                        // Toast.makeText(HallListActivity.this,
                        // "param: " + obj.getString("hallName"),
                        // Toast.LENGTH_LONG).show();

                        rowItems.add(item);
                    }

                } else {

                    Log.d("Hall", "Error: " + e.getMessage());

                }

                // Toast.makeText(HallListActivity.this,
                // "size: " + rowItems.size(),
                // Toast.LENGTH_LONG).show();

                lv.setAdapter(adapter);
                // mProgressDialog.removeDialog();
            }
        });

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.content.DialogInterface.OnCancelListener#onCancel(android.content
     * .DialogInterface)
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        // TODO Auto-generated method stub

    }

    public class TimeConsumingTask extends AsyncTask<Void, String, Void>
            implements OnCancelListener {
        ProgressHUD mProgressHUD;

        @Override
        protected void onPreExecute() {
            mProgressHUD = ProgressHUD.show(HallListActivity.this,
                    "Loading...", true, true, this);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                publishProgress("Connecting");
                Thread.sleep(2000);
                publishProgress("Downloading");
                Thread.sleep(5000);
                publishProgress("Done");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mProgressHUD.setMessage(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressHUD.dismiss();
            super.onPostExecute(result);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            this.cancel(true);
            mProgressHUD.dismiss();
        }
    }

    // private void getDataFromParse() {
    //
    // ParseQuery<ParseObject> query1 = ParseQuery.getQuery("hallDetail");
    // // query1.setCachePolicy(ParseQuery.CachePolicy.);
    // query1.findInBackground(new FindCallback<ParseObject>() {
    //
    // @Override
    // public void done(List<ParseObject> objects,
    // com.parse.ParseException arg1) {
    //
    // if (arg1 != null) {
    //
    // // Log.e("Toatal image are :", " : " + objects.size());
    // return;
    // }
    // rowItems.clear();
    //
    // Log.e("Toatal image are :", " : " + objects.size());
    //
    // for (int i = 0; i < objects.size(); i++) {
    //
    // ParseObject obj = objects.get(i);
    // // Log.e("captions  and no of imaeges are:" + i,
    // // obj.getObjectId());
    // //
    // // HallPogo t = new HallPogo(obj.getParseFile("img1"), obj
    // // .getString("description"), obj
    // // .getString("location"), obj.getNumber("capicity"),
    // // obj.getNumber("contactNo"), obj
    // // .getNumber("bookingAmount"), obj
    // // .getString("email"), obj
    // // .getParseGeoPoint("geoL"));
    //
    // // rowItems.add(t);
    //
    // HallRowItem item = new HallRowItem(
    // obj.getParseFile("img1"), obj.getString("email"),
    // obj.getString("description"));
    //
    // rowItems.add(item);
    //
    // }
    //
    // //
    //
    // lv.setAdapter(adapter);
    // }
    //
    // });
    //
    // }

}

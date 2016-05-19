package com.omega.weddingapp.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.omega.weddingapp.halls.HallListActivity;
import com.omega.weddingapp.main.R;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends Activity {

    private List<CityRowItem> rowItems;

    Double dLatiTo = 0.0;

    Double dLatTo = 0.0;
    Double dLonTo = 0.0;
    Double dLnTo = 0.0;


    private boolean locationStatus;


    String pname;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.e("Activity", "CityListActivity");
        ListView lv = (ListView) findViewById(R.id.myList);
        rowItems = new ArrayList<CityRowItem>();
        String titles[] = {};

        pname = getIntent().getStringExtra("provinence");

        switch (getIntent().getIntExtra("Position", 0)) {
            case 0:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Muscat);
                break;
            case 1:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Dhofar);
                break;
            case 2:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Musandam);
                break;
            case 3:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Buraimi);
                break;
            case 4:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Al_Dakhiliyah);
                break;
            case 5:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Al_Batinah_North);
                break;
            case 6:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Al_Batinah_South);
                break;
            case 7:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Al_Sharqiyah_North);
                break;
            case 8:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Al_Sharqiyah_South);
                break;
            case 9:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Al_Dhahirah);
                break;
            case 10:
                titles = getResources().getStringArray(
                        R.array.Governorate_of_Al_Wusta);
                break;

        }

        String[] descriptions = {"Toatal Halls available 20",
                "Toatal Halls available 40", "Toatal Halls available 50",
                "Toatal Halls available 20", "Toatal Halls available 70",
                "Toatal Halls available 20", "Toatal Halls available 54",
                "Toatal Halls available 25", "Toatal Halls available 35",
                "Toatal Halls available 60", "Toatal Halls available 62"};
        // Populate the List
        for (int i = 0; i < titles.length; i++) {
            CityRowItem item = new CityRowItem(titles[i], descriptions[i]);

            rowItems.add(item);
        }

        // Set the adapter on the ListView
        CityLazyAdapter adapter = new CityLazyAdapter(getApplicationContext(),
                R.layout.list_item_card, rowItems);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Toast.makeText(CityListActivity.this,
                // "You have chosen the: " + rowItems.get(position),
                // Toast.LENGTH_LONG).show();

                Intent i = new Intent(CityListActivity.this,
                        HallListActivity.class);

                i.putExtra("cityname", "" + rowItems.get(position));
                i.putExtra("pname", pname);

                Log.e("Location", pname + " Province     city" + rowItems.get(position));

                startActivity(i);
                overridePendingTransition(R.anim.mainfadein,
                        R.anim.splashfadeout);

            }
        });

    }

    // private void executeLocationAndInternetServices() {
    // if (Util.checkConnectivity(CityListActivity.this)) {
    // if (new GPSClass().getLocation(CityListActivity.this,
    // new LocationResult() {
    // @Override
    // public void gotLocation(Location location) {
    // new AppPreferences(CityListActivity.this)
    // .saveLat(String.valueOf(location
    // .getLatitude()));
    // new AppPreferences(CityListActivity.this)
    // .saveLon(String.valueOf(location
    // .getLongitude()));
    // locationStatus = true;
    // }
    // })) {
    // // doNothing
    // } else {
    // showNetworkDialog("gps");
    // }
    // } else {
    // showNetworkDialog("internet");
    // }
    // }

}

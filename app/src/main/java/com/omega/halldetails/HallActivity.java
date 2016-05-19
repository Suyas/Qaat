package com.omega.halldetails;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.omega.weddingapp.mail.NetworkUtils;
import com.omega.weddingapp.main.CustomeProgressDialog;
import com.omega.weddingapp.main.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HallActivity extends FragmentActivity implements TabListener {

    public static ArrayList<String> arr_date = new ArrayList<String>();


    TextView location, capacity, bookingamt, contactNo, desc, hall_name, email;

    String strhallname, strbookigamt, strcapcity, strcontact, stremail,
            strlocation, strdesc, adminName;
    //ArrayList<Integer>
    Double strlat, strlong;

    public static ArrayList<HallGalleryPogo> images = null;

    ArrayList<String> data = new ArrayList<String>();

    private MyViewPager MyViewPager;
    private TabsPagerAdapter mAdapter;
    CustomeProgressDialog mProgressDialog;
    private ActionBar actionBar;

    // Tab titles
//    private String[] tabs = {"نبذة عن القاعة", "صور القاعة", "احجز القاعة", "خارطة موقع القاعة"};
    private String[] tabs = {"نبذة عن القاعة", "صور القاعة", "احجز القاعة"};
    NetworkUtils isNetAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hall_details);

        Log.e("Activity", "Hall Activity");
        strhallname = getIntent().getStringExtra("hallname");
        Log.e("hall name", strhallname);
        isNetAvailable = new NetworkUtils();
        if (isNetAvailable.isNetworkConnectionOn(HallActivity.this)) {
            getDataFromParse();
        } else {
            Toast.makeText(HallActivity.this, R.string.internet_not_available, Toast.LENGTH_SHORT).show();
        }

        //new getDataFromParse().execute();


        // Toast.makeText(HallActivity.this, "hall passed is " + hallname,
        // Toast.LENGTH_LONG).show();

        // Initialization
        MyViewPager = (MyViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();

        // hallname=getIntent().getStringExtra("hallname");

        // actionBar.setDisplayShowHomeEnabled(false);
        // actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setHomeButtonEnabled(false);

        // getActionBar().setDisplayHomeAsUpEnabled(true);
        // getActionBar().setHomeButtonEnabled(true);
        //
        // getActionBar().setBackgroundDrawable(
        // new ColorDrawable(Color.parseColor("#36648B")));

        // LayoutInflater mInflater = LayoutInflater.from(this);
        //
        // View mCustomView = mInflater.inflate(R.layout.custom_actionbar,
        // null);
        // TextView mTitleTextView = (TextView)
        // mCustomView.findViewById(R.id.title_text);
        // mTitleTextView.setText("My Own Title");
        //
        // ImageButton imageButton = (ImageButton) mCustomView
        // .findViewById(R.id.Button);
        // imageButton.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View view) {
        // Toast.makeText(getApplicationContext(), "Refresh Clicked!",
        // Toast.LENGTH_LONG).show();
        // }
        // });
        //
        // actionBar.setCustomView(mCustomView);
        // actionBar.setDisplayShowCustomEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));

        }

        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#B05F2F")));

        MyViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            public void onPageSelected(int arg0) {
                actionBar.setSelectedNavigationItem(arg0);
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     *
     */
    private void getDataFromParse() {

        final ProgressDialog progressDialog = new ProgressDialog(HallActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        images = new ArrayList<HallGalleryPogo>();
        mProgressDialog = new CustomeProgressDialog(HallActivity.this);
        //	mProgressDialog.showDialog("please Wait", "While We Are Fetching data");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Hallimages");
        query.whereEqualTo("HallName", strhallname);
        //  query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects,
                             com.parse.ParseException arg1) {

                if (arg1 == null) {

                    if (objects.size() > 0) {

                        for (int i = 0; i < objects.size(); i++) {

                            ParseObject obj = objects.get(i);

                            HallGalleryPogo img = new HallGalleryPogo(obj
                                    .getParseFile("Hallimages"));

                            // HallPogo t = new HallPogo(obj.getParseFile("img1"));
                            // obj
                            // .getString("description"), obj
                            // .getString("location"), obj.getNumber("capicity"),
                            // obj.getNumber("contactNo"), obj
                            // .getNumber("bookingAmount"), obj
                            // .getString("email"), obj
                            // .getParseGeoPoint("geoL"));
                            // img.add(img1);
                            images.add(img);


                        }
                    }


                    // Log.e("Toatal image are :", " : " + objects.size());
                    return;
                }

                Log.e("Toatal image are :", " : " + objects.size());

                Log.e(" size",
                        " " + images.size());

            }

        });
        ParseQuery<ParseObject> querydata = ParseQuery.getQuery("BookedHall");

        querydata.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> count, ParseException e) {
                // TODO Auto-generated method stub

                arr_date.clear();

                if (e == null) {

                    if (count.size() > 0) {
                        for (int i = 0; i < count.size(); i++) {
                            ParseObject obj = count.get(i);

                            String halldate = obj.getString("BookedDate");

                            //	String hcount = "" + obj.getInt("");

                            arr_date.add(halldate);
                        }
                    }
                } else {

                    Log.d("score", "Error: " + e.getMessage());

                    // }

                }
            }


        });


        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("hallDetail");
        query1.whereEqualTo("hallName", strhallname);
        //  query1.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    if (object == null) {

                        Log.d("Hall", "The getFirst request failed.");
                        progressDialog.dismiss();
                    } else {

                        //object.getString

                        strbookigamt = object.getString("bookingAmount");
                        strcapcity = object.getString("capicity");
                        strcontact = object.getString("contactNo");

                        stremail = object.getString("HAllOwnerEmail");
                        Log.e("Email", "" + stremail);
                        strlocation = object.getString("location");

                        strdesc = object.getString("desc");

                        ParseGeoPoint point = object.getParseGeoPoint("geoL");

                        strlat = point.getLatitude();
                        strlong = point.getLongitude();

                        mProgressDialog.removeDialog();
                        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(),
                                strhallname, strbookigamt, strcapcity, strcontact,
                                stremail, strlocation, strdesc, strlat, strlong, images, HallActivity.this);

                        MyViewPager.setAdapter(mAdapter);

                        progressDialog.dismiss();
                    }


                }
            }

        });


    }

    /**
     *
     */

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view

        MyViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }


}
package com.omega.weddingapp.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omega.weddingapp.mail.NetworkUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QaatSplash extends Activity {

    ArrayList<String> arrr_hname = new ArrayList<String>();
    public static ArrayList<String> arr_hcount;
    Button undo1;

    public static HashMap<String, String> hashm;
    NetworkUtils isNetAvailable;

    View layout;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.expert_splash_screen);



        // if (com.omega.omegaplus.Utils.NetworkUtils
        // .isNetworkConnectionOn(getApplicationContext())) {


        arr_hcount = new ArrayList<String>();


//        if (Util.checkConnectivity(QaatSplash.this)) {




        isNetAvailable=new NetworkUtils();
        if (isNetAvailable.isNetworkConnectionOn(QaatSplash.this)){getdata();}else{
            Toast.makeText(QaatSplash.this,R.string.internet_not_available,Toast.LENGTH_SHORT).show();
        }


//        } else {


//            Toast.makeText(getApplicationContext(), "مطلوب العمل على الاتصال بالانترنت", Toast.LENGTH_LONG).show();


//        startActivity(new Intent(QaatSplash.this, MainActivity.class));

//        QaatSplash.this.finish();


        //	showNetworkDialog("internet");


//        }


        // Handler h = new Handler();
        //
        // h.postDelayed(new Runnable() {
        //
        // @Override
        // public void run() {
        //
        //
        // Log.e("size", "hall count= "+arr_hcount.size());
        //
        //
        //
        // // }
        //
        // }
        // }, 1000);

        // }

    }
//
//	@SuppressWarnings("deprecation")
//	public void showNetworkDialog(final String message) {
//		AlertDialog.Builder alertDialog = new AlertDialog.Builder(QaatSplash.this);
//		// Setting Dialog Title
//		alertDialog.setTitle("نظام GPS غير مفعل");
//		// Setting Dialog Message
//		alertDialog
//				.setMessage("نظام GPS لا يعمل هل تريد الذهاب لإعدادات الهاتف");
//		// On pressing Settings button
//		alertDialog.setPositiveButton("إعدادات",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//
//
//						Intent i = new Intent(
//								android.provider.Settings.ACTION_WIRELESS_SETTINGS);
//						startActivityForResult(i, 2);
//						Intent i1 = new Intent(
//								android.provider.Settings.ACTION_WIFI_SETTINGS);
//						startActivityForResult(i1, 3);
//				
//						getdata();
//						
//						
//					}
//				});
//		// on pressing cancel button
//		alertDialog.setNegativeButton("الغاء",
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.cancel();
//					}
//				});
//		// Showing Alert Message
//		alertDialog.show();
//	}

    private void getdata() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("HallCount");
        query.orderByAscending("hid");


        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> count, ParseException e) {

                // Log.e("size", "size is= "+count.size());

                arr_hcount.clear();

                if (e == null) {

                    if (count.size()>0) {
                        for (int i = 0; i < count.size(); i++) {
                            ParseObject obj = count.get(i);

                            String hcount = "" + obj.getInt("HallCount");

                            arr_hcount.add(hcount);

                        }
                        startActivity(new Intent(QaatSplash.this, MainActivity.class));

                        QaatSplash.this.finish();
                    }else{
                        Toast.makeText(QaatSplash.this,"No Hall Available",Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Log.d("score", "Error: " + e.getMessage());

                }

                Log.e("size", "hall count= " + arr_hcount.size());



            }

        });

    }

}
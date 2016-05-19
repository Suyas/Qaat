/**
 *
 */
package com.omega.addnewhall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.omega.weddingapp.admin.TaskList;
import com.omega.weddingapp.city.GPSTracker;
import com.omega.weddingapp.mail.NetworkUtils;
import com.omega.weddingapp.main.R;
import com.omega.wedingapp.map.MyLocationListener;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddnewHall extends Activity {

    GPSTracker gps;

    ParseFile file;
    Typeface tf = null;
    String geoAdd;
    private Button select_gal;
    private Uri mImageCaptureUri;
    private ImageView gal_1;
    private ImageView gal_2;
    private ImageView gal_3;
    private ImageView gal_4;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    private Bitmap gallery_image1;
    private Bitmap gallery_image2;
    private Bitmap gallery_image3;
    private Bitmap gallery_image4;
    private LocationManager locationManager = null;
    Double lat, longt;
    private AlertDialog dialogImage;
    String cityname, hallanme, pname, myEmailid;

    EditText e_mail, e_hallname, e_desc, e_bookingamt, e_capacity, e_add,
            e_phno, e_geoloc;
    Spinner e_citname, e_pname;

    private boolean locationStatus;
    Button save;
    ParseGeoPoint point;
    ParseGeoPoint location;
    Location mylocation;
    TextView statict, geotext;
    private Double imlat, imlong;
    boolean isNetworkEnabled = false;
    MyLocationListener mylistner;

    ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> province = new ArrayList<String>();
    String City, Province;
    int CityPos, ProvincePos;

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addnew_hall);
        captureImageInitialization();
        Log.e("Activity", "AddnewHall");
        tf = Typeface.createFromAsset(AddnewHall.this.getAssets(),
                "al-mohanad.ttf");

        select_gal = (Button) findViewById(R.id.addhall_img);
        // select_gal.setClickable(true);
        statict = (TextView) findViewById(R.id.static_text);
        geotext = (TextView) findViewById(R.id.geotext);
        e_citname = (Spinner) findViewById(R.id.hall_city_name);

        e_pname = (Spinner) findViewById(R.id.hall_state_name);

        e_hallname = (EditText) findViewById(R.id.hall_newname);

        e_mail = (EditText) findViewById(R.id.hall_email);

        e_desc = (EditText) findViewById(R.id.hall_desc);

        e_bookingamt = (EditText) findViewById(R.id.hall_bookingamt);

        e_capacity = (EditText) findViewById(R.id.hall_capacity);

        e_add = (EditText) findViewById(R.id.hall_add);

        e_phno = (EditText) findViewById(R.id.hall_phone_no);

        e_geoloc = (EditText) findViewById(R.id.hall_geoloc);

        save = (Button) findViewById(R.id.hall_btn_save);



        e_mail.setClickable(false);

/*
        */
/** get configured email **//*

        Account[] accounts = AccountManager.get(this).getAccountsByType(
                "com.google");
        myEmailid = accounts[0].name.toString();
*/

        preferences = getSharedPreferences("LoginPreference", 0);
        myEmailid=preferences.getString("AdminEmail",null);

//        imlat = Double
//                .parseDouble(new AppPreferences(AddnewHall.this).getLat());
//        imlong = Double.parseDouble(new AppPreferences(AddnewHall.this)
//                .getLon());
//        imlat=Double.parseDouble(point.getCurrentLocationInBackground(100); )
//        point = new ParseGeoPoint(imlat, imlong);
//        getLocation();
//

        mylistner = new MyLocationListener(
                AddnewHall.this);
        if (getlocationstate()) {

//        userLat = mylistner.latitude;
//        userLong = mylistner.longitude;
            Log.e("Location ", "" + mylistner.latitude + "  " + mylistner.longitude);

            imlat = mylistner.latitude;
            imlong = mylistner.longitude;
            e_geoloc.setText("" + imlat + " " + imlong);
            e_geoloc.setEnabled(false);
            point = new ParseGeoPoint(imlat, imlong);

        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(AddnewHall.this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });


            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog

                    dialogInterface.cancel();
                }
            });

            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);

            alertDialog.show();
        }
        pname = getIntent().getStringExtra("pname");
        cityname = getIntent().getStringExtra("cityname");

        // e_pname.setText(pname);

        // e_citname.setText(cityname);
        if (myEmailid!=null){
            e_mail.setText(myEmailid);
        }

        e_add.setText(geoAdd);

        // e_pname.setEnabled(false);
        // e_citname.setEnabled(false);
        e_mail.setEnabled(false);

        statict.setTypeface(tf);
        save.setTypeface(tf);
        // e_citname.setTypeface(tf);
        // e_pname.setTypeface(tf);
        e_add.setTypeface(tf);
        select_gal.setTypeface(tf);
        e_mail.setTypeface(tf);
        e_capacity.setTypeface(tf);
        e_bookingamt.setTypeface(tf);
        e_hallname.setTypeface(tf);
        e_desc.setTypeface(tf);
        e_phno.setTypeface(tf);
        geotext.setTypeface(tf);

        addProvinces();

        // Spinner click listener
        // e_citname.setOnItemSelectedListener(this);

        e_pname.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // TODO Auto-generated method stub
                Province = parent.getItemAtPosition(pos).toString();
                ProvincePos = pos;
                Toast.makeText(parent.getContext(),
                        "" + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
                addCity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        e_citname.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // TODO Auto-generated method stub
                City = parent.getItemAtPosition(pos).toString();
                CityPos = pos;

                Toast.makeText(parent.getContext(),
                        "" + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        select_gal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // image_selected=0;
                // dialogImage.show();
                new SelectGalleryImagesInformationDialog()
                        .showDialog(AddnewHall.this);
            }
        });

        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Toast.makeText(getApplicationContext(),
                // ""+e_hallname.getText().toString(), 1000).show();

                boolean t = collectData();

                if (t) {

                    if (NetworkUtils.isNetworkConnectionOn(AddnewHall.this)) {

                        ParseQuery<ParseObject> pquery = ParseQuery
                                .getQuery("HallCount");
                        pquery.whereEqualTo("State", pname);
                        pquery.getFirstInBackground(new GetCallback<ParseObject>() {
                            public void done(ParseObject object,
                                             ParseException e) {
                                if (object == null) {
                                    Log.e("score",
                                            "The getFirst request failed.");

                                    // Toast.makeText(getApplicationContext(),
                                    // "param error"+object.getInt("HallCount"),
                                    // 1000).show();

                                } else {

                                    // Toast.makeText(getApplicationContext(),
                                    // "param not an error"+object.getInt("HallCount"),
                                    // 1000).show();

                                    Log.e("hello",
                                            "hiiii"
                                                    + object.getInt("HallCount"));

                                    object.increment("HallCount");

                                    try {
                                        object.save();
                                    } catch (ParseException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    }

                                    Log.d("score", "Retrieved the object.");
                                }
                            }
                        });

                        final ProgressDialog dialog = new ProgressDialog(AddnewHall.this);
                        dialog.setMessage("Saving Hall");
                        dialog.setCancelable(false);
                        dialog.show();

                        ParseObject obj = new ParseObject("hallList");
                        obj.put("state", Province);
                        obj.put("city", City);
//                        obj.put("state", "testState");
//                        obj.put("city", "testCity");

                        obj.put("hallName", e_hallname.getText().toString());
                        obj.put("HallAdmin", preferences.getString("AdminEmail", null));
                        obj.put("isApproved", false);

                        obj.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                ParseObject obj1 = new ParseObject("hallDetail");
                                obj1.put("hallName", e_hallname.getText().toString());

//                        obj1.put("state", pname);
//                        obj1.put("city", cityname);
                                obj1.put("state", Province);
                                obj1.put("city", City);
                                obj1.put("HAllOwnerEmail", preferences.getString("AdminEmail", null));

                                obj1.put("bookingAmount", e_bookingamt.getText()
                                        .toString());

                                obj1.put("capicity", e_capacity.getText().toString());

                                obj1.put("contactNo", e_phno.getText().toString());
                                obj1.put("desc", e_desc.getText().toString());
                                obj1.put("email", myEmailid);
                                obj1.put("location", e_add.getText().toString());
                                obj1.put("geoL", point);

                                // placeObject.put("location", point);

                                obj1.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        dialog.dismiss();
                                        Intent i = new Intent(AddnewHall.this,
                                                TaskList.class);

                                        startActivity(i);

                                        AddnewHall.this.finish();
                                    }
                                });


                            }
                        });


                    } else {

                        new AlertDialog.Builder(AddnewHall.this)
                                .setTitle("Warning")
                                .setMessage(
                                        "Working internet connection is required. ")
                                .setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                // continue with delete
                                            }
                                        })

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

                }

            }

        });

    }

    public void getLocation() {


//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        criteria.setAltitudeRequired(true);
//        criteria.setBearingRequired(true);
//        criteria.setCostAllowed(true);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
//
//        location.getCurrentLocationInBackground(1000, criteria, new LocationCallback() {
//            @Override
//            public void done(ParseGeoPoint parseGeoPoint, ParseException e) {
////                point = parseGeoPoint;
//                if (e == null) {
//                    Log.e("Location", "" + parseGeoPoint.getLatitude());
//                    Log.e("Location", "" + parseGeoPoint.getLongitude());
//
//                } else {
//                    Log.e("Location", "" + e.toString());
//                }
//            }
//        });

        final LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location thisLocation) {
                Log.e("Location", "callback location");
                location = new ParseGeoPoint(thisLocation.getLatitude(), thisLocation.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }


            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, mLocationListener);
        } catch (Exception e) {
            Log.e("Location", "" + e.toString());
        }
//        Log.e("Location", "" + location.getLatitude());
//        Log.e("Location", "" + location.getLongitude());
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
//                1000, mLocationListener);

    }

    protected boolean collectData() {

        String hallName = e_hallname.getText().toString().trim();

        if (hallName.length() <= 0) {

            Toast.makeText(AddnewHall.this, "Please Enter HallName Field",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        String edesc = e_desc.getText().toString().trim();

        if (edesc.length() <= 0) {
            Toast.makeText(AddnewHall.this, "Please Eneter Desc Field",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        String bAmt = e_bookingamt.getText().toString().trim();

        if (bAmt.length() <= 0) {
            Toast.makeText(AddnewHall.this,
                    "Please Enter Booking Amount  Field", Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        String capacity = e_capacity.getText().toString().trim();

        if (capacity.length() <= 0) {
            Toast.makeText(AddnewHall.this, "Please Enter Capacity Field",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        String eadd = e_add.getText().toString().trim();
        if (eadd.length() <= 0) {
            Toast.makeText(AddnewHall.this, "Please Enter Add Field",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        String ephno = e_phno.getText().toString().trim();

        if (ephno.length() <= 0) {
            Toast.makeText(AddnewHall.this, "Please Phone Number Field",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    public void addProvinces() {

        // { "محافظة مسقط", "محافظة ظفار", "محافظة مسندم",
        // "محافظة البريمي", "محافظة الداخلية", "محافظة شمال الباطنة",
        // "محافظة جنوب الباطنة", "محافظة شمال الشرقية",
        // "محافظة جنوب الشرقية", "محافظة الظاهرة", "محافظة الوسطى",
        // "التواصل معنا" };
        //

        province.add("محافظة مسقط");
        province.add("محافظة ظفار");
        province.add("محافظة مسندم");
        province.add("محافظة البريمي");
        province.add("محافظة الداخلية");
        province.add("محافظة شمال الباطنة");
        province.add("محافظة جنوب الباطنة");
        province.add("محافظة شمال الشرقية");
        province.add("محافظة جنوب الشرقية");
        province.add("محافظة الظاهرة");
        province.add("محافظة الوسطى");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, province);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        e_pname.setAdapter(dataAdapter);
    }

    public void addCity() {
        String titles[] = {};

        switch (ProvincePos) {
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

        // city.add("city1");
        // city.add("city2");
        // city.add("city3");
        // city.add("city4");
        // city.add("city5");
        // city.add("city6");
        // city.add("city7");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, titles);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        e_citname.setAdapter(dataAdapter);

    }

    private void captureImageInitialization() {
        /**
         * a selector dialog to display two image source options, from camera
         * ‘Take from camera’ and from existing files ‘Select from gallery’
         */
        final String[] items = new String[]{"اختر من معرض"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("حدد صورة");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                // if (item
                // == 0) {
                // // /**
                // // * To take a photo from camera, pass intent action
                // // * ‘MediaStore.ACTION_IMAGE_CAPTURE‘ to open the camera
                // // app.
                // // */
                // // Intent intent = new
                // // Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // //
                // // /**
                // // * Also specify the Uri to save the image on specified
                // // path
                // // * and file name. Note that this Uri variable also used by
                // // * gallery app to hold the selected image path.
                // // */
                // // mImageCaptureUri = Uri.fromFile(new File(Environment
                // // .getExternalStorageDirectory(), "tmp_avatar_"
                // // + String.valueOf(System.currentTimeMillis())
                // // + ".jpg"));
                // //
                // // intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                // // mImageCaptureUri);
                // //
                // // try {
                // // intent.putExtra("return-data", true);
                // //
                // // startActivityForResult(intent, PICK_FROM_CAMERA);
                // // } catch (ActivityNotFoundException e) {
                // // e.printStackTrace();
                // // }
                //
                // Intent takePicture = new Intent(
                // MediaStore.ACTION_IMAGE_CAPTURE);
                // startActivityForResult(takePicture, PICK_FROM_CAMERA);
                //
                // } else {
                Intent pickPhoto = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, PICK_FROM_FILE);
                // }
            }
        });

        dialogImage = builder.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            // case PICK_FROM_CAMERA:
            // /**
            // * After taking a picture, do the crop
            // */
            // // doCrop();
            //
            // if (resultCode == RESULT_OK) {
            // Uri selectedImage = imageReturnedIntent.getData();
            //
            // gal_1.setImageURI(selectedImage);
            //
            // Toast.makeText(getApplicationContext(), "image 1", 1000).show();
            //
            // }
            //
            // break;

            case PICK_FROM_FILE:
                /**
                 * After selecting image from files, save the selected path
                 */

                mImageCaptureUri = imageReturnedIntent.getData();

                doCrop();

                if (resultCode == RESULT_OK) {

                    Uri selectedImage = imageReturnedIntent.getData();

                    // if (image_selected == 1) {
                    //
                    // gal_1.setImageURI(selectedImage);
                    // } else if (image_selected == 2) {
                    // gal_2.setImageURI(selectedImage);
                    //
                    // }
                    //
                    // else if (image_selected == 3) {
                    //
                    // gal_3.setImageURI(selectedImage);
                    // } else if (image_selected == 4) {
                    //
                    // gal_4.setImageURI(selectedImage);
                    // }

                }

                // doCrop();

                break;

            case CROP_FROM_CAMERA:
                String name = e_hallname.getText().toString();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Log.e("kas pan ye bhau ", "mi tar ithach yetoy");
                Bundle extras = imageReturnedIntent.getExtras();
                /**
                 * After cropping the image, get the bitmap of the cropped image and
                 * display it on imageview.
                 */
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    if (image_selected == 1) {
                        gallery_image1 = getRoundedCornerBitmap(photo, 10);
                        gal_1.setImageBitmap(gallery_image1);

                        if (gallery_image1 == null) {
                            gallery_image1 = BitmapFactory.decodeResource(
                                    getResources(), R.drawable.img1);
                        }

                        gallery_image1.compress(Bitmap.CompressFormat.PNG, 50,
                                stream);

                        byte[] image = stream.toByteArray();
                        file = new ParseFile("hallanme" + ".png", image);
                        file.saveInBackground();

                        ParseObject obj = new ParseObject("Hallimages");

                        obj.put("HallName", name);
                        obj.put("state", Province);
                        obj.put("city", City);
//                        obj.put("state", pname);
//                        obj.put("city", cityname);
//                        obj.put("state", "testState");
//                        obj.put("city", "testCity");

                        obj.put("Hallimages", file);

                        obj.saveInBackground();

                        gal_1.setImageBitmap(gallery_image1);

//                        Toast.makeText(getApplicationContext(), "image 1", 1000)
//                                .show();

                    } else if (image_selected == 2) {

                        gallery_image2 = getRoundedCornerBitmap(photo, 10);
                        gal_2.setImageBitmap(gallery_image2);

                        if (gallery_image2 == null) {
                            gallery_image2 = BitmapFactory.decodeResource(
                                    getResources(), R.drawable.img1);
                        }

                        gallery_image2.compress(Bitmap.CompressFormat.PNG, 50,
                                stream);

                        byte[] image = stream.toByteArray();
                        file = new ParseFile("hallanme" + ".png", image);
                        file.saveInBackground();

                        ParseObject obj = new ParseObject("Hallimages");

                        obj.put("HallName", name);
                        obj.put("state", Province);
                        obj.put("city", City);
//                        obj.put("state", pname);
//                        obj.put("city", cityname);
//                        obj.put("state", "testState");
//                        obj.put("city", "testCity");

                        obj.put("Hallimages", file);

                        obj.saveInBackground();

//                        Toast.makeText(getApplicationContext(), "image 2", 1000)
//                                .show();

                        gal_2.setImageBitmap(gallery_image2);

                    } else if (image_selected == 3) {

                        gallery_image3 = getRoundedCornerBitmap(photo, 10);
                        gal_3.setImageBitmap(gallery_image3);

                        if (gallery_image3 == null) {
                            gallery_image3 = BitmapFactory.decodeResource(
                                    getResources(), R.drawable.img1);
                        }

                        gallery_image3.compress(Bitmap.CompressFormat.PNG, 50,
                                stream);

                        byte[] image = stream.toByteArray();
                        file = new ParseFile("hallanme" + ".png", image);
                        file.saveInBackground();

                        ParseObject obj = new ParseObject("Hallimages");

                        obj.put("HallName", name);
                        obj.put("state", Province);
                        obj.put("city", City);
//                        obj.put("state", pname);
//                        obj.put("city", cityname);
//                        obj.put("state", "testState");
//                        obj.put("city", "testCity");

                        obj.put("Hallimages", file);

                        obj.saveInBackground();

//                        Toast.makeText(getApplicationContext(), "image 3", 1000)
//                                .show();

                        gallery_image3 = getRoundedCornerBitmap(photo, 10);
                    } else if (image_selected == 4) {

                        gallery_image4 = getRoundedCornerBitmap(photo, 10);
                        gal_4.setImageBitmap(gallery_image4);

                        if (gallery_image4 == null) {
                            gallery_image4 = BitmapFactory.decodeResource(
                                    getResources(), R.drawable.img1);
                        }

                        gallery_image4.compress(Bitmap.CompressFormat.PNG, 50,
                                stream);

                        byte[] image = stream.toByteArray();
                        file = new ParseFile("hallanme" + ".png", image);
                        file.saveInBackground();

                        ParseObject obj = new ParseObject("Hallimages");

                        obj.put("HallName", name);

//                        obj.put("state", pname);

//                        obj.put("city", cityname);
//                        obj.put("state", "testState");
//                        obj.put("city", "testCity");
                        obj.put("state", Province);
                        obj.put("city", City);
                        obj.put("Hallimages", file);

                        obj.saveInBackground();

//                        Toast.makeText(getApplicationContext(), "image 4", 1000)
//                                .show();

                        gallery_image4 = getRoundedCornerBitmap(photo, 10);
                        // gal_4.setImageBitmap(gallery_image4);
                    }

                }

                File f = new File(mImageCaptureUri.getPath());
                /**
                 * Delete the temporary image
                 */
                if (f.exists())
                    f.delete();

                break;

        }
    }// end of activityResult

    //
    //
    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        /**
         * Open image crop app by starting an intent
         * ‘com.android.camera.action.CROP‘.
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        /**
         * Check if there is image cropper app installed.
         */
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();

        /**
         * If there is no image cropper app, display warning message
         */
        if (size == 0) {

            Toast.makeText(this, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();
            return;
        } else {
            /**
             * Specify the image path, crop dimension and scale
             */
            DisplayMetrics displayMetrics = AddnewHall.this.getResources()
                    .getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;

            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            /**
             * There is posibility when more than one image cropper app exist,
             * so we have to check for it first. If there is only one app, open
             * then app.
             */

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                /**
                 * If there are several app exist, create a custom chooser to
                 * let user selects the app.
                 */
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("اختيار المحاصيل التطبيق");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }//

    public class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }

    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.crop_selector, options);

            mOptions = options;

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    // end of cropOptionAdapter

    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int roundPixelSize) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = roundPixelSize;
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }// end getRoundedCornerBitmap

    private boolean getlocationstate() {

        locationManager = (LocationManager) AddnewHall.this
                .getSystemService(Context.LOCATION_SERVICE);


        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return isNetworkEnabled;
    }

    // private void executeLocationAndInternetServices() {
    // if (Util.checkConnectivity(AddnewHall.this)) {
    // if (new GPSClass().getLocation(AddnewHall.this,
    // new LocationResult() {
    // @Override
    // public void gotLocation(Location location) {
    //
    // try {
    // new AppPreferences(AddnewHall.this)
    // .saveLat(String.valueOf(location
    // .getLatitude()));
    // new AppPreferences(AddnewHall.this)
    // .saveLon(String.valueOf(location
    // .getLongitude()));
    //
    // } catch (Exception e) {
    // }
    //
    // lat = location.getLatitude();
    // longt = location.getLongitude();
    //
    //
    // // Geocoder geocoder;
    // // List<Address> addresses = null;
    // // geocoder = new Geocoder(AddnewHall.this,
    // // Locale.getDefault());
    // // try {
    // // addresses = geocoder.getFromLocation(location
    // // .getLatitude(), location
    // // .getLongitude(), 1);
    // // } catch (IOException e) {
    // // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // // }
    // //
    // // geoAdd = addresses.get(0).getAddressLine(0);
    // // String city = addresses.get(0).getAddressLine(1);
    // // String country =
    // // addresses.get(0).getAddressLine(2);
    //
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

    @SuppressWarnings("deprecation")
    public void showNetworkDialog(final String message) {
        // final exit of application
        AlertDialog.Builder builder = new AlertDialog.Builder(AddnewHall.this);
        builder.setTitle(getResources().getString(R.string.app_name));
        if (message.equals("gps")) {
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

    static int image_selected = 0;

    /***************************
     * Select Area of service
     ******************************/
    public class SelectGalleryImagesInformationDialog {
        Dialog dialog;

        public Dialog showDialog(Context context) {

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);

            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            dialog.setContentView(R.layout.select_gallery_custome_dialog);

            gal_1 = (ImageView) dialog.findViewById(R.id.img_gal1);
            gal_1.setClickable(true);

            gal_1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    image_selected = 1;
                    dialogImage.show();
                }
            });

            gal_2 = (ImageView) dialog.findViewById(R.id.img_gal2);
            gal_2.setClickable(true);

            gal_2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    image_selected = 2;
                    dialogImage.show();
                }
            });
            gal_3 = (ImageView) dialog.findViewById(R.id.img_gal3);
            gal_3.setClickable(true);

            gal_3.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    image_selected = 3;
                    dialogImage.show();
                }
            });
            gal_4 = (ImageView) dialog.findViewById(R.id.img_gal4);
            gal_4.setClickable(true);
            gal_4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    image_selected = 4;
                    dialogImage.show();
                }
            });

            Button cancelBtn = (Button) dialog.findViewById(R.id.btn_backe);

            cancelBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            // cancelBtn.setOnClickListener(new OnClickListener() {
            // public void onClick(View v) {
            //
            // dialog.dismiss();
            // }
            // });

            dialog.show();
            return dialog;
        }

        public void removeDialog() {
            dialog.dismiss();
        }
    }// end of SelectGalleryImagesInformationDialog

    // @Override
    // public void onItemSelected(AdapterView<?> parent, View view, int
    // position, long id) {
    // // TODO Auto-generated method stub
    //
    // String item = parent.getItemAtPosition(position).toString();
    // // Showing selected spinner item
    // Toast.makeText(parent.getContext(), "Selected: " + item,
    // Toast.LENGTH_LONG).show();
    // }
    //
    // @Override
    // public void onNothingSelected(AdapterView<?> arg0) {
    // // TODO Auto-generated method stub
    //
    // }

}

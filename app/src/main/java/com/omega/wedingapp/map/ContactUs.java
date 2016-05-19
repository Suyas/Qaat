package com.omega.wedingapp.map;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.omega.halldetails.BookHall;
import com.omega.weddingapp.mail.NetworkUtils;
import com.omega.weddingapp.main.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

//SendEmailAsyncTask(user_Name).execute();

public class ContactUs extends Activity implements TimePickerDialog.OnTimeSetListener {
    TextView text;
    View layout;

    private LayoutInflater inflater;
    Typeface tf;
    String path;

    TextView tvfrom, tvto, tvhallname, fromTime, toTime;
    String myEmailid;
    private ViewGroup view;
    Button save, btn_dates;
    EditText user_name, user_ph_no, user_sub, user_msg, user_email;
    String id = null;
    String name, email, ph_no, umsg, usub;
    int enquiryToken;
    String Fromtime, Totime;

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOURTime = 60 * MINUTE;

//    int dateArraySize = 0;


    Date fromDate, toDate;
    Date fromDateFormatter, toDateFormatter;

    Date currentDate;

    String hallname, from, to, owneremail;
    public static ArrayList<Date> userSelectedDates;
    public static ArrayList<Date> userSelectedDatesForDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        inflater = LayoutInflater.from(this);
        view = (ViewGroup) inflater.inflate(R.layout.email_layout, null);

        Typeface mFont = Typeface
                .createFromAsset(getAssets(), "al-mohanad.ttf");
        // OmegaApplication.setFont(view, mFont);

        setContentView(view);

        // setUpActionBar();

        /** get configured email **/
        Account[] accounts = AccountManager.get(this).getAccountsByType(
                "com.google");
        myEmailid = accounts[0].name.toString();

        email = myEmailid;

        userSelectedDates = new ArrayList<Date>();
        userSelectedDatesForDisplay = new ArrayList<Date>();
        userSelectedDates.addAll(BookHall.selectedDates);
        userSelectedDatesForDisplay.addAll(BookHall.selectedDates);

        Log.e("Recvd Dates", "" + userSelectedDates.toString());


//        dateArraySize = userSelectedDates.size();


//        Log.e("Dates", "size  " + dateArraySize);
        save = (Button) findViewById(R.id.btn_save);
        btn_dates = (Button) findViewById(R.id.btn_dates);

        user_email = (EditText) findViewById(R.id.user_email);
        user_name = (EditText) findViewById(R.id.user_name);
        user_ph_no = (EditText) findViewById(R.id.user_phone_no);
        user_sub = (EditText) findViewById(R.id.subject);
        user_msg = (EditText) findViewById(R.id.msg);

        tvhallname = (TextView) findViewById(R.id.emailhallname);

        tvfrom = (TextView) findViewById(R.id.fromdate);
        tvto = (TextView) findViewById(R.id.todate);
        fromTime = (TextView) findViewById(R.id.fromtime);
        toTime = (TextView) findViewById(R.id.totime);

//        from = getIntent().getStringExtra("from");
//        to = getIntent().getStringExtra("to");
        hallname = getIntent().getStringExtra("hallname");


        tvhallname.setText(hallname);
//EE MMM dd HH:mm:ss z yyyy


        String toDateString = userSelectedDates.get(userSelectedDates.size() - 1).toString();
        String FromDateString = userSelectedDates.get(0).toString();


        tvfrom.setText("من " + parseTodaysDate(FromDateString));
        tvto.setText("إلى " + parseTodaysDate(toDateString));

        owneremail = getIntent().getStringExtra("adminEmail");

        user_email.setText(email);
        user_email.setEnabled(false);

        user_email.setTypeface(mFont);

        tvhallname.setTypeface(mFont);
        tvfrom.setTypeface(mFont);
        tvfrom.setTypeface(mFont);
        user_name.setTypeface(mFont);
        user_ph_no.setTypeface(mFont);
        user_msg.setTypeface(mFont);
        save.setTypeface(mFont);
        user_msg.setTypeface(mFont);

//        currentDate = new Date(System.currentTimeMillis());

        returnChangedDate();
        Log.e("New userSelectedDates", "" + userSelectedDates.toString());

        btn_dates.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        ContactUs.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });


        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                boolean t = collectData();

                if (NetworkUtils.isNetworkConnectionOn(getApplicationContext())) {

                    if (t) {

                        // Intent email = new
                        // Intent(Intent.ACTION_SENDTO,Uri.fromParts(
                        // "mailto",owneremail, null));
                        // // email.putExtra(Intent.EXTRA_EMAIL, new
                        // String[]{owneremail});
                        // email.putExtra(Intent.EXTRA_SUBJECT, usub);
                        // email.putExtra(Intent.EXTRA_TEXT, umsg);
                        // email.setType("message/rfc822");
                        // startActivity(Intent.createChooser(email,
                        // "Choose an Email client :"));
                        //
                        bookDates();
//This is the email code
//                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
//                                Uri.fromParts("mailto", owneremail, null));
//                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, usub);
//                        emailIntent.putExtra(Intent.EXTRA_TEXT, "From: " + name
//                                + "\n" + "Ph no: " + ph_no + "\n"
//                                + "From Date: " + from + "\n" + "To Date: "
//                                + to + "\n" + "Hall Name: " + hallname + "\n"
//                                + umsg);
//                        startActivity(Intent.createChooser(emailIntent,
//                                "Send email..."));

                        // new SendEmailAsyncTask(name, ph_no, usub, umsg,
                        // email,owneremail).execute();

                        Toast.makeText(getApplicationContext(),
                                "شكرا، تم إرسال الحجز بنجاح.",
                                Toast.LENGTH_LONG).show();


//						ParseObject object=new ParseObject("HallRequests");
//						object.put("UserName", name);
//						object.put("PhoneNumber", ph_no);
//						object.put("FromDate", from);
//						object.put("ToDate", to);
//						object.put("HallName", hallname);
//						object.put("Message", umsg);
//						object.saveInBackground(new SaveCallback() {
//							
//							@Override
//							public void done(ParseException e) {
//								// TODO Auto-generated method stub
//							if (e==null) {
//								Toast.makeText(ContactUs, text, duration)
//							}	
//							}
//						});


                        // ContactUs.this.finish();
                        //
                        // Intent i = new Intent(ContactUs.this,
                        // MainActivity.class);
                        //
                        // startActivity(i);
                        //
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Working internet connection is required. ",
                            Toast.LENGTH_LONG).show();

                }

            }

            private boolean collectData() {
                Fromtime = fromTime.getText().toString().trim();
                Totime = toTime.getText().toString().trim();

                if (Fromtime.length() <= 0) {

                    Toast.makeText(ContactUs.this, "Please select start time.",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

                if (Totime.length() <= 0) {

                    Toast.makeText(ContactUs.this, "Please select end time.",
                            Toast.LENGTH_LONG).show();
                    return false;
                }


                name = user_name.getText().toString().trim();

                if (name.length() <= 0) {

                    Toast.makeText(ContactUs.this, "Full Name is required.",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

                usub = user_sub.getText().toString().trim();

                if (usub.length() <= 0) {
                    Toast.makeText(
                            ContactUs.this,
                            "Please provide a valid Subject for this communication.",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

                umsg = user_msg.getText().toString().trim();

                if (umsg.length() <= 0) {
                    Toast.makeText(ContactUs.this,
                            "Please provide your Email Address.",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

                ph_no = user_ph_no.getText().toString().trim();

                if (ph_no.length() <= 0 || ph_no.length() < 8)

                {

                    Toast.makeText(ContactUs.this,
                            "Please provide your Phone Number.",
                            Toast.LENGTH_LONG).show();
                    return false;
                }

                return true;

            }

        });

    }

    public static int randInt() {
        int min;
        int max;
        min = 1;
        max = 999999999;
        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void returnChangedDate() {
        long HOUR = 3600 * 1000;

        userSelectedDates.clear();
        for (int i = 0; i < userSelectedDatesForDisplay.size(); i++) {
            userSelectedDates.add(new Date(userSelectedDatesForDisplay.get(i).getTime() + 6 * HOUR));
        }


    }


    public void bookDates() {
        enquiryToken = randInt();
        final ProgressDialog dialog = new ProgressDialog(ContactUs.this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();
        Account[] accounts = AccountManager.get(ContactUs.this).getAccountsByType(
                "com.google");
        String myEmailid = accounts[0].name.toString();
        ParseObject userQuerysummary = new ParseObject("UserQuerySummary");
        userQuerysummary.put("Name", name);
        userQuerysummary.put("HallName", hallname);
        userQuerysummary.put("Email", owneremail);
        userQuerysummary.put("Phone", ph_no);
        userQuerysummary.put("EnquiryToken", "" + enquiryToken);
        Log.e("EnquiryToken", "" + enquiryToken);


        userQuerysummary.put("StartDate", userSelectedDates.get(0));
        userQuerysummary.put("StartDateString", userSelectedDates.get(0).toString());

//        Log.e("first", " " + (new SimpleDateFormat("dd-MMM-yyyy").format(fromDate)));
        userQuerysummary.put("EndDate", userSelectedDates.get(userSelectedDates.size() - 1));
//        Log.e("last", " " + toDate);

        userQuerysummary.put("StartTime", Fromtime);
        userQuerysummary.put("EndTime", Totime);
        Log.e("In Summary", "" + userSelectedDates.toString());

        userQuerysummary.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e == null) {
                    Log.e("EnquiryToken", "" + enquiryToken);
                    saveInDetails(enquiryToken);
                } else {
                    Log.e("OMG", "ERROR " + e.toString());
                }
            }
        });


    }

    public void saveInDetails(int enquiryToken) {

        try {
            Account[] accounts = AccountManager.get(ContactUs.this).getAccountsByType(
                    "com.google");
            String myEmailid = accounts[0].name.toString();
            Log.e("In Details", "" + userSelectedDates.toString());
            for (int i = 0; i < userSelectedDates.size(); i++) {
                ParseObject object = new ParseObject("UserQueryDetails");

                object.put("HallName", hallname);
                object.put("Email", owneremail);
                object.put("Phone", ph_no);
                object.put("BookedDates", userSelectedDates.get(i));
                Log.e("BookedDates", "" + userSelectedDates.get(i).toString());
                Log.e("EnquiryToken", "" + enquiryToken);
                object.put("EnquiryToken", "" + enquiryToken);
                object.put("Name", name);
                if (userSelectedDates.get(0).toString().equals(userSelectedDates.get(userSelectedDates.size() - 1).toString())) {
                    object.put("StartTime", Fromtime);
                    object.put("EndTime", Totime);
                    Log.e("Dates", "Single " + userSelectedDates.get(i).toString());
                } else {
                    if (userSelectedDates.get(i).toString().equals(userSelectedDates.get(0).toString())) {
                        object.put("StartTime", Fromtime);
                        object.put("EndTime", "23:59");
                        Log.e("Dates", "First From Time saved " + userSelectedDates.get(i).toString());

                    } else if (userSelectedDates.get(i).toString().equals(userSelectedDates.get(userSelectedDates.size() - 1).toString())) {
                        object.put("StartTime", "00:00");
                        object.put("EndTime", Totime);
                        Log.e("Dates", "Last To Time saved " + userSelectedDates.get(i).toString());
                    } else {
                        object.put("StartTime", "00:00");
                        object.put("EndTime", "23:59");
                        Log.e("Dates", "Other Times saved  " + userSelectedDates.get(i).toString());
                    }


                }

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.e("SaveCallback", "saved");
                        }
                    }
                });

            }
            ContactUs.this.finish();
        } catch (Exception e) {
            Log.e("SaveCallback", "" + e.toString());
        }

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        Fromtime = "" + hourString + " : " + minuteString;
        Totime = "" + hourStringEnd + " : " + minuteStringEnd;

        if ((!Fromtime.equals(null)) && (!Totime.equals(null))) {
            fromTime.setText(Fromtime);
            toTime.setText(Totime);
        } else if (!Totime.equals(null)) {
            toTime.setText(Totime);
        } else if (!Fromtime.equals(null)) {
            fromTime.setText(Fromtime);
        }

        Log.e("From To Time", "" + Fromtime);
        Log.e("From To Time", "" + Totime);
    }

    // private void setUpActionBar() {
    //
    // final ActionBar actionBar = getActionBar();
    // actionBar.setCustomView(R.layout.custom_action_bar);
    // actionBar.setDisplayShowTitleEnabled(false);
    // actionBar.setDisplayShowCustomEnabled(true);
    // actionBar.setDisplayUseLogoEnabled(false);
    // actionBar.setDisplayShowHomeEnabled(false);
    //
    // TextView txtGhost = (TextView) findViewById(R.id.header);
    // Typeface font = Typeface.createFromAsset(getAssets(),
    // "fonts/MISTRAL.TTF");
    // txtGhost.setTypeface(font);
    //
    // }
    public static String parseTodaysDate(String time) {


        String inputPattern = "EEE MMM d HH:mm:ss zzz yyyy";

        String outputPattern = "dd/MM/yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            Log.e("mini", "Converted Date Today:" + str);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        Date returnDate;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            returnDate = dateFormat.parse(str);
            Log.e("Converted Date", "" + returnDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}

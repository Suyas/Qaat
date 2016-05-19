package com.omega.weddingapp.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.omega.weddingapp.main.R;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BlockDates extends Activity implements TimePickerDialog.OnTimeSetListener {

    private CalendarPickerView calendar;
    Button blockDates, selectTime;
    public static ArrayList<Date> selectedDates;
    String Fromtime, Totime;
    TextView fromTime, toTime;
    int counter;
    SharedPreferences preferences;
    Bundle extras;
    String hallname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_block_dates);
        preferences = getSharedPreferences("LoginPreference", 0);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) findViewById(R.id.qaatCalendarBlock);
        blockDates = (Button) findViewById(R.id.blockDates);
        selectTime = (Button) findViewById(R.id.selectTime);

        fromTime = (TextView) findViewById(R.id.fromTime);
        toTime = (TextView) findViewById(R.id.toTime);
        extras=getIntent().getExtras();
        if (extras != null) {
            hallname = extras.getString("HallName");
        }

        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        calendar.highlightDates(getHolidays());

        blockDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDates = (ArrayList<Date>) calendar
                        .getSelectedDates();
                Fromtime = fromTime.getText().toString().trim();
                Totime = toTime.getText().toString().trim();

                if (!(Fromtime.equals("No time selected") && (Totime.equals("No time selected")))) {
                    blockDatesOnAdminConfirm();
                } else {
                    Toast.makeText(BlockDates.this, "يرجى تحديد كل الأوقات", Toast.LENGTH_SHORT).show();
                }


            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        BlockDates.this,
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

    }

    private ArrayList<Date> getHolidays() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
//        String dateInString = "29-09-2015";
//        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //get current date time with Date()
        Date date = new Date();
        //System.out.println(dateFormat.format(date)); don't print it, but save it!
        String yourDate = dateFormat.format(date);

        try {
            date = dateFormat.parse(yourDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Date> holidays = new ArrayList<>();
        holidays.add(date);
        return holidays;
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

    public void blockDatesOnAdminConfirm() {


        final ProgressDialog dialog = new ProgressDialog(BlockDates.this);
        dialog.setCancelable(false);
        dialog.setMessage("Blocking Dates");
        dialog.show();
        for (int i = 0; i < selectedDates.size(); i++) {
            ParseObject object = new ParseObject("AdminConfirmations");
            object.put("isApproved", "Approved");
            object.put("Name", preferences.getString("AdminName", "Admin"));
            object.put("Email", preferences.getString("AdminEmail", "Admin"));
            object.put("Phone", preferences.getString("AdminPhone", "Admin"));
            object.put("BookedDates", selectedDates.get(i));
            object.put("HallName", hallname);
            object.put("ExtraComments", "Booked by Admin");
            if (selectedDates.get(0).toString().equals(selectedDates.get(selectedDates.size() - 1).toString())) {
                object.put("FromTime", Fromtime);
                object.put("ToTime", Totime);

            } else {
                if (selectedDates.get(i).toString().equals(selectedDates.get(0).toString())) {
                    object.put("FromTime", Fromtime);
                    object.put("ToTime", "23:59");
                    Log.e("Dates", "First From Time saved " + selectedDates.get(i).toString());
                } else if (selectedDates.get(i).toString().equals(selectedDates.get(selectedDates.size() - 1).toString())) {
                    object.put("FromTime", "00:00");
                    object.put("ToTime", Totime);
                    Log.e("Dates", "Last To Time saved " + selectedDates.get(i).toString());
                } else {
                    object.put("FromTime", "00:00");
                    object.put("ToTime", "23:59");
                    Log.e("Dates", "Other Times saved  " + selectedDates.get(i).toString());
                }


            }


            counter = i;

            Log.e("setData", "i " + i);
            object.saveInBackground(new SaveCallback() {

                @Override
                public void done(com.parse.ParseException arg0) {
                    // TODO Auto-generated method stub
//                    dialog.dismiss();

                    Log.e("setData", selectedDates.size() + " counter " + counter);


//                    Toast.makeText(context, "Request Updating...",
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (counter == selectedDates.size() - 1) {
            dialog.dismiss();
            Toast.makeText(BlockDates.this, "تم الحفظ", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BlockDates.this, TaskList.class));
            BlockDates.this.finish();
        }


    }

}

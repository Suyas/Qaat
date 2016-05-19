package com.omega.halldetails;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.omega.weddingapp.main.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookedDatesDetails extends Activity {
    ListView bookedDatesDetailsList;
    Bundle extras;
    Date from, to;
    String hallName;
    String adminEmail;

    ArrayList<BookedDatesPOJO> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_booked_dates_details);
        extras = getIntent().getExtras();
        if (extras != null) {
            from = (Date) extras.get("fromDate");
            to = (Date) extras.get("toDate");
            hallName = extras.getString("hallName");
            adminEmail = extras.getString("adminEmail");
        }
        bookedDatesDetailsList = (ListView) findViewById(R.id.bookedDatesDetailsList);
        items = new ArrayList<BookedDatesPOJO>();
        getBookedDatesList();
    }

    public void getBookedDatesList() {
        final ProgressDialog dialog = new ProgressDialog(BookedDatesDetails.this);
        dialog.setCancelable(false);
        dialog.setMessage("Getting Information");
        dialog.show();
        ParseQuery<ParseObject> range = ParseQuery.getQuery("AdminConfirmations");
        range.whereLessThanOrEqualTo("BookedDates", to);
        range.whereGreaterThanOrEqualTo("BookedDates", from);
        range.whereEqualTo("HallName", hallName);
        range.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        ParseObject object = list.get(i);
                        BookedDatesPOJO rowItems = new BookedDatesPOJO(object.getDate("BookedDates"), object.getString("FromTime"), object.getString("ToTime"));
                        items.add(rowItems);
                    }
                    bookedDatesDetailsList.setAdapter(new BookedDatesAdapter(items, BookedDatesDetails.this, hallName, adminEmail));
                    dialog.dismiss();
                } else {
                    Toast.makeText(BookedDatesDetails.this, "Error Getting data", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

    }


}

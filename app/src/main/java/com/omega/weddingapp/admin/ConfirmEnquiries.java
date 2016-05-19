package com.omega.weddingapp.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import java.util.List;

public class ConfirmEnquiries extends Activity {
    ListView confirmLogsList;
    ArrayList<ConfirmPOJO> items;
    SharedPreferences preferences;
    Bundle extras;
    String hallname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_confirm_enquiries);

        confirmLogsList = (ListView) findViewById(R.id.confirmLogsList);
        items = new ArrayList<ConfirmPOJO>();
        extras = getIntent().getExtras();
        preferences = getSharedPreferences("LoginPreference", 0);
        if (extras != null) {
            hallname = extras.getString("HallName");
        }


        getDataFromConfirm();
    }

    public void getDataFromConfirm() {
        final ProgressDialog dialog = new ProgressDialog(ConfirmEnquiries.this);
        dialog.setCancelable(false);
        dialog.setMessage("Getting Logs");
        dialog.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("AdminConfirmations");
        query.orderByAscending("BookedDates");
        query.whereEqualTo("Email", preferences.getString("FullName", null));
        query.whereEqualTo("HallName", hallname);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> list, ParseException e) {
                                       if (e == null) {
                                           if (list.size() > 0) {
                                               for (int i = 0; i < list.size(); i++) {
                                                   ParseObject object = list.get(i);
                                                   ConfirmPOJO rowIetm = new ConfirmPOJO(object.getString("Name"),
                                                           object.getString("Phone"),
                                                           object.getString("HallName"),
                                                           object.getString("FromTime"),
                                                           object.getString("ToTime"),
                                                           object.getObjectId(),
                                                           object.getDate("BookedDates"));
                                                   items.add(rowIetm);


                                               }
                                               dialog.dismiss();
                                               confirmLogsList.setAdapter(new ConfirmEnquiriesAdapter(items, ConfirmEnquiries.this));

                                           } else {
                                               dialog.dismiss();
                                               Toast.makeText(ConfirmEnquiries.this, "No logs found!",
                                                       Toast.LENGTH_SHORT).show();
                                           }
                                       } else

                                       {
                                           dialog.dismiss();
                                           Toast.makeText(ConfirmEnquiries.this, "Error fetching your data",
                                                   Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               }

        );


    }

}

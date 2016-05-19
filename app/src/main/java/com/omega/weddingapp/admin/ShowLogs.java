package com.omega.weddingapp.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class ShowLogs extends Activity {

    ListView listView;
    ArrayList<LogsPOJO> rowItems;
    SharedPreferences preferences;
    Bundle extras;
    String hallname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_logs);
        listView = (ListView) findViewById(R.id.logsList);
        rowItems = new ArrayList<LogsPOJO>();
        preferences = getSharedPreferences("LoginPreference", 0);

        extras = getIntent().getExtras();

        if (extras != null) {
            hallname = extras.getString("HallName");
        }
        Log.e("OMG", "Recvd hall " + hallname);

        getList();

    }

    public void getList() {
        final ProgressDialog dialog = new ProgressDialog(ShowLogs.this);
        dialog.setCancelable(false);
        dialog.setMessage("Getting Logs");
        dialog.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserQuerySummary");
        query.whereEqualTo("Email", preferences.getString("AdminEmail", null));
        query.whereEqualTo("HallName", hallname);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> logsList, ParseException e) {
                // TODO Auto-generated method stub
                if (e == null) {
                    if (logsList.size() > 0) {
                        for (int i = 0; i < logsList.size(); i++) {
                            ParseObject object = logsList.get(i);

                            LogsPOJO pojo = new LogsPOJO(object
                                    .getString("Name"), object
                                    .getString("Email"), object
                                    .getString("Phone"), object
                                    .getDate("StartDate"),
                                    object.getDate("EndDate"),
                                    object.getString("HallName"),
                                    object.getString("EnquiryToken"),
                                    object.getString("EndTime"),
                                    object.getString("StartTime"),
                                    object.getObjectId());
                            Log.e("EndTime", "" + object.getString("EndTime"));
                            Log.e("StartTime", "" + object.getString("StartTime"));
                            rowItems.add(pojo);

                        }

                        dialog.dismiss();
                        LogsAdapter adapter = new LogsAdapter(ShowLogs.this,
                                rowItems);
                        listView.setAdapter(adapter);

                    } else {
                        dialog.dismiss();
                        Toast.makeText(ShowLogs.this, "No logs found!",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialog.dismiss();
                    Toast.makeText(ShowLogs.this,
                            "Error while getting your logs", Toast.LENGTH_SHORT)
                            .show();

                    Log.e("OMG", "" + e.toString());
                }
            }
        });

    }

}

package com.omega.weddingapp.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.omega.weddingapp.main.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AdminHallList extends Activity {

    ArrayList<String> items = new ArrayList<>();
    ListView adminHallList;
    SharedPreferences preferences;
    Bundle extras;
    String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin_hall_list);
        preferences = getSharedPreferences("LoginPreference", 0);

        extras = getIntent().getExtras();
        if (extras != null) {
            className = extras.getString("Token");
        }


        adminHallList = (ListView) findViewById(R.id.adminHallList);

        getDataFromParse();

        adminHallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (className.equals("ShowLogs")) {
                    Log.e("ARRAY", "" + items.get(position));
                    Intent intent = new Intent(AdminHallList.this, ShowLogs.class);
                    intent.putExtra("HallName", items.get(position).toString());
                    startActivity(intent);

                } else if (className.equals("ConfirmEnquiries")) {
                    Intent intent = new Intent(AdminHallList.this, ConfirmEnquiries.class);
                    intent.putExtra("HallName", items.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AdminHallList.this, BlockDates.class);
                    intent.putExtra("HallName", items.get(position));
                    startActivity(intent);
                }
            }
        });


    }

    public void getDataFromParse() {
        final ProgressDialog dialog = new ProgressDialog(AdminHallList.this);
        dialog.setMessage("Getting list of halls");
        dialog.setCancelable(false);
        dialog.show();

        Log.e("Email", "" + preferences.getString("AdminEmail", null));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("hallList");
        query.whereEqualTo("HallAdmin", preferences.getString("AdminEmail", null));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {

                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            items.add(list.get(i).getString("hallName"));
                        }

                        dialog.dismiss();
                        adminHallList.setAdapter(new AdminHallListAdapter(items, AdminHallList.this));

                    } else {
                        dialog.dismiss();
                        Toast.makeText(AdminHallList.this, "You dont have hall", Toast.LENGTH_LONG).show();
                    }

                } else {
                    dialog.dismiss();
                    Log.e("List", "" + e.toString());
                    Toast.makeText(AdminHallList.this, "You dont have hall", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}

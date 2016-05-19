package com.omega.weddingapp.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.omega.addnewhall.AddnewHall;
import com.omega.weddingapp.main.R;

public class TaskList extends Activity {

    Button createButton, showLogsButton, bookButton, blockDatesButton;
    private LocationManager locationManager = null;
    boolean isNetworkEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_task_list);

        createButton = (Button) findViewById(R.id.createButton);
        showLogsButton = (Button) findViewById(R.id.showLogsButton);
        bookButton = (Button) findViewById(R.id.bookButton);
        blockDatesButton = (Button) findViewById(R.id.blockDatesButton);

        createButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (getlocationstate()) {

                    Intent intent = new Intent(TaskList.this, AddnewHall.class);
                    startActivity(intent);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(TaskList.this);
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
            }
        });

        showLogsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Intent intent = new Intent(TaskList.this, ShowLogs.class);
                Intent intent = new Intent(TaskList.this, AdminHallList.class);
                intent.putExtra("Token", "ShowLogs");
                startActivity(intent);
            }
        });

        bookButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(TaskList.this, AdminHallList.class);
                intent.putExtra("Token", "ConfirmEnquiries");
                startActivity(intent);
            }
        });

        blockDatesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskList.this, AdminHallList.class);
                intent.putExtra("Token", "BlockDates");
                startActivity(intent);

            }
        });

    }

    private boolean getlocationstate() {

        locationManager = (LocationManager) TaskList.this
                .getSystemService(Context.LOCATION_SERVICE);


        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return isNetworkEnabled;
    }

}

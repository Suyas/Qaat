package com.omega.weddingapp.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.omega.weddingapp.main.R;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Dell on 07-10-2015.
 */
public class ConfirmEnquiriesAdapter extends BaseAdapter {
    ArrayList<ConfirmPOJO> rowItems;
    Context context;
    static final int TIME_DIALOG_ID = 1111;
    private int hour;
    private int minute;


    public ConfirmEnquiriesAdapter(ArrayList<ConfirmPOJO> rowItems, Context context) {
        this.rowItems = rowItems;
        this.context = context;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.confirm_logs_row_items, null);
            holder = new ViewHolder();
            holder.userName = (TextView) convertView.findViewById(R.id.userName);
            holder.hallName = (TextView) convertView.findViewById(R.id.hallName);
            holder.delete = (TextView) convertView.findViewById(R.id.delete);
            holder.fromTime = (TextView) convertView.findViewById(R.id.fromTime);
            holder.phoneNumber = (TextView) convertView.findViewById(R.id.phoneNumber);

            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.toTime = (TextView) convertView.findViewById(R.id.toTime);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ConfirmPOJO rowItem = (ConfirmPOJO) getItem(position);

        holder.userName.setText("" + rowItem.getUserName());
        holder.hallName.setText("" + rowItem.getHallName());
        holder.fromTime.setText("" + rowItem.getFrom());
        holder.date.setText("" + new SimpleDateFormat("dd-MMM-yyyy").format(rowItem.getDate()).toString());
        holder.toTime.setText("" + rowItem.getTo());
        holder.phoneNumber.setText("" + rowItem.getPhone());
        final Calendar c = Calendar.getInstance();
        // Current Hour
        hour = c.get(Calendar.HOUR_OF_DAY);
        // Current Minute
        minute = c.get(Calendar.MINUTE);

        // set current time into output textview
        holder.fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.showDialog(TIME_DIALOG_ID);
            }
        });

        holder.toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure you want to delete this entry?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRow(rowItem.getObjectId());
                        // Write your code here to invoke YES event
//                        Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
//                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView userName;
        TextView hallName;
        TextView delete;
        TextView fromTime;
        TextView toTime;
        TextView date;
        TextView phoneNumber;

    }

    public void deleteRow(String objectId) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait! Updating data");
        dialog.setCancelable(false);
        dialog.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AdminConfirmations");

        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    parseObject.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            Intent intent = new Intent(context, ConfirmEnquiries.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    });
                } else {
                    Toast.makeText(context, "Error while performing the operation", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}

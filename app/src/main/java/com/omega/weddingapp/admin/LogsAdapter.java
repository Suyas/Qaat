package com.omega.weddingapp.admin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.omega.weddingapp.main.R;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogsAdapter extends BaseAdapter {

    Context context;
    ArrayList<LogsPOJO> rowItems;
    CharSequence[] items = {"Approve", "Reject"};
    public static ArrayList<Date> selectedDates;
    public static ArrayList<String> fromTime;
    public static ArrayList<String> endTime;
    int counter = 0;

    public LogsAdapter(Context context, ArrayList<LogsPOJO> rowItems) {
        super();
        this.context = context;
        this.rowItems = rowItems;
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
            convertView = mInflater.inflate(R.layout.logs_row_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.email = (TextView) convertView.findViewById(R.id.email);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.from = (TextView) convertView.findViewById(R.id.from);
            holder.fromTime = (TextView) convertView.findViewById(R.id.fromTime);

            holder.to = (TextView) convertView.findViewById(R.id.to);
            holder.toTime = (TextView) convertView.findViewById(R.id.toTime);
            holder.hallName = (TextView) convertView
                    .findViewById(R.id.hallName);
            holder.linLayout = (LinearLayout) convertView
                    .findViewById(R.id.linLayout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final LogsPOJO rowItem = (LogsPOJO) getItem(position);

        holder.name.setText("" + rowItem.getName());
        holder.email.setText("" + rowItem.getEmail());
        holder.phone.setText("" + rowItem.getPhone());
        holder.from.setText("" + new SimpleDateFormat("dd-MMM-yyyy").format(rowItem.getFrom()).toString());
        holder.to.setText("" + new SimpleDateFormat("dd-MMM-yyyy").format(rowItem.getTo()).toString());
        holder.fromTime.setText("" + rowItem.getStartTime());
        holder.toTime.setText("" + rowItem.getEndTime());
        holder.hallName.setText("" + rowItem.getHallName());

        holder.linLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showDialog(rowItem.getName(), rowItem.getEmail(), rowItem.getPhone(), rowItem.getFrom().toString(), rowItem.getTo().toString(), rowItem.getHallName(), rowItem.getObjectId(), rowItem.getEnquiryToken());
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView email;
        TextView phone;
        TextView from;
        TextView fromTime;
        TextView to;
        TextView toTime;
        TextView hallName;
        LinearLayout linLayout;
    }

    public void showDialog(final String Name, final String Email, final String Phone, final String fromDate, final String toDate, final String hallName, final String objectId, final String enquiryToken) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.action_dialog);
        final RadioGroup group = (RadioGroup) dialog
                .findViewById(R.id.radioAction);
        final EditText resonText = (EditText) dialog
                .findViewById(R.id.resonText);
        Button okButton = (Button) dialog.findViewById(R.id.okButton);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // get selected radio button from radioGroup
                if (resonText.getText().toString().length() > 0) {
                    final int selectedId = group.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    final RadioButton radioActionButton = (RadioButton) dialog
                            .findViewById(selectedId);
                    String textApprove = radioActionButton.getText().toString()
                            .trim();
                    Log.e("dialog", "" + radioActionButton.getText());

                    Log.e("dialogtext", textApprove);
                    String textsample = "????";


                    if (textApprove.equalsIgnoreCase(context.getResources().getString(R.string.accept))) {
                        getDataFromQueryDetails(textApprove, resonText.getText()
                                .toString(), Name, Email, Phone, fromDate, toDate, hallName, objectId, enquiryToken);


                    } else if (textApprove.equalsIgnoreCase(context.getResources().getString(R.string.reject))) {
                        Toast.makeText(context, "Request Rejected",
                                Toast.LENGTH_SHORT).show();
                        deleteFromSummaryScreen(objectId, enquiryToken,hallName);

                    }

                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Please enter comments",
                            Toast.LENGTH_SHORT).show();

//
                }

            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void setData(final String approve,
                        final String extraComments, final String Name, final String Email, final String Phone, final String fromDate, final String toDate, final String hallName, final String objectId, final String enquiryToken) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait! Saving data");
        dialog.setCancelable(false);
        dialog.show();

        for (int i = 0; i < selectedDates.size(); i++) {
            ParseObject object = new ParseObject("AdminConfirmations");
            object.put("isApproved", approve);
            object.put("Name", Name);
            object.put("Email", Email);
            object.put("Phone", Phone);
            object.put("BookedDates", selectedDates.get(i));
            object.put("FromTime", fromTime.get(i));
            object.put("ToTime", endTime.get(i));
            object.put("HallName", hallName);
            object.put("ExtraComments", extraComments);
            counter = i;
            Log.e("setData", "i " + i);
            object.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException arg0) {
                    // TODO Auto-generated method stub
//                    dialog.dismiss();


//                    Toast.makeText(context, "Request Updating...",
//                            Toast.LENGTH_SHORT).show();


                }
            });
        }
        if (counter == selectedDates.size() - 1) {
            dialog.dismiss();
            deleteFromSummaryScreen(objectId, enquiryToken, hallName);
        }

    }

    public void deleteFromSummaryScreen(final String objectId, final String enquiryToken, final String hallName) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait! Saving data");
        dialog.setCancelable(false);
        dialog.show();
        Log.e("deleteFromSummaryScreen", "objectId " + enquiryToken);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserQuerySummary");
        query.getInBackground(enquiryToken, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    parseObject.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
//                                Toast.makeText(context, "Request Updated",
//                                        Toast.LENGTH_SHORT).show();
                                deleteFromDetailsScreen(objectId, hallName);
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(context, "Some Error occured while updating your request",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(context, "Some Error occured while updating your request",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    public void getDataFromQueryDetails(final String approve,
                                        final String extraComments, final String Name, final String Email, final String Phone, final String fromDate, final String toDate, final String hallName, final String objectId, final String enquiryToken) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait! Saving data");
        dialog.setCancelable(false);
        dialog.show();
        selectedDates = new ArrayList<Date>();
        fromTime = new ArrayList<String>();
        endTime = new ArrayList<String>();
        Log.e("getDataFromQueryDetails", "called" + objectId);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserQueryDetails");
        query.whereEqualTo("EnquiryToken", objectId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            ParseObject object = list.get(i);
                            selectedDates.add(object.getDate("BookedDates"));
                            fromTime.add(object.getString("StartTime"));
                            endTime.add(object.getString("EndTime"));
                            Log.e("selectedDates", "" + selectedDates.get(i).toString());
                            Log.e("selectedDates", list.size() + "     " + selectedDates.size());

                            if (list.size() == selectedDates.size()) {
                                setData(approve, extraComments, Name, Email, Phone, fromDate, toDate, hallName, objectId, enquiryToken);
                                dialog.dismiss();
                            }
                        }


                    } else {
                        dialog.dismiss();
                    }


                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void deleteFromDetailsScreen(final String enquiryToken, final String hallName) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait! Saving data");
        dialog.setCancelable(false);
        dialog.show();
        counter = 0;
        Log.e("deleteFromDetailsScreen", "called ");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserQueryDetails");
        query.whereEqualTo("EnquiryToken", enquiryToken);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        ParseObject object = list.get(i);
                        object.deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                            }
                        });
                        counter = i;

                    }

                    Intent i = new Intent(context, ShowLogs.class);
                    i.putExtra("HallName", hallName);
                    context.startActivity(i);
                    ((Activity) context).finish();
                    dialog.dismiss();

                } else {
                    dialog.dismiss();
                    Toast.makeText(context, "Some Error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

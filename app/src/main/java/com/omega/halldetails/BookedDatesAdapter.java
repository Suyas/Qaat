package com.omega.halldetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omega.weddingapp.main.R;
import com.omega.wedingapp.map.ContactUs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Dell on 05-10-2015.
 */
public class BookedDatesAdapter extends BaseAdapter {
    ArrayList<BookedDatesPOJO> items;
    Context context;
    String hallName;
    String adminEmail,add;


    public BookedDatesAdapter(ArrayList<BookedDatesPOJO> items, Context context, String hallName,String adminEmail) {
        this.items = items;
        this.context = context;
        this.hallName = hallName;
        this.adminEmail = adminEmail;
    }



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;


        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.booked_dates_row_items, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.bookedDate);
            holder.fromTime = (TextView) convertView.findViewById(R.id.fromTime);
            holder.toTime = (TextView) convertView.findViewById(R.id.toTime);
            holder.bookAnyWay = (TextView) convertView.findViewById(R.id.bookAnyWay);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BookedDatesPOJO rowItem = (BookedDatesPOJO) getItem(position);
        holder.date.setText("Date :" + new SimpleDateFormat("dd-MMM-yyyy").format(rowItem.getBookedDate()).toString());
        holder.fromTime.setText("From : " + rowItem.getFromTime());
        holder.toTime.setText("To :" + rowItem.getToTime());
        holder.bookAnyWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactUs.class);
                intent.putExtra("hallname", hallName);
                intent.putExtra("adminEmail", adminEmail);
                context.startActivity(intent);

            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView date, fromTime, toTime, bookAnyWay;
    }

}

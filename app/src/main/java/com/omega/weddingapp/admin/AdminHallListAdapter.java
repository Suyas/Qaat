package com.omega.weddingapp.admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omega.weddingapp.main.R;

import java.util.ArrayList;

/**
 * Created by Dell on 29-01-2016.
 */

public class AdminHallListAdapter extends BaseAdapter {

    ArrayList<String> rowItems;
    Context context;

    public AdminHallListAdapter(ArrayList<String> rowItems, Context context) {
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
            convertView = mInflater.inflate(R.layout.admin_hall_list_row_item, null);
            holder = new ViewHolder();
            holder.hallName = (TextView) convertView.findViewById(R.id.hallNameAdmin);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.hallName.setText(rowItems.get(position));

        return convertView;
    }


    public class ViewHolder {
        TextView hallName;
    }

}

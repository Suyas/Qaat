package com.omega.weddingapp.main;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class LazyAdapter extends ArrayAdapter<RowItem> {

    Context context;
    Typeface tf = null;

    public LazyAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
        tf = Typeface.createFromAsset(context
                .getAssets(), "AraJozoor-Regular.ttf");

    }

    public class ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        LinearLayout card, linlayout;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.card = (LinearLayout) convertView.findViewById(R.id.card);
            holder.linlayout = (LinearLayout) convertView.findViewById(R.id.linout);
            holder.image = (ImageView) convertView.findViewById(R.id.list_image);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.description = (TextView) convertView.findViewById(R.id.description);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.linlayout.setVisibility((position == 11) ? View.INVISIBLE : View.VISIBLE);

        holder.image.setImageResource(rowItem.getImageId());
        holder.title.setText(rowItem.getTitle());
        holder.description.setText(rowItem.getDesc());

        holder.title.setTypeface(tf);
        holder.description.setTypeface(tf);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_animation);
        holder.card.startAnimation(animation);


        return convertView;
    }
}

package com.omega.weddingapp.halls;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omega.weddingapp.main.R;

import java.util.List;

public class HallLazyAdapter extends ArrayAdapter<HallRowItem> {

    Context context;
    Typeface tf = null;

    public HallLazyAdapter(Context context, int resourceId,
                           List<HallRowItem> items) {
        super(context, resourceId, items);
        this.context = context;
        tf = Typeface.createFromAsset(context.getAssets(),
                "AraJozoor-Regular.ttf");

    }

    public class ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        LinearLayout card;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        HallRowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.hall_list_layout, null);
            holder = new ViewHolder();
            holder.card = (LinearLayout) convertView.findViewById(R.id.card);
            // holder.image =
            // (ImageView)convertView.findViewById(R.id.list_image);
            holder.title = (TextView) convertView.findViewById(R.id.hall_name);
            // holder.description =
            // (TextView)convertView.findViewById(R.id.description);
            //
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        // TranslateAnimation animation = null;
        // if (position &gt; mLastPosition) {
        // animation = new TranslateAnimation(
        // Animation.RELATIVE_TO_SELF,
        // 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
        // Animation.RELATIVE_TO_SELF, 1.0f,
        // Animation.RELATIVE_TO_SELF, 0.0f);
        //
        // animation.setDuration(600);
        // convertView.startAnimation(animation);
        // mLastPosition = position;
        // }

        //
        // holder.image.setImageResource(rowItem.getImageId());
        holder.title.setText(rowItem.getTitle());
        // holder.description.setText(rowItem.getDesc());
        //
        holder.title.setTypeface(tf);
        // holder.description.setTypeface(tf);
        //

        Animation animationY = new TranslateAnimation(0, 0, 800, 0);
        animationY.setDuration(600);
        convertView.startAnimation(animationY);
        animationY = null;

        //
        // Animation animationY = new TranslateAnimation(0, 0,0 ,0);
        // animationY.setDuration(100);
        // convertView.startAnimation(animationY);
        // animationY = null;

        return convertView;
    }
}

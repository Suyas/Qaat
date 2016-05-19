package com.omega.weddingapp.city;

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

import com.omega.weddingapp.main.R;

import java.util.List;

public class CityLazyAdapter extends ArrayAdapter<CityRowItem> {

    Context context;
    Typeface tf = null;

    public CityLazyAdapter(Context context, int resourceId, List<CityRowItem> items) {
        super(context, resourceId, items);
        this.context = context;
        tf = Typeface.createFromAsset(context
                .getAssets(), "AraJozoor-Regular.ttf");

    }

    public class ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        LinearLayout card;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CityRowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_card, null);
            holder = new ViewHolder();
            holder.card = (LinearLayout) convertView.findViewById(R.id.card);
//            holder.image = (ImageView)convertView.findViewById(R.id.list_image);
            holder.title = (TextView) convertView.findViewById(R.id.city_name);
//            holder.description = (TextView)convertView.findViewById(R.id.description);
//            
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
//
//        holder.image.setImageResource(rowItem.getImageId());
        holder.title.setText(rowItem.getTitle());
//        holder.description.setText(rowItem.getDesc());
//        
        holder.title.setTypeface(tf);
//      holder.description.setTypeface(tf);
//        

        Animation anim;
        if (position % 2 == 0) {
            anim = AnimationUtils.loadAnimation(getContext(), R.anim.card_a);
        } else {
            anim = AnimationUtils.loadAnimation(getContext(), R.anim.card_b);
        }

        convertView.startAnimation(anim);


//
//		Animation animationY = new TranslateAnimation(0, 0,800 ,0);
//		animationY.setDuration(600);
//		convertView.startAnimation(animationY);  
//		animationY = null; 
//
//        

//		Animation animationY = new TranslateAnimation(0, 0, holder.card.getHeight(), 0);


//        
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.card_animation);
//        holder.card.startAnimation(animation);
//        
//        
        return convertView;
    }
}

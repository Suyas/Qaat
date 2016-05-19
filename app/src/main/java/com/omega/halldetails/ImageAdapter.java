package com.omega.halldetails;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseFile;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    Context context;
    int count = 10000;// for repeating images (circular)
    int[] pageIDsArray = new int[count];

    ArrayList<HallGalleryPogo> hallimages;

    public ImageAdapter(Context context, ViewPager pager,
                        ArrayList<HallGalleryPogo> hallimages) {

        super();
        this.context = context;
        this.hallimages = hallimages;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return hallimages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        final HallGalleryPogo data = hallimages.get(position);


        ParseFile photoFile = data.getPromoImage();

        if (photoFile != null) {
            //Get singleton instance of ImageLoader
            ImageLoader imageLoader = ImageLoader.getInstance();
            //Load the image from the url into the ImageView.
            imageLoader.displayImage(photoFile.getUrl(), imageView);
        }


        //imageView.setImageResource(pageIDsArray[position]);
        ((ViewPager) container).addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

}

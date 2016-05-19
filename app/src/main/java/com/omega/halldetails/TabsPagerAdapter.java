package com.omega.halldetails;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.omega.parse.SingleParallaxScrollView;
import com.omega.weddingapp.main.CustomeProgressDialog;

import java.util.ArrayList;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    ArrayList<String> data = new ArrayList<String>();
    String strhallname, strbookigamt, strcapcity, strcontact, stremail,
            strlocation, strdesc;
    ArrayList<HallGalleryPogo> images = null;

    Context mcontext;
    CustomeProgressDialog mProgressDialog;

    Double strlat, strlong;

    public TabsPagerAdapter(FragmentManager fm, String hallname,
                            String strbookigamt, String strcapcity, String strcontact,
                            String stremail, String strlocation, String strdesc, Double lat,
                            Double longt, ArrayList<HallGalleryPogo> images, Context a) {
        super(fm);
        this.strhallname = hallname;
        this.strbookigamt = strbookigamt;
        this.strcapcity = strcapcity;
        this.strcontact = strcontact;
        this.stremail = stremail;
        this.strlocation = strlocation;
        this.strdesc = strdesc;
        this.strlat = lat;
        this.strlong = longt;
        this.images = images;
        this.mcontext = a;
        Log.e("Email 1", "" + stremail);
        notifyDataSetChanged();
    }

    Fragment fragment ;

    @Override
    public Fragment getItem(int index) {
        fragment = new Fragment();
        switch (index) {
            case 0:

                fragment = new SingleParallaxScrollView(strhallname, strbookigamt,
                        strcapcity, strcontact, stremail, strlocation, strdesc, images, strlat, strlong);

//			fragment = new DescriptionOfHallFragment(strhallname, strbookigamt,
//					strcapcity, strcontact, stremail, strlocation, strdesc);
                break;
            case 1:


                fragment = new HallGalleryFragment(strhallname, images);
                break;
            case 2:
                Log.e("Email 3", "" + stremail);
                fragment = new BookHall(strhallname, stremail);
//                fragment = new BookingFragment(strhallname, stremail);
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }


}

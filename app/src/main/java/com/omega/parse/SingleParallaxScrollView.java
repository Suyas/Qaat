package com.omega.parse;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.omega.halldetails.HallGalleryPogo;
import com.omega.weddingapp.main.CustomeProgressDialog;
import com.omega.weddingapp.main.R;
import com.parse.ParseFile;

import java.util.ArrayList;

public class SingleParallaxScrollView extends Fragment {

    Typeface mFont;
    ViewPager gallery;
    TextView location, capacity, bookingamt, contactNo, desc, hall_name, email;
    Button navigateButton;
    CustomeProgressDialog mProgressDialog;
    View rootView;
    String strhallname, strbookigamt, strcapcity, strcontact, stremail,
            strlocation, strdesc;
    ArrayList<HallGalleryPogo> images = null;
    String text;
    ImageView imageView;
    ArrayList<String> data = new ArrayList<String>();
    HallGalleryPogo imagepogo;
    Double strlat, strlong;

    public SingleParallaxScrollView(String hallname, String strbookigamt,
                                    String strcapcity, String strcontact, String stremail,
                                    String strlocation, String strdesc,
                                    ArrayList<HallGalleryPogo> images, Double strlat, Double strlong) {

        this.strhallname = hallname;

        this.strbookigamt = strbookigamt;
        this.strcapcity = strcapcity;
        this.strcontact = strcontact;
        this.stremail = stremail;
        this.strlocation = strlocation;
        this.strdesc = strdesc;
        this.images = images;
        this.strlat = strlat;
        this.strlong = strlong;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.scroll_one_parallax,
                container, false);

        if (images.size() > 0) {
            imagepogo = images.get(0);
        }

        mFont = Typeface.createFromAsset(getActivity().getAssets(),
                "al-mohanad.ttf");

        imageView = (ImageView) rootView.findViewById(R.id.img1);

        hall_name = (TextView) rootView.findViewById(R.id.hall_name);

        bookingamt = (TextView) rootView.findViewById(R.id.booking_amt);
        contactNo = (TextView) rootView.findViewById(R.id.contact_no);
        email = (TextView) rootView.findViewById(R.id.email);
        capacity = (TextView) rootView.findViewById(R.id.capacity);
        location = (TextView) rootView.findViewById(R.id.location);
        desc = (TextView) rootView.findViewById(R.id.desc);

        navigateButton = (Button) rootView.findViewById(R.id.navigateButton);


        bookingamt.setTypeface(mFont);
        contactNo.setTypeface(mFont);
        email.setTypeface(mFont);
        capacity.setTypeface(mFont);
        location.setTypeface(mFont);
        hall_name.setTypeface(mFont);
        desc.setTypeface(mFont);
        navigateButton.setTypeface(mFont);


        navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + strlat + "," + strlong));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        if (images.size() > 0) {

            ParseFile photoFile = imagepogo.getPromoImage();

            if (photoFile != null) {
                //Get singleton instance of ImageLoader
                ImageLoader imageLoader = ImageLoader.getInstance();
                //Load the image from the url into the ImageView.
                imageLoader.displayImage(photoFile.getUrl(), imageView);
            }
        }

        location.setText("عنوان القاعة " + strlocation);

        email.setText("البريد الالكتروني " + stremail);

        hall_name.setText("" + strhallname);

        capacity.setText("" + "سعة القاعة " + strcapcity);

        contactNo.setText("" + "رقم الهاتف " + strcontact);

        bookingamt.setText("" + "قيمة حجز القاعة بالريال " + strbookigamt);

        text = strdesc;
        desc.setText(strdesc);
        return rootView;

    }


}

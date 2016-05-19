package com.omega.halldetails;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omega.weddingapp.main.CustomeProgressDialog;
import com.omega.weddingapp.main.R;

import java.util.ArrayList;

public class DescriptionOfHallFragment extends Fragment {
    Typeface mFont;
    ViewPager gallery;
    TextView location, capacity, bookingamt, contactNo, desc, hall_name, email;
    CustomeProgressDialog mProgressDialog;
    View rootView;
    String strhallname, strbookigamt, strcapcity, strcontact, stremail,
            strlocation, strdesc;
    /**
     * @param hallname
     */
    String text;

    ArrayList<String> data = new ArrayList<String>();

    public DescriptionOfHallFragment(String hallname, String strbookigamt,
                                     String strcapcity, String strcontact, String stremail,
                                     String strlocation, String strdesc) {

        this.strhallname = hallname;

        this.strbookigamt = strbookigamt;
        this.strcapcity = strcapcity;
        this.strcontact = strcontact;
        this.stremail = stremail;
        this.strlocation = strlocation;
        this.strdesc = strdesc;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.halldetails_layout, container,
                false);

        mFont = Typeface.createFromAsset(getActivity().getAssets(),
                "al-mohanad.ttf");

        hall_name = (TextView) rootView.findViewById(R.id.hall_name);

        bookingamt = (TextView) rootView.findViewById(R.id.booking_amt);
        contactNo = (TextView) rootView.findViewById(R.id.contact_no);
        email = (TextView) rootView.findViewById(R.id.email);
        capacity = (TextView) rootView.findViewById(R.id.capacity);
        location = (TextView) rootView.findViewById(R.id.location);
        desc = (TextView) rootView.findViewById(R.id.desc);

        bookingamt.setTypeface(mFont);
        contactNo.setTypeface(mFont);
        email.setTypeface(mFont);
        capacity.setTypeface(mFont);
        location.setTypeface(mFont);
        hall_name.setTypeface(mFont);
        desc.setTypeface(mFont);

        location.setText("Location: " + strlocation);

        email.setText("Email: " + stremail);


        hall_name.setText("" + strhallname);


        capacity.setText("" + "Capacity: "
                + strcapcity);

        contactNo.setText("" + "Contact: "
                + strcontact);

        bookingamt.setText("" + "Booking Amount: "
                + strbookigamt);

        text = strdesc;


        desc.setText(text + "" + text + "" + text + "" + text + text + "" + text + "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniamLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad miLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad miLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad miLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad mi");


        return rootView;
    }

    // private void getDataFromParse() {
    //
    // mProgressDialog = new CustomeProgressDialog(getActivity());
    // mProgressDialog.showDialog("please Wait", "While We Are Fetching data");
    //
    // ParseQuery<ParseObject> query = ParseQuery.getQuery("hallDetail");
    // query.whereEqualTo("hallName", strhallname);
    //
    // query.getFirstInBackground(new GetCallback<ParseObject>() {
    // public void done(ParseObject object, ParseException e) {
    // if (object == null) {
    //
    // Log.d("Hall", "The getFirst request failed.");
    //
    // } else {
    //
    // location.setText("Location: "+object.getString("location"));
    //
    // email.setText("Email: "+object.getString("email"));
    //
    //
    // hall_name.setText(""+object.getString("hallName"));
    //
    //
    //
    // capacity.setText("" + "Capacity: "
    // + object.getNumber("capicity"));
    //
    // contactNo.setText("" + "Contact: "
    // + object.getNumber("contactNo"));
    //
    // bookingamt.setText("" + "Booking Amount: "
    // + object.getNumber("bookingAmount"));
    //
    // text = object.getString("desc");
    //
    // desc.setText(text);
    //
    // Log.d("Hall", "Retrieved the object.");
    //
    // }
    // mProgressDialog.removeDialog();
    // }
    //
    // });
    //
    // }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // outState.putString("text", text);
        // outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            // String text1 = savedInstanceState.getString("text");

            // desc.setText("param");
        }
    }

}

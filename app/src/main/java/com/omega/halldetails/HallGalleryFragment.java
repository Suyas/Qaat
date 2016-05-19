package com.omega.halldetails;

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

public class HallGalleryFragment extends Fragment {

    ViewPager gallery;
    TextView location, capacity, bookingamt, contactNo, desc;
    CustomeProgressDialog mProgressDialog;
    View rootView;
    ImageAdapter adapter;
    // ImageAdapter adapter;
    ArrayList<HallGalleryPogo> images = null;
    String hallname;
    // public static ArrayList<Integer> img=new ArrayList<Integer>();


    public HallGalleryFragment(String hallname, ArrayList<HallGalleryPogo> images) {


        this.hallname = hallname;
        this.images = images;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.hallgallery_layout, container,
                false);

        //	new getDataFromParse().execute();

        gallery = (ViewPager) rootView
                .findViewById(R.id.gallery_pager);

        adapter = new ImageAdapter(getActivity(), gallery, images);

        gallery.setAdapter(adapter);
        gallery.setCurrentItem(0);


        return rootView;
    }

//	public class getDataFromParse extends AsyncTask<String, Void, String> {
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//
//			// images.clear();
//			// mProgressDialog = new CustomeProgressDialog(getActivity());
//			// mProgressDialog.showDialog("please Wait",
//			// "While We Are Fetching data");
//
//		}
//
//		@Override
//		protected String doInBackground(String... urls) {
//
//			images = new ArrayList<HallGalleryPogo>();
//
//			ParseQuery<ParseObject> query = ParseQuery.getQuery("Hallimages");
//			query.whereEqualTo("HallName", hallname);
//			query.findInBackground(new FindCallback<ParseObject>() {
//
//				@Override
//				public void done(List<ParseObject> objects,
//						com.parse.ParseException arg1) {
//
//					if (arg1 != null) {
//
//						// Log.e("Toatal image are :", " : " + objects.size());
//						return;
//					}
//
//					Log.e("Toatal image are :", " : " + objects.size());
//
//					for (int i = 0; i < objects.size(); i++) {
//
//						ParseObject obj = objects.get(i);
//
//						HallGalleryPogo img = new HallGalleryPogo(obj
//								.getParseFile("Hallimages"));
//
//						// HallPogo t = new HallPogo(obj.getParseFile("img1"));
//						// obj
//						// .getString("description"), obj
//						// .getString("location"), obj.getNumber("capicity"),
//						// obj.getNumber("contactNo"), obj
//						// .getNumber("bookingAmount"), obj
//						// .getString("email"), obj
//						// .getParseGeoPoint("geoL"));
//						// img.add(img1);
//						images.add(img);
//
//					}
//					Log.e(" size of arraylist in parse function:",
//							" " + images.size());
//					gallery = (ViewPager) rootView
//							.findViewById(R.id.gallery_pager);
//					adapter = new ImageAdapter(getActivity(), gallery, images);
//
//					gallery.setAdapter(adapter);
//					gallery.setCurrentItem(0);
//				}
//
//			});
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//
//			// mProgressDialog.removeDialog();
//			//
//			// adapter.notifyDataSetChanged();
//
//		}
//	}

}

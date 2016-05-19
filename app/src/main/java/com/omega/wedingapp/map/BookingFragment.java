package com.omega.wedingapp.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.omega.weddingapp.main.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingFragment extends Fragment {
    public BookingFragment() {
    }

    String bookingbegintime, bookingendtime;

    String msg1 = "القاعة غير التقنى ل هذه التواريخ";
    String msg2 = "قاعات غير التقنى";

    Long a;
    MyBuddyAdapter myadapter;
    ListView hall_list;
    String button_start_time, button_end_time;
    String setstime, setetime;

    public static ArrayList<String> arr_booked_hall_list = new ArrayList<String>();

    private TextView tvmsg, tvhname;
    Button save;
    int beginhour, endhour;
    String begintimeslot = "begintimeslot", endtimeslot = "endtimeslot";
    // String bookigstartdate, bookingenddate;

    String strbeginslot, strendslot;
    Button startdate, enddate, starttime, endtime;

    String start_date, end_date, start_time, end_time;

    private final Calendar mCalendar = Calendar.getInstance();

    private int hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY);

    private int minute = mCalendar.get(Calendar.MINUTE);

    private int day = mCalendar.get(Calendar.DAY_OF_MONTH);

    private int month = mCalendar.get(Calendar.MONTH);

    private int year = mCalendar.get(Calendar.YEAR);

    String hallname, mobno;
    Typeface mFont;


//    public BookingFragment(String hallname, String mobno) {
//
//        this.hallname = hallname;
//        this.mobno = mobno;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.datepicker_layout, container,

                false);

        mFont = Typeface.createFromAsset(getActivity().getAssets(),
                "al-mohanad.ttf");

        //
        // Toast.makeText(getActivity(),
        // "inside Booking fragments " + hallname,
        // Toast.LENGTH_LONG).show();
        //

        hall_list = (ListView) rootView.findViewById(R.id.hall_list);
        tvmsg = (TextView) rootView.findViewById(R.id.msg);
        tvhname = (TextView) rootView.findViewById(R.id.hall_name);

        tvhname.setText(hallname);

        // tvendTime = (TextView) rootView.findViewById(R.id.endtime);
        // tvstartDate = (TextView) rootView.findViewById(R.id.tvDate);
        // tvenddate = (TextView) rootView.findViewById(R.id.enddate_tv);
        // // enddate_tv

        startdate = (Button) rootView.findViewById(R.id.from);
        enddate = (Button) rootView.findViewById(R.id.to);
        save = (Button) rootView.findViewById(R.id.save);

        startdate.setTypeface(mFont);
        enddate.setTypeface(mFont);
        save.setTypeface(mFont);

        // starttime = (Button) rootView.findViewById(R.id.startTime);
        // endtime = (Button) rootView.findViewById(R.id.endtime);

        // resetDate();
        //
        // resetTime();

        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // start_date = tvstartDate.getText().toString().trim();
                // end_date = tvenddate.getText().toString().trim();
                //
                // start_time = tvstartTime.getText().toString().trim();
                // end_time = tvendTime.getText().toString().trim();

                strbeginslot = bookingbegintime;
                // +begintimeslot;

                strendslot = bookingendtime;
                Log.e("save", strbeginslot + "  " + strbeginslot);

                // +endtimeslot;
                //
                // Toast.makeText(getActivity(), "" + strbeginslot,
                // 1000).show();
                //
                // Toast.makeText(getActivity(), "" + strendslot, 1000).show();

                // Log.e("start date", "" + strbeginslot);
                //
                // Log.e("end date", " " + strendslot);

                Log.e("start date", "" + strbeginslot + " " + strendslot);

                //
                // ParseQuery<ParseObject> queryA = ParseQuery
                // .getQuery("HallBooking");
                // queryA.whereGreaterThanOrEqualTo("beginslot", strbeginslot);
                //
                // ParseQuery<ParseObject> queryB = ParseQuery
                // .getQuery("HallBooking");
                // queryB.whereLessThanOrEqualTo("beginslot", strbeginslot);
                //
                // List<ParseQuery<ParseObject>> queries = new
                // ArrayList<ParseQuery<ParseObject>>();
                // queries.add(queryA);
                // queries.add(queryB);
                //
                // ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
                //

                //
                // ParseQuery<ParseObject> queryA = ParseQuery
                // .getQuery("HallBooking");
                //
                // queryA.whereGreaterThanOrEqualTo("beginslot", strbeginslot);
                // queryA.whereLessThanOrEqualTo("beginslot", strendslot);
                // queryA.countInBackground(new CountCallback() {
                // public void done(int count, ParseException e) {
                // if (e == null) {
                // //
                // Toast.makeText(getActivity(),
                // " size is" + count, 1000).show();
                // } else {
                //
                // Toast.makeText(getActivity(),
                // " not found", 1000).show();
                //
                //
                //
                //
                // // The request failed
                // }
                // }
                // });
                //
                //
                //
                //
                ParseQuery<ParseObject> queryA = ParseQuery
                        .getQuery("HallBooking");

                queryA.whereEqualTo("hallid", hallname);
                queryA.whereGreaterThanOrEqualTo("beginslot", strbeginslot);
                queryA.whereLessThanOrEqualTo("beginslot", strendslot);
                queryA.orderByAscending("beginslot");
                queryA.findInBackground(new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> data, ParseException e) {

                        arr_booked_hall_list.clear();

                        tvmsg.setTextColor(Color.parseColor("#FFF380"));

                        if (e == null) {

                            // Toast.makeText(getActivity(),
                            // " size is" + data.size(), 1000).show();

                            if (data.size() > 0) {

                                tvmsg.setText(msg1);

                            } else

                            {

                                showNetworkDialog(hallname);

                                tvmsg.setText(msg2);

                            }

                            for (int i = 0; i < data.size(); i++) {

                                String date = data.get(i)
                                        .getString("beginslot");
                                int slot = (Integer) data.get(i).getNumber(
                                        "Slot");

                                Log.e("data found is", " begin=" + date
                                        + "-end-  " + slot);

                                arr_booked_hall_list.add(date);

                            }

                        } else {

                            Toast.makeText(getActivity(),
                                    " hall is not avilable", Toast.LENGTH_SHORT).show();

                        }

                        adddata();

                    }

                    private void showNetworkDialog(String string) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                                getActivity());
                        // Setting Dialog Title
                        alertDialog.setTitle(string);
                        // Setting Dialog Message
                        alertDialog
                                .setMessage("هل تريد أن تطلب الحجز لهذه القاعة؟");
                        // On pressing Settings button
                        alertDialog.setPositiveButton("نعم",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        Intent myIntent = new Intent(
                                                getActivity(), ContactUs.class);

                                        myIntent.putExtra("hallname", hallname);
                                        myIntent.putExtra("from", setstime);
                                        myIntent.putExtra("to", setetime);
                                        myIntent.putExtra("mobno", mobno);
                                        getActivity().startActivity(myIntent);

                                    }
                                });
                        // on pressing cancel button
                        alertDialog.setNegativeButton("الغاء",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.cancel();
                                    }
                                });
                        // Showing Alert Message
                        alertDialog.show();

                    }

                    private void adddata() {

                        myadapter = new MyBuddyAdapter(getActivity(),
                                arr_booked_hall_list);
                        hall_list.setAdapter(myadapter);
                    }
                });

                //
                //

                // query.getFirstInBackground(new GetCallback<ParseObject>() {
                //
                //
                //
                // @Override
                // public void done(ParseObject object, ParseException e) {
                //
                // if (object == null) {
                //
                // Log.e("hall list", "The getFirst request failed.");
                //
                //
                // } else {
                //
                // Toast.makeText(getActivity(), " hall is avaible",
                // 1000).show();
                //
                // }
                //
                // }
                // });

                // Toast.makeText(
                // getActivity(),
                // " " + start_date + " " + end_date + " " + start_time
                // + " " + end_time, 1000).show();

            }
        });

        // tvstartDate.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // nothing to do, just to let onTouchListener work
        //
        // }
        // });
        //
        // tvstartTime.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // nothing to do, just to let onTouchListener work
        //
        // }
        // });

        // final TimePickerDialog timePickerDialog1 = TimePickerDialog
        // .newInstance(new OnTimeSetListener() {
        //
        // @Override
        // public void onTimeSet(RadialPickerLayout view,
        // int hourOfDay, int minute) {
        //
        // beginhour = hourOfDay;
        //
        // // Toast.makeText(getActivity(), "" +hourOfDay,
        // // 1000).show();
        //
        // if ((hourOfDay >= 0) && (hourOfDay < 7)
        // || (hourOfDay >= 16) && (hourOfDay < 24)) {
        //
        // begintimeslot = "3";
        //
        // }
        //
        // if ((hourOfDay >= 7) && (hourOfDay <= 11)) {
        //
        // begintimeslot = "1";
        //
        // }
        //
        // if ((hourOfDay > 11) && (hourOfDay < 16)) {
        //
        // begintimeslot = "2";
        //
        // }
        //
        // String c = pad3(hourOfDay);
        //
        // // tvstartTime.setText(new StringBuilder()
        // // .append(pad2(hourOfDay)).append(":")
        // // .append(pad(minute)).append(c));
        // // tvstartTime.setTextColor(getResources().getColor(
        // // android.R.color.holo_blue_light));
        // //
        //
        // //
        //
        // strbeginslot = bookingbegintime + begintimeslot;
        //
        // setstime= button_start_time+" "+pad2(hourOfDay)+":"+pad(minute)+""+c;
        // // button_start_time+""+pad2(hourOfDay)+":"+pad2(minute);
        //
        // // button_start_time=button_start_time+""+new StringBuilder()
        // // .append(pad2(hourOfDay)).append(":")
        // // .append(pad(minute)).append(c);
        // startdate.setText(setstime);
        //
        //
        // }
        // }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar
        // .get(Calendar.MINUTE), false);

        // final TimePickerDialog timePickerDialog2 = TimePickerDialog
        // .newInstance(new OnTimeSetListener() {
        //
        // @Override
        // public void onTimeSet(RadialPickerLayout view,
        // int hourOfDay, int minute) {
        //
        // endhour = hourOfDay;
        //
        // // Toast.makeText(getActivity(), "" +hourOfDay,
        // // 1000).show();
        //
        // if ((hourOfDay >= 0) && (hourOfDay < 7)
        // || (hourOfDay >= 16) && (hourOfDay < 24)) {
        //
        // endtimeslot = "3";
        //
        // }
        //
        // if ((hourOfDay >= 7) && (hourOfDay <= 11)) {
        //
        // endtimeslot = "1";
        //
        // }
        //
        // if ((hourOfDay > 11) && (hourOfDay < 16)) {
        //
        // endtimeslot = "2";
        //
        // }
        //
        // Object c = pad3(hourOfDay);
        //
        // // tvendTime.setText(new StringBuilder()
        // // .append(pad2(hourOfDay)).append(":")
        // // .append(pad(minute)).append(c));
        // // tvendTime.setTextColor(getResources().getColor(
        // // android.R.color.holo_blue_light));
        //
        //
        // setetime= button_end_time+" "+pad2(hourOfDay)+":"+pad(minute)+""+c;
        //
        // enddate.setText(setetime);
        //
        // strendslot = bookingendtime + endtimeslot;
        //
        // }
        // }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar
        // .get(Calendar.MINUTE), false);

        // final TimePickerDialog timePickerDialog24h = TimePickerDialog
        // .newInstance(new OnTimeSetListener() {
        //
        // @Override
        // public void onTimeSet(RadialPickerLayout view,
        // int hourOfDay, int minute) {
        //
        // tvDisplayTime.setText(new StringBuilder()
        // .append(pad(hourOfDay)).append(":")
        // .append(pad(minute)));
        //
        // tvDisplayTime.setTextColor(getResources().getColor(
        // android.R.color.holo_blue_light));
        // }
        // }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar
        // .get(Calendar.MINUTE), true);

        // final DatePickerDialog datePickerDialog1 = DatePickerDialog
        // .newInstance(new OnDateSetListener() {
        //
        // public void onDateSet(DatePickerDialog datePickerDialog,
        // int year, int month, int day) {
        //
        // // month += 1;
        //
        // String beginmonth = pad(month + 1);
        //
        // String beginday = pad(day);
        // //
        // // bookigstartdate = year + "" + beginmonth + ""
        // // + beginday;
        //
        // // tvstartDate.setText(new StringBuilder()
        // // .append(pad(day)).append(" ")
        // // .append(pad(month + 1)).append(" ")
        // // .append(pad(year)));
        // // tvstartDate.setTextColor(getResources().getColor(
        // // android.R.color.holo_blue_light));
        // //
        //
        //
        // // button_start_time=new StringBuilder()
        // // .append(pad(day)).append("-")
        // // .append(pad(month + 1)).append("-")
        // // .append(pad(year));
        //
        // button_start_time=pad(day)+"-"+pad(month+1)+"-"+pad(year);
        //
        // bookingbegintime = year + "" + beginmonth + ""
        // + beginday;
        //
        // timePickerDialog1.show(getActivity()
        // .getFragmentManager(), "param");
        //
        // }
        //
        // }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
        // mCalendar.get(Calendar.DAY_OF_MONTH));

        // final DatePickerDialog datePickerDialog2 = DatePickerDialog
        // .newInstance(new OnDateSetListener() {
        //
        // public void onDateSet(DatePickerDialog datePickerDialog,
        // int year, int month, int day) {
        //
        // String endmonth = pad(month + 1);
        //
        // String endday = pad(day);
        //
        // // bookingenddate = year + "" + endmonth + "" + endday;
        //
        // // tvenddate.setText(new
        // // StringBuilder().append(pad(day))
        // // .append(" ").append(pad(month + 1)).append(" ")
        // // .append(pad(year)));
        // // tvenddate.setTextColor(getResources().getColor(
        // // android.R.color.holo_blue_light));
        //
        // bookingendtime = year + "" + endmonth + "" + endday;
        //
        // // enddate.setText(new StringBuilder()
        // // .append(pad(day)).append(" ")
        // // .append(pad(month + 1)).append(" ")
        // // .append(pad(year)));
        // //
        // button_end_time=pad(day)+"-"+pad(month+1)+"-"+pad(year);
        //
        //
        // timePickerDialog2.show(getActivity()
        // .getFragmentManager(), "param");
        //
        // }
        //
        // }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
        // mCalendar.get(Calendar.DAY_OF_MONTH));

        startdate.setOnClickListener(new OnClickListener() {

            private String tag = "param";

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(getActivity(), CalendarView.class);

                startActivityForResult(i, 1987);
                // startActivity(i);

                // datePickerDialog1.show(getActivity().getFragmentManager(),
                // tag);

                Log.e("hello", "tag is=" + tag);

            }
        });

        enddate.setOnClickListener(new OnClickListener() {

            private String tag;

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(getActivity(), CalendarView.class);

                startActivity(i);

                // datePickerDialog2.show(getActivity().getFragmentManager(),
                // tag);

            }
        });

        // starttime.setOnClickListener(new OnClickListener() {
        //
        // private String tag;
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        //
        // timePickerDialog1.show(getActivity().getFragmentManager(), tag);
        //
        // }
        // });
        //
        // endtime.setOnClickListener(new OnClickListener() {
        //
        // private String tag;
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        //
        // timePickerDialog2.show(getActivity().getFragmentManager(), tag);
        //
        // }
        // });

        return rootView;
    }

    // private void resetTime() {
    // tvstartTime.setText(new StringBuilder().append(pad(hourOfDay))
    // .append(":").append(pad(minute)));
    // tvstartTime.setTextColor(getResources().getColor(
    // android.R.color.darker_gray));
    //
    // }
    //
    // private void resetDate() {
    // startdate.setText(new StringBuilder().append(pad(day)).append(" ")
    // .append(pad(month + 1)).append(" ").append(pad(year)));
    // startdate.setTextColor(getResources().getColor(
    // android.R.color.darker_gray));
    //
    // }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private static String pad2(int c) {
        if (c == 12)
            return String.valueOf(c);
        if (c == 00)
            return String.valueOf(c + 12);
        if (c > 12)
            return String.valueOf(c - 12);
        else
            return String.valueOf(c);
    }

    private static String pad3(int c) {

        if (c == 12)
            return " PM";
        if (c == 00)
            return " AM";
        if (c > 12)
            return " PM";
        else
            return " AM";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    public class MyBuddyAdapter extends BaseAdapter {

        public String title[];
        public String description[];
        ArrayList<String> halllist = new ArrayList<String>();
        public Activity context;

        public LayoutInflater inflater;

        @SuppressWarnings("static-access")
        public MyBuddyAdapter(Activity context, ArrayList<String> halllist) {
            super();

            this.context = context;
            this.halllist = halllist;

            this.inflater = (LayoutInflater) context
                    .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return halllist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        class ViewHolder {
            TextView txtName;
            Button btndelete, btnadd;
            LinearLayout row, v;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.hall_list_row, null);

                holder.txtName = (TextView) convertView
                        .findViewById(R.id.hall_name);

                String text = halllist.get(position);

                String year = text.substring(0, 4);

                String month = text.substring(4, 6);
                String day = text.substring(6, 8);

                holder.txtName.setText(day + "-" + month + "-" + year);

                // // .findViewById(R.id.bdelete);
                //
                // // holder.btnadd = (Button)
                // convertView.findViewById(R.id.badd);
                convertView.setTag(holder);
            } else
                holder = (ViewHolder) convertView.getTag();

            return convertView;

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1888) {
            String text = (String) data.getExtras().get("data");
        }

    }
}

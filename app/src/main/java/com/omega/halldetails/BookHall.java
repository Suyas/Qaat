package com.omega.halldetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.omega.weddingapp.main.R;
import com.omega.wedingapp.map.ContactUs;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BookHall extends Fragment {
    private CalendarPickerView calendar;
    Button book;
    public static ArrayList<Date> selectedDates;
    String hallName = "";
    String adminEmail = "";


    public BookHall(String hallName, String adminEmail) {
        this.hallName = hallName;
        this.adminEmail = adminEmail;
        Log.e("Email 4", "" + adminEmail);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_hall, container, false);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) v.findViewById(R.id.qaatCalendar);
        book = (Button) v.findViewById(R.id.book);
        Date today = new Date();
        calendar.init(today, nextYear.getTime())
                .withSelectedDate(today)
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        calendar.highlightDates(getHolidays());
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDates = (ArrayList<Date>) calendar
                        .getSelectedDates();
                returnChangedDate();

                if (selectedDates.size() == 1) {
                    getAvailabilityOne();
                } else {
                    getAvailabilityRange();
                }


//                Toast.makeText(getActivity(), selectedDates.toString(),
//                        Toast.LENGTH_LONG).show();


            }
        });

        return v;
    }

    private ArrayList<Date> getHolidays() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
//        String dateInString = "29-09-2015";
//        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //get current date time with Date()
        Date date = new Date();
        //System.out.println(dateFormat.format(date)); don't print it, but save it!
        String yourDate = dateFormat.format(date);

        try {
            date = dateFormat.parse(yourDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Date> holidays = new ArrayList<>();
        holidays.add(date);
        return holidays;
    }


    public void getAvailabilityRange() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
////        String yourDate = dateFormat.format(selectedDates.get(0).toString());
//
////        Log.e("Start Date", "" + dateFormat.format(selectedDates.get(0).toString()));
////        Log.e("Start Date", "" + selectedDates.get(selectedDates.size() - 1).toString());
//

        ParseQuery<ParseObject> range = ParseQuery.getQuery("AdminConfirmations");

        Log.e("DatesArray", "" + selectedDates.size());
        range.whereGreaterThanOrEqualTo("BookedDates", selectedDates.get(0));
        range.whereLessThanOrEqualTo("BookedDates", selectedDates.get(selectedDates.size() - 1));

        range.whereEqualTo("HallName", hallName);

//        Log.e("Dates Check", selectedDates.get(0) + " from    to" + selectedDates.get(selectedDates.size() - 1));
//        Log.e("Dates Check", "" + hallName);


        range.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        Intent i = new Intent(getActivity(), BookedDatesDetails.class);
                        i.putExtra("fromDate", selectedDates.get(0));
                        i.putExtra("toDate", selectedDates.get(selectedDates.size() - 1));
                        i.putExtra("hallName", hallName);
                        i.putExtra("adminEmail", adminEmail);
                        startActivity(i);
                        Toast.makeText(getActivity(), "Dates not Available", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
//                        bookDates();
                        Intent intent = new Intent(getActivity(), ContactUs.class);
                        intent.putExtra("hallname", hallName);
                        intent.putExtra("adminEmail", adminEmail);
                        startActivity(intent);


                        dialog.dismiss();

                    }
                }
            }
        });

    }


    public void getAvailabilityOne() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
////        String yourDate = dateFormat.format(selectedDates.get(0).toString());
//
////        Log.e("Start Date", "" + dateFormat.format(selectedDates.get(0).toString()));
////        Log.e("Start Date", "" + selectedDates.get(selectedDates.size() - 1).toString());
//

        ParseQuery<ParseObject> range = ParseQuery.getQuery("AdminConfirmations");

        Log.e("DatesArray", "" + selectedDates.size());
//        range.whereGreaterThanOrEqualTo("BookedDates", selectedDates.get(0));
//        range.whereLessThanOrEqualTo("BookedDates", selectedDates.get(selectedDates.size() - 1));

        range.whereEqualTo("BookedDates", selectedDates.get(0));
        range.whereEqualTo("HallName", hallName);

        Log.e("Dates Check", selectedDates.get(0) + "");
        Log.e("Dates Check", "" + hallName);


        range.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    Log.e("Recvd", "" + list.size());

                    if (list.size() > 0) {
                        Intent i = new Intent(getActivity(), BookedDatesDetails.class);
                        i.putExtra("fromDate", selectedDates.get(0));
                        i.putExtra("toDate", selectedDates.get(selectedDates.size() - 1));
                        i.putExtra("hallName", hallName);
                        i.putExtra("adminEmail", adminEmail);
                        startActivity(i);
                        Toast.makeText(getActivity(), "Dates not Available", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
//                        bookDates();
                        Intent intent = new Intent(getActivity(), ContactUs.class);
                        intent.putExtra("hallname", hallName);
                        intent.putExtra("adminEmail", adminEmail);
                        startActivity(intent);


                        dialog.dismiss();

                    }
                }
            }
        });

    }

    public void returnChangedDate() {
        long HOUR = 3600 * 1000;
        int size = selectedDates.size();
        ArrayList<Date> userSelectedDatesForDisplay = new ArrayList<>();

        userSelectedDatesForDisplay.addAll(selectedDates);

        selectedDates.clear();
        for (int i = 0; i < size; i++) {
            selectedDates.add(new Date(userSelectedDatesForDisplay.get(i).getTime() + 6 * HOUR));
        }


    }

}



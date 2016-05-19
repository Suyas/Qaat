package com.omega.weddingapp.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.omega.weddingapp.city.CityListActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<RowItem> rowItems;
Context context;
    String hcount = "";

    static int size;
    private static Integer[] images = {R.drawable.muskat, R.drawable.dhofer,
            R.drawable.musandam, R.drawable.buraimi, R.drawable.dakhiliyah,
            R.drawable.b_n, R.drawable.b_s, R.drawable.sharqiyahn,
            R.drawable.s_south, R.drawable.dhahirah, R.drawable.wusta,
            R.drawable.contactusbg};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.myList);
        TextView bottomText=(TextView)findViewById(R.id.bottom);

        rowItems = new ArrayList<RowItem>();

        String[] titles = {"محافظة مسقط", "محافظة ظفار", "محافظة مسندم",
                "محافظة البريمي", "محافظة الداخلية", "محافظة شمال الباطنة",
                "محافظة جنوب الباطنة", "محافظة شمال الشرقية",
                "محافظة جنوب الشرقية", "محافظة الظاهرة", "محافظة الوسطى",
                ""};






        for (int i = 0; i < 11; i++) {

            if (QaatSplash.arr_hcount.size() > 0) {

                hcount = QaatSplash.arr_hcount.get(i);
            }

            RowItem item = new RowItem(images[i], titles[i],
                    "مجموع القاعات المتاحة " + hcount);
            Log.e("size", "inside= " + hcount);

            rowItems.add(item);
        }

        Log.e("size", "size is= " + QaatSplash.arr_hcount.size());

        RowItem item = new RowItem(images[11], titles[11],
                "مجموع القاعات المتاحة");

        rowItems.add(item);

        // Set the adapter on the ListView
        LazyAdapter adapter = new LazyAdapter(getApplicationContext(),
                R.layout.list_row, rowItems);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Toast.makeText(MainActivity.this,
                // "You have chosen the: " + rowItems.get(position),
                // Toast.LENGTH_LONG).show();
                //

                if (position == 11)

                {

//					new InfoActivity().showDialog(MainActivity.this);


                    //
                    Intent i = new Intent(MainActivity.this,
                            Login.class);

                    startActivity(i);
                    //
                    overridePendingTransition(R.anim.mainfadein,
                            R.anim.splashfadeout);

                } else {

                    Intent i = new Intent(MainActivity.this,CityListActivity.class);
                    i.putExtra("Position", position);
                    i.putExtra("provinence", rowItems.get(position).toString());
                    startActivity(i);



                    overridePendingTransition(R.anim.mainfadein,
                            R.anim.splashfadeout);
                }
                // overridePendingTransition(
                // R.anim.card_animation,R.anim.card_animation);

            }
        });


        bottomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(" التواصل معنا التكرم بالاتصال علي الرقم 99244661  ")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    public class InfoActivity {
        Dialog dialog;

        public Dialog showDialog(Context context) {

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);

            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

            dialog.setContentView(R.layout.activity_information_acctivity);

            dialog.show();
            return dialog;
        }

        public void removeDialog() {
            dialog.dismiss();
        }
    }// end of SelectGalleryImagesInformationDialog

}

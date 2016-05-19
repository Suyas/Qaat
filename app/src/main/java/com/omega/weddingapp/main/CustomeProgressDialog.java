package com.omega.weddingapp.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class CustomeProgressDialog extends Activity {
    Dialog dialog;
    private View view;

    public CustomeProgressDialog(Context context) {

        dialog = new Dialog(context);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setCancelable(true);
//
//		dialog.setCanceledOnTouchOutside(false);
//		
//		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT);

        view = LayoutInflater.from(context).inflate(
                R.layout.custome_dialog_for_progress_bar, null);


        dialog.setContentView(view);


    }

//	public void showDialog(String header, String msg) {
//
//		((TextView) dialog.findViewById(R.id.progress_dialog_header))
//				.setText("" + header);
//		((TextView) dialog.findViewById(R.id.progress_dialog_msg)).setText(""
//				+ msg);
//
//		dialog.show();
//	}

    public void removeDialog() {
        dialog.dismiss();
    }
}

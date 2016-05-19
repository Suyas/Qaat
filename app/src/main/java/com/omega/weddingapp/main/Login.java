package com.omega.weddingapp.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.omega.weddingapp.admin.TaskList;
import com.omega.weddingapp.mail.NetworkUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Login extends Activity {

    Button signup, login;

    EditText username, password;
    String userName, passWord;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
NetworkUtils isNetAvailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        preferences = getSharedPreferences("LoginPreference", 0);
        editor = preferences.edit();


        isNetAvailable=new NetworkUtils();


        signup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                Login.this.finish();

            }
        });
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (isValid()) {

                    if (isNetAvailable.isNetworkConnectionOn(Login.this)){login();}else{
                        Toast.makeText(Login.this,R.string.internet_not_available,Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    public boolean isValid() {

        userName = username.getText().toString().trim();
        passWord = password.getText().toString().trim();

        if (userName.length() <= 0) {
            Toast.makeText(Login.this, "Please enter username",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() <= 0) {
            Toast.makeText(Login.this, "Please enter password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    public void login() {
        final ProgressDialog dialog = new ProgressDialog(Login.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loging in...");
        dialog.show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("HallOwner");
        query.whereEqualTo("Email", userName);
        query.whereEqualTo("Password", passWord);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> user, ParseException e) {
                // TODO Auto-generated method stub
                if (e == null) {
                    Log.e("users", "" + user.size());
                    if (user.size() > 0) {
                        editor.putString("AdminName", user.get(0).getString("FullName"));
                        editor.putString("AdminEmail", user.get(0).getString("Email"));
                        editor.putString("AdminPhone", user.get(0).getString("Phone"));
                        editor.putString("FullName", userName);
                        editor.commit();

                        dialog.dismiss();
                        Intent i = new Intent(Login.this, TaskList.class);
                        startActivity(i);
                        Login.this.finish();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Login.this, "User not found",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(Login.this, "Error finding your details",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}

package com.omega.weddingapp.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.omega.weddingapp.mail.NetworkUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends Activity {

    EditText signupUsername, signupEmailId, signupPhone, signupAddress,
            signupPassword, signupConfirmPassword;

    String userName, emailId, phone, address, password, confirmPassword;

    Button signup;
    NetworkUtils isNetAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        isNetAvailable=new NetworkUtils();
        signupUsername = (EditText) findViewById(R.id.signupUsername);
        signupEmailId = (EditText) findViewById(R.id.signupEmailId);
        signupPhone = (EditText) findViewById(R.id.signupPhone);
        signupAddress = (EditText) findViewById(R.id.signupAddress);
        signupPassword = (EditText) findViewById(R.id.signupPassword);
        signupConfirmPassword = (EditText) findViewById(R.id.signupConfirmPassword);

        signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (isValid()) {

                    if (isNetAvailable.isNetworkConnectionOn(SignUp.this)){
                        signUpUser();
                    }
                    else{
                        Toast.makeText(SignUp.this,R.string.internet_not_available,Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });

    }

    public boolean isValid() {

        userName = signupUsername.getText().toString().trim();
        emailId = signupEmailId.getText().toString().trim();
        phone = signupPhone.getText().toString().trim();
        address = signupAddress.getText().toString().trim();
        password = signupPassword.getText().toString().trim();
        confirmPassword = signupConfirmPassword.getText().toString().trim();

        if (userName.length() <= 0) {
            Toast.makeText(SignUp.this, "Please enter Username",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (emailId.length() <= 0) {
            Toast.makeText(SignUp.this, "Please enter Email Id",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.length() <= 0) {
            Toast.makeText(SignUp.this, "Please enter Phone Number",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address.length() <= 0) {
            Toast.makeText(SignUp.this, "Please enter Address",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() <= 0) {
            Toast.makeText(SignUp.this, "Please enter Password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirmPassword.length() <= 0) {
            Toast.makeText(SignUp.this, "Please enter Confirm Password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUp.this, "Password Mismatch", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;

    }

    public void signUpUser() {
        final ProgressDialog dialog = new ProgressDialog(SignUp.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait! Signing you up");
        dialog.show();

        ParseObject newUser = new ParseObject("HallOwner");
        newUser.put("FullName", userName);
        newUser.put("Email", emailId);
        newUser.put("Password", password);
        newUser.put("Phone", phone);
        newUser.put("address", address);
        newUser.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(SignUp.this, "Success!", Toast.LENGTH_SHORT)
                        .show();
                dialog.dismiss();
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
                SignUp.this.finish();
            }
        });

    }

}

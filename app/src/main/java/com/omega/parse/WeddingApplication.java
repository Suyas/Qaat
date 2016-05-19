package com.omega.parse;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.omega.weddingapp.main.R;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class WeddingApplication extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        this.getApplicationContext();

        Parse.initialize(this, "InDExrssR8d5us2gnZL4tzhUA3E3fNKBm0t4nJhN", "CpClDeQ1wdz9bP6Y0e0yDpsjPo9rJD27JVHjqEDM");


        //	PushService.setDefaultPushCallback(this, ProductActivity.class);

        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);


        /** get configured email **/
        Account[] accounts = AccountManager.get(this).getAccountsByType(
                "com.google");
        //String myEmailid = accounts[0].name.toString();

        ParseInstallation installation = ParseInstallation
                .getCurrentInstallation();
        //installation.put("UserEmail", myEmailid);
        installation.saveInBackground();


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img2)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();

        //Create a config with those options.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);


    }

}

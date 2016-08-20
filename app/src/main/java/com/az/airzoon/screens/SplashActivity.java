package com.az.airzoon.screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.az.airzoon.R;
import com.az.airzoon.preferences.PrefManager;

import java.util.Locale;

/**
 * Created by siddharth on 7/25/2016.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        initViews();
        registerEvents();
        loadFrence();
        //posting some delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                decideToLaunchNextScreen();
            }
        }, 1200);
    }

    public void loadFrence() {
        Locale myLocale = new Locale("fr");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private void decideToLaunchNextScreen() {
        finish();
        PrefManager prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeAlreadyLaunched()) {
            prefManager.setFirstTimeLaunch(true);
            Intent i = new Intent(this, TutorialActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(this, AirZoonMapActivity.class);
            startActivity(i);
        }


    }

    private void initViews() {
    }

    private void registerEvents() {

    }

//    public void testMutipart() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    MultipartUtility multipart = new MultipartUtility("http://airzoonapp.com/feedback.php", "UTF-8");
//                    // In your case you are not adding form data so ignore this
//                /*This is to add parameter values */
//                    multipart.addFormField("feedback", "plz fix");
//
//                    multipart.addFormField("email", "sid@testmail.com");
//
//
//                    List<String> response = multipart.finish();
//
//                    for (String line : response) {
//                        System.out.println("line>>" + line);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//
//    }


}


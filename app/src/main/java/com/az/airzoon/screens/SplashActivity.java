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
//        loadFrence();
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


}


package com.az.airzoon.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.az.airzoon.R;
import com.az.airzoon.preferences.PrefManager;

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

        //posting some delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                decideToLaunchNextScreen();
            }
        }, 1000);
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


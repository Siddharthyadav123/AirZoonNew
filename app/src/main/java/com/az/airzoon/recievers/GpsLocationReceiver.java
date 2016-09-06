package com.az.airzoon.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.az.airzoon.application.MyApplication;

/**
 * Created by siddharth on 9/6/2016.
 */
public class GpsLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                System.out.println(">>sid GPS broadcast");

                MyApplication.getInstance().getLocationModel().initialize();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //fetching location
                        MyApplication.getInstance().getLocationModel().initialize();
                        System.out.println(">>half sec");
                    }
                }, 500);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

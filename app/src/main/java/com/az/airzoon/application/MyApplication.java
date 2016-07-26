package com.az.airzoon.application;

import android.app.Application;

import com.az.airzoon.models.AirZoonModel;

/**
 * Created by siddharth on 7/26/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //loading static shops list intially
        AirZoonModel.getInstance().loadStaticShows();
    }
}

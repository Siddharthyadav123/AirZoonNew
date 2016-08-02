package com.az.airzoon.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.az.airzoon.models.AirZoonModel;

import java.util.Calendar;

/**
 * Created by siddharth on 7/26/2016.
 */
public class MyApplication extends Application {
    public static MyApplication myApplication = null;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        MultiDex.install(this);
        //loading static shops list intially
        AirZoonModel.getInstance().loadStaticHotSpots();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public String getCurrentDate() {
        String currentDate = "";
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);//calender year starts from 1900 so you must add 1900 to the value recevie.i.e., 1990+112 = 2012
        int cmonth = c.get(Calendar.MONTH);//this is april so you will receive  3 instead of 4.
        int cday = c.get(Calendar.DAY_OF_MONTH);

        switch (cmonth + 1) {
            case 1:
                currentDate = "Jan";
                break;
            case 2:
                currentDate = "Feb";
                break;
            case 3:
                currentDate = "Mar";
                break;
            case 4:
                currentDate = "Apr";
                break;
            case 5:
                currentDate = "May";
                break;
            case 6:
                currentDate = "Jun";
                break;
            case 7:
                currentDate = "Jul";
                break;
            case 8:
                currentDate = "Aug";
                break;
            case 9:
                currentDate = "Sep";
                break;
            case 10:
                currentDate = "Oct";
                break;
            case 11:
                currentDate = "Nov";
                break;
            case 12:
                currentDate = "Dec";
                break;
        }

        return "last sync: " + currentDate + ", " + cday + "/" + cyear;
    }
}

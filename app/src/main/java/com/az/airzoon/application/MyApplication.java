package com.az.airzoon.application;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.az.airzoon.R;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.database.AirZoonDB;
import com.az.airzoon.dataobjects.UserProfileDO;
import com.az.airzoon.gps.LocationModel;
import com.az.airzoon.listeners.LatLongFound;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.models.FontModel;
import com.az.airzoon.preferences.PrefManager;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;
import com.az.airzoon.volly.LruBitmapCache;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Calendar;

/**
 * Created by siddharth on 7/26/2016.
 */
public class MyApplication extends Application implements APICallback {
    public static MyApplication myApplication = null;

    public static final String TAG = MyApplication.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public UserProfileDO userProfileDO;
    public AirZoonDB airZoonDB;

    public LocationModel locationModel;
    public LatLongFound latLongFound;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        MultiDex.install(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        //load fonts
        FontModel.getInstance().loadFonts(this);

        //load user profile
        userProfileDO = new UserProfileDO(this);
        userProfileDO.loadProfile();

        //load db
        airZoonDB = new AirZoonDB(this);

        //location model
        locationModel = new LocationModel(this);

        //loading static shops list intially
        AirZoonModel.getInstance().loadAndParseHotSpot(null, airZoonDB);


        //requesting server for latest spots
        PrefManager prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeAlreadyLaunched()) {
//            System.out.println(">>sid>> going for sync");
            APIHandler apiHandler = new APIHandler(this, this, RequestConstant.REQUEST_GET_HOTSPOT_LIST,
                    Request.Method.GET, URLConstants.URL_GET_HOTSPOT_LIST, false, null, null, null, null);
            apiHandler.setShowToastOnRespone(false);
            apiHandler.requestAPI();
        }
    }

    public void enableGPS(final Activity activity) {
        float sourceLat = MyApplication.getInstance().getLocationModel().getLatitude();
        float sourceLong = MyApplication.getInstance().getLocationModel().getLongitude();
        if (sourceLat == 0.0 && sourceLong == 0.0) {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(getResources().getString(R.string.enableGPSText));  // GPS not found
                builder.setMessage(getResources().getString(R.string.enableGPSBody)); // Want to enable?
                builder.setPositiveButton(getResources().getString(R.string.settingsGPSText), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancelText), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, int tag) {
        // set the default tag if tag is empty
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(int tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
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

    public String getCurrentDate(Context context) {
        String currentDate = "";
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);//calender year starts from 1900 so you must add 1900 to the value recevie.i.e., 1990+112 = 2012
        int cmonth = c.get(Calendar.MONTH);//this is april so you will receive  3 instead of 4.
        int cday = c.get(Calendar.DAY_OF_MONTH);

        switch (cmonth + 1) {
            case 1:
                currentDate = context.getResources().getString(R.string.janText);
                break;
            case 2:
                currentDate = context.getResources().getString(R.string.fabText);
                break;
            case 3:
                currentDate = context.getResources().getString(R.string.marText);
                break;
            case 4:
                currentDate = context.getResources().getString(R.string.aprText);
                break;
            case 5:
                currentDate = context.getResources().getString(R.string.mayText);
                break;
            case 6:
                currentDate = context.getResources().getString(R.string.juneText);
                break;
            case 7:
                currentDate = context.getResources().getString(R.string.julyText);
                break;
            case 8:
                currentDate = context.getResources().getString(R.string.augText);
                break;
            case 9:
                currentDate = context.getResources().getString(R.string.sepText);
                break;
            case 10:
                currentDate = context.getResources().getString(R.string.octText);
                break;
            case 11:
                currentDate = context.getResources().getString(R.string.novText);
                break;
            case 12:
                currentDate = context.getResources().getString(R.string.decText);
                break;
        }

        return currentDate + ", " + cday + "/" + cyear;
    }

    public UserProfileDO getUserProfileDO() {
        return userProfileDO;
    }

    public AirZoonDB getAirZoonDB() {
        return airZoonDB;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    /**
     * Checking for all possible internet providers
     **/
    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public boolean checkPermission(final Context context) {
//        int currentAPIVersion = Build.VERSION.SDK_INT;
//        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//                    alertBuilder.setCancelable(true);
//                    alertBuilder.setTitle("Permission necessary");
//                    alertBuilder.setMessage("External storage permission is necessary");
//                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                        }
//                    });
//                    AlertDialog alert = alertBuilder.create();
//                    alert.show();
//                } else {
//                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                }
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
//    }


    public boolean checkConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    public void showAleart(Context context, final DailogCallback dailogCallback, String title, String bodyText, String yesBtnText, String noBtnText) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(bodyText);
        builder1.setTitle(title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                yesBtnText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dailogCallback.onDailogYesClick();
                    }
                });

        builder1.setNegativeButton(
                noBtnText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dailogCallback.onDailogNoClick();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        switch (requestId) {
            case RequestConstant.REQUEST_GET_HOTSPOT_LIST:
                AirZoonModel.getInstance().loadAndParseHotSpot(responseString, airZoonDB);
//                System.out.println(">>sid>> sync over");
                break;
        }
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }

    public interface DailogCallback {
        public void onDailogYesClick();

        public void onDailogNoClick();
    }

    public void showNormalDailog(Context context, String bodyText) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(bodyText);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                context.getString(R.string.YesText),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public LatLongFound getLatLongFound() {
        return latLongFound;
    }

    public void setLatLongFound(LatLongFound latLongFound) {
        this.latLongFound = latLongFound;
    }
}


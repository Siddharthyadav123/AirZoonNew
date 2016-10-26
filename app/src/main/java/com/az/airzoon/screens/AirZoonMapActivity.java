package com.az.airzoon.screens;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.dialog_screens.AboutUsDialog;
import com.az.airzoon.dialog_screens.FavoritesDialog;
import com.az.airzoon.dialog_screens.FilterSettingsDialog;
import com.az.airzoon.dialog_screens.HotspotDetailDailog;
import com.az.airzoon.dialog_screens.ProfileDialog;
import com.az.airzoon.dialog_screens.SearchSpotDialog;
import com.az.airzoon.listeners.ImageCallback;
import com.az.airzoon.listeners.LatLongFound;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.preferences.PrefManager;
import com.az.airzoon.recievers.SphericalUtil;
import com.az.airzoon.social_integration.FaceBookModel;
import com.az.airzoon.social_integration.SocialLoginInterface;
import com.az.airzoon.social_integration.TwitterModel;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;
import com.cocosw.bottomsheet.BottomSheet;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.network.HttpRequest;

public class AirZoonMapActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, APICallback {

    private final int SELECT_FILE = 101;
    private final int REQUEST_CAMERA = 102;
    private GoogleMap mMap;
    private ImageView moreImageView;
    private ImageView searchImageView;
    private ImageView filterImageView;

    private ImageView profileImageView;
    private ImageView faviourateImageView;
    private ImageView syncImageView;
    private ImageView aboutUsImageView;


    boolean isOpen = false;
    private AirZoonModel airZoonModel;
    private View snippetView = null;
    private ArrayList<AirZoonDo> airZoonDoArrayList = new ArrayList<>();

    //guide views
    private View guidFullScreenView;
    private ImageView guideArrowImageView;
    private ImageView guideBoxImageView;
    private TextView guideText;

    //for refresging
    private View loadingBlanckBgView;
    private ProgressBar progressBar;

    private TextView lastSyncTextView;

    boolean isMenuAnimInProgress = false;

    private PrefManager prefManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        airZoonModel = AirZoonModel.getInstance();
        prefManager = new PrefManager(this);
        setContentView(R.layout.activity_air_zoon_map);
        setUpMap();
        initViews();
        registerEvents();
        setUI();
        requestTurnGPSOn();

        MyApplication.getInstance().enableGPS(this);


//        getSHAKeyForFaceBook();
    }

    private void checkKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getApplication().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String myHashCode = HttpRequest.Base64.encodeBytes(md.digest());
                Toast.makeText(AirZoonMapActivity.this, ">> hash>> " + myHashCode, Toast.LENGTH_LONG).show();
                System.out.println(">>" + myHashCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestTurnGPSOn() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        AirZoonMapActivity.REQUEST_LOCATION);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void getSHAKeyForFaceBook() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.az.airzoon",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
////                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                System.out.println(">>> Key " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//    }


    private void initViews() {
        moreImageView = (ImageView) findViewById(R.id.moreImageView);
        searchImageView = (ImageView) findViewById(R.id.searchImageView);
        filterImageView = (ImageView) findViewById(R.id.filterImageView);
        profileImageView = (ImageView) findViewById(R.id.profileImageView);
        faviourateImageView = (ImageView) findViewById(R.id.faviourateImageView);
        syncImageView = (ImageView) findViewById(R.id.syncImageView);
        aboutUsImageView = (ImageView) findViewById(R.id.aboutUsImageView);
        guidFullScreenView = findViewById(R.id.guidFullScreenView);
        guideArrowImageView = (ImageView) findViewById(R.id.guideArrowImageView);
        guideBoxImageView = (ImageView) findViewById(R.id.guideBoxImageView);
        guideText = (TextView) findViewById(R.id.guideText);
//        loadingBlanckBgView = findViewById(R.id.loadingBlanckBgView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        lastSyncTextView = (TextView) findViewById(R.id.lastSyncTextView);
    }

    private void registerEvents() {
        moreImageView.setOnClickListener(this);
        searchImageView.setOnClickListener(this);
        filterImageView.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        faviourateImageView.setOnClickListener(this);
        syncImageView.setOnClickListener(this);
        aboutUsImageView.setOnClickListener(this);
        guidFullScreenView.setOnClickListener(this);
    }


    private void setUI() {
        //setting last sync time
        PrefManager prefManager = new PrefManager(this);
        String syncDate = prefManager.getLstSyncTime();
        if (syncDate == null) {
            prefManager.setLastSyncTime(MyApplication.getInstance().getCurrentDate(this));
            lastSyncTextView.setText(getResources().getString(R.string.lastSyncText) + " " + MyApplication.getInstance().getCurrentDate(this));
        } else {
            lastSyncTextView.setText(getResources().getString(R.string.lastSyncText) + " " + syncDate);
        }
        showLastSyncAndScheduleitsHide();
    }

    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        //set camera
        LatLng cameraAngle = new LatLng(15.4389899f, -61.4377411f);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraAngle));
        moveToCurrentLocation(cameraAngle);

        //plot points
        loadAirZoonShops();
        enableMyLocation();
    }

    private void loadAirZoonShops() {
        // Clear previous markers
        mMap.clear();
        airZoonDoArrayList = airZoonModel.getFilteredList();
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {

            try {
//                System.out.println(">>sid adding spot");
                final AirZoonDo airZoonDo = airZoonDoArrayList.get(i);
                LatLng locationLatLong = new LatLng(Double.parseDouble(airZoonDo.getLat()), Double.parseDouble(airZoonDo.getLng()));
                Bitmap markerBitmap = BitmapFactory.decodeResource(getResources(), airZoonModel.getHotSpotMarkerResByType(airZoonDo.getType()));

                mMap.addMarker(new MarkerOptions().position(locationLatLong).icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)).title(i + ""));
                mMap.setInfoWindowAdapter(new CustomInfoAdapter());
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        onMarkerInfoWindowClick(marker);
                    }
                });
                //sid
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void onMarkerInfoWindowClick(Marker marker) {
        int index = Integer.parseInt(marker.getTitle());
        AirZoonDo airZoonDo = airZoonDoArrayList.get(index);
        HotspotDetailDailog hotspotDetailDailog = new HotspotDetailDailog(this, airZoonDo);
        hotspotDetailDailog.showDialog(HotspotDetailDailog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }


    private View getSnippedView() {
        if (snippetView == null) {
            snippetView = AirZoonMapActivity.this.getLayoutInflater().inflate(R.layout.marker_snippet_view, null);
        }
        return snippetView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        switch (requestId) {
            case RequestConstant.REQUEST_GET_HOTSPOT_LIST:
                refreshAirZoonMapAsPerFileteration(responseString);
//                Toast.makeText(AirZoonMapActivity.this, "responseString>>" + responseString, Toast.LENGTH_SHORT).show();
                MyApplication.getInstance().showNormalDailog(this, getString(R.string.syncedSuccessFulText));

                //setting last sync time
                lastSyncTextView.setText(getResources().getString(R.string.lastSyncText) + " " + prefManager.getLstSyncTime());
                prefManager.setLastSyncTime(MyApplication.getInstance().getCurrentDate(this));
                showLastSyncAndScheduleitsHide();
                break;
        }
    }

    Handler syncHandler = null;

    public void showLastSyncAndScheduleitsHide() {
        if (lastSyncTextView.getVisibility() == View.GONE) {
            lastSyncTextView.setVisibility(View.VISIBLE);
            Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            lastSyncTextView.startAnimation(fadeIn);
        }
        if (syncHandler == null) {
            syncHandler = new Handler();
        }
        syncHandler.removeCallbacks(syncRunnable);
        syncHandler.postDelayed(syncRunnable, 60000);
    }

    //delaying by 1 mins
    Runnable syncRunnable = new Runnable() {
        @Override
        public void run() {
            if (lastSyncTextView != null && lastSyncTextView.getVisibility() == View.VISIBLE) {
                Animation fadeOut = AnimationUtils.loadAnimation(AirZoonMapActivity.this, R.anim.fade_out);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        lastSyncTextView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                lastSyncTextView.startAnimation(fadeOut);
            }
        }
    };

    public void refreshAirZoonMapAsPerFileteration(String responseString) {
        airZoonModel.loadAndParseHotSpot(responseString, MyApplication.getInstance().getAirZoonDB());
        loadAirZoonShops();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {
//        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
//        loadingBlanckBgView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }


    /**
     * Marker Snippet Adapter
     */
    class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {

        public CustomInfoAdapter() {
        }

        @Override
        public View getInfoContents(Marker marker) {
//            System.out.println(">>sid preparing snippet");
            int index = Integer.parseInt(marker.getTitle());
            AirZoonDo airZoonDo = airZoonDoArrayList.get(index);
            View snippetView = getSnippedView();
            TextView hotSpotName = (TextView) snippetView.findViewById(R.id.hotSpotName);
            ImageView hotspotCatImageView = (ImageView) snippetView.findViewById(R.id.hotspotCatImageView);
            ImageView hotspotSpeedImageView = (ImageView) snippetView.findViewById(R.id.hotspotSpeedImageView);
            TextView hotspotSpeedTextView = (TextView) snippetView.findViewById(R.id.hotspotSpeedTextView);
            ImageView moreImageView = (ImageView) snippetView.findViewById(R.id.moreImageView);

            hotspotCatImageView.setImageResource(airZoonModel.getHotSpotBigImageResByCat(airZoonDo.getCategory()));
            hotspotSpeedImageView.setImageResource(airZoonModel.getSpeeddoMeterImage(airZoonDo.getSpeed()));
            hotSpotName.setText(airZoonDo.getName());
            hotspotSpeedTextView.setText(airZoonDo.getSpeed());

            return snippetView;
        }

        @Override
        public View getInfoWindow(Marker arg0) {
            return null;
        }


    }


    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 8.3f));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8.3f), 2000, null);

    }

    private void letsMoveCamera(int km) {
        float myLat = MyApplication.getInstance().getLocationModel().getLatitude();
        float myLong = MyApplication.getInstance().getLocationModel().getLongitude();
        if (myLat != 0.0 && myLong != 0.0) {
            MyApplication.getInstance().setLatLongFound(null);
            int meters = km * 1000;
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

            Resources r = getResources();
            int mapSideInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeight, r.getDisplayMetrics());

            LatLng cameraAngle = new LatLng(myLat, myLong);
            LatLngBounds latLngBounds = calculateBounds(cameraAngle, meters);
            if (latLngBounds != null) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, mapSideInPixels, mapSideInPixels, 0);
                if (mMap != null)
                    mMap.animateCamera(cameraUpdate);
            }
        }
    }


    public boolean animateToMeters(final int km) {
        final float myLat = MyApplication.getInstance().getLocationModel().getLatitude();
        final float myLong = MyApplication.getInstance().getLocationModel().getLongitude();

        if (myLat != 0.0 && myLong != 0.0) {
            letsMoveCamera(km);
            return true;
        } else {
            MyApplication.getInstance().setLatLongFound(new LatLongFound() {
                @Override
                public void onLatLongFound() {
                    letsMoveCamera(km);
                }
            });
            MyApplication.getInstance().enableGPS(this);
            return false;
        }
    }

    private LatLngBounds calculateBounds(LatLng center, double radius) {
        return new LatLngBounds.Builder().
                include(SphericalUtil.computeOffset(center, radius, 0)).
                include(SphericalUtil.computeOffset(center, radius, 90)).
                include(SphericalUtil.computeOffset(center, radius, 180)).
                include(SphericalUtil.computeOffset(center, radius, 270)).build();
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        //Disable Map Toolbar:
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moreImageView:
                moreBtnClick();
                break;
            case R.id.searchImageView:
                onSearchButtonClick();
                closeMore();
//                checkKeyHash();
                break;
            case R.id.filterImageView:
                onFilterButtonClick();
                closeMore();
                break;
            case R.id.profileImageView:
                onProfileButtonClick();
                closeMore();
                break;
            case R.id.faviourateImageView:
                onFaviourateButtonClick();
                closeMore();
                break;
            case R.id.syncImageView:
                onSyncButtonClick();
                closeMore();
                break;
            case R.id.aboutUsImageView:
                onAboutUsButtonClick();
                closeMore();
                break;
            case R.id.guidFullScreenView:
                hideGuide();
                break;


        }

    }

    @Override
    public void onBackPressed() {
        if (isGuideShowingCurrently()) {
            hideGuide();
        } else {
            super.onBackPressed();
        }

    }

    private void onAboutUsButtonClick() {
        AboutUsDialog aboutUsDialog = new AboutUsDialog(this);
        aboutUsDialog.showDialog(ProfileDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }

    private void onSyncButtonClick() {
        progressBar.setVisibility(View.VISIBLE);
        APIHandler apiHandler = new APIHandler(this, this, RequestConstant.REQUEST_GET_HOTSPOT_LIST,
                Request.Method.GET, URLConstants.URL_GET_HOTSPOT_LIST, false, null, null, null, null);
        apiHandler.requestAPI();
    }

    private void onFaviourateButtonClick() {
        FavoritesDialog favoritesDialog = new FavoritesDialog(this);
        favoritesDialog.showDialog(ProfileDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }

    private void onProfileButtonClick() {
        ProfileDialog profileDialog = new ProfileDialog(this);
        profileDialog.showDialog(ProfileDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }

    private void onFilterButtonClick() {
        FilterSettingsDialog filterSettingsDialog = new FilterSettingsDialog(this);
        filterSettingsDialog.showDialog(ProfileDialog.ANIM_TYPE_TOP_IN_TOP_OUT);
    }

    private void onSearchButtonClick() {
        SearchSpotDialog searchSpotDialog = new SearchSpotDialog(this);
        searchSpotDialog.showDialog(ProfileDialog.ANIM_TYPE_TOP_IN_TOP_OUT);
    }


    private void performDelayAnim(final ImageView imageView, int duration, final boolean isOpenAnim, final Animation fab_open, final Animation fab_close) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOpenAnim) {
                    imageView.startAnimation(fab_open);
                } else {
                    imageView.startAnimation(fab_close);
                }
            }
        }, duration);
    }


    private void openMore() {
        if (!isOpen) {
            isMenuAnimInProgress = true;
            isOpen = true;
            moreImageView.setImageResource(R.drawable.button2);
            enableButtonBtnClick();
            Animation fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
            performDelayAnim(profileImageView, 50 * 3, true, fab_open, null);
            Animation fab_open2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_b4);
            performDelayAnim(faviourateImageView, 50 * 2, true, fab_open2, null);
            Animation fab_open3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_b3);
            performDelayAnim(syncImageView, 50, true, fab_open3, null);
            Animation fab_open4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_b2);
            aboutUsImageView.startAnimation(fab_open4);
            fab_open4.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isMenuAnimInProgress = false;
                    if (!prefManager.isMoreInfoShown()) {
                        prefManager.setMoreInfoShown(true);
                        showGuide();
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    }


    private void closeMore() {
        if (isOpen) {
            isMenuAnimInProgress = true;
            hideGuide();
            moreImageView.setImageResource(R.drawable.button1);
            isOpen = false;
            Animation fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
            profileImageView.startAnimation(fab_close);
            Animation fab_close2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_b2);
            performDelayAnim(faviourateImageView, 50, false, null, fab_close2);
            Animation fab_close3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_b3);
            performDelayAnim(syncImageView, 50 * 2, false, null, fab_close3);
            Animation fab_close4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_b4);
            performDelayAnim(aboutUsImageView, 50 * 3, false, null, fab_close4);
            fab_close4.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isMenuAnimInProgress = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            disableButtonBtnClick();
        }

    }

    private void moreBtnClick() {
        if (!isMenuAnimInProgress) {
            if (isOpen) {
                closeMore();
            } else {
                openMore();
            }
        }

    }


    private void enableButtonBtnClick() {
        aboutUsImageView.setEnabled(true);
        syncImageView.setEnabled(true);
        faviourateImageView.setEnabled(true);
        profileImageView.setEnabled(true);
    }

    private void disableButtonBtnClick() {
        aboutUsImageView.setEnabled(false);
        syncImageView.setEnabled(false);
        faviourateImageView.setEnabled(false);
        profileImageView.setEnabled(false);
    }

    private void showGuide() {
        guidFullScreenView.setVisibility(View.VISIBLE);
        guideArrowImageView.setVisibility(View.VISIBLE);
        guideBoxImageView.setVisibility(View.VISIBLE);
        guideText.setVisibility(View.VISIBLE);
    }

    private void hideGuide() {
        guidFullScreenView.setVisibility(View.GONE);
        guideArrowImageView.setVisibility(View.GONE);
        guideBoxImageView.setVisibility(View.GONE);
        guideText.setVisibility(View.GONE);
    }

    private boolean isGuideShowingCurrently() {
        if (guidFullScreenView.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }


    /**
     * --------------------FB code------------
     */
    private CallbackManager fbCallbackManager = null;
    private FaceBookModel faceBookModel = null;

    public void requestFBLogin(SocialLoginInterface socialLoginInterface) {
        fbCallbackManager = CallbackManager.Factory.create();
        faceBookModel = new FaceBookModel(this, socialLoginInterface);
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().registerCallback(fbCallbackManager, faceBookModel);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_status", "user_birthday"));
    }


    /**
     * --------------------Twitter code------------
     */
    private TwitterLoginButton twitLoginButton;
    public static final String TWITTER_KEY = "89iwWGduJYZ4s1cfUPEx85g8U";
    public static final String TWITTER_SECRET = "ymO6YbndVdQVsUatEUcmUPDTxbxHC3VoKv9mmMA2nQOmHLQjjH\n";

    public void requestTwitterLogin(SocialLoginInterface socialLoginInterface) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new com.twitter.sdk.android.Twitter(authConfig));
        twitLoginButton = new TwitterLoginButton(this);
        twitLoginButton.setVisibility(View.GONE);
        TwitterModel twitterModel = new TwitterModel(this, socialLoginInterface);
        twitLoginButton.setCallback(twitterModel);
        twitLoginButton.performClick();
    }


    public static final int REQUEST_LOCATION = 111;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    MyApplication.getInstance().getLocationModel().initialize();
                } else {
                }
                return;
            }
            case MyApplication.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isCameraSelected)
                        cameraIntent();
                    else
                        galleryIntent();
                } else {
                }
                break;
        }
    }


    ////////////bottom sheet////////
    private BottomSheet bottomSheet;
    private ImageCallback imageCallback;

    public void showProfilePicBottomSheet(ImageCallback imageCallback) {
        this.imageCallback = imageCallback;

        bottomSheet = new BottomSheet.Builder(this).title(this.getResources().getString(R.string.selectPictureText))
                .sheet(R.menu.pic_bottom_sheet_menu).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.gallary:
                                galleryIntent();
                                break;
                            case R.id.camera:
                                cameraIntent();
                                break;
                        }
                    }
                }).show();
    }

    boolean isCameraSelected = true;

    private void cameraIntent() {
        boolean result = MyApplication.getInstance().checkPermission(this);
        if (result) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
            isCameraSelected = true;
        }
    }

    private void galleryIntent() {
        boolean result = MyApplication.getInstance().checkPermission(this);
        if (result) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
            isCameraSelected = false;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            else {
                if (fbCallbackManager != null) {
                    fbCallbackManager.onActivityResult(requestCode, resultCode, data);
                }
                if (twitLoginButton != null) {
                    twitLoginButton.onActivityResult(requestCode, resultCode, data);
                }
            }
        } else {
            if (fbCallbackManager != null) {
                fbCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
            if (twitLoginButton != null) {
                twitLoginButton.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imageCallback != null) {
                imageCallback.onImageFetched(bm);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imageCallback != null) {
            imageCallback.onImageFetched(thumbnail);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

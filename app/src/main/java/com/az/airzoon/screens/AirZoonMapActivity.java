package com.az.airzoon.screens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.dialog_screens.AboutUsDialog;
import com.az.airzoon.dialog_screens.FavoritesDialog;
import com.az.airzoon.dialog_screens.FilterSettingsDialog;
import com.az.airzoon.dialog_screens.HotspotDetailDailog;
import com.az.airzoon.dialog_screens.ProfileDialog;
import com.az.airzoon.dialog_screens.SearchSpotDialog;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.preferences.PrefManager;
import com.google.android.gms.fitness.data.Application;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AirZoonMapActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

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

    boolean isGuideShown = false;
    boolean isMenuAnimInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        airZoonModel = AirZoonModel.getInstance();
        setContentView(R.layout.activity_air_zoon_map);
        setUpMap();
        initViews();
        registerEvents();
        setUI();
    }


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
        loadingBlanckBgView = findViewById(R.id.loadingBlanckBgView);
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
            prefManager.setLastSyncTime(MyApplication.getInstance().getCurrentDate());
            lastSyncTextView.setText(MyApplication.getInstance().getCurrentDate());
        } else {
            lastSyncTextView.setText(syncDate);
        }
    }

    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        loadAirZoonShops();
        enableMyLocation();
    }

    private void loadAirZoonShops() {
        // Clear previous markers
        mMap.clear();

        airZoonDoArrayList = airZoonModel.getFilteredList();
        System.out.println(">>map refresh size" + airZoonDoArrayList.size());
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
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
            if (i == airZoonDoArrayList.size() - 1) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(locationLatLong));
                moveToCurrentLocation(locationLatLong);
            }
        }
    }

    private void onMarkerInfoWindowClick(Marker marker) {
        int index = Integer.parseInt(marker.getTitle());
        AirZoonDo airZoonDo = airZoonDoArrayList.get(index);
        HotspotDetailDailog hotspotDetailDailog = new HotspotDetailDailog(this, airZoonDo);
        hotspotDetailDailog.showDialog(HotspotDetailDailog.ANIM_TYPE_LEFT_IN_RIGHT_OUT);
    }


    private View getSnippedView() {
        if (snippetView == null) {
            snippetView = AirZoonMapActivity.this.getLayoutInflater().inflate(R.layout.marker_snippet_view, null);
        }
        return snippetView;
    }


    /**
     * Marker Snippet Adapter
     */
    class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {

        public CustomInfoAdapter() {
        }

        @Override
        public View getInfoContents(Marker marker) {
            int index = Integer.parseInt(marker.getTitle());
            AirZoonDo airZoonDo = airZoonDoArrayList.get(index);
            View snippetView = getSnippedView();
            TextView hotSpotName = (TextView) snippetView.findViewById(R.id.hotSpotName);
            ImageView hotspotCatImageView = (ImageView) snippetView.findViewById(R.id.hotspotCatImageView);
            ImageView hotspotSpeedImageView = (ImageView) snippetView.findViewById(R.id.hotspotSpeedImageView);
            TextView hotspotSpeedTextView = (TextView) snippetView.findViewById(R.id.hotspotSpeedTextView);
            ImageView moreImageView = (ImageView) snippetView.findViewById(R.id.moreImageView);

            hotspotCatImageView.setImageResource(airZoonModel.getHotSpotSmallImageResByCat(airZoonDo.getCategory()));
            hotspotSpeedImageView.setImageResource(airZoonModel.getHotSpotSpeedResBy(airZoonDo.getSpeed()));
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
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
        aboutUsDialog.showDialog(ProfileDialog.ANIM_TYPE_LEFT_IN_RIGHT_OUT);
    }

    private void onSyncButtonClick() {
        refreshMapAsPerFilterAlsoPerformSync();
    }

    private void onFaviourateButtonClick() {
        FavoritesDialog favoritesDialog = new FavoritesDialog(this);
        favoritesDialog.showDialog(ProfileDialog.ANIM_TYPE_LEFT_IN_RIGHT_OUT);
    }

    private void onProfileButtonClick() {
        ProfileDialog profileDialog = new ProfileDialog(this);
        profileDialog.showDialog(ProfileDialog.ANIM_TYPE_LEFT_IN_RIGHT_OUT);
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
                    if (!isGuideShown) {
                        isGuideShown = true;
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


    public void refreshMapAsPerFilterAlsoPerformSync() {
        RefreshMapAsync refreshMapAsync = new RefreshMapAsync();
        refreshMapAsync.execute();

    }

    public class RefreshMapAsync extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println(">>map refresh start");
            loadingBlanckBgView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            AirZoonModel.getInstance().loadStaticHotSpots();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            loadAirZoonShops();
            loadingBlanckBgView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            System.out.println(">>map refresh end");

            //setting last sync time
            PrefManager prefManager = new PrefManager(AirZoonMapActivity.this);
            prefManager.setLastSyncTime(MyApplication.getInstance().getCurrentDate());
            lastSyncTextView.setText(prefManager.getLstSyncTime());
        }
    }


}

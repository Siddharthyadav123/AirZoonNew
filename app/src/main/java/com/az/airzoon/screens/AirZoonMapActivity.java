package com.az.airzoon.screens;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.az.airzoon.R;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.dialog_screens.ProfileDialog;
import com.az.airzoon.dialog_screens.SearchAirZoonDailog;
import com.az.airzoon.models.AirZoonModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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

    private RelativeLayout moreMenuContainerRelLayout;

    boolean moreOptionOpen = false;
    private AirZoonModel airZoonModel;

    private ArrayList<AirZoonDo> airZoonDoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        airZoonModel = AirZoonModel.getInstance();
        setContentView(R.layout.activity_air_zoon_map);
        setUpMap();
        initViews();
//        closeMore();
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
        moreMenuContainerRelLayout = (RelativeLayout) findViewById(R.id.moreMenuContainerRelLayout);
    }

    private void registerEvents() {
        moreImageView.setOnClickListener(this);
        searchImageView.setOnClickListener(this);
        filterImageView.setOnClickListener(this);
        profileImageView.setOnClickListener(this);
        faviourateImageView.setOnClickListener(this);
        syncImageView.setOnClickListener(this);
        aboutUsImageView.setOnClickListener(this);
    }


    private void setUI() {
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
        airZoonDoArrayList = airZoonModel.getAirZoonDoArrayList();
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            AirZoonDo airZoonDo = airZoonDoArrayList.get(i);
            LatLng locationLatLong = new LatLng(Double.parseDouble(airZoonDo.getLat()), Double.parseDouble(airZoonDo.getLng()));

            Bitmap markerBitmap = null;
            if (airZoonDo.getType().equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
                markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_airzoon);
            } else if (airZoonDo.getType().equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
                markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_paid);
            } else {
                markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_free);
            }

            mMap.addMarker(new MarkerOptions().position(locationLatLong).title(airZoonDo.getName()).icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)));

            if (i == airZoonDoArrayList.size() - 1) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(locationLatLong));
                moveToCurrentLocation(locationLatLong);
            }
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
                onMoreButtonClick(v);
                break;
            case R.id.searchImageView:
                onSearchButtonClick();
                break;
            case R.id.filterImageView:
                onFilterButtonClick();
                break;
            case R.id.profileImageView:
                onProfileButtonClick();
                break;
            case R.id.faviourateImageView:
                onFaviourateButtonClick();
                break;
            case R.id.syncImageView:
                onSyncButtonClick();
                break;
            case R.id.aboutUsImageView:
                onAboutUsButtonClick();
                break;
        }

    }

    private void onAboutUsButtonClick() {
        Toast.makeText(AirZoonMapActivity.this, "onAboutUsButtonClick.", Toast.LENGTH_SHORT).show();
    }

    private void onSyncButtonClick() {
        Toast.makeText(AirZoonMapActivity.this, "onSyncButtonClicke.", Toast.LENGTH_SHORT).show();
    }

    private void onFaviourateButtonClick() {
        Toast.makeText(AirZoonMapActivity.this, "onFaviourateButtonClick.", Toast.LENGTH_SHORT).show();
    }

    private void onProfileButtonClick() {
        ProfileDialog profileDialog = new ProfileDialog(this);
        profileDialog.showDialog(ProfileDialog.ANIM_TYPE_LEFT_IN_RIGHT_OUT);

    }

    private void onFilterButtonClick() {
        Toast.makeText(AirZoonMapActivity.this, "Working on the same.", Toast.LENGTH_SHORT).show();
    }

    private void onSearchButtonClick() {
        SearchAirZoonDailog searchAirZoonDailog = new SearchAirZoonDailog(this);
        searchAirZoonDailog.showDialog(ProfileDialog.ANIM_TYPE_TOP_IN_TOP_OUT);
    }


    private void onMoreButtonClick(View v) {
        moreBtnClick();
        if (!moreOptionOpen) {
            moreOptionOpen = true;
        } else {
            moreOptionOpen = false;
        }

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
//        setVisiblityOfBottomOptions(View.VISIBLE);
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
    }

    private void closeMore() {
        moreImageView.setImageResource(R.drawable.button1);

        Animation fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        profileImageView.startAnimation(fab_close);
        Animation fab_close2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_b2);
        performDelayAnim(faviourateImageView, 50, false, null, fab_close2);
        Animation fab_close3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_b3);
        performDelayAnim(syncImageView, 50 * 2, false, null, fab_close3);
        Animation fab_close4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_b4);
        performDelayAnim(aboutUsImageView, 50 * 3, false, null, fab_close4);
        disableButtonBtnClick();
    }

    private void moreBtnClick() {
        if (moreOptionOpen) {
            closeMore();
        } else {
            openMore();
        }
    }

    private void setVisiblityOfBottomOptions(int visiblity) {
        if (aboutUsImageView.getVisibility() == View.INVISIBLE) {
            aboutUsImageView.setVisibility(visiblity);
            syncImageView.setVisibility(visiblity);
            faviourateImageView.setVisibility(visiblity);
            profileImageView.setVisibility(visiblity);
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
}

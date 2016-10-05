package com.az.airzoon.dialog_screens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.listeners.LatLongFound;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.social_integration.ProfilePicLoader;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;

/**
 * Created by sid on 30/07/2016.
 */
public class HotspotDetailDailog extends AbstractBaseDialog implements APICallback, LatLongFound {
    private ImageView defaultImageView;
    private ImageView closeProfileImageView;
    private ImageView faviourateImageView;
    private ImageView shareImageView;
    private TextView hotSpotNameTextView;
    private ImageView hotSpotCategoryImage;
    private TextView hotSpotTypeTextView;
    private ImageView speedImageView;
    private TextView hotSpotSpeedTextView;
    private TextView hotSpotAddressTextView;
    private TextView hotSpotContactNoTextView;
    private TextView hotSpotOpening1TextView;
    private TextView hotSpotOpening2TextView;
    private LinearLayout goButtonLinLayout;
    private LinearLayout callButtonLinLayout;
    private TextView reportAnErrorText;
    private LinearLayout shareLayout;

    private AirZoonDo airZoonDo;
    boolean openFromFav = false;

    public HotspotDetailDailog(Context context, AirZoonDo airZoonDo) {
        super(context);
        this.airZoonDo = airZoonDo;
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.hotspot_detail_layout, null);
    }

    @Override
    public void initViews(View view) {
        defaultImageView = (ImageView) view.findViewById(R.id.defaultImageView);
        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);
        faviourateImageView = (ImageView) view.findViewById(R.id.faviourateImageView);
        shareImageView = (ImageView) view.findViewById(R.id.shareImageView);
        hotSpotNameTextView = (TextView) view.findViewById(R.id.hotSpotNameTextView);
        hotSpotCategoryImage = (ImageView) view.findViewById(R.id.hotSpotCategoryImage);
        hotSpotTypeTextView = (TextView) view.findViewById(R.id.hotSpotTypeTextView);
        speedImageView = (ImageView) view.findViewById(R.id.speedImageView);
        hotSpotSpeedTextView = (TextView) view.findViewById(R.id.hotSpotSpeedTextView);
        hotSpotContactNoTextView = (TextView) view.findViewById(R.id.hotSpotContactNoTextView);
        hotSpotOpening1TextView = (TextView) view.findViewById(R.id.hotSpotOpening1TextView);
        hotSpotOpening2TextView = (TextView) view.findViewById(R.id.hotSpotOpening2TextView);
        hotSpotAddressTextView = (TextView) view.findViewById(R.id.hotSpotAddressTextView);
        goButtonLinLayout = (LinearLayout) view.findViewById(R.id.goButtonLinLayout);
        callButtonLinLayout = (LinearLayout) view.findViewById(R.id.callButtonLinLayout);
        reportAnErrorText = (TextView) view.findViewById(R.id.reportAnErrorText);

        shareLayout = (LinearLayout) view.findViewById(R.id.shareLayout);

        reportAnErrorText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        faviourateImageView.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        goButtonLinLayout.setOnClickListener(this);
        callButtonLinLayout.setOnClickListener(this);
        reportAnErrorText.setOnClickListener(this);
    }

    @Override
    public void setInfoInUI(View view) {
        loadImage();
        hotSpotNameTextView.setText(airZoonDo.getName());
        hotSpotTypeTextView.setText(airZoonDo.getCategory());
        hotSpotSpeedTextView.setText(airZoonDo.getSpeed());

        String address2 = "";
        if (airZoonDo.getAddress2() != null && airZoonDo.getAddress2().trim().length() > 0) {
            address2 = ", " + airZoonDo.getAddress2().trim();
        }

        String city = "";
        if (airZoonDo.getCity() != null && airZoonDo.getCity().trim().length() > 0) {
            city = ", " + airZoonDo.getCity().trim();
        }

        String country = "";
        if (airZoonDo.getCountry() != null && airZoonDo.getCountry().trim().length() > 0) {
            country = ", " + airZoonDo.getCountry().trim();
        }

        hotSpotAddressTextView.setText(airZoonDo.getAddress() + address2 + city + country);

        if (airZoonDo.getPhone() != null && airZoonDo.getPhone().length() > 0) {
            hotSpotContactNoTextView.setText(airZoonDo.getPhone());
        } else {
            hotSpotContactNoTextView.setText(activity.getString(R.string.noPhoneNumText));
        }

        hotSpotOpening1TextView.setText(airZoonDo.getOpening_one());
        hotSpotOpening2TextView.setText(airZoonDo.getOpening_two());
        hotSpotCategoryImage.setImageResource(AirZoonModel.getInstance().getHotSpotBigImageResByCat(airZoonDo.getCategory()));
        if (airZoonDo.isFaviourate()) {
            faviourateImageView.setImageResource(R.drawable.selectedstar);
        } else {
            faviourateImageView.setImageResource(R.drawable.star);
        }

        speedImageView.setImageResource(AirZoonModel.getInstance().getSpeeddoMeterImage(airZoonDo.getSpeed()));
    }

    private void loadImage() {
        if (airZoonDo.getImage() != null && airZoonDo.getImage().length() > 0
                && !airZoonDo.getImage().equalsIgnoreCase("image/other.jpg")) {
            String imageURL = "http://airzoonapp.com/" + airZoonDo.getImage();
            ProfilePicLoader profilePicLoader = new ProfilePicLoader(activity);
            profilePicLoader.downloadAirzoonFrame(defaultImageView, imageURL, airZoonDo.getId() + "");
        }
    }

    @Override
    public void onClickEvent(View actionView) {
        switch (actionView.getId()) {
            case R.id.closeProfileImageView:
                dismiss();
                if (openFromFav) {
                    FavoritesDialog favoritesDialog = new FavoritesDialog(activity);
                    favoritesDialog.showDialog(EditProfileDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
                }
                break;
            case R.id.faviourateImageView:
                onFaviourateImageClick();
                break;
            case R.id.shareLayout:
                onShareBtnClick();
                break;
            case R.id.goButtonLinLayout:
                float sourceLat = MyApplication.getInstance().getLocationModel().getLatitude();
                float sourceLong = MyApplication.getInstance().getLocationModel().getLongitude();
                if (sourceLat == 0.0 && sourceLong == 0.0) {
                    MyApplication.getInstance().setLatLongFound(this);
                    MyApplication.getInstance().enableGPS(activity);
                } else {
                    onGoBtnClick();
                }

                break;
            case R.id.callButtonLinLayout:
                onCallBtnClick();
                break;
            case R.id.reportAnErrorText:
                dismiss();
                onReportAnErrorClick();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        MyApplication.getInstance().setLatLongFound(null);
    }

    @Override
    public void onDailogYesClick() {
        dismiss();
        ProfileDialog profileDialog = new ProfileDialog(activity);
        profileDialog.showDialog(EditProfileDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);

    }

    @Override
    public void onDailogNoClick() {

    }

    private void onFaviourateImageClick() {
        if (MyApplication.getInstance().getUserProfileDO().isLoggedInAlrady()) {
            String requestString = null;

            String fav = "";
            if (!airZoonDo.isFaviourate()) {
                requestString = activity.getResources().getString(R.string.loadingText);
                fav = "Yes";
            } else {
                requestString = activity.getResources().getString(R.string.loadingText);
                fav = "No";
            }
            //hitting server
            APIHandler apiHandler = new APIHandler(activity, this, RequestConstant.REQUEST_POST_FAVIOURATES,
                    Request.Method.POST, URLConstants.URL_POST_FAVIOURATES, true, requestString, null,
                    null, airZoonDo.getRequestParamsForFav(fav));
            apiHandler.requestAPI();
        } else {
            showAleart(activity.getString(R.string.errorText),
                    activity.getString(R.string.loginErrorText),
                    activity.getString(R.string.signInText),
                    activity.getString(R.string.cancelText));
        }

    }

    private void onReportAnErrorClick() {
        ReportIssueDialog reportIssueDialog = new ReportIssueDialog(activity, airZoonDo.getName());
        reportIssueDialog.showDialog(ReportIssueDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }

    private void onGoBtnClick() {
        float sourceLat = MyApplication.getInstance().getLocationModel().getLatitude();
        float sourceLong = MyApplication.getInstance().getLocationModel().getLongitude();

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + sourceLat + "," + sourceLong + "&daddr=" + airZoonDo.getLat() + "," + airZoonDo.getLng()));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        activity.startActivity(intent);

        MyApplication.getInstance().setLatLongFound(null);
    }


    private void onCallBtnClick() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, activity.getResources().getString(R.string.provideCallingPermsnText), Toast.LENGTH_SHORT).show();
            return;
        }

        if (airZoonDo.getPhone() != null && airZoonDo.getPhone().length() > 0) {
            String phoneNum = "tel:" + airZoonDo.getPhone();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(phoneNum));
            activity.startActivity(callIntent);
        } else {
            Toast.makeText(activity, activity.getResources().getString(R.string.contactNumNotFoundText), Toast.LENGTH_SHORT).show();
        }

    }

    private void onShareBtnClick() {
        String shareText = activity.getString(R.string.shareText) + " \n" + airZoonDo.getName() + " \n" + activity.getString(R.string.urlText);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }


    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        if (!airZoonDo.isFaviourate()) {
            faviourateImageView.setImageResource(R.drawable.selectedstar);
            airZoonDo.setFaviourate(true);
//            Toast.makeText(activity, activity.getResources().getString(R.string.addedToFavText), Toast.LENGTH_SHORT).show();
        } else {
            faviourateImageView.setImageResource(R.drawable.star);
            airZoonDo.setFaviourate(false);
//            Toast.makeText(activity, activity.getResources().getString(R.string.removedFromFavText), Toast.LENGTH_SHORT).show();
        }
        MyApplication.getInstance().getAirZoonDB().updateFav(airZoonDo);
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }

    public void setOpenFromFav(boolean openFromFav) {
        this.openFromFav = openFromFav;
    }

    @Override
    public void onLatLongFound() {
        onGoBtnClick();
    }
}

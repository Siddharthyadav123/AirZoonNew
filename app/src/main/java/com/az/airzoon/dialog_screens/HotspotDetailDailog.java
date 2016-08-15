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

import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.models.AirZoonModel;

/**
 * Created by sid on 30/07/2016.
 */
public class HotspotDetailDailog extends AbstractBaseDialog {

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
        hotSpotNameTextView.setText(airZoonDo.getName());
        hotSpotTypeTextView.setText(airZoonDo.getCategory());
        hotSpotSpeedTextView.setText(airZoonDo.getSpeed());
        hotSpotAddressTextView.setText(airZoonDo.getAddress() + " " + airZoonDo.getAddress2());
        hotSpotContactNoTextView.setText(airZoonDo.getPhone());
        hotSpotOpening1TextView.setText(airZoonDo.getOpening_one());
        hotSpotOpening2TextView.setText(airZoonDo.getOpening_two());
        hotSpotCategoryImage.setImageResource(AirZoonModel.getInstance().getHotSpotBigImageResByCat(airZoonDo.getCategory()));
        if (airZoonDo.isFaviourate()) {
            faviourateImageView.setImageResource(R.drawable.selectedstar);
        } else {
            faviourateImageView.setImageResource(R.drawable.star);
        }
    }

    @Override
    public void onClickEvent(View actionView) {
        switch (actionView.getId()) {
            case R.id.closeProfileImageView:
                dismiss();
                break;
            case R.id.faviourateImageView:
                onFaviourateImageClick();
                break;
            case R.id.shareLayout:
                onShareBtnClick();
                break;
            case R.id.goButtonLinLayout:
                onGoBtnClick();
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

    private void onFaviourateImageClick() {

        if (MyApplication.getInstance().getUserProfileDO().isLoggedInAlrady()) {
            if (!airZoonDo.isFaviourate()) {
                faviourateImageView.setImageResource(R.drawable.selectedstar);
                airZoonDo.setFaviourate(true);
                Toast.makeText(activity, "Added to Favorites", Toast.LENGTH_SHORT).show();
            } else {
                faviourateImageView.setImageResource(R.drawable.star);
                airZoonDo.setFaviourate(false);
                Toast.makeText(activity, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            }
            MyApplication.getInstance().getAirZoonDB().updateFav(airZoonDo);
        } else {
            Toast.makeText(activity, "Please login using facebook or twitter first.", Toast.LENGTH_SHORT).show();
        }

    }

    private void onReportAnErrorClick() {
        ReportIssueDialog reportIssueDialog = new ReportIssueDialog(activity);
        reportIssueDialog.showDialog(ReportIssueDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }

    private void onGoBtnClick() {
        String latLongString = "geo:" + airZoonDo.getLat() + "," + airZoonDo.getLng();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(latLongString));
        i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        activity.startActivity(i);
    }

    private void onCallBtnClick() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "Provide Calling permission", Toast.LENGTH_SHORT).show();
            return;
        }

        if (airZoonDo.getPhone() != null && airZoonDo.getPhone().length() > 0) {
            String phoneNum = "tel:" + airZoonDo.getPhone();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(phoneNum));
            activity.startActivity(callIntent);
        } else {
            Toast.makeText(activity, "Contact number not found", Toast.LENGTH_SHORT).show();
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


}

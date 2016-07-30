package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.az.airzoon.R;
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
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        faviourateImageView.setOnClickListener(this);
        shareImageView.setOnClickListener(this);
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
    }

    @Override
    public void onClickEvent(View actionView) {
        switch (actionView.getId()) {
            case R.id.closeProfileImageView:
                dismiss();
                break;
            case R.id.faviourateImageView:
                dismiss();
                break;
            case R.id.shareImageView:
                dismiss();
                break;
            case R.id.goButtonLinLayout:
                dismiss();
                break;
            case R.id.callButtonLinLayout:
                dismiss();
                break;
            case R.id.reportAnErrorText:
                dismiss();
                break;
        }
    }
}

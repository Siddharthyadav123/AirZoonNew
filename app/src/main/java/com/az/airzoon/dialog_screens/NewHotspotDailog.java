package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.az.airzoon.R;

/**
 * Created by sid on 31/07/2016.
 */
public class NewHotspotDailog extends AbstractBaseDialog {
    public NewHotspotDailog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.new_hotspot_layout, null);
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void registerEvnts(View view) {

    }

    @Override
    public void setInfoInUI(View view) {

    }

    @Override
    public void onClickEvent(View actionView) {

    }
}

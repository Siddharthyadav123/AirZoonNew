package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.az.airzoon.R;

/**
 * Created by siddharth on 7/27/2016.
 */
public class SearchAirZoonDailog extends AbstractBaseDialog {

    public SearchAirZoonDailog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.profile_dialog_layout, null);
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

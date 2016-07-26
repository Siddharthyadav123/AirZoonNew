package com.az.airzoon.dialog_screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

/**
 * Created by sid on 26/07/2016.
 */
public abstract class AbstractBaseDialog extends Dialog implements View.OnClickListener {

    protected Activity activity;
    private View view = null;
    private LayoutInflater layoutInflater;

    public AbstractBaseDialog(Context context) {
        super(context);
        this.activity = (Activity) context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = setViews(layoutInflater);
        setContentView(view);
        initViews(view);
        registerEvnts(view);
        setInfoInUI(view);
    }

    public abstract View setViews(LayoutInflater layoutInflater);

    public abstract void initViews(View view);

    public abstract void registerEvnts(View view);

    public abstract void setInfoInUI(View view);

    public abstract void onClickEvent(View actionView);

    @Override
    public void onClick(View view) {
        onClickEvent(view);
    }
}

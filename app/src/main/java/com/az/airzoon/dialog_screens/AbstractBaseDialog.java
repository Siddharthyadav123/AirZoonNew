package com.az.airzoon.dialog_screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.az.airzoon.R;

/**
 * Created by sid on 26/07/2016.
 */
public abstract class AbstractBaseDialog extends Dialog implements View.OnClickListener {

    public static final int ANIM_TYPE_DEFAULT = -1;
    public static final int ANIM_TYPE_LEFT_IN_RIGHT_OUT = 0;
    public static final int ANIM_TYPE_TOP_IN_TOP_OUT = 1;
    private int animType = ANIM_TYPE_DEFAULT;

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


    public void showDial0g(int animType) {
        this.animType = animType;
        show();
    }

    @Override
    public void show() {
        switch (animType) {
            case ANIM_TYPE_LEFT_IN_RIGHT_OUT:
                getWindow().getAttributes().windowAnimations = R.style.dialog_animation_left_in_right_out;
                break;

            case ANIM_TYPE_TOP_IN_TOP_OUT:
                getWindow().getAttributes().windowAnimations = R.style.dialog_animation_top_in_top_out;
                break;
        }

        super.show();
    }

    @Override
    public void onClick(View view) {
        onClickEvent(view);
    }

}

package com.az.airzoon.dialog_screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.dataobjects.UserProfileDO;
import com.az.airzoon.social_integration.ProfilePicLoader;

/**
 * Created by sid on 26/07/2016.
 */
public abstract class AbstractBaseDialog extends Dialog implements View.OnClickListener {

    public static final int ANIM_TYPE_DEFAULT = -1;
    public static final int ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT = 0;
    public static final int ANIM_TYPE_TOP_IN_TOP_OUT = 1;
    private int animType = ANIM_TYPE_DEFAULT;

    protected Activity activity;
    private View view = null;
    private LayoutInflater layoutInflater;
    private ProgressDialog progressDialog;

    protected UserProfileDO userProfileDO;

    public AbstractBaseDialog(Context context) {
        super(context);
        this.activity = (Activity) context;
        layoutInflater = LayoutInflater.from(context);
        userProfileDO = MyApplication.getInstance().getUserProfileDO();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
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


    public void showDialog(int animType) {
        this.animType = animType;
        show();
    }

    @Override
    public void show() {
        switch (animType) {
            case ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT:
                getWindow().getAttributes().windowAnimations = R.style.dialog_animation_bottom_in_bottom_out;
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


    public void showPogress(String titleText, String bodyText) {
        progressDialog = ProgressDialog.show(activity, titleText, bodyText, true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
    }

    public void hideProgressLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void loadProfileImage(ImageView imageView, ProgressBar progressBar) {
        ProfilePicLoader profilePicLoader = new ProfilePicLoader(activity, userProfileDO);
        profilePicLoader.downloadProfilePic(imageView, progressBar);
    }

    protected void showAleart(String title, String bodyText, String yesBtnText, String noBtnText) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(bodyText);
        builder1.setTitle(title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                yesBtnText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        onDailogYesClick();
                    }
                });

        builder1.setNegativeButton(
                noBtnText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        onDailogNoClick();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public abstract void onDailogYesClick();

    public abstract void onDailogNoClick();

    public final boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    protected void showNormalDailog(String bodyText) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(bodyText);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                activity.getString(R.string.YesText),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}

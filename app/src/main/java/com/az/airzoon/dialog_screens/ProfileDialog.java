package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.az.airzoon.R;

/**
 * Created by sid on 26/07/2016.
 */
public class ProfileDialog extends AbstractBaseDialog {

    private ImageView userDPImageView;
    private ImageView fbOnOffImageView;
    private ImageView twOnOffImageView;
    private ImageView closeProfileImageView;

    private TextView userNameTextView;
    private TextView userProfileTextView;
    private TextView phoneNumTextView;

    private Button editProfileButton;

    boolean isFbOn = false;
    boolean isTwitterOn = false;

    public ProfileDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.profile_dialog_layout, null);
    }

    @Override
    public void initViews(View view) {
        userDPImageView = (ImageView) view.findViewById(R.id.userDPImageView);
        fbOnOffImageView = (ImageView) view.findViewById(R.id.fbOnOffImageView);
        twOnOffImageView = (ImageView) view.findViewById(R.id.twOnOffImageView);
        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);

        userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
        userProfileTextView = (TextView) view.findViewById(R.id.userProfileTextView);
        phoneNumTextView = (TextView) view.findViewById(R.id.phoneNumTextView);
        editProfileButton = (Button) view.findViewById(R.id.editProfileButton);
    }

    @Override
    public void registerEvnts(View view) {
        fbOnOffImageView.setOnClickListener(this);
        twOnOffImageView.setOnClickListener(this);
        closeProfileImageView.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
    }

    @Override
    public void setInfoInUI(View view) {
    }

    @Override
    public void onClickEvent(View actionView) {
        switch (actionView.getId()) {
            case R.id.fbOnOffImageView:
                onFbBtnClick();
                break;
            case R.id.twOnOffImageView:
                onTwitterBtnClick();
                break;
            case R.id.closeProfileImageView:
                dismiss();
                break;
            case R.id.editProfileButton:
                onEditProfileBtnClick();
                break;
        }
    }

    private void onEditProfileBtnClick() {

    }

    private void onTwitterBtnClick() {
        if (isTwitterOn) {
            isTwitterOn = false;
            fbOnOffImageView.setEnabled(true);
            fbOnOffImageView.setAlpha(1f);
            twOnOffImageView.setImageResource(R.drawable.swich_off);
            logout();
        } else {
            isTwitterOn = true;
            fbOnOffImageView.setEnabled(false);
            fbOnOffImageView.setAlpha(0.9f);
            twOnOffImageView.setImageResource(R.drawable.switch_on);
            requestTwitterLogin();
        }
    }


    private void onFbBtnClick() {
        if (isFbOn) {
            isFbOn = false;
            twOnOffImageView.setEnabled(true);
            twOnOffImageView.setAlpha(1f);
            fbOnOffImageView.setImageResource(R.drawable.swich_off);
            logout();
        } else {
            isFbOn = true;
            twOnOffImageView.setEnabled(false);
            twOnOffImageView.setAlpha(0.9f);
            fbOnOffImageView.setImageResource(R.drawable.switch_on);
            requestFbLogin();
        }

    }

    private void logout() {
        Toast.makeText(activity, "logout", Toast.LENGTH_SHORT).show();
    }

    private void requestFbLogin() {
        Toast.makeText(activity, "Fb login", Toast.LENGTH_SHORT).show();
    }

    private void requestTwitterLogin() {
        Toast.makeText(activity, "Tw login", Toast.LENGTH_SHORT).show();
    }


}

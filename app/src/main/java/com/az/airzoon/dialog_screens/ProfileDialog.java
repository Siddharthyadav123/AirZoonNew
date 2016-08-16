package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.az.airzoon.R;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.dataobjects.UserProfileDO;
import com.az.airzoon.screens.AirZoonMapActivity;
import com.az.airzoon.social_integration.SocialLoginInterface;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;

/**
 * Created by sid on 26/07/2016.
 */
public class ProfileDialog extends AbstractBaseDialog implements SocialLoginInterface, APICallback {

    private ImageView userDPImageView;
    private ImageView fbOnOffImageView;
    private ImageView twOnOffImageView;
    private ImageView closeProfileImageView;

    private TextView userNameTextView;
    private TextView userAddressTextView;
    private TextView phoneNumTextView;

    private Button editProfileButton;
    private ProgressBar progressBar;

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
        userAddressTextView = (TextView) view.findViewById(R.id.userAddressTextView);
        phoneNumTextView = (TextView) view.findViewById(R.id.phoneNumTextView);
        editProfileButton = (Button) view.findViewById(R.id.editProfileButton);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

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
        if (userProfileDO.isLoggedInAlrady()) {
            if (userProfileDO.getLoginType().equalsIgnoreCase(Constants.LOGIN_TYPE_FB)) {
                setFbBtnStateOn();
            } else if (userProfileDO.getLoginType().equalsIgnoreCase(Constants.LOGIN_TYPE_TWITTER)) {
                setTwitterBtnStateOn();
            }
            setProfileUI();
        }
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
        if (userProfileDO.isLoggedInAlrady()) {
            dismiss();
            EditProfileDialog editProfileDialog = new EditProfileDialog(activity);
            editProfileDialog.showDialog(EditProfileDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
        } else {
            Toast.makeText(activity, activity.getResources().getString(R.string.loginErrorText), Toast.LENGTH_SHORT).show();
        }

    }


    private void setTwitterBtnStateOn() {
        isTwitterOn = true;
        fbOnOffImageView.setEnabled(false);
        fbOnOffImageView.setAlpha(0.9f);
        twOnOffImageView.setImageResource(R.drawable.switch_on);
    }

    private void setTwitterBtnStateOFF() {
        isTwitterOn = false;
        fbOnOffImageView.setEnabled(true);
        fbOnOffImageView.setAlpha(1f);
        twOnOffImageView.setImageResource(R.drawable.swich_off);
    }


    private void setFbBtnStateOn() {
        isFbOn = true;
        twOnOffImageView.setEnabled(false);
        twOnOffImageView.setAlpha(0.9f);
        fbOnOffImageView.setImageResource(R.drawable.switch_on);
    }

    private void setFbBtnStateOFF() {
        isFbOn = false;
        twOnOffImageView.setEnabled(true);
        twOnOffImageView.setAlpha(1f);
        fbOnOffImageView.setImageResource(R.drawable.swich_off);
    }

    private void onFbBtnClick() {
        if (isFbOn) {
            setFbBtnStateOFF();
            logout();
        } else {
            setFbBtnStateOn();
            requestFbLogin();
        }
    }

    private void onTwitterBtnClick() {
        if (isTwitterOn) {
            setTwitterBtnStateOFF();
            logout();
        } else {
            setTwitterBtnStateOn();
            requestTwitterLogin();

        }
    }

    private void requestTwitterLogin() {
        showPogress(activity.getString(R.string.twitterText), activity.getResources().getString(R.string.pleaseWaitText));
        ((AirZoonMapActivity) activity).requestTwitterLogin(this);
    }

    private void requestFbLogin() {
        showPogress(activity.getString(R.string.facebookText), activity.getResources().getString(R.string.pleaseWaitText));
        ((AirZoonMapActivity) activity).requestFBLogin(this);
    }

    private void logout() {
        Toast.makeText(activity, activity.getResources().getString(R.string.logoutSuccessfullyText), Toast.LENGTH_SHORT).show();
        if (userProfileDO != null)
            userProfileDO.destroyProfile();
        resetProfile();
    }

    private void resetProfile() {
        userDPImageView.setImageResource(R.drawable.profile_default);
        userNameTextView.setText(activity.getString(R.string.noNameText));
        userAddressTextView.setText(activity.getString(R.string.noEmailAddressText));
        phoneNumTextView.setText(activity.getString(R.string.noPhoneNumText));

    }


    @Override
    public void onSocialLoginSuccess(UserProfileDO userProfileDO, String socialType) {
        hideProgressLoading();
        this.userProfileDO = userProfileDO;
        Toast.makeText(activity, activity.getResources().getString(R.string.welcomeText) + " " + userProfileDO.getName(), Toast.LENGTH_SHORT).show();
        setProfileUI();

        if (socialType.equalsIgnoreCase(Constants.LOGIN_TYPE_FB)) {
            setFbBtnStateOn();
        } else {
            setTwitterBtnStateOn();
        }
        loginToOurServer();
    }

    private void loginToOurServer() {
        APIHandler apiHandler = new APIHandler(activity, this, RequestConstant.REQUEST_POST_NEW_USER,
                Request.Method.POST, URLConstants.URL_POST_NEW_USER_OR_EDIT_USER, false, "Posting user profile", userProfileDO.formJSONToPostNewProfile(), null);
        apiHandler.requestAPI();
    }

    private void setProfileUI() {
        loadProfileImage(userDPImageView, progressBar);
        userNameTextView.setText(userProfileDO.getName());

        if (userProfileDO.getEmail() != null && userProfileDO.getEmail().length() > 0) {
            userAddressTextView.setText(userProfileDO.getEmail());
        } else {
            userAddressTextView.setText(activity.getString(R.string.noEmailAddressText));
        }

        if (userProfileDO.getPhoneNum() != null && userProfileDO.getPhoneNum().length() > 0) {
            phoneNumTextView.setText(userProfileDO.getPhoneNum());
        } else {
            phoneNumTextView.setText(activity.getString(R.string.noPhoneNumText));
        }

    }


    @Override
    public void onSocialLoginFailure(String error, String socialType) {
        Toast.makeText(activity, "Login failed " + error, Toast.LENGTH_SHORT).show();

        hideProgressLoading();
    }

    @Override
    public void onSocialLoginCancel(String socialType) {
        hideProgressLoading();
    }


    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        System.out.println(">>response>>" + responseString);
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {
        System.out.println(">>response fail>>" + errorString);
    }
}

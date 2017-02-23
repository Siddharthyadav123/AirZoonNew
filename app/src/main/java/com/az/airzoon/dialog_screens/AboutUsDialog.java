package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.az.airzoon.BuildConfig;
import com.az.airzoon.R;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.screens.TutorialActivity;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;

/**
 * Created by sid on 30/07/2016.
 */
public class AboutUsDialog extends AbstractBaseDialog implements APICallback {

    private ImageView closeProfileImageView;
    private TextView fbBtnTextView;
    private TextView twitterBtnTextView;
    private TextView sendFeedbackBtnTextView;
    private TextView watchTheGuideBtnTextView;
    private TextView bodyText;
    private TextView versionTextView;
    private TextView privacyPolicyTextView;

    public AboutUsDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.about_us_dialog, null);
    }

    @Override
    public void initViews(View view) {
        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);
        fbBtnTextView = (TextView) view.findViewById(R.id.fbBtnTextView);
        twitterBtnTextView = (TextView) view.findViewById(R.id.twitterBtnTextView);
        sendFeedbackBtnTextView = (TextView) view.findViewById(R.id.sendFeedbackBtnTextView);
        watchTheGuideBtnTextView = (TextView) view.findViewById(R.id.watchTheGuideBtnTextView);
        bodyText = (TextView) view.findViewById(R.id.bodyText);
        versionTextView = (TextView) view.findViewById(R.id.versionTextView);
        privacyPolicyTextView = (TextView) view.findViewById(R.id.privacyPolicyTextView);
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        fbBtnTextView.setOnClickListener(this);
        twitterBtnTextView.setOnClickListener(this);
        sendFeedbackBtnTextView.setOnClickListener(this);
        watchTheGuideBtnTextView.setOnClickListener(this);
        privacyPolicyTextView.setOnClickListener(this);
    }

    @Override
    public void setInfoInUI(View view) {
        bodyText.setText(activity.getResources().getString(R.string.aboutUsBody1) + "\n\n" +
                activity.getResources().getString(R.string.aboutUsBody2));

//        versionTextView.setText("Version " + BuildConfig.VERSION_NAME);

        //server doesn't have frech text
        //requesting
        APIHandler apiHandler = new APIHandler(activity, this, RequestConstant.REQUEST_GET_ABOUT_US_BODY_TEXT,
                Request.Method.GET, URLConstants.URL_GET_ABOUT_US_BODY_TEXT, false,
                null, null, null, null);
        apiHandler.setShowToastOnRespone(false);
        apiHandler.requestAPI();

    }

    @Override
    public void onClickEvent(View actionView) {
        switch (actionView.getId()) {
            case R.id.closeProfileImageView:
                dismiss();
                break;
            case R.id.fbBtnTextView:
                onFbBtnClick();
                break;
            case R.id.twitterBtnTextView:
                onTwitterBtnClick();
                break;
            case R.id.sendFeedbackBtnTextView:
                onSendFeedbackBtnClick();
                break;
            case R.id.watchTheGuideBtnTextView:
                onWatchTheGuideBtnClick();
                break;
            case R.id.privacyPolicyTextView:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://airzoonapp.com/privacy-policy.html"));
                getContext().startActivity(browserIntent);
                break;
        }
    }

    @Override
    public void onDailogYesClick() {

    }

    @Override
    public void onDailogNoClick() {

    }

    private void onWatchTheGuideBtnClick() {
        Intent i = new Intent(activity, TutorialActivity.class);
        activity.startActivity(i);
    }

    private void onSendFeedbackBtnClick() {
        dismiss();
        SendAFeedbackDialog sendAFeedbackDialog = new SendAFeedbackDialog(activity);
        sendAFeedbackDialog.showDialog(SendAFeedbackDialog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }

    private void onTwitterBtnClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/airZoonWifi"));
        activity.startActivity(browserIntent);
    }

    private void onFbBtnClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/airzoon"));
        activity.startActivity(browserIntent);
    }

    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        bodyText.setText(responseString);
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }
}

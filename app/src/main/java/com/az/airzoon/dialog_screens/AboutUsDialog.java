package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.screens.TutorialActivity;

/**
 * Created by sid on 30/07/2016.
 */
public class AboutUsDialog extends AbstractBaseDialog {

    private ImageView closeProfileImageView;
    private TextView fbBtnTextView;
    private TextView twitterBtnTextView;
    private TextView sendFeedbackBtnTextView;
    private TextView watchTheGuideBtnTextView;

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
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        fbBtnTextView.setOnClickListener(this);
        twitterBtnTextView.setOnClickListener(this);
        sendFeedbackBtnTextView.setOnClickListener(this);
        watchTheGuideBtnTextView.setOnClickListener(this);
    }

    @Override
    public void setInfoInUI(View view) {

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
        }
    }

    private void onWatchTheGuideBtnClick() {
        Intent i = new Intent(activity, TutorialActivity.class);
        activity.startActivity(i);
    }

    private void onSendFeedbackBtnClick() {
        dismiss();
        SendAFeedbackDialog sendAFeedbackDialog = new SendAFeedbackDialog(activity);
        sendAFeedbackDialog.showDialog(SendAFeedbackDialog.ANIM_TYPE_LEFT_IN_RIGHT_OUT);
    }

    private void onTwitterBtnClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/airZoonWifi"));
        activity.startActivity(browserIntent);
    }

    private void onFbBtnClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/airzoon"));
        activity.startActivity(browserIntent);
    }
}

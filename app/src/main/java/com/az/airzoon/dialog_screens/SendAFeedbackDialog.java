package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.az.airzoon.R;

/**
 * Created by sid on 30/07/2016.
 */
public class SendAFeedbackDialog extends AbstractBaseDialog {

    private ImageView closeProfileImageView;
    private EditText emailEditText;
    private EditText commentEditText;
    private Button submitButton;

    public SendAFeedbackDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.feedback_layout, null);
    }

    @Override
    public void initViews(View view) {
        closeProfileImageView = (ImageView) findViewById(R.id.closeProfileImageView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        submitButton = (Button) findViewById(R.id.submitButton);
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        submitButton.setOnClickListener(this);
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
            case R.id.submitButton:
                onSubmitBtnClick();
                break;
        }
    }

    private void onSubmitBtnClick() {
        dismiss();
    }
}

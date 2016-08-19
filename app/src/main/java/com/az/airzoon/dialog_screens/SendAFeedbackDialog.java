package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;
import com.az.airzoon.volly.RequestParam;

import java.util.ArrayList;

/**
 * Created by sid on 30/07/2016.
 */
public class SendAFeedbackDialog extends AbstractBaseDialog implements APICallback {

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
        if (MyApplication.getInstance().getUserProfileDO().isLoggedInAlrady()) {
            emailEditText.setText(MyApplication.getInstance().getUserProfileDO().getEmail());
        }

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

    private boolean validateUI() {
        if (emailEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(activity, activity.getResources().getString(R.string.errorEnterEmailId), Toast.LENGTH_SHORT).show();
            return false;
        } else if (commentEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(activity, activity.getResources().getString(R.string.errorEnterComment), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void onSubmitBtnClick() {
        if (validateUI()) {
            ArrayList<RequestParam> requestParams = new ArrayList<>();
            requestParams.add(new RequestParam("email", emailEditText.getText().toString().trim()));
            requestParams.add(new RequestParam("feedback", commentEditText.getText().toString().trim()));

            APIHandler apiHandler = new APIHandler(activity, this, RequestConstant.REQUEST_POST_FEEDBACK,
                    Request.Method.POST, URLConstants.URL_POST_FEEDBACK, true,
                    activity.getResources().getString(R.string.sendingYourFeedbackText), null,
                    null, requestParams);
            apiHandler.requestAPI();
        }

    }

    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        Toast.makeText(activity, activity.getResources().getString(R.string.issuesReportedSuccessfullyText), Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }
}

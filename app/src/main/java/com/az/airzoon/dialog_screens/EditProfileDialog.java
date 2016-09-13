package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.az.airzoon.R;
import com.az.airzoon.adapters.SearchScreenSpinnerAdapter;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;

import java.util.ArrayList;

/**
 * Created by siddharth on 8/1/2016.
 */
public class EditProfileDialog extends AbstractBaseDialog implements APICallback {

    private ImageView closeProfileImageView;
    private ImageView userDPImageView;
    private EditText nameEditText;
    private TextView fbText;
    private TextView twText;
    private EditText emailEditText;
    private EditText phoneNumEditText;
    private Spinner genderSpinner;
    private TextView cloudeBackupTextView;
    private Button cancelButton;
    private Button submitButton;
    private ProgressBar progressBar;
    private RelativeLayout cludeBackupLayout;
    private ImageView cludeBackUpImage;

    private boolean isSaveToServer = true;


    public EditProfileDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.edit_profile_layout, null);
    }

    @Override
    public void initViews(View view) {
        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);
        userDPImageView = (ImageView) view.findViewById(R.id.userDPImageView);
        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        fbText = (TextView) view.findViewById(R.id.fbText);
        twText = (TextView) view.findViewById(R.id.twText);
        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        phoneNumEditText = (EditText) view.findViewById(R.id.phoneNumEditText);
        genderSpinner = (Spinner) view.findViewById(R.id.genderSpinner);
        cloudeBackupTextView = (TextView) view.findViewById(R.id.cloudeBackupTextView);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);
        submitButton = (Button) view.findViewById(R.id.submitButton);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        cludeBackupLayout = (RelativeLayout) view.findViewById(R.id.cludeBackupLayout);
        cludeBackUpImage = (ImageView) view.findViewById(R.id.cludeBackUpImage);
    }

    @Override
    public void registerEvnts(View view) {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        closeProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveBtnclick();
            }
        });
//        cludeBackupLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isSaveToServer) {
//                    isSaveToServer = false;
//                    cludeBackUpImage.setAlpha(0.5f);
//                } else {
//                    isSaveToServer = true;
//                    cludeBackUpImage.setAlpha(1f);
//                }
//
//            }
//        });
    }


    @Override
    public void setInfoInUI(View view) {
        ArrayList<String> itemList = new ArrayList<>();
        itemList.add(activity.getString(R.string.genderMaleText));
        itemList.add(activity.getString(R.string.genderFeMaleText));
        SearchScreenSpinnerAdapter searchScreenSpinnerAdapter = new SearchScreenSpinnerAdapter(activity, itemList);
        genderSpinner.setAdapter(searchScreenSpinnerAdapter);

        nameEditText.setText(userProfileDO.getName());
        if (userProfileDO.getLoginType().equalsIgnoreCase(Constants.LOGIN_TYPE_FB)) {
            fbText.setText(activity.getString(R.string.connectedText));
        } else {
            twText.setText(activity.getString(R.string.connectedText));
        }
        emailEditText.setText(userProfileDO.getEmail());
        phoneNumEditText.setText(userProfileDO.getPhoneNum());

        if (userProfileDO.getGender() != null && userProfileDO.getGender().length() > 0) {
            if (userProfileDO.getGender().equalsIgnoreCase("m") || userProfileDO.getGender().equalsIgnoreCase("male")) {
                genderSpinner.setSelection(0);
            } else {
                genderSpinner.setSelection(1);
            }
        }
        loadProfileImage(userDPImageView, progressBar);
    }

    private void onSaveBtnclick() {
        if (validateUI()) {
            userProfileDO.setName(nameEditText.getText().toString());
            userProfileDO.setEmail(emailEditText.getText().toString());
            userProfileDO.setPhoneNum(phoneNumEditText.getText().toString());
            if (genderSpinner.getSelectedItemPosition() == 0)
                userProfileDO.setGender("male");
            else
                userProfileDO.setGender("female");

            if (isSaveToServer) {
                //requesting
                APIHandler apiHandler = new APIHandler(activity, this, RequestConstant.REQUEST_POST_EDIT_USER,
                        Request.Method.POST, URLConstants.URL_POST_NEW_USER_OR_EDIT_USER, true,
                        activity.getResources().getString(R.string.loadingText), null, null,
                        userProfileDO.getRequestParamsToUpdateUserProfile());
                apiHandler.requestAPI();
            } else {
                dismiss();
                userProfileDO.saveProfile(userProfileDO.getLoginType());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showNormalDailog(activity.getResources().getString(R.string.savedSuccessfulText));
                    }
                });
            }


        }


    }

    private boolean validateUI() {
        if (nameEditText.getText().toString().trim().length() == 0) {
            showNormalDailog(activity.getResources().getString(R.string.errorEnterName));
            return false;
        } else if (emailEditText.getText().toString().trim().length() == 0) {
            showNormalDailog(activity.getResources().getString(R.string.errorEnterEmailId));
            return false;
        } else if (!isValidEmail(emailEditText.getText().toString().trim())) {
            showNormalDailog(activity.getResources().getString(R.string.errorEnterCorrectEmailId));
            return false;
        }
        return true;
    }


    @Override
    public void onClickEvent(View actionView) {

    }

    @Override
    public void onDailogYesClick() {

    }

    @Override
    public void onDailogNoClick() {

    }

    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        dismiss();
        userProfileDO.saveProfile(userProfileDO.getLoginType());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showNormalDailog(activity.getResources().getString(R.string.savedSuccessfulText));
            }
        });
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }


}

package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.adapters.SearchScreenSpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by siddharth on 8/1/2016.
 */
public class EditProfileDialog extends AbstractBaseDialog {

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
                dismiss();
            }
        });
    }

    @Override
    public void setInfoInUI(View view) {
        ArrayList<String> itemList = new ArrayList<>();
        itemList.add(activity.getString(R.string.genderMaleText));
        itemList.add(activity.getString(R.string.genderFeMaleText));
        SearchScreenSpinnerAdapter searchScreenSpinnerAdapter = new SearchScreenSpinnerAdapter(activity, itemList);
        genderSpinner.setAdapter(searchScreenSpinnerAdapter);
    }

    @Override
    public void onClickEvent(View actionView) {

    }
}

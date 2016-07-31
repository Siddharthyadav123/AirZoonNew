package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.az.airzoon.R;

/**
 * Created by sid on 31/07/2016.
 */
public class ReportIssueDialog extends AbstractBaseDialog {

    private final int FLAG_SELECTED = 0;
    private final int FLAG_NOT_SELECTED = 1;


    private ImageView nameCheckBoxImageView;
    private ImageView addressCheckBoxImageView;
    private ImageView categoryCheckBoxImageView;
    private ImageView hoursCheckBoxImageView;
    private ImageView phoneCheckBoxImageView;
    private ImageView speedCheckBoxImageView;

    private ImageView nameRICheckBoxImageView;
    private ImageView addressRICheckBoxImageView;
    private ImageView categoryRICheckBoxImageView;
    private ImageView hoursRICheckBoxImageView;
    private ImageView phoneRICheckBoxImageView;
    private ImageView speedRICheckBoxImageView;

    private EditText commentEditText;
    private ImageView closeProfileImageView;
    private Button sendButton;
    private Button dontMindButton;

    public ReportIssueDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.report_issue_layout, null);
    }

    @Override
    public void initViews(View view) {
        nameCheckBoxImageView = (ImageView) view.findViewById(R.id.nameCheckBoxImageView);
        addressCheckBoxImageView = (ImageView) view.findViewById(R.id.addressCheckBoxImageView);
        categoryCheckBoxImageView = (ImageView) view.findViewById(R.id.categoryCheckBoxImageView);
        hoursCheckBoxImageView = (ImageView) view.findViewById(R.id.hoursCheckBoxImageView);
        phoneCheckBoxImageView = (ImageView) view.findViewById(R.id.phoneCheckBoxImageView);
        speedCheckBoxImageView = (ImageView) view.findViewById(R.id.speedCheckBoxImageView);

        nameRICheckBoxImageView = (ImageView) view.findViewById(R.id.nameRICheckBoxImageView);
        addressRICheckBoxImageView = (ImageView) view.findViewById(R.id.addressRICheckBoxImageView);
        categoryRICheckBoxImageView = (ImageView) view.findViewById(R.id.categoryRICheckBoxImageView);
        hoursRICheckBoxImageView = (ImageView) view.findViewById(R.id.hoursRICheckBoxImageView);
        phoneRICheckBoxImageView = (ImageView) view.findViewById(R.id.phoneRICheckBoxImageView);
        speedRICheckBoxImageView = (ImageView) view.findViewById(R.id.speedRICheckBoxImageView);

        commentEditText = (EditText) view.findViewById(R.id.commentEditText);
        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);
        sendButton = (Button) view.findViewById(R.id.sendButton);
        dontMindButton = (Button) view.findViewById(R.id.dontMindButton);
    }

    @Override
    public void registerEvnts(View view) {
        nameCheckBoxImageView.setOnClickListener(this);
        addressCheckBoxImageView.setOnClickListener(this);
        categoryCheckBoxImageView.setOnClickListener(this);
        hoursCheckBoxImageView.setOnClickListener(this);
        phoneCheckBoxImageView.setOnClickListener(this);
        speedCheckBoxImageView.setOnClickListener(this);

        nameRICheckBoxImageView.setOnClickListener(this);
        addressRICheckBoxImageView.setOnClickListener(this);
        categoryRICheckBoxImageView.setOnClickListener(this);
        hoursRICheckBoxImageView.setOnClickListener(this);
        phoneRICheckBoxImageView.setOnClickListener(this);
        speedRICheckBoxImageView.setOnClickListener(this);

        closeProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        dontMindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void setInfoInUI(View view) {

    }

    @Override
    public void onClickEvent(View actionView) {
        if (actionView.getTag() != null) {
            if (Integer.parseInt(actionView.getTag().toString()) == FLAG_SELECTED) {
                actionView.setTag(FLAG_NOT_SELECTED);
                ((ImageView) actionView).setImageResource(R.drawable.com_facebook_button_check_off);
            } else {
                actionView.setTag(FLAG_SELECTED);
                ((ImageView) actionView).setImageResource(R.drawable.com_facebook_button_check_on);
            }
        } else {
            actionView.setTag(FLAG_SELECTED);
            ((ImageView) actionView).setImageResource(R.drawable.com_facebook_button_check_on);
        }

    }
}

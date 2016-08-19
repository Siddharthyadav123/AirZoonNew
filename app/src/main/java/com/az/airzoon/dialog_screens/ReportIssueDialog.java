package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.az.airzoon.R;

import java.util.HashMap;

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

    HashMap<String, String> reportHasmap = new HashMap<>();

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
        nameCheckBoxImageView.setTag("err_name");
        reportHasmap.put("err_name", "false");

        addressCheckBoxImageView = (ImageView) view.findViewById(R.id.addressCheckBoxImageView);
        addressCheckBoxImageView.setTag("err_address");
        reportHasmap.put("err_address", "false");


        categoryCheckBoxImageView = (ImageView) view.findViewById(R.id.categoryCheckBoxImageView);
        categoryCheckBoxImageView.setTag("err_category");
        reportHasmap.put("err_category", "false");


        hoursCheckBoxImageView = (ImageView) view.findViewById(R.id.hoursCheckBoxImageView);
        hoursCheckBoxImageView.setTag("err_hours");
        reportHasmap.put("err_hours", "false");

        phoneCheckBoxImageView = (ImageView) view.findViewById(R.id.phoneCheckBoxImageView);
        phoneCheckBoxImageView.setTag("err_phone");
        reportHasmap.put("err_phone", "false");

        speedCheckBoxImageView = (ImageView) view.findViewById(R.id.speedCheckBoxImageView);
        speedCheckBoxImageView.setTag("err_speed");
        reportHasmap.put("err_speed", "false");

        /////////resonse info
        nameRICheckBoxImageView = (ImageView) view.findViewById(R.id.nameRICheckBoxImageView);
        nameRICheckBoxImageView.setTag("req_name");
        reportHasmap.put("req_name", "false");

        addressRICheckBoxImageView = (ImageView) view.findViewById(R.id.addressRICheckBoxImageView);
        addressRICheckBoxImageView.setTag("req_address");
        reportHasmap.put("req_address", "false");

        categoryRICheckBoxImageView = (ImageView) view.findViewById(R.id.categoryRICheckBoxImageView);
        categoryRICheckBoxImageView.setTag("req_category");
        reportHasmap.put("req_category", "false");

        hoursRICheckBoxImageView = (ImageView) view.findViewById(R.id.hoursRICheckBoxImageView);
        hoursRICheckBoxImageView.setTag("req_hours");
        reportHasmap.put("req_hours", "false");

        phoneRICheckBoxImageView = (ImageView) view.findViewById(R.id.phoneRICheckBoxImageView);
        phoneRICheckBoxImageView.setTag("req_phone");
        reportHasmap.put("req_phone", "false");

        speedRICheckBoxImageView = (ImageView) view.findViewById(R.id.speedRICheckBoxImageView);
        speedRICheckBoxImageView.setTag("req_speed");
        reportHasmap.put("req_speed", "false");

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
        if (reportHasmap.get(actionView.getTag().toString()).equals("true")) {
            reportHasmap.put(actionView.getTag().toString(), "false");
            ((ImageView) actionView).setImageResource(R.drawable.com_facebook_button_check_off);
        } else {
            reportHasmap.put(actionView.getTag().toString(), "true");
            ((ImageView) actionView).setImageResource(R.drawable.com_facebook_button_check_on);
        }
    }
}

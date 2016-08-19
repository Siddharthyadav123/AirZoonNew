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
import java.util.HashMap;

/**
 * Created by sid on 31/07/2016.
 */
public class ReportIssueDialog extends AbstractBaseDialog implements APICallback {


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

    private String airZoonName;

    public ReportIssueDialog(Context context, String airZoonName) {
        super(context);
        this.airZoonName = airZoonName;
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.report_issue_layout, null);
    }

    @Override
    public void initViews(View view) {
        nameCheckBoxImageView = (ImageView) view.findViewById(R.id.nameCheckBoxImageView);
        nameCheckBoxImageView.setTag("nameError");
        reportHasmap.put("nameError", "none");

        addressCheckBoxImageView = (ImageView) view.findViewById(R.id.addressCheckBoxImageView);
        addressCheckBoxImageView.setTag("addressError");
        reportHasmap.put("addressError", "none");


        categoryCheckBoxImageView = (ImageView) view.findViewById(R.id.categoryCheckBoxImageView);
        categoryCheckBoxImageView.setTag("categoryError");
        reportHasmap.put("categoryError", "none");


        hoursCheckBoxImageView = (ImageView) view.findViewById(R.id.hoursCheckBoxImageView);
        hoursCheckBoxImageView.setTag("hourError");
        reportHasmap.put("hourError", "none");

        phoneCheckBoxImageView = (ImageView) view.findViewById(R.id.phoneCheckBoxImageView);
        phoneCheckBoxImageView.setTag("phoneError");
        reportHasmap.put("phoneError", "none");

        speedCheckBoxImageView = (ImageView) view.findViewById(R.id.speedCheckBoxImageView);
        speedCheckBoxImageView.setTag("speedError");
        reportHasmap.put("speedError", "none");

        /////////resonse info
        nameRICheckBoxImageView = (ImageView) view.findViewById(R.id.nameRICheckBoxImageView);
        nameRICheckBoxImageView.setTag("nameRequest");
        reportHasmap.put("nameRequest", "none");

        addressRICheckBoxImageView = (ImageView) view.findViewById(R.id.addressRICheckBoxImageView);
        addressRICheckBoxImageView.setTag("addressRequest");
        reportHasmap.put("addressRequest", "none");

        categoryRICheckBoxImageView = (ImageView) view.findViewById(R.id.categoryRICheckBoxImageView);
        categoryRICheckBoxImageView.setTag("categoryRequest");
        reportHasmap.put("categoryRequest", "none");

        hoursRICheckBoxImageView = (ImageView) view.findViewById(R.id.hoursRICheckBoxImageView);
        hoursRICheckBoxImageView.setTag("hourRequest");
        reportHasmap.put("hourRequest", "none");

        phoneRICheckBoxImageView = (ImageView) view.findViewById(R.id.phoneRICheckBoxImageView);
        phoneRICheckBoxImageView.setTag("phoneRequest");
        reportHasmap.put("phoneRequest", "none");

        speedRICheckBoxImageView = (ImageView) view.findViewById(R.id.speedRICheckBoxImageView);
        speedRICheckBoxImageView.setTag("speedRequest");
        reportHasmap.put("speedRequest", "none");

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
                onSendBtnClick();
            }
        });

        dontMindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void onSendBtnClick() {
        if (validateUI()) {
            //requesting
            APIHandler apiHandler = new APIHandler(activity, this, RequestConstant.REQUEST_POST_ERROR_REPORT,
                    Request.Method.POST, URLConstants.URL_POST_ERROR_REPORT, true,
                    activity.getResources().getString(R.string.reportingIssueText), null, null,
                    formRequestParams());

            apiHandler.requestAPI();
        }
    }

    private boolean validateUI() {
        if (commentEditText.getText().toString().trim().length() == 0) {
            Toast.makeText(activity, activity.getResources().getString(R.string.errorEnterComment),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void setInfoInUI(View view) {

    }

    @Override
    public void onClickEvent(View actionView) {
        if (reportHasmap.get(actionView.getTag().toString()).equals("none")) {
            reportHasmap.put(actionView.getTag().toString(), actionView.getTag().toString() + "Selected");
            ((ImageView) actionView).setImageResource(R.drawable.com_facebook_button_check_on);
        } else {
            reportHasmap.put(actionView.getTag().toString(), "none");
            ((ImageView) actionView).setImageResource(R.drawable.com_facebook_button_check_off);
        }
    }

    /**
     * hotspot, comment, err_name, err_address, err_category, err_hours, err_phone, err_speed,
     * req_name, req_address, req_category, req_hours, req_phone, req_speed,
     *
     * @return
     */
    private ArrayList<RequestParam> formRequestParams() {
        ArrayList<RequestParam> requestParams = new ArrayList<>();
        requestParams.add(new RequestParam("hotspot", airZoonName));
        requestParams.add(new RequestParam("comment", commentEditText.getText().toString()));

        requestParams.add(new RequestParam("err_name", reportHasmap.get("nameError")));
        requestParams.add(new RequestParam("err_address", reportHasmap.get("addressError")));
        requestParams.add(new RequestParam("err_category", reportHasmap.get("categoryError")));
        requestParams.add(new RequestParam("err_hours", reportHasmap.get("hourError")));
        requestParams.add(new RequestParam("err_phone", reportHasmap.get("phoneError")));
        requestParams.add(new RequestParam("err_speed", reportHasmap.get("speedError")));


        requestParams.add(new RequestParam("req_name", reportHasmap.get("nameRequest")));
        requestParams.add(new RequestParam("req_address", reportHasmap.get("addressRequest")));
        requestParams.add(new RequestParam("req_category", reportHasmap.get("categoryRequest")));
        requestParams.add(new RequestParam("req_hours", reportHasmap.get("hourRequest")));
        requestParams.add(new RequestParam("req_phone", reportHasmap.get("phoneRequest")));
        requestParams.add(new RequestParam("req_speed", reportHasmap.get("speedRequest")));

        return requestParams;
    }

    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        dismiss();
    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }
}

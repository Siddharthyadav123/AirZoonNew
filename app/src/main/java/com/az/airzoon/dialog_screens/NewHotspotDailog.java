package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.az.airzoon.R;
import com.az.airzoon.adapters.NewSpotSpinnerAdapter;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.constants.RequestConstant;
import com.az.airzoon.constants.URLConstants;
import com.az.airzoon.gps.LocationModel;
import com.az.airzoon.listeners.ImageCallback;
import com.az.airzoon.screens.AirZoonMapActivity;
import com.az.airzoon.screens.SearchResultActivity;
import com.az.airzoon.volly.APICallback;
import com.az.airzoon.volly.APIHandler;
import com.az.airzoon.volly.RequestParam;

import java.util.ArrayList;

/**
 * Created by sid on 31/07/2016.
 */
public class NewHotspotDailog extends AbstractBaseDialog implements APICallback, ImageCallback {

    private EditText enterSpotNameEditText;
    private ImageView clearSpotNameImageView;
    private Spinner hotSpotTypeSpinner;
    private Spinner hotSpotCategorySpinner;
    private EditText enterPhoneNumEditText;
    private EditText addressEditText;
    private TextView addImageTextView;
    private ImageView closeProfileImageView;
    private ImageView locImage;
    private ImageView imageToBeuploaded;

    private Button cancelButton;
    private Button submitButton;
    private String fetchedAddress = null;

    public NewHotspotDailog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.new_hotspot_layout, null);
    }

    @Override
    public void initViews(View view) {
        enterSpotNameEditText = (EditText) view.findViewById(R.id.enterSpotNameEditText);
        clearSpotNameImageView = (ImageView) view.findViewById(R.id.clearSpotNameImageView);
        hotSpotTypeSpinner = (Spinner) view.findViewById(R.id.hotSpotTypeSpinner);
        hotSpotCategorySpinner = (Spinner) view.findViewById(R.id.hotSpotCategorySpinner);
        enterPhoneNumEditText = (EditText) view.findViewById(R.id.enterPhoneNumEditText);
        addressEditText = (EditText) view.findViewById(R.id.addressEditText);
        addImageTextView = (TextView) view.findViewById(R.id.addImageTextView);
        locImage = (ImageView) view.findViewById(R.id.locImage);
        imageToBeuploaded = (ImageView) view.findViewById(R.id.imageToBeuploaded);

        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);

        cancelButton = (Button) view.findViewById(R.id.cancelButton);
        submitButton = (Button) view.findViewById(R.id.submitButton);
    }

    @Override
    public void registerEvnts(View view) {
        clearSpotNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterSpotNameEditText.setText("");
            }
        });


        addImageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity instanceof AirZoonMapActivity) {
                    ((AirZoonMapActivity) activity).showProfilePicBottomSheet(NewHotspotDailog.this);
                } else {
                    ((SearchResultActivity) activity).showProfilePicBottomSheet(NewHotspotDailog.this);
                }

            }
        });

        closeProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitBtnClick();
            }
        });

        locImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAddress();
            }
        });


    }

    private void requestAddress() {
        MyApplication.getInstance().getLocationModel().findMyAddress(activity, addressCallback);
    }

    LocationModel.AddressCallback addressCallback = new LocationModel.AddressCallback() {
        @Override
        public void onAddressResult(boolean isFound, String addressOrError) {
            if (isFound) {
                fetchedAddress = addressOrError;
                addressEditText.setText(fetchedAddress);
            } else {
                Toast.makeText(activity, addressOrError, Toast.LENGTH_SHORT).show();
            }
        }
    };


    //Parameters = spot_name, type, category, ph_no, address, image
    private ArrayList<RequestParam> getRequestParams() {
        ArrayList<RequestParam> requestParams = new ArrayList<>();
        requestParams.add(new RequestParam("spot_name", enterSpotNameEditText.getText().toString().trim()));
        requestParams.add(new RequestParam("type", getHotSpotType()));
        requestParams.add(new RequestParam("category", getHotSpotCat()));
        requestParams.add(new RequestParam("ph_no", enterPhoneNumEditText.getText().toString().trim()));
        requestParams.add(new RequestParam("address", addressEditText.getText().toString().trim()));
        requestParams.add(new RequestParam("image", "non.png"));
        return requestParams;
    }

    private String getHotSpotCat() {
        switch (hotSpotCategorySpinner.getSelectedItemPosition()) {
            case 0:
                return Constants.HOTSPOT_TYPE_AIRZOON;
            case 1:
                return Constants.HOTSPOT_TYPE_PAID;
            case 2:
                return Constants.HOTSPOT_TYPE_FREE;
        }
        return null;
    }

    private String getHotSpotType() {
        switch (hotSpotTypeSpinner.getSelectedItemPosition()) {
            case 0:
                return Constants.HOTSPOT_CATEGORY_AIRPORT;
            case 1:
                return Constants.HOTSPOT_CATEGORY_BAR;
            case 2:
                return Constants.HOTSPOT_CATEGORY_BARBER;
            case 3:
                return Constants.HOTSPOT_CATEGORY_FAST_FOOD;
            case 4:
                return Constants.HOTSPOT_CATEGORY_HOTEL;
            case 5:
                return Constants.HOTSPOT_CATEGORY_MALL;
            case 6:
                return Constants.HOTSPOT_CATEGORY_OTHER;
            case 7:
                return Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES;
            case 8:
                return Constants.HOTSPOT_CATEGORY_RESTAURANT;
            case 9:
                return Constants.HOTSPOT_CATEGORY_SPORT_CENTER;
            case 10:
                return Constants.HOTSPOT_CATEGORY_HEALTH_AND_WELLNESS;

        }
        return null;
    }

    private void onSubmitBtnClick() {
        if (MyApplication.getInstance().getUserProfileDO().isLoggedInAlrady()) {
            if (validateUI()) {
                //requesting
                APIHandler apiHandler = new APIHandler(activity, this, RequestConstant.REQUEST_POST_NEW_SPOT,
                        Request.Method.POST, URLConstants.URL_POST_NEW_SPOT, true,
                        activity.getResources().getString(R.string.pleaseWaitText), null, null, getRequestParams());
                apiHandler.requestAPI();
            }
        } else {
            MyApplication.getInstance().showNormalDailog(activity, activity.getResources().getString(R.string.loginErrorText));
        }
    }

    @Override
    public void setInfoInUI(View view) {
        setHotSpotTypeSpinner();
        setHotSpotCategorySpinner();
    }

    private void setHotSpotTypeSpinner() {
        ArrayList<String> hotSpotTypeList = new ArrayList<>();
        hotSpotTypeList.add(Constants.HOTSPOT_TYPE_AIRZOON);
        hotSpotTypeList.add(Constants.HOTSPOT_TYPE_PAID);
        hotSpotTypeList.add(Constants.HOTSPOT_TYPE_FREE);

        NewSpotSpinnerAdapter newSpotSpinnerAdapter = new NewSpotSpinnerAdapter(activity, hotSpotTypeList);
        hotSpotTypeSpinner.setAdapter(newSpotSpinnerAdapter);

    }

    private void setHotSpotCategorySpinner() {
        ArrayList<String> hotSpotCategoryList = new ArrayList<>();
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_AIRPORT);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_BAR);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_BARBER);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_FAST_FOOD);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_HOTEL);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_MALL);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_OTHER);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_RESTAURANT);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_SPORT_CENTER);
        hotSpotCategoryList.add(Constants.HOTSPOT_CATEGORY_HEALTH_AND_WELLNESS);

        NewSpotSpinnerAdapter newSpotSpinnerAdapter = new NewSpotSpinnerAdapter(activity, hotSpotCategoryList);
        hotSpotCategorySpinner.setAdapter(newSpotSpinnerAdapter);
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

    //spot_name, type, category, ph_no, address, image
    public boolean validateUI() {
        if (enterSpotNameEditText.getText().toString().trim().length() == 0
                && enterPhoneNumEditText.getText().toString().trim().length() == 0
                && addressEditText.getText().toString().trim().length() == 0) {

            showNormalDailog(activity.getResources().getString(R.string.pleaseFillText)
                    + " " + activity.getResources().getString(R.string.spotNameText)
                    + ", " + activity.getResources().getString(R.string.contactNumText)
                    + ", " + activity.getResources().getString(R.string.streetAddressText)
            );
            return false;
        } else if (enterSpotNameEditText.getText().toString().trim().length() == 0) {
            showNormalDailog(activity.getResources().getString(R.string.pleaseFillText)
                    + " " + activity.getResources().getString(R.string.spotNameText));
            return false;
        } else if (enterPhoneNumEditText.getText().toString().trim().length() == 0) {

            showNormalDailog(activity.getResources().getString(R.string.pleaseFillText)
                    + " " + activity.getResources().getString(R.string.contactNumText));
            return false;
        } else if (addressEditText.getText().toString().trim().length() == 0) {

            showNormalDailog(activity.getResources().getString(R.string.pleaseFillText)
                    + " " + activity.getResources().getString(R.string.streetAddressText));
            return false;
        }
        return true;
    }

    @Override
    public void onAPISuccessResponse(int requestId, String responseString) {
        dismiss();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showNormalDailog(activity.getResources().getString(R.string.reportErrorResponse));
            }
        });

    }

    @Override
    public void onAPIFailureResponse(int requestId, String errorString) {

    }

    @Override
    public void onImageFetched(Bitmap bitmap) {
        if (bitmap != null) {
            imageToBeuploaded.setVisibility(View.VISIBLE);
            imageToBeuploaded.setImageBitmap(bitmap);
        } else {
            imageToBeuploaded.setVisibility(View.GONE);
        }

    }
}

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
import com.az.airzoon.adapters.NewSpotSpinnerAdapter;
import com.az.airzoon.constants.Constants;

import java.util.ArrayList;

/**
 * Created by sid on 31/07/2016.
 */
public class NewHotspotDailog extends AbstractBaseDialog {

    private EditText enterSpotNameEditText;
    private ImageView clearSpotNameImageView;
    private Spinner hotSpotTypeSpinner;
    private Spinner hotSpotCategorySpinner;
    private EditText enterPhoneNumEditText;
    private EditText addressEditText;
    private TextView addImageTextView;

    private Button cancelButton;
    private Button submitButton;


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
        cancelButton.setOnClickListener(new View.OnClickListener() {
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

        NewSpotSpinnerAdapter newSpotSpinnerAdapter = new NewSpotSpinnerAdapter(activity, hotSpotCategoryList);
        hotSpotCategorySpinner.setAdapter(newSpotSpinnerAdapter);
    }

    @Override
    public void onClickEvent(View actionView) {

    }
}

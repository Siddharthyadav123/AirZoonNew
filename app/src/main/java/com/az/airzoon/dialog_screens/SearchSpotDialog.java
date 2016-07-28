package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.az.airzoon.R;
import com.az.airzoon.adapters.SearchScreenSpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by sid on 28/07/2016.
 */
public class SearchSpotDialog extends AbstractBaseDialog {

    private ImageView closeProfileImageView;
    private EditText searchSpotNameEditText;
    private Spinner optionsSpinner;

    private Button searchButton;

    public SearchSpotDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.search_hotspot_layout, null);
    }

    @Override
    public void initViews(View view) {
        closeProfileImageView = (ImageView) findViewById(R.id.closeProfileImageView);
        searchSpotNameEditText = (EditText) findViewById(R.id.searchSpotNameEditText);
        optionsSpinner = (Spinner) findViewById(R.id.optionsSpinner);
        searchButton = (Button) findViewById(R.id.searchButton);
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setInfoInUI(View view) {
        ArrayList<String> options = new ArrayList<String>();
        options.add(activity.getString(R.string.allHotspotText));
        options.add(activity.getString(R.string.airzoonHotspotText));
        options.add(activity.getString(R.string.freeHotSpotText));
        options.add(activity.getString(R.string.paidHotspotText));
        SearchScreenSpinnerAdapter searchScreenSpinnerAdapter = new SearchScreenSpinnerAdapter(activity, options);
        optionsSpinner.setAdapter(searchScreenSpinnerAdapter);
    }

    @Override
    public void onClickEvent(View actionView) {
        switch (actionView.getId()) {
            case R.id.closeProfileImageView:
                dismiss();
                break;
            case R.id.searchButton:
                onSearchBtnClick();
                break;
        }
    }

    private void onSearchBtnClick() {

    }
}

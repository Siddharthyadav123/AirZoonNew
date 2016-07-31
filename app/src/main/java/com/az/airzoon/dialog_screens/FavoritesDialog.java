package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.az.airzoon.R;

/**
 * Created by sid on 30/07/2016.
 */
public class FavoritesDialog extends AbstractBaseDialog {

    private ImageView closeProfileImageView;
    private ListView favoritesListView;
    private Button submitButton;

    public FavoritesDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.favorities_layout, null);
    }

    @Override
    public void initViews(View view) {
        closeProfileImageView = (ImageView) findViewById(R.id.closeProfileImageView);
        favoritesListView = (ListView) findViewById(R.id.favoritesListView);
        submitButton = (Button) findViewById(R.id.submitButton);
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        favoritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
        NewHotspotDailog newHotspotDailog = new NewHotspotDailog(activity);
        newHotspotDailog.showDialog(NewHotspotDailog.ANIM_TYPE_LEFT_IN_RIGHT_OUT);
    }
}

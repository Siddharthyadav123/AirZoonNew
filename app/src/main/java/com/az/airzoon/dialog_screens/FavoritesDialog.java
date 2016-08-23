package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.az.airzoon.R;
import com.az.airzoon.adapters.FavoritesListAdapter;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.models.AirZoonModel;

import java.util.ArrayList;

/**
 * Created by sid on 30/07/2016.
 */
public class FavoritesDialog extends AbstractBaseDialog {

    private ImageView closeProfileImageView;
    private ListView favoritesListView;
    private Button submitButton;
    private ArrayList<AirZoonDo> faviourateList = null;

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
                dismiss();
                AirZoonDo airZoonDo = faviourateList.get(position);
                HotspotDetailDailog hotspotDetailDailog = new HotspotDetailDailog(activity, airZoonDo);
                hotspotDetailDailog.setOpenFromFav(true);
                hotspotDetailDailog.showDialog(HotspotDetailDailog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
            }
        });
    }


    @Override
    public void setInfoInUI(View view) {
        faviourateList = AirZoonModel.getInstance().getFaviorateHotSpotList();
        FavoritesListAdapter favoritesListAdapter = new FavoritesListAdapter(activity, faviourateList);
        favoritesListView.setAdapter(favoritesListAdapter);
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

    @Override
    public void onDailogYesClick() {

    }

    @Override
    public void onDailogNoClick() {

    }

    private void onSubmitBtnClick() {
        dismiss();
        NewHotspotDailog newHotspotDailog = new NewHotspotDailog(activity);
        newHotspotDailog.showDialog(NewHotspotDailog.ANIM_TYPE_BOTTOM_IN_BOTTOM_OUT);
    }
}

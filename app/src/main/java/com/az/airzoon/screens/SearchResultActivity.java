package com.az.airzoon.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.adapters.SearchResultListAdapter;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.models.AirZoonModel;

import java.util.ArrayList;

/**
 * Created by sid on 29/07/2016.
 */
public class SearchResultActivity extends Activity {

    public static final String KEY_FILTER_TEXT = "keyFilterText";
    public static final String KEY_FILTER_TYPE = "keyFilterType";

    private ImageView backBtnImageView;
    private Button submitAHotspotButton;
    private ListView hotspotListView;
    private TextView infoText;

    private ArrayList<AirZoonDo> airZoonDoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);
        initViews();
        registerEvents();
        retriveIntentAndDecideSearchList();
    }


    private void initViews() {
        backBtnImageView = (ImageView) findViewById(R.id.backBtnImageView);
        submitAHotspotButton = (Button) findViewById(R.id.submitAHotspotButton);
        hotspotListView = (ListView) findViewById(R.id.hotspotListView);
        infoText = (TextView) findViewById(R.id.infoText);
    }

    private void registerEvents() {
        backBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitAHotspotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hotspotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void retriveIntentAndDecideSearchList() {
        String filterText = getIntent().getStringExtra(KEY_FILTER_TEXT);
        String hotSpotType = getIntent().getStringExtra(KEY_FILTER_TYPE);

        System.out.println(">>hotSpotType" + hotSpotType);

        if (filterText != null && filterText.toString().trim().length() > 0) {
            if (hotSpotType != null && hotSpotType.toString().trim().length() > 0) {
                //by search text and type
                airZoonDoArrayList = AirZoonModel.getInstance().getHotSpotListBySearchTextAndType(filterText, hotSpotType);
            } else {
                //by search text only
                airZoonDoArrayList = AirZoonModel.getInstance().getHotSpotListBySearchTextOnly(filterText);
            }
        } else {
            if (hotSpotType != null && hotSpotType.toString().trim().length() > 0) {
                //by type only
                airZoonDoArrayList = AirZoonModel.getInstance().getHotSpotListByType(hotSpotType);
            } else {
                //all list
                airZoonDoArrayList = AirZoonModel.getInstance().getAirZoonDoArrayList();
            }
        }

        //loading list view
        if (airZoonDoArrayList.size() > 0) {
            loadListView();
        } else {
            infoText.setVisibility(View.VISIBLE);
        }


    }

    private void loadListView() {
        SearchResultListAdapter searchResultListAdapter = new SearchResultListAdapter(this, airZoonDoArrayList);
        hotspotListView.setAdapter(searchResultListAdapter);
    }


}

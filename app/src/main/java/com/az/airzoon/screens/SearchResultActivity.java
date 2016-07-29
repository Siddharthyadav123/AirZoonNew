package com.az.airzoon.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.az.airzoon.R;
import com.az.airzoon.adapters.SearchResultListAdapter;

/**
 * Created by sid on 29/07/2016.
 */
public class SearchResultActivity extends Activity {

    private ImageView backBtnImageView;
    private Button submitAHotspotButton;
    private ListView hotspotListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);
        initViews();
        registerEvents();
        loadListView();
    }

    private void initViews() {
        backBtnImageView = (ImageView) findViewById(R.id.backBtnImageView);
        submitAHotspotButton = (Button) findViewById(R.id.submitAHotspotButton);
        hotspotListView = (ListView) findViewById(R.id.hotspotListView);
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

    private void loadListView() {
        SearchResultListAdapter searchResultListAdapter = new SearchResultListAdapter(this);
        hotspotListView.setAdapter(searchResultListAdapter);
    }
}

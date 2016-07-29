package com.az.airzoon.dialog_screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.az.airzoon.R;
import com.az.airzoon.adapters.FilterSettingAdapter;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.models.FilterSettingModel;

/**
 * Created by siddharth on 7/28/2016.
 */
public class FilterSettingsDialog extends AbstractBaseDialog {

    private ImageView closeProfileImageView;
    private ListView filterSettingListView;
    private Button doneButton;
    private FilterSettingAdapter filterSettingAdapter = null;

    public FilterSettingsDialog(Context context) {
        super(context);
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.filter_setting_layout, null);
    }

    @Override
    public void initViews(View view) {
        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);
        filterSettingListView = (ListView) view.findViewById(R.id.filterSettingListView);
        doneButton = (Button) view.findViewById(R.id.doneButton);
    }

    @Override
    public void registerEvnts(View view) {
        closeProfileImageView.setOnClickListener(this);
        doneButton.setOnClickListener(this);

        filterSettingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (FilterSettingModel.getInstance().getFilterSetting(Constants.FILTER_SETTING_ARRAY[position])) {
                    FilterSettingModel.getInstance().setFilterSetting(Constants.FILTER_SETTING_ARRAY[position], false);
                } else {
                    FilterSettingModel.getInstance().setFilterSetting(Constants.FILTER_SETTING_ARRAY[position], true);
                }
                filterSettingAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setInfoInUI(View view) {
        filterSettingAdapter = new FilterSettingAdapter(activity);
        filterSettingListView.setAdapter(filterSettingAdapter);
    }

    @Override
    public void onClickEvent(View actionView) {
        switch (actionView.getId()) {
            case R.id.closeProfileImageView:
                dismiss();
                break;
            case R.id.doneButton:
                dismiss();
                break;
        }
    }
}

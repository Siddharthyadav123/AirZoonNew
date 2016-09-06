package com.az.airzoon.dialog_screens;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.adapters.FilterSettingAdapter;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.models.FilterSettingModel;
import com.az.airzoon.screens.AirZoonMapActivity;

/**
 * Created by siddharth on 7/28/2016.
 */
public class FilterSettingsDialog extends AbstractBaseDialog {

    private ImageView closeProfileImageView;
    private ListView filterSettingListView;
    private Button doneButton;
    private FilterSettingAdapter filterSettingAdapter = null;

    private SeekBar rangeSeekBar;
    private TextView rangeKMTextView;

    private FilterSettingModel filterSettingModel;

    public FilterSettingsDialog(Context context) {
        super(context);
        filterSettingModel = FilterSettingModel.getInstance();
    }

    @Override
    public View setViews(LayoutInflater layoutInflater) {
        return layoutInflater.inflate(R.layout.filter_setting_layout, null);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initViews(View view) {
        closeProfileImageView = (ImageView) view.findViewById(R.id.closeProfileImageView);
        filterSettingListView = (ListView) view.findViewById(R.id.filterSettingListView);
        doneButton = (Button) view.findViewById(R.id.doneButton);

        rangeSeekBar = (SeekBar) view.findViewById(R.id.rangeSeekBar);
        rangeKMTextView = (TextView) view.findViewById(R.id.rangeKMTextView);

        // Change seekbar color to green.
        rangeSeekBar.getProgressDrawable().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
//        rangeSeekBar.getThumb().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);

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

        rangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    updateSeekRange(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void updateSeekRange(int rangeSeek) {
        filterSettingModel.setRangeSeek(rangeSeek);
        rangeSeekBar.setProgress(rangeSeek);
        if (rangeSeek > 0) {
            rangeKMTextView.setText("" + ((rangeSeek + 1) * 5) + "Km");
        } else {
            rangeKMTextView.setText("" + (1 * 5) + "Km");
        }
    }

    @Override
    public void setInfoInUI(View view) {
        int rangeSeek = filterSettingModel.getRangeSeek();
        updateSeekRange(rangeSeek);
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
                ((AirZoonMapActivity) activity).refreshAirZoonMapAsPerFileteration(null);
                int seekRange = filterSettingModel.getRangeSeek() * 5;
                boolean isCameraAnimated = ((AirZoonMapActivity) activity).animateToMeters(seekRange);
                dismiss();
                if (isCameraAnimated) {
                    showNormalDailog(activity.getResources().getString(R.string.settingSavedText));
                }
                break;
        }
    }

    @Override
    public void onDailogYesClick() {

    }

    @Override
    public void onDailogNoClick() {

    }
}

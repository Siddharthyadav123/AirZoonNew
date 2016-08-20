package com.az.airzoon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.models.FilterSettingModel;

/**
 * Created by sid on 29/07/2016.
 */
public class FilterSettingAdapter extends BaseAdapter {

    private Context context;

    public FilterSettingAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return Constants.FILTER_SETTING_ARRAY.length;
    }

    @Override
    public Object getItem(int position) {
        return Constants.FILTER_SETTING_ARRAY[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.filter_list_item, null);
        }

        TextView filterTypeText = (TextView) convertView.findViewById(R.id.filterTypeText);
        ImageView filterImage = (ImageView) convertView.findViewById(R.id.filterImage);
        ImageView filterCheckBoxImage = (ImageView) convertView.findViewById(R.id.filterCheckBoxImage);

        String category = Constants.FILTER_SETTING_ARRAY[position];
        filterTypeText.setText(AirZoonModel.getInstance().getHotSpotTypeCatLanguageProtedText(category));
        filterImage.setImageResource(AirZoonModel.getInstance().getHotSpotSmallImageResByCat(category));

        if (FilterSettingModel.getInstance().getFilterSetting(Constants.FILTER_SETTING_ARRAY[position])) {
            filterCheckBoxImage.setImageResource(R.drawable.selected);
        } else {
            filterCheckBoxImage.setImageResource(R.drawable.unselected);
        }

        return convertView;
    }
}

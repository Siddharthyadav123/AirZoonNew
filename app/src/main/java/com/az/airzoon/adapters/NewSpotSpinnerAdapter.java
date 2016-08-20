package com.az.airzoon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.models.AirZoonModel;

import java.util.ArrayList;

/**
 * Created by siddharth on 8/1/2016.
 */
public class NewSpotSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> itemList = new ArrayList<>();

    public NewSpotSpinnerAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        if (itemList != null && itemList.size() > 0) {
            return itemList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.new_spot_spinner_item, null);
        }
        RelativeLayout itemParentLayout = (RelativeLayout) convertView.findViewById(R.id.itemParentLayout);
        ImageView itemImageView = (ImageView) convertView.findViewById(R.id.itemImageView);
        TextView itemNameTextView = (TextView) convertView.findViewById(R.id.itemNameTextView);
        ImageView downArrow = (ImageView) convertView.findViewById(R.id.downArrow);

        downArrow.setVisibility(View.VISIBLE);
        itemNameTextView.setTextColor(context.getResources().getColor(R.color.White));

        itemImageView.setImageResource(AirZoonModel.getInstance().getHotSpotSmallImageResByCat(itemList.get(position)));

        itemNameTextView.setText(AirZoonModel.getInstance().getHotSpotTypeCatLanguageProtedText(itemList.get(position)));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.new_spot_spinner_item, null);
        }
        RelativeLayout itemParentLayout = (RelativeLayout) convertView.findViewById(R.id.itemParentLayout);
        ImageView itemImageView = (ImageView) convertView.findViewById(R.id.itemImageView);
        TextView itemNameTextView = (TextView) convertView.findViewById(R.id.itemNameTextView);
        itemParentLayout.setBackgroundColor(context.getResources().getColor(R.color.White));
        itemNameTextView.setTextColor(context.getResources().getColor(R.color.Black));

        itemParentLayout.setPadding(10, 0, 10, 0);

        itemImageView.setImageResource(AirZoonModel.getInstance().getHotSpotSmallImageResByCat(itemList.get(position)));

        itemNameTextView.setText(AirZoonModel.getInstance().getHotSpotTypeCatLanguageProtedText(itemList.get(position)));

        return convertView;
    }

}

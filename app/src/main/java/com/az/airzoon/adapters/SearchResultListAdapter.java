package com.az.airzoon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.models.AirZoonModel;

import java.util.ArrayList;

/**
 * Created by sid on 29/07/2016.
 */
public class SearchResultListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<AirZoonDo> airZoonDoArrayList = new ArrayList<>();

    public SearchResultListAdapter(Context context, ArrayList<AirZoonDo> airZoonDoArrayList) {
        this.context = context;
        this.airZoonDoArrayList = airZoonDoArrayList;
    }

    @Override
    public int getCount() {
        return airZoonDoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return airZoonDoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_result_list_item_layout, null);
        }
        TextView hotSpotName = (TextView) convertView.findViewById(R.id.hotSpotName);
        TextView hotSpotAddress = (TextView) convertView.findViewById(R.id.hotSpotAddress);
        TextView hotspotSpeedTextView = (TextView) convertView.findViewById(R.id.hotspotSpeedTextView);

        ImageView hotspotTypeImageView = (ImageView) convertView.findViewById(R.id.hotspotTypeImageView);
        ImageView hotspotCatImageView = (ImageView) convertView.findViewById(R.id.hotspotCatImageView);
        ImageView hotspotSpeedImageView = (ImageView) convertView.findViewById(R.id.hotspotSpeedImageView);


        AirZoonDo airZoonDo = airZoonDoArrayList.get(position);
        hotSpotName.setText(airZoonDo.getName());
        hotSpotAddress.setText(airZoonDo.getAddress() + " " + airZoonDo.getAddress2());
        hotspotSpeedTextView.setText(airZoonDo.getSpeed());

        hotspotTypeImageView.setImageResource(AirZoonModel.getInstance().getHotSpotMarkerResByType(airZoonDo.getType()));
        hotspotCatImageView.setImageResource(AirZoonModel.getInstance().getHotSpotSmallImageResByCat(airZoonDo.getCategory()));


        return convertView;
    }
}

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
 * Created by siddharth on 8/1/2016.
 */
public class FavoritesListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AirZoonDo> airZoonDoArrayList = null;

    public FavoritesListAdapter(Context context, ArrayList<AirZoonDo> airZoonDoArrayList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.favorites_list_item, null);
        }
        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView subNameTextView = (TextView) convertView.findViewById(R.id.subNameTextView);
        ImageView itemImageView = (ImageView) convertView.findViewById(R.id.itemImageView);
        AirZoonDo airZoonDo = airZoonDoArrayList.get(position);

        nameTextView.setText(airZoonDo.getName());
        subNameTextView.setText(airZoonDo.getAddress() + " " + airZoonDo.getAddress2());
        itemImageView.setImageResource(AirZoonModel.getInstance().getHotSpotBigImageResByCat(airZoonDo.getCategory()));
        return convertView;
    }
}

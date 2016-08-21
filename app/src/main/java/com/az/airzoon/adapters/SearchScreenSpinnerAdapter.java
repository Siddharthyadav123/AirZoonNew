package com.az.airzoon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.models.FontModel;

import java.util.ArrayList;

/**
 * Created by siddharth on 7/28/2016.
 */
public class SearchScreenSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> itemList = new ArrayList<>();

    public SearchScreenSpinnerAdapter(Context context, ArrayList<String> itemList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.basic_spinner_list_layout, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(itemList.get(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.basic_spinner_list_layout, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(itemList.get(position));
        textView.setTextColor(context.getResources().getColor(R.color.Black));
        return convertView;
    }

}

package com.az.airzoon.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    public TextView getView(int position, View convertView, ViewGroup parent) {
        TextView v = new TextView(context);
        v.setText(itemList.get(position));
        return v;
    }

    @Override
    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = new TextView(context);
        v.setText(itemList.get(position));
        return v;
    }

}

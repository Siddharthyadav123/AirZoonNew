package com.az.airzoon.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;

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
        v.setTextColor(context.getResources().getColor(R.color.White));
        v.setTextSize(MyApplication.getInstance().convertDpToPixel(8f, context));
        v.setText(itemList.get(position));
        int padding = (int) MyApplication.getInstance().convertDpToPixel(5f, context);
        v.setPadding(padding, padding, padding, padding);
        return v;
    }

    @Override
    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = new TextView(context);
        v.setTextSize(MyApplication.getInstance().convertDpToPixel(11f, context));
        v.setText(itemList.get(position));
        int padding = (int) MyApplication.getInstance().convertDpToPixel(8f, context);
        v.setPadding(padding, padding, padding, padding);
        return v;
    }

}

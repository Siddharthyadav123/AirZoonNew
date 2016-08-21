package com.az.airzoon.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.application.MyApplication;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.az.airzoon.models.AirZoonModel;
import com.az.airzoon.swipe_menu.SwipeMenu;
import com.az.airzoon.swipe_menu.SwipeMenuItem;
import com.az.airzoon.swipe_menu.SwipeMenuLayout;
import com.az.airzoon.swipe_menu.SwipeMenuListView;
import com.az.airzoon.swipe_menu.SwipeMenuView;

import java.util.ArrayList;

/**
 * Created by sid on 29/07/2016.
 */
public class SearchResultListAdapter extends BaseAdapter implements SwipeMenuView.OnSwipeItemClickListener {

    private Context context;
    private ArrayList<AirZoonDo> airZoonDoArrayList = new ArrayList<>();
    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener;

    public SearchResultListAdapter(Context context, ArrayList<AirZoonDo> airZoonDoArrayList, SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener) {
        this.context = context;
        this.airZoonDoArrayList = airZoonDoArrayList;
        this.onMenuItemClickListener = onMenuItemClickListener;
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
        return formSwipeLayout(position, convertView, parent);
    }

    private SwipeMenuLayout formSwipeLayout(int position, View convertView, ViewGroup parent) {
        SwipeMenu menu = new SwipeMenu(context);
        menu.setViewType(getItemViewType(position));
        createMenu(menu, position);

        SwipeMenuView menuView = null;
        SwipeMenuLayout layout = null;
        View mainView = null;

        if (convertView == null) {
            mainView = LayoutInflater.from(context).inflate(R.layout.search_result_list_item_layout, null);
            menuView = new SwipeMenuView(menu, (SwipeMenuListView) parent);
            menuView.setOnSwipeItemClickListener(this);
            SwipeMenuListView listView = (SwipeMenuListView) parent;
            layout = new SwipeMenuLayout(mainView, menuView,
                    listView.getCloseInterpolator(),
                    listView.getOpenInterpolator());
            layout.setPosition(position);
        } else {
            layout = (SwipeMenuLayout) convertView;
            menuView = layout.getMenuView();
            mainView = layout.getContentView();
            menuView.setmMenu(menu);
            menuView.refreshItems();
            layout.setPosition(position);
        }


        TextView hotSpotName = (TextView) mainView.findViewById(R.id.hotSpotName);
        TextView hotSpotAddress = (TextView) mainView.findViewById(R.id.hotSpotAddress);
        TextView hotspotSpeedTextView = (TextView) mainView.findViewById(R.id.hotspotSpeedTextView);
        ImageView hotspotTypeImageView = (ImageView) mainView.findViewById(R.id.hotspotTypeImageView);
        ImageView hotspotCatImageView = (ImageView) mainView.findViewById(R.id.hotspotCatImageView);
        ImageView hotspotSpeedImageView = (ImageView) mainView.findViewById(R.id.hotspotSpeedImageView);


        AirZoonDo airZoonDo = airZoonDoArrayList.get(position);
        hotSpotName.setText(airZoonDo.getName());
        hotSpotAddress.setText(airZoonDo.getAddress() + " " + airZoonDo.getAddress2());
        hotspotSpeedTextView.setText(airZoonDo.getSpeed());

        hotspotSpeedImageView.setImageResource(AirZoonModel.getInstance().getSpeeddoMeterImage(airZoonDo.getSpeed()));
        hotspotTypeImageView.setImageResource(AirZoonModel.getInstance().getHotSpotMarkerResByType(airZoonDo.getType()));
        hotspotCatImageView.setImageResource(AirZoonModel.getInstance().getHotSpotBigImageResByCat(airZoonDo.getCategory()));

        return layout;
    }


    private void createMenu(SwipeMenu menu, int position) {
        SwipeMenuItem item = new SwipeMenuItem(context);
        item.setBackground(new ColorDrawable(Color.TRANSPARENT));
        item.setWidth((int) MyApplication.getInstance().convertDpToPixel(120, context));
        if (airZoonDoArrayList.get(position).isFaviourate()) {
            item.setIcon(R.drawable.selectedstar);
        } else {
            item.setIcon(R.drawable.unselectedstar);
        }
        menu.addMenuItem(item);

//        item = new SwipeMenuItem(context);
//        item.setBackground(new ColorDrawable(Color.GRAY));
//        item.setWidth((int) MyApplication.getInstance().convertDpToPixel(90, context));
//        item.setIcon(R.drawable.share);
//        menu.addMenuItem(item);
        System.out.println(">>inside22>>" + menu.getMenuItems().size() + "  >>pos" + position);
    }

    @Override
    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, view, index);
        }
    }
}

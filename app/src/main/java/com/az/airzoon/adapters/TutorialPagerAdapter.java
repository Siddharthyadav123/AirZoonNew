package com.az.airzoon.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.az.airzoon.R;
import com.az.airzoon.screens.AirZoonMapActivity;

/**
 * Created by siddharth on 7/25/2016.
 */
public class TutorialPagerAdapter extends PagerAdapter {

    private Context context;

    private int[] pageHeaderTextArray =
            {R.string.tt_p1_header_text,
                    R.string.tt_p2_header_text,
                    R.string.tt_p3_header_text,
                    R.string.tt_p4_header_text};

    private int[] pageFooterTextArray = {R.string.tt_p1_footer_text,
            R.string.tt_p2_footer_text,
            R.string.tt_p3_footer_text,
            R.string.tt_p4_footer_text};

    private int[] pageBodyImagesArray = {R.drawable.slideone,
            R.drawable.slidefour};


    public TutorialPagerAdapter(Context context) {
        this.context = context;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tutorial_pager_item_layout, null, false);
        TextView headerTextView = (TextView) view.findViewById(R.id.headerTextView);
        TextView footerTextView = (TextView) view.findViewById(R.id.footerTextView);
        ImageView centerImage = (ImageView) view.findViewById(R.id.centerImage);
        TextView goTextView = (TextView) view.findViewById(R.id.goTextView);
        LinearLayout bodyLinLayoutPage2 = (LinearLayout) view.findViewById(R.id.bodyLinLayoutPage2);
        LinearLayout bodyLinLayoutPage3 = (LinearLayout) view.findViewById(R.id.bodyLinLayoutPage3);

        headerTextView.setText(pageHeaderTextArray[position]);
        footerTextView.setText(pageFooterTextArray[position]);

        try {
            if (position == 0) {
                bodyLinLayoutPage2.setVisibility(View.GONE);
                bodyLinLayoutPage3.setVisibility(View.GONE);
                centerImage.setVisibility(View.VISIBLE);
                centerImage.setBackgroundResource(pageBodyImagesArray[0]);
            } else if (position == 3) {
                bodyLinLayoutPage2.setVisibility(View.GONE);
                bodyLinLayoutPage3.setVisibility(View.GONE);
                centerImage.setVisibility(View.VISIBLE);
                centerImage.setImageResource(pageBodyImagesArray[1]);
            } else if (position == 1) {
                centerImage.setVisibility(View.GONE);
                bodyLinLayoutPage2.setVisibility(View.VISIBLE);
                bodyLinLayoutPage3.setVisibility(View.GONE);
            } else if (position == 2) {
                centerImage.setVisibility(View.GONE);
                bodyLinLayoutPage2.setVisibility(View.GONE);
                bodyLinLayoutPage3.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (position == 3) {
            goTextView.setVisibility(View.VISIBLE);
        } else {
            goTextView.setVisibility(View.GONE);
        }

        goTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                Intent i = new Intent(context, AirZoonMapActivity.class);
                activity.startActivity(i);
                activity.finish();
            }
        });


        container.addView(view);
        return view;
    }


    @Override
    public int getCount() {
        return pageFooterTextArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

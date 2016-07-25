package com.az.airzoon.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.az.airzoon.R;

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

    private int[] pageBodyImagesArray = {R.drawable.tutoial_p1,
            R.drawable.tutorial_p2,
            R.drawable.tutorial_p3,
            R.drawable.tutorial_p4};


    public TutorialPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tutorial_pager_item_layout, container, false);
        TextView headerTextView = (TextView) view.findViewById(R.id.headerTextView);
        TextView footerTextView = (TextView) view.findViewById(R.id.footerTextView);
        ImageView centerImage = (ImageView) view.findViewById(R.id.centerImage);

        headerTextView.setText(pageHeaderTextArray[position]);
        footerTextView.setText(pageFooterTextArray[position]);

        if (position == 0) {
            centerImage.setBackgroundResource(pageBodyImagesArray[position]);
        } else {
            centerImage.setImageResource(pageBodyImagesArray[position]);
        }


        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return pageBodyImagesArray.length;
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

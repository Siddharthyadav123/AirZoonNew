package com.az.airzoon.screens;
/**
 * Created by a on 6/20/2016.
 */

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.az.airzoon.R;
import com.az.airzoon.adapters.TutorialPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TutorialPagerAdapter tutorialPagerAdapter;
    private CircleIndicator pagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_layout);
        initViews();
        registerEvents();
        setTuorialPagerAdapter();
//        changeStatusBarColor();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.tutorialPager);
        pagerIndicator = (CircleIndicator) findViewById(R.id.pagerIndicator);
    }

    private void registerEvents() {

    }

    private void setTuorialPagerAdapter() {
        tutorialPagerAdapter = new TutorialPagerAdapter(this);
        viewPager.setAdapter(tutorialPagerAdapter);
        pagerIndicator.setViewPager(viewPager);
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


}

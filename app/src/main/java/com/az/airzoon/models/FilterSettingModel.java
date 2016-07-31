package com.az.airzoon.models;

import com.az.airzoon.constants.Constants;

/**
 * Created by sid on 29/07/2016.
 */
public class FilterSettingModel {
    private boolean filterFreeHotspotEnable = true;
    private boolean filterPaidHotspotEnable = true;
    private boolean filterAirzonHotspotEnable = true;

    private boolean filterHotelEnable = true;
    private boolean filterRestaurantEnable = true;
    private boolean filterSportsCenterEnable = true;
    private boolean filterBarEnable = true;
    private boolean filterFastFoodEnable = true;
    private boolean filterAirportEnable = true;
    private boolean filterBarberEnable = true;
    private boolean filterMallEnable = true;
    private boolean filterPancakesWafflesEnable = true;
    private boolean filterOtherEnable = true;

    public static FilterSettingModel filterSettingModel = null;

    public static int rangeSeek = 0;

    public static FilterSettingModel getInstance() {
        if (filterSettingModel == null) {
            filterSettingModel = new FilterSettingModel();
        }
        return filterSettingModel;
    }

    public void loadFilterSettings() {

    }


    public boolean getFilterSetting(String cateogry) {
        if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            return filterAirzonHotspotEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            return filterPaidHotspotEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_FREE)) {
            return filterFreeHotspotEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_RESTAURANT)) {
            return filterRestaurantEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HOTEL)) {
            return filterHotelEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_SPORT_CENTER)) {
            return filterSportsCenterEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BAR)) {
            return filterBarEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_FAST_FOOD)) {
            return filterFastFoodEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_MALL)) {
            return filterMallEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_AIRPORT)) {
            return filterAirportEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BARBER)) {
            return filterBarberEnable;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES)) {
            return filterPancakesWafflesEnable;
        } else {
            //others
            return filterOtherEnable;
        }

    }

    public void setFilterSetting(String cateogry, boolean status) {

        if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            filterAirzonHotspotEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            filterPaidHotspotEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_FREE)) {
            filterFreeHotspotEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_RESTAURANT)) {
            filterRestaurantEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HOTEL)) {
            filterHotelEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_SPORT_CENTER)) {
            filterSportsCenterEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BAR)) {
            filterBarEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_FAST_FOOD)) {
            filterFastFoodEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_MALL)) {
            filterMallEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_AIRPORT)) {
            filterAirportEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BARBER)) {
            filterBarberEnable = status;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES)) {
            filterPancakesWafflesEnable = status;
        } else {
            //others
            filterOtherEnable = status;
        }

    }

    public int getRangeSeek() {
        return rangeSeek;
    }

    public void setRangeSeek(int rangeSeek) {
        FilterSettingModel.rangeSeek = rangeSeek;
    }
}

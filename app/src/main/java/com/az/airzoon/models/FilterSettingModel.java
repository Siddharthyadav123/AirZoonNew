package com.az.airzoon.models;

import com.az.airzoon.application.MyApplication;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.preferences.PrefManager;

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
    private boolean filterHealthAndWellnessEnable = true;

    public static FilterSettingModel filterSettingModel = null;
    public static int rangeSeek = 0;
    private PrefManager prefManager;


    public static FilterSettingModel getInstance() {
        if (filterSettingModel == null) {
            filterSettingModel = new FilterSettingModel();
        }
        return filterSettingModel;
    }

    public void saveKey(String key, boolean value) {
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }
        prefManager.getEditor().putBoolean(key, value);
        prefManager.getEditor().commit();
    }

    public boolean getValue(String key) {
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }
        return prefManager.getPref().getBoolean(key, true);
    }

    public boolean getFilterSetting(String cateogry) {
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }

        if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            return filterAirzonHotspotEnable = getValue("filterAirzonHotspotEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            return filterPaidHotspotEnable = getValue("filterPaidHotspotEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_FREE)) {
            return filterFreeHotspotEnable = getValue("filterFreeHotspotEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_RESTAURANT)) {
            return filterRestaurantEnable = getValue("filterRestaurantEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HOTEL)) {
            return filterHotelEnable = getValue("filterHotelEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_SPORT_CENTER)) {
            return filterSportsCenterEnable = getValue("filterSportsCenterEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BAR)) {
            return filterBarEnable = getValue("filterBarEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_FAST_FOOD)) {
            return filterFastFoodEnable = getValue("filterFastFoodEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_MALL)) {
            return filterMallEnable = getValue("filterMallEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_AIRPORT)) {
            return filterAirportEnable = getValue("filterAirportEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BARBER)) {
            return filterBarberEnable = getValue("filterBarberEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES)) {
            return filterPancakesWafflesEnable = getValue("filterPancakesWafflesEnable");

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HEALTH_AND_WELLNESS)) {
            return filterHealthAndWellnessEnable = getValue("filterHealthAndWellnessEnable");

        } else {
            //others
            return filterOtherEnable = getValue("filterOtherEnable");
        }

    }


    public void setFilterSetting(String cateogry, boolean status) {
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }

        if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            filterAirzonHotspotEnable = status;
            saveKey("filterAirzonHotspotEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            filterPaidHotspotEnable = status;
            saveKey("filterPaidHotspotEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_FREE)) {
            filterFreeHotspotEnable = status;
            saveKey("filterFreeHotspotEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_RESTAURANT)) {
            filterRestaurantEnable = status;
            saveKey("filterRestaurantEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HOTEL)) {
            filterHotelEnable = status;
            saveKey("filterHotelEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_SPORT_CENTER)) {
            filterSportsCenterEnable = status;
            saveKey("filterSportsCenterEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BAR)) {
            filterBarEnable = status;
            saveKey("filterBarEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_FAST_FOOD)) {
            filterFastFoodEnable = status;
            saveKey("filterFastFoodEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_MALL)) {
            filterMallEnable = status;
            saveKey("filterMallEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_AIRPORT)) {
            filterAirportEnable = status;
            saveKey("filterAirportEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BARBER)) {
            filterBarberEnable = status;
            saveKey("filterBarberEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES)) {
            filterPancakesWafflesEnable = status;
            saveKey("filterPancakesWafflesEnable", status);

        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HEALTH_AND_WELLNESS)) {
            filterHealthAndWellnessEnable = status;
            saveKey("filterHealthAndWellnessEnable", status);

        } else {
            //others
            filterOtherEnable = status;
            saveKey("filterOtherEnable", status);
        }

    }


    public int getRangeSeek() {
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }
        return rangeSeek = prefManager.getPref().getInt("rangeSeek", 0);
    }

    public void setRangeSeek(int rangeSeek) {
        FilterSettingModel.rangeSeek = rangeSeek;
        if (prefManager == null) {
            prefManager = new PrefManager(MyApplication.getInstance());
        }
        prefManager.getEditor().putInt("rangeSeek", rangeSeek);
        prefManager.getEditor().commit();
    }
}

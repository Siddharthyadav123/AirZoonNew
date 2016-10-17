package com.az.airzoon.constants;

import com.az.airzoon.BuildConfig;

/**
 * Created by sid on 07/08/2016.
 */
public class URLConstants {
    public static final String BASE_URL = BuildConfig.BASE_URL;

    public static final String URL_GET_HOTSPOT_LIST = BASE_URL + "/json.php";
    public static final String URL_GET_ABOUT_US_BODY_TEXT = BASE_URL + "/about_json.php";
    public static final String URL_POST_NEW_SPOT = BASE_URL + "/app_spot.php";
    public static final String URL_POST_NEW_USER_OR_EDIT_USER = BASE_URL + "/user_json.php";
    public static final String URL_POST_FEEDBACK = BASE_URL + "/feedback.php";
    public static final String URL_POST_ERROR_REPORT = BASE_URL + "/helpus.php";
    public static final String URL_POST_FAVIOURATES = BASE_URL + "/fivourite.php";

}

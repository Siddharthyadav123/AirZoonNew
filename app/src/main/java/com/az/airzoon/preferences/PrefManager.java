package com.az.airzoon.preferences;

/**
 * Created by a on 6/20/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared preferences file name
    private static final String PREF_NAME = "airzoon_pref";


    private static final String KEY_FIRST_TIME_LAUNCH = "key_first_time_launch";
    private static final String KEY_MORE_INFO_SHOWN = "key_info_shown";
    private static final String KEY_LAST_SYNC_TIME = "key_last_sync_time";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(KEY_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setMoreInfoShown(boolean isMoreInfoShown) {
        editor.putBoolean(KEY_MORE_INFO_SHOWN, isMoreInfoShown);
        editor.commit();
    }

    public void setLastSyncTime(String lastSyncTime) {
        editor.putString(KEY_LAST_SYNC_TIME, lastSyncTime);
        editor.commit();
    }

    public boolean isFirstTimeAlreadyLaunched() {
        return pref.getBoolean(KEY_FIRST_TIME_LAUNCH, false);
    }

    public boolean isMoreInfoShown() {
        return pref.getBoolean(KEY_MORE_INFO_SHOWN, false);
    }

    public String getLstSyncTime() {
        return pref.getString(KEY_LAST_SYNC_TIME, null);
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
}

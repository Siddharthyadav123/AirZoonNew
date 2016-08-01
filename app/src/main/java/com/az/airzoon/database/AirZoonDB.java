package com.az.airzoon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sid on 01/08/2016.
 */
public class AirZoonDB extends SQLiteOpenHelper {

    public static final String AIRZOON_DB_NAME = "AIRZOON_DB";

    public static final String AIRZOON_TABLE = "airzoon_table";

    /**
     * Columns
     */
    private final String COL_ID = "id";
    private final String COL_NAME = "name";
    private final String COL_ZIP = "zip";
    private final String COL_PHONE = "phone";
    private final String COL_SPEED = "speed";
    private final String COL_IMAGE = "image";
    private final String COL_ADDRESS_TWO = "add_two";
    private final String COL_LONG = "long";
    private final String COL_LAT = "lat";
    private final String COL_TYPE = "type";
    private final String COL_DATE = "date";
    private final String COL_COUNTRY = "country";
    private final String COL_CITY = "city";
    private final String COL_OPENING_TWO = "opening_two";
    private final String COL_CATEGORY = "category";
    private final String COL_OPENING_ONE = "opening_one";
    private final String COL_ADDRESS = "address";
    private final String COL_CAT_IMAGE = "cat_image";
    private final String COL_FAV_COUNT = "fav_count";
    private final String COL_IS_FREE = "is_free";
    private final String COL_FAVIOURATE = "is_fav";


    public AirZoonDB(Context context) {
        super(context, AIRZOON_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

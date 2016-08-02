package com.az.airzoon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.az.airzoon.dataobjects.AirZoonDo;

/**
 * Created by sid on 01/08/2016.
 */
public class AirZoonDB extends SQLiteOpenHelper {
    // Database Column Type
    public final String TEXT_PRIMARY_KEY = " TEXT PRIMARY KEY";
    public final String TEXT = " TEXT";
    public final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY";
    public final String INTEGER = " INTEGER";
    public final String BOOLEAN = " BOOLEAN";


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

    private final String[] PROJECTION = new String[]{COL_ID,
            COL_NAME, COL_ZIP, COL_PHONE, COL_SPEED, COL_IMAGE, COL_ADDRESS_TWO,
            COL_LONG, COL_LAT, COL_TYPE, COL_DATE, COL_COUNTRY, COL_CITY, COL_OPENING_TWO, COL_CATEGORY,
            COL_OPENING_ONE, COL_ADDRESS, COL_CAT_IMAGE, COL_FAV_COUNT, COL_IS_FREE, COL_FAVIOURATE};


    private final String[] COLUMN_TYPE = new String[]{TEXT_PRIMARY_KEY,
            TEXT, TEXT, TEXT, TEXT, TEXT, TEXT,
            TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT, TEXT,
            TEXT, TEXT, TEXT, TEXT, TEXT, BOOLEAN};


    public AirZoonDB(Context context) {
        super(context, AIRZOON_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + AIRZOON_TABLE + " (";
        for (int i = 0; i < PROJECTION.length; i++) {
            if (i < (PROJECTION.length - 1))
                sql += PROJECTION[i] + COLUMN_TYPE[i] + ",";
            else
                sql += PROJECTION[i] + COLUMN_TYPE[i] + ");";
        }
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AIRZOON_TABLE);
        onCreate(db);
    }

    public void addAirZoonHotSpot(AirZoonDo airZoonDo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, airZoonDo.getId());
        contentValues.put(COL_NAME, airZoonDo.getName());
        contentValues.put(COL_ZIP, airZoonDo.getZip());
        contentValues.put(COL_PHONE, airZoonDo.getPhone());
        contentValues.put(COL_SPEED, airZoonDo.getSpeed());
        contentValues.put(COL_IMAGE, airZoonDo.getImage());
        contentValues.put(COL_ADDRESS_TWO, airZoonDo.getAddress2());
        contentValues.put(COL_LONG, airZoonDo.getLng());
        contentValues.put(COL_LAT, airZoonDo.getLat());
        contentValues.put(COL_TYPE, airZoonDo.getType());
        contentValues.put(COL_DATE, airZoonDo.getDate());
        contentValues.put(COL_COUNTRY, airZoonDo.getCountry());
        contentValues.put(COL_CITY, airZoonDo.getCity());
        contentValues.put(COL_OPENING_TWO, airZoonDo.getOpening_two());
        contentValues.put(COL_CATEGORY, airZoonDo.getCategory());
        contentValues.put(COL_OPENING_ONE, airZoonDo.getOpening_one());
        contentValues.put(COL_ADDRESS, airZoonDo.getAddress());
        contentValues.put(COL_CAT_IMAGE, airZoonDo.getCategory_image());
        contentValues.put(COL_FAV_COUNT, airZoonDo.getFav_count());
        contentValues.put(COL_IS_FREE, airZoonDo.getIs_free());
        contentValues.put(COL_FAVIOURATE, airZoonDo.isFaviourate());

        db.insert(AIRZOON_TABLE, null, contentValues);
    }

    public boolean updateAirzoonHotspot(String id, AirZoonDo airZoonDo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, airZoonDo.getName());
        contentValues.put(COL_ZIP, airZoonDo.getZip());
        contentValues.put(COL_PHONE, airZoonDo.getPhone());
        contentValues.put(COL_SPEED, airZoonDo.getSpeed());
        contentValues.put(COL_IMAGE, airZoonDo.getImage());
        contentValues.put(COL_ADDRESS_TWO, airZoonDo.getAddress2());
        contentValues.put(COL_LONG, airZoonDo.getLng());
        contentValues.put(COL_LAT, airZoonDo.getLat());
        contentValues.put(COL_TYPE, airZoonDo.getType());
        contentValues.put(COL_DATE, airZoonDo.getDate());
        contentValues.put(COL_COUNTRY, airZoonDo.getCountry());
        contentValues.put(COL_CITY, airZoonDo.getCity());
        contentValues.put(COL_OPENING_TWO, airZoonDo.getOpening_two());
        contentValues.put(COL_CATEGORY, airZoonDo.getCategory());
        contentValues.put(COL_OPENING_ONE, airZoonDo.getOpening_one());
        contentValues.put(COL_ADDRESS, airZoonDo.getAddress());
        contentValues.put(COL_CAT_IMAGE, airZoonDo.getCategory_image());
        contentValues.put(COL_FAV_COUNT, airZoonDo.getFav_count());
        contentValues.put(COL_IS_FREE, airZoonDo.getIs_free());
        contentValues.put(COL_FAVIOURATE, airZoonDo.isFaviourate());
        db.update(AIRZOON_TABLE, contentValues, COL_ID + " = ? ", new String[]{airZoonDo.getId()});
        return true;
    }
}

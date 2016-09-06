package com.az.airzoon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.az.airzoon.dataobjects.AirZoonDo;

import java.util.ArrayList;

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

    public void addOrUpdateAirZoonHotSpot(AirZoonDo airZoonDo) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + AIRZOON_TABLE + " WHERE " + COL_ID + " ='" + airZoonDo.getId() + "'", null);
        if (c.moveToFirst()) {
            updateAirzoonHotspot(airZoonDo);
        } else {
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

//            System.out.println(">>rec sid added>>" + airZoonDo.getId());
        }
    }

    private boolean updateAirzoonHotspot(AirZoonDo airZoonDo) {
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
//        contentValues.put(COL_FAVIOURATE, airZoonDo.isFaviourate());
        db.update(AIRZOON_TABLE, contentValues, COL_ID + " = ? ", new String[]{airZoonDo.getId()});

//        System.out.println(">>rec sid updated >>" + airZoonDo.getId());

        return true;
    }

    public boolean updateFav(AirZoonDo airZoonDo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FAVIOURATE, airZoonDo.isFaviourate());
        db.update(AIRZOON_TABLE, contentValues, COL_ID + " = ? ", new String[]{airZoonDo.getId()});

//        System.out.println(">>rec sid fav updated >>" + airZoonDo.getId());

        return true;
    }

    public boolean removeAllFav() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FAVIOURATE, false);
        int count = db.update(AIRZOON_TABLE, contentValues, null, null);

//        System.out.println(">>rec sid fav updated count >>" + count);

        return true;
    }

    // Getting All Contacts
    public ArrayList<AirZoonDo> getAllHotSpotList() {
        ArrayList<AirZoonDo> hotSpotList = new ArrayList<AirZoonDo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + AIRZOON_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AirZoonDo airZoonDo = new AirZoonDo();
                airZoonDo.setId(cursor.getString(cursor.getColumnIndex(COL_ID)));
                airZoonDo.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                airZoonDo.setZip(cursor.getString(cursor.getColumnIndex(COL_ZIP)));
                airZoonDo.setPhone(cursor.getString(cursor.getColumnIndex(COL_PHONE)));
                airZoonDo.setSpeed(cursor.getString(cursor.getColumnIndex(COL_SPEED)));
                airZoonDo.setImage(cursor.getString(cursor.getColumnIndex(COL_IMAGE)));
                airZoonDo.setAddress2(cursor.getString(cursor.getColumnIndex(COL_ADDRESS_TWO)));
                airZoonDo.setLng(cursor.getString(cursor.getColumnIndex(COL_LONG)));
                airZoonDo.setLat(cursor.getString(cursor.getColumnIndex(COL_LAT)));
                airZoonDo.setType(cursor.getString(cursor.getColumnIndex(COL_TYPE)));
                airZoonDo.setDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
                airZoonDo.setCountry(cursor.getString(cursor.getColumnIndex(COL_COUNTRY)));
                airZoonDo.setCity(cursor.getString(cursor.getColumnIndex(COL_CITY)));
                airZoonDo.setOpening_two(cursor.getString(cursor.getColumnIndex(COL_OPENING_TWO)));
                airZoonDo.setCategory(cursor.getString(cursor.getColumnIndex(COL_CATEGORY)));
                airZoonDo.setOpening_one(cursor.getString(cursor.getColumnIndex(COL_OPENING_ONE)));
                airZoonDo.setAddress(cursor.getString(cursor.getColumnIndex(COL_ADDRESS)));
                airZoonDo.setCategory_image(cursor.getString(cursor.getColumnIndex(COL_CAT_IMAGE)));
                airZoonDo.setFav_count(cursor.getString(cursor.getColumnIndex(COL_FAV_COUNT)));
                airZoonDo.setIs_free(cursor.getString(cursor.getColumnIndex(COL_IS_FREE)));
                airZoonDo.setFaviourate(cursor.getInt(cursor.getColumnIndex(COL_FAVIOURATE)) > 0);

                hotSpotList.add(airZoonDo);
//                System.out.println(">>rec sid read>>" + airZoonDo.getId());
            } while (cursor.moveToNext());
        }

//        System.out.println(">>rec sid total read size>>" + hotSpotList.size());

        return hotSpotList;
    }

    public int getTableCount() {
        String countQuery = "SELECT  * FROM " + AIRZOON_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
}

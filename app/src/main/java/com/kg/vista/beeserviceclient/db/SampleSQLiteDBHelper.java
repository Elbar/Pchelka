package com.kg.vista.beeserviceclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 6/21/17.
 */

public class SampleSQLiteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "requests_database";

    public static final String REQUEST_TABLE_NAME = "request";

    public static final String REQUEST_COLUMN_ID = "_id";

    public static final String REQUEST_COLUMN_SUBCATEGORY = "subcategory_name";

    public static final String REQUEST_COLUMN_DESC = "desc";

    public static final String REQUEST_COLUMN_CASH = "approx_cash";

    public static final String REQUEST_COLUMN_ADDRESS = "address";

    public static final String REQUEST_COLUMN_PHONE = "phone";

    public SampleSQLiteDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + REQUEST_TABLE_NAME + " (" +

                REQUEST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                REQUEST_COLUMN_SUBCATEGORY + " TEXT, " +

                REQUEST_COLUMN_DESC + " TEXT, " +

                REQUEST_COLUMN_CASH + " INT UNSIGNED, " +

                REQUEST_COLUMN_ADDRESS + " TEXT, " +

                REQUEST_COLUMN_PHONE + " TEXT" + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME);

        onCreate(db);



    }
}

package com.kg.vista.beeserviceclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "requests.db";
    private static final int DATABASE_VERSION = 2;

    public static final String REQUEST_TABLE_NAME = "request";
    public static final String REQUEST_COLUMN_ID = "_id";
    public static final String REQUEST_COLUMN_SUBCATEGORY = "subcategory";
    public static final String REQUEST_COLUMN_DESC = "desc";
    public static final String REQUEST_COLUMN_CASH = "cash";
    public static final String REQUEST_COLUMN_ADDRESS = "address";
    public static final String REQUEST_COLUMN_PHONE = "phone";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + REQUEST_TABLE_NAME +
                        "(" + REQUEST_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        REQUEST_COLUMN_SUBCATEGORY + " TEXT, " +
                        REQUEST_COLUMN_DESC + " TEXT, " +
                        REQUEST_COLUMN_CASH + " INTEGER, " +
                        REQUEST_COLUMN_ADDRESS + " TEXT, " +
                        REQUEST_COLUMN_PHONE+ " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertRequest(String subcategory, String desc, int cash, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(REQUEST_COLUMN_SUBCATEGORY, subcategory );
        contentValues.put(REQUEST_COLUMN_DESC, desc);
        contentValues.put(REQUEST_COLUMN_CASH, cash);
        contentValues.put(REQUEST_COLUMN_ADDRESS, address);
        contentValues.put(REQUEST_COLUMN_PHONE, phone);


        db.insert(REQUEST_TABLE_NAME, null, contentValues);
        return true;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REQUEST_TABLE_NAME);
        return numRows;
    }

//    public boolean updateRequest(Integer id, String name, String gender, int age) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(REQUEST_COLUMN_NAME, name);
//        contentValues.put(REQUEST_COLUMN_GENDER, gender);
//        contentValues.put(REQUEST_COLUMN_AGE, age);
//        db.update(REQUEST_TABLE_NAME, contentValues, REQUEST_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }

//    public Integer deleteRequest(Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(REQUEST_TABLE_NAME,
//                REQUEST_COLUMN_ID + " = ? ",
//                new String[] { Integer.toString(id) });
//    }

    public Cursor getRequest(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + REQUEST_TABLE_NAME + " WHERE " +
                REQUEST_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllRequests() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + REQUEST_TABLE_NAME, null );
        return res;
    }
}
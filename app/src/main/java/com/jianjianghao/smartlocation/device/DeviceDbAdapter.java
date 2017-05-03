package com.jianjianghao.smartlocation.device;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ag on 5/1/2015.
 */
public class DeviceDbAdapter {

    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_CONTENT = "content";
    public static final String COL_MAN = "man";
    public static final String COL_NUMBER = "number";
    public static final String COL_STATE = "state";
    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_NAME = INDEX_ID + 1;
    public static final int INDEX_CONTENT = INDEX_ID + 2;
    public static final int INDEX_MAN = INDEX_ID + 3;
    public static final int INDEX_NUMBER = INDEX_ID + 4;
    public static final int INDEX_STATE = INDEX_ID + 5;
    //used for logging
    private static final String TAG = "DeviceDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "db_devices";
    private static final String TABLE_NAME = "tbl_devices";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_NAME + " TEXT, " +
                    COL_CONTENT + " TEXT, " +
                    COL_MAN + " TEXT, " +
                    COL_NUMBER+ " TEXT, " +
                    COL_STATE + " INTEGER );";

    public DeviceDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }
    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }
    //close
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //CREATE
    //note that the id will be created for you automatically
    public void createDevice(String name,String content,String man,String number, boolean state) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_CONTENT, content);
        values.put(COL_MAN, man);
        values.put(COL_NUMBER, number);
        values.put(COL_STATE, state ? 1 : 0);
        mDb.insert(TABLE_NAME, null, values);
    }
    //overloaded to take a device
    public long createDevice(Device device) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, device.getName());
        values.put(COL_CONTENT, device.getContent());
        values.put(COL_MAN, device.getMan());
        values.put(COL_NUMBER, device.getNumber());
        values.put(COL_STATE, device.getState()); // Contact Device Number
    // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);
    }
    //READ
    public Device fetchDeviceById(int id) {

        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID,COL_NAME,
                        COL_CONTENT,COL_MAN,COL_NUMBER, COL_STATE}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        return new Device(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_NAME),
                cursor.getString(INDEX_CONTENT),
                cursor.getString(INDEX_MAN),
                cursor.getString(INDEX_NUMBER),
                cursor.getInt(INDEX_STATE)
        );
    }
    public Cursor fetchAllDevices() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,COL_NAME,
                        COL_CONTENT, COL_MAN,COL_NUMBER,COL_STATE},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //UPDATE
    public void updateDevice(Device device) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, device.getName());
        values.put(COL_CONTENT, device.getContent());
        values.put(COL_MAN, device.getMan());
        values.put(COL_NUMBER, device.getNumber());
        values.put(COL_STATE, device.getState());
        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(device.getId())});
    }
    //DELETE
    public void deleteDeviceById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }
    public void deleteAllDevices() {
        mDb.delete(TABLE_NAME, null, null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}

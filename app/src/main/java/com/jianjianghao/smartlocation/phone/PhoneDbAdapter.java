package com.jianjianghao.smartlocation.phone;

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
public class PhoneDbAdapter {

    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";
    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_NAME = INDEX_ID + 1;
    public static final int INDEX_CONTENT = INDEX_ID + 2;
    public static final int INDEX_IMPORTANT = INDEX_ID + 3;
    //used for logging
    private static final String TAG = "PhoneDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private static final String DATABASE_NAME = "db_phones";
    private static final String TABLE_NAME = "tbl_phones";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_NAME + " TEXT, " +
                    COL_CONTENT + " TEXT, " +
                    COL_IMPORTANT + " INTEGER );";

    public PhoneDbAdapter(Context ctx) {
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
    public void createReminder(String name,String content, boolean important) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_CONTENT, content);
        values.put(COL_IMPORTANT, important ? 1 : 0);
        mDb.insert(TABLE_NAME, null, values);
    }
    //overloaded to take a phone
    public long createReminder(Phone phone) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, phone.getName());
        values.put(COL_CONTENT, phone.getContent()); // Contact Name
        values.put(COL_IMPORTANT, phone.getImportant()); // Contact Device Number
    // Inserting Row
        return mDb.insert(TABLE_NAME, null, values);
    }
    //READ
    public Phone fetchReminderById(int id) {

        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID,COL_NAME,
                        COL_CONTENT, COL_IMPORTANT}, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        return new Phone(
                cursor.getInt(INDEX_ID),
                cursor.getString(INDEX_NAME),
                cursor.getString(INDEX_CONTENT),
                cursor.getInt(INDEX_IMPORTANT)
        );
    }
    public Cursor fetchAllReminders() {
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID,COL_NAME,
                        COL_CONTENT, COL_IMPORTANT},
                null, null, null, null, null
        );
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //UPDATE
    public void updateReminder(Phone phone) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, phone.getName());
        values.put(COL_CONTENT, phone.getContent());
        values.put(COL_IMPORTANT, phone.getImportant());
        mDb.update(TABLE_NAME, values,
                COL_ID + "=?", new String[]{String.valueOf(phone.getId())});
    }
    //DELETE
    public void deleteReminderById(int nId) {
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }
    public void deleteAllReminders() {
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

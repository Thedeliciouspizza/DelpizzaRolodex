package com.example.rolodex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class RolodexDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rolodex.db";
    private static final int DATABASE_VERSION = 1;

    public RolodexDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " TEXT PRIMARY KEY," +
                COLUMN_FIRST_NAME + " TEXT," +
                COLUMN_LAST_NAME + " TEXT," +
                COLUMN_MIDDLE_NAME + " TEXT," +
                COLUMN_PHONE_NUMBER + " TEXT" +
                ")";
        db.execSQL(createTableSql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public static final String TABLE_NAME = "rolodex_records";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
}

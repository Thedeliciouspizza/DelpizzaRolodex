package com.example.rolodex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

public class RolodexFileStorage {
    // We create this class for database management where we can read, write, save, and delete rolodex contacts
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase database;

    public RolodexFileStorage(Context context) {
        RolodexDatabaseHelper dbHelper = new RolodexDatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    public ArrayList<RolodexRecord> readRecords() { // We try to find the file where all the rolodex contacts are stored
        ArrayList<RolodexRecord> records = new ArrayList<>();
        Cursor cursor = database.query(RolodexDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(RolodexDatabaseHelper.COLUMN_ID);
            int firstNameIndex = cursor.getColumnIndex(RolodexDatabaseHelper.COLUMN_FIRST_NAME);
            int lastNameIndex = cursor.getColumnIndex(RolodexDatabaseHelper.COLUMN_LAST_NAME);
            int middleNameIndex = cursor.getColumnIndex(RolodexDatabaseHelper.COLUMN_MIDDLE_NAME);
            int phoneNumberIndex = cursor.getColumnIndex(RolodexDatabaseHelper.COLUMN_PHONE_NUMBER);

            do {
                String id = cursor.getString(idIndex);
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                String middleName = cursor.getString(middleNameIndex);
                String phoneNumber = cursor.getString(phoneNumberIndex);

                RolodexRecord record = new RolodexRecord(id, firstName, lastName, middleName, phoneNumber);
                records.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return records;
    }

    public void writeRecords(ArrayList<RolodexRecord> records) {
        database.beginTransaction();
        try {
            for (RolodexRecord record : records) {
                ContentValues values = new ContentValues();
                values.put(RolodexDatabaseHelper.COLUMN_ID, record.getId());
                values.put(RolodexDatabaseHelper.COLUMN_FIRST_NAME, record.getFirstName());
                values.put(RolodexDatabaseHelper.COLUMN_LAST_NAME, record.getLastName());
                values.put(RolodexDatabaseHelper.COLUMN_MIDDLE_NAME, record.getMiddleName());
                values.put(RolodexDatabaseHelper.COLUMN_PHONE_NUMBER, record.getPhoneNumber());

                database.insertWithOnConflict(RolodexDatabaseHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error writing records to database: " + e.getMessage());
        } finally {
            database.endTransaction();
        }
    }

    public void saveRecord(RolodexRecord record) {
        ContentValues values = new ContentValues();
        values.put(RolodexDatabaseHelper.COLUMN_ID, record.getId());
        values.put(RolodexDatabaseHelper.COLUMN_FIRST_NAME, record.getFirstName());
        values.put(RolodexDatabaseHelper.COLUMN_LAST_NAME, record.getLastName());
        values.put(RolodexDatabaseHelper.COLUMN_MIDDLE_NAME, record.getMiddleName());
        values.put(RolodexDatabaseHelper.COLUMN_PHONE_NUMBER, record.getPhoneNumber());

        database.insertWithOnConflict(RolodexDatabaseHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void deleteRecord(RolodexRecord recordToDelete) {
        String[] selectionArgs = {recordToDelete.getId()};
        database.delete(RolodexDatabaseHelper.TABLE_NAME, RolodexDatabaseHelper.COLUMN_ID + "=?", selectionArgs);
    }
}
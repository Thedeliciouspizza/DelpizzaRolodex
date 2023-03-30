package com.example.rolodex;

import android.content.Context;
import android.util.Log;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RolodexFileStorage {
    // We create this class for file management where we can read, write, save, and delete rolodex contacts
    private final String TAG = getClass().getSimpleName();
    private final String FILE_NAME = "rolodex_records.json";
    private final File file;
    private final Context context;
    private final Gson gson;

    public RolodexFileStorage(Context context) {
        this.context = context;
        this.gson = new Gson();
        this.file = new File(context.getFilesDir(), FILE_NAME);
    }

    public ArrayList<RolodexRecord> readRecords() { // We try to find the file where all the rolodex contacts are stored
        ArrayList<RolodexRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Type type = new TypeToken<ArrayList<RolodexRecord>>() {}.getType();
            records = gson.fromJson(reader, type);
        } catch (IOException e) {
            Log.e(TAG, "Error reading file: " + e.getMessage());
        }

        return records;
    }

    public void writeRecords(ArrayList<RolodexRecord> records) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(records, writer);
        } catch (IOException e) {
            Log.e(TAG, "Error writing file: " + e.getMessage());
        }
    }

    public void saveRecord(RolodexRecord record) {
        ArrayList<RolodexRecord> records = readRecords();
        boolean recordExists = false;

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId() == record.getId()) {
                records.set(i, record);
                recordExists = true;
                break;
            }
        }

        if (!recordExists) {
            records.add(record);
        }

        writeRecords(records);
    }

    public void deleteRecord(RolodexRecord recordToDelete) {
        ArrayList<RolodexRecord> records = readRecords();

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId() == recordToDelete.getId()) {
                records.remove(i);
                break;
            }
        }

        writeRecords(records);
    }
}
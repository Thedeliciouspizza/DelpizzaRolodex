package com.example.rolodex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RolodexRecordArrayAdapter extends ArrayAdapter<RolodexRecord> {
    private Context Context;
    private List<RolodexRecord> Records;

    // Constructor for the class
    public RolodexRecordArrayAdapter(Context context, int resource, List<RolodexRecord> records) {
        super(context, resource, records);
        Context = context;
        Records = records;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_list_item_activated_2, parent, false);

        // Set up the text views
        TextView nameTextView = view.findViewById(android.R.id.text1);
        TextView phoneTextView = view.findViewById(android.R.id.text2);

        // Get the record at the specified position
        RolodexRecord record = Records.get(position);

        // Set the name and phone number on the text views
        nameTextView.setText(record.getName());
        phoneTextView.setText(record.getPhoneNumber());

        return view;
    }
}
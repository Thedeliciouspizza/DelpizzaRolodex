package com.example.rolodex;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AddDialogFragment.AddDialogListener, UpdateDialogFragment.UpdateDialogListener, DeleteDialogFragment.DeleteDialogListener {
    private int selectedPosition = -1;
    private ArrayList<RolodexRecord> rolodexRecords;
    private ArrayAdapter<RolodexRecord> rolodexAdapter;
    private ListView rolodexListView;
    private Toolbar Toolbar;
    private ImageButton btnAdd;
    private ImageButton btnUpdate;
    private ImageButton btnDelete;
    private RolodexFileStorage rolodexFileStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up everything that needs to be setup
        rolodexFileStorage = new RolodexFileStorage(this);

        rolodexRecords = new ArrayList<>();
        rolodexAdapter = new RolodexRecordArrayAdapter(this, android.R.layout.simple_list_item_activated_2, rolodexRecords);

        Toolbar = findViewById(R.id.my_toolbar);
        btnAdd = findViewById(R.id.add_button);
        btnUpdate = findViewById(R.id.update_button);
        btnDelete = findViewById(R.id.delete_button);

        rolodexListView = findViewById(R.id.rolodexListView);
        rolodexListView.setAdapter(rolodexAdapter);
        rolodexListView.setOnItemClickListener(this);
        rolodexListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        setSupportActionBar(Toolbar);

        // retrieve any saved records
        rolodexRecords = rolodexFileStorage.readRecords();
        rolodexAdapter.addAll(rolodexRecords);
        rolodexAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) { // onClicks for the three buttons within our toolbar
        switch (v.getId()) {
            case R.id.add_button:
                showAddDialog();
                break;
            case R.id.update_button:
                if (selectedPosition >= 0) {
                    showUpdateDialog();
                } else {
                    Toast.makeText(this, "Please select a record to update.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_button:
                if (selectedPosition >= 0) {
                    showDeleteDialog();
                } else {
                    Toast.makeText(this, "Please select a record to delete.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override // We get the selected rolodex contact when there is a click
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedPosition = position;
    }

    private void showAddDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddDialogFragment addDialog = AddDialogFragment.newInstance();
        addDialog.show(fragmentManager, "AddDialogFragment");
    }

    private void showUpdateDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        UpdateDialogFragment updateDialog = UpdateDialogFragment.newInstance(rolodexRecords.get(selectedPosition));
        updateDialog.show(fragmentManager, "UpdateDialogFragment");
    }

    private void showDeleteDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DeleteDialogFragment deleteDialog = DeleteDialogFragment.newInstance();
        deleteDialog.show(fragmentManager, "DeleteDialogFragment");
    }

    public void addRecord(RolodexRecord record) {
        rolodexRecords.add(record);
        rolodexAdapter.add(record);
        rolodexAdapter.notifyDataSetChanged();

        // save the new record
        rolodexFileStorage.writeRecords(rolodexRecords);
    }

    public void updateRecord(RolodexRecord record) {
        rolodexRecords.set(selectedPosition, record);
        rolodexAdapter.notifyDataSetChanged();

        // save the updated record
        RolodexFileStorage fileStorage = new RolodexFileStorage(this);
        fileStorage.saveRecord(record);

        Toast.makeText(this, "Record updated", Toast.LENGTH_SHORT).show();
    }

    public void deleteRecord() {

        RolodexRecord recordToDelete = rolodexRecords.get(selectedPosition);
        rolodexRecords.remove(selectedPosition);
        rolodexAdapter.remove(recordToDelete);
        rolodexAdapter.notifyDataSetChanged();
        selectedPosition = -1;

        // delete the selected record from storage
        RolodexFileStorage fileStorage = new RolodexFileStorage(this);
        fileStorage.deleteRecord(recordToDelete);

        Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddDialogPositiveClick(RolodexRecord record) {
    }

    @Override
    public void onAddDialogNegativeClick(DialogFragment dialog) {
    }

    @Override
    public void onDeleteDialogPositiveClick() {
    }

    @Override
    public void onDeleteDialogNegativeClick() {
    }

    @Override
    public void onUpdateDialogPositiveClick() {
    }

    @Override
    public void onUpdateDialogNegativeClick() {
    }
}
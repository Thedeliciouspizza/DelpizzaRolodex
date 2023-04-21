package com.example.rolodex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddDialogFragment extends DialogFragment {
    // We create a dialog fragment for our add button
    public interface AddDialogListener {
        // We can tell if the positive or negative buttons have been clicked
        void onAddDialogPositiveClick(RolodexRecord record);
        void onAddDialogNegativeClick(DialogFragment dialog);
    }

    private AddDialogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);

        // Create variables to make referencing the gui easier
        final EditText firstNameEditText = view.findViewById(R.id.edit_text_first_name);
        final EditText lastNameEditText = view.findViewById(R.id.edit_text_last_name);
        final EditText middleNameEditText = view.findViewById(R.id.edit_text_middle_name);
        final EditText phoneEditText = view.findViewById(R.id.edit_text_phone);

        builder.setView(view)
                .setTitle(R.string.add_dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get the text from the edit texts
                        String firstName = firstNameEditText.getText().toString().trim();
                        String lastName = lastNameEditText.getText().toString().trim();
                        String middleName = middleNameEditText.getText().toString().trim();
                        String phoneNumber = phoneEditText.getText().toString().trim();

                        // Make sure all fields are full except for middle as that one is optional
                        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty()) {
                            Toast.makeText(getActivity(), R.string.fill_required_fields, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Create a new RolodexRecord object and pass it to the listener
                        RolodexRecord contact = new RolodexRecord(firstName, lastName, middleName, phoneNumber);
                        MainActivity activity = (MainActivity) requireActivity();
                        activity.addRecord(contact);
                        // We clicked the positive button let it be known
                        mListener.onAddDialogPositiveClick(contact);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // We clicked the negative button let it be known
                        mListener.onAddDialogNegativeClick(AddDialogFragment.this);
                        dismiss();
                    }
                });

        return builder.create();
    }

    public static AddDialogFragment newInstance() {
        return new AddDialogFragment();
    }
}

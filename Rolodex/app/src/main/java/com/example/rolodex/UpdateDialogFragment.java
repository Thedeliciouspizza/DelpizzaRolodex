package com.example.rolodex;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class UpdateDialogFragment extends DialogFragment {
    // We create a dialog fragment for our update button
    private static final String ARG_CONTACT = "contact";
    private RolodexRecord contact;

    @Override
    public void onCreate(Bundle savedInstanceState) { // We bring in the RolodexRecord object that was selected
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = (RolodexRecord) getArguments().getSerializable(ARG_CONTACT);
        }
    }

    public interface UpdateDialogListener { // We can tell if the positive or negative buttons have been clicked
        void onUpdateDialogPositiveClick();
        void onUpdateDialogNegativeClick();
    }

    UpdateDialogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {  // Set the listener
        super.onAttach(context);
        mListener = (UpdateDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);

        // Create variables to make referencing the gui easier

        final EditText firstNameEditText = view.findViewById(R.id.edit_text_first_name);
        final EditText lastNameEditText = view.findViewById(R.id.edit_text_last_name);
        final EditText middleNameEditText = view.findViewById(R.id.edit_text_middle_name);
        final EditText phoneEditText = view.findViewById(R.id.edit_text_phone);

        // Show the user the information they are changing

        firstNameEditText.setText(contact.getFirstName());
        lastNameEditText.setText(contact.getLastName());
        middleNameEditText.setText(contact.getMiddleName());
        phoneEditText.setText(contact.getPhoneNumber());

        builder.setView(view)
            .setTitle(R.string.update_dialog_title)
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
                                Toast.makeText(getActivity(), "Please enter all required fields", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // The RolodexRecord object that was selected now gets redone with our information given
                            contact.setFirstName(firstName);
                            contact.setLastName(lastName);
                            contact.setMiddleName(middleName);
                            contact.setPhoneNumber(phoneNumber);

                            MainActivity activity = (MainActivity) requireActivity();
                            // We send our object to the updateRecord method so that our object gets placed in the array
                            activity.updateRecord(contact);

                            // We clicked the positive button let it be known
                            mListener.onUpdateDialogPositiveClick();

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // We clicked the negative button let it be known
                            mListener.onUpdateDialogNegativeClick();

                        }
                    });

        return builder.create();
    }
    public static UpdateDialogFragment newInstance(RolodexRecord contact) {
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }
}
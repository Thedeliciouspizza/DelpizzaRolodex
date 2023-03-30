package com.example.rolodex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.rolodex.R;

public class DeleteDialogFragment extends DialogFragment {
    // We create a dialog fragment for our delete button

    public interface DeleteDialogListener {
        // We can tell if the positive or negative buttons have been clicked
        void onDeleteDialogPositiveClick();
        void onDeleteDialogNegativeClick();
    }

    private DeleteDialogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (DeleteDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_confirmation)
                .setTitle(R.string.delete_dialog_title)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        MainActivity activity = (MainActivity) getActivity();
                        // We want to call the deleteRecord method when the button is clicked
                        activity.deleteRecord();
                        dismiss();
                        // We clicked the positive button let it be known
                        mListener.onDeleteDialogPositiveClick();
                    }
                })
                .setNegativeButton(R.string.dontdelete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // We clicked the negative button let it be known
                        mListener.onDeleteDialogNegativeClick();
                    }
                });

        return builder.create();
    }

    public static DeleteDialogFragment newInstance() {
        return new DeleteDialogFragment();
    }
}

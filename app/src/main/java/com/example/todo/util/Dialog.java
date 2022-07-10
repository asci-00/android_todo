package com.example.todo.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("R.string.dialog_start_game")
                .setPositiveButton("R.string.start", (DialogInterface.OnClickListener) (dialog, id) -> {
                    // START THE GAME!
                })
                .setNegativeButton("R.string.cancel", (DialogInterface.OnClickListener) (dialog, id) -> {
                    // User cancelled the dialog
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

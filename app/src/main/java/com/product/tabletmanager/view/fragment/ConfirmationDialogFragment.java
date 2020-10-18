package com.product.tabletmanager.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.product.tabletmanager.AlarmReceiver;

public class ConfirmationDialogFragment extends DialogFragment {
    public static final String TAG = DialogFragment.class.getSimpleName();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments == null) {
            Log.d(TAG, "onCreateDialog: bundle" + arguments);
            setShowsDialog(false);
            dismiss();
            return null;
        }
        String name = arguments.getString(AlarmReceiver.KEY_DRUG_NAME);
        String time = arguments.getString(AlarmReceiver.KEY_DRAG_TIME);

        Log.d(TAG, String.format("onCreateDialog: %s, %s",name,time));
        return new AlertDialog.Builder(getContext())
                .setTitle("Confirmation")
                .setMessage("Have you taken pills?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    //todo
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    //todo
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .create();
    }
}

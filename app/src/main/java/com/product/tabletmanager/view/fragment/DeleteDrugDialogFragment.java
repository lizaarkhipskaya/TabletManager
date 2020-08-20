package com.product.tabletmanager.view.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeleteDrugDialogFragment extends DialogFragment {
    private static String TAG = DeleteDrugDialogFragment.class.getSimpleName();

    private DeleteDrugClickListener mClickListener;

    public static DeleteDrugDialogFragment newInstance(DeleteDrugClickListener listener) {
        DeleteDrugDialogFragment fragment = new DeleteDrugDialogFragment();
        fragment.mClickListener = listener;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (mClickListener == null) {
            throw new UnsupportedOperationException("Host have to implement " + DeleteDrugClickListener
                    .class.getSimpleName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure, you want to delete drug?");
        builder.setPositiveButton("Yes", (dialog, id) ->
                mClickListener.onDeleteClick());
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            dismiss();
        });
        return builder.create();
    }

    public interface DeleteDrugClickListener {
        void onDeleteClick();
    }
}

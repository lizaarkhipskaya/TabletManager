package com.product.tabletmanager.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimeClickDialogFragment extends DialogFragment {

    private static View mTimeView;
    private static OnPositiveButtonClick mOnPositiveButtonClickImpl;

    public static TimeClickDialogFragment newInstance(View timeView, OnPositiveButtonClick
            onPositiveButtonClickImpl) {
        TimeClickDialogFragment fragment = new TimeClickDialogFragment();
        mTimeView = timeView;
        mOnPositiveButtonClickImpl = onPositiveButtonClickImpl;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to edit time?");
        builder.setPositiveButton("Delete", (d, i) -> mOnPositiveButtonClickImpl.onClick(mTimeView));
        builder.setNegativeButton("Cancel", (d, i) -> dismiss());
        return builder.create();
    }

    interface OnPositiveButtonClick {
        void onClick(View childView);
    }
}

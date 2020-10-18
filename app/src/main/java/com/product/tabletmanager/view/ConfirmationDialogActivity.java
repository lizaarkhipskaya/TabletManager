package com.product.tabletmanager.view;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.product.tabletmanager.view.fragment.ConfirmationDialogFragment;

public class ConfirmationDialogActivity extends FragmentActivity {
    public static final String TAG = ConfirmationDialogActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "show "+ConfirmationDialogFragment.class.getSimpleName());
        DialogFragment dialogFragment = new ConfirmationDialogFragment();
        dialogFragment.setArguments(savedInstanceState);
        dialogFragment.show(getSupportFragmentManager(),
                ConfirmationDialogFragment.class.getSimpleName());
    }
}

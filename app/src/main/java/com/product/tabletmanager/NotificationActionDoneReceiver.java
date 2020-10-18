package com.product.tabletmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.product.tabletmanager.view.ConfirmationDialogActivity;
import com.product.tabletmanager.view.fragment.ConfirmationDialogFragment;

public class NotificationActionDoneReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationActionDoneReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Intent activityIntent = new Intent(context,ConfirmationDialogActivity.class);
        activityIntent.putExtras(intent);
        context.startActivity(activityIntent);
    }
}

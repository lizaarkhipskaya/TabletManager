package com.product.tabletmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.util.AlarmHelper;

public class AlarmCancellationReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmCancellationReceiver.class.getSimpleName();
    public static final String KEY_DRUG = "key_drug";

    @Override
    public void onReceive(Context context, Intent intent) {
        Drug drug = intent.getExtras().getParcelable(KEY_DRUG);
        Log.i(TAG, "onReceive: retrieve extras " + drug);
        if (drug == null) {
            return;
        }
        AlarmHelper.getInstance().cancelAlarm(context, drug);
    }
}

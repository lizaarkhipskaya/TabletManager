package com.product.tabletmanager.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.product.tabletmanager.AlarmReceiver;
import com.product.tabletmanager.model.Drug;

import java.util.Calendar;
import java.util.List;

public class AlarmHelper {

    private static final String LOG_TAG = AlarmHelper.class.getSimpleName();

    public static void setAlarm(Context context, @NonNull Drug drug) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Calendar time : drug.getDayTime()) {
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            Bundle bundle = new Bundle();
            bundle.putString(AlarmReceiver.KEY_DRUG_NAME, drug.getName());
            bundle.putString(AlarmReceiver.KEY_DRAG_FORM, drug.getForm().toString());
            bundle.putLong(AlarmReceiver.KEY_DRAG_TIME, time.getTimeInMillis());
            bundle.putInt(AlarmReceiver.KEY_DRAG_DOSAGE, drug.getDosage());
            alarmIntent.putExtras(bundle);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    CommonUtils.getInstance().getIdentifier(drug, time), alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            if (pendingIntent != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                Log.d(LOG_TAG, String.format("scheduleAlarm: name:%s, form:%s, dosage:%d, timeInMiles:%d",
                        drug.getName(), drug.getForm().toString(), drug.getDosage(), time.getTimeInMillis()));
            } else {
                Log.e(LOG_TAG, "scheduleAlarm: pending intent is null");
            }
        }
    }

    public static void cancelAlarm(Context context, @NonNull Drug drug) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Calendar time : drug.getDayTime()) {
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    CommonUtils.getInstance().getIdentifier(drug, time), alarmIntent, 0);

            if (pendingIntent != null) {
                Log.d(LOG_TAG, "cancelAlarm: " + time.getTime().toString());
                alarmManager.cancel(pendingIntent);
            } else {
                Log.e(LOG_TAG, "cancelAlarm: pending intent is null");
            }
        }
    }

    public static void setAlarm(Context context, @NonNull List<Drug> drugs) {
        for (Drug drug : drugs) {
            setAlarm(context, drug);
        }
    }
}

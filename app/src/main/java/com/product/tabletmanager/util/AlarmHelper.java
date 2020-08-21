package com.product.tabletmanager.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
            alarmIntent.putExtra(AlarmReceiver.TITLE_KEY, drug.getName());
            alarmIntent.putExtra(AlarmReceiver.CONTENT_KEY, drug.getForm());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, time.hashCode(), alarmIntent, 0);

            if (pendingIntent != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                Log.d(LOG_TAG, "scheduleAlarm: set repeating for time " + time.getTime().toString());
            } else {
                Log.e(LOG_TAG, "scheduleAlarm: pending intent is null");
            }
        }
    }

    public static void cancelAlarm(Context context, @NonNull Drug drug) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Calendar time : drug.getDayTime()) {
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, time.hashCode(), alarmIntent,
                    0);

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

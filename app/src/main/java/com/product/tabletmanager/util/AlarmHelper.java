package com.product.tabletmanager.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.product.tabletmanager.AlarmCancellationReceiver;
import com.product.tabletmanager.AlarmReceiver;
import com.product.tabletmanager.model.Drug;

import java.util.Calendar;
import java.util.List;

public class AlarmHelper {

    private static final String LOG_TAG = AlarmHelper.class.getSimpleName();
    private static AlarmHelper mInstance;

    public static AlarmHelper getInstance() {
        if (mInstance == null) {
            mInstance = new AlarmHelper();
        }
        return mInstance;
    }

    public void setAlarm(Context context, @NonNull Drug drug) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (Calendar time : drug.getDayTime()) {
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            alarmIntent.putExtra(AlarmReceiver.TITLE_KEY, drug.getName());
            alarmIntent.putExtra(AlarmReceiver.CONTENT_KEY, drug.getForm());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    CommonUtils.getInstance().getIdentifier(drug, time), alarmIntent, 0);

            if (pendingIntent != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                        time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                Log.d(LOG_TAG, "scheduleAlarm: set repeating for " + drug.getName() + " time "
                        + time.getTime().toString()); //todo user friendly time
            } else {
                Log.e(LOG_TAG, "scheduleAlarm: pending intent is null");
            }
        }
        scheduleAlarmCancellation(context, drug, alarmManager);
    }

    private void scheduleAlarmCancellation(Context context, Drug drug, AlarmManager alarmManager) {
        Log.d(LOG_TAG, "scheduleAlarmCancellation: " + drug.toString());
        Calendar drugEndDate = Calendar.getInstance();
        drugEndDate.setTimeInMillis(drug.getEndDate().getTime());

        Calendar timeForCancellation = Calendar.getInstance();
        timeForCancellation.set(drugEndDate.get(Calendar.YEAR),
                drugEndDate.get(Calendar.MONTH),
                drugEndDate.get(Calendar.DAY_OF_MONTH),
                23, 59);

        Intent broadcastIntent = new Intent(context, AlarmCancellationReceiver.class);
        Bundle extras = new Bundle();
        extras.putParcelable(AlarmCancellationReceiver.KEY_DRUG, drug);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, drug.hashCode(),
                broadcastIntent, 0);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeForCancellation.getTimeInMillis(),
                pendingIntent);
    }

    public void cancelAlarm(Context context, @NonNull Drug drug) {
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

    public void setAlarm(Context context, @NonNull List<Drug> drugs) {
        for (Drug drug : drugs) {
            setAlarm(context, drug);
        }
    }
}

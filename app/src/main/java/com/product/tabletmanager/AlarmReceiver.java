package com.product.tabletmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.product.tabletmanager.util.CommonUtils;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String KEY_DRUG_NAME = "drugName";
    public static final String KEY_DRAG_TIME = "timeForDrug";
    public static final String KEY_DRAG_FORM = "timeForDrug";
    public static final String KEY_DRAG_DOSAGE = "timeForDrug"; //todo parcelable

    private static final String LOG_TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String name = extras.getString(KEY_DRUG_NAME);
        String form = extras.getString(KEY_DRAG_FORM);
        int dosage = extras.getInt(KEY_DRAG_DOSAGE, 0);
        long timeInMiles = extras.getLong(KEY_DRAG_TIME, 0L);

        Log.d(LOG_TAG, String.format("onReceive: name:%s, form:%s, dosage:%d, timeInMiles:%d",
                name, form, dosage, timeInMiles));
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(form) || dosage == 0 || timeInMiles == 0) {
            return;
        }
        Calendar drugTime = Calendar.getInstance();
        drugTime.setTimeInMillis(timeInMiles);
        showNotification(context, name, form, dosage, drugTime);
    }

    @SuppressLint("DefaultLocale")
    private void showNotification(Context context, String name, String form, int dosage, Calendar drugTime) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "3000";
            CharSequence channelName = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            manager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(context, chanel_id);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder
                .setContentTitle(name)
                .setContentText(String.format("%s    %s x %d",
                        CommonUtils.getInstance().getDateString(drugTime.getTime()),
                        form, dosage))
                .setSmallIcon(R.drawable.ic_plus)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        manager.notify((int) System.currentTimeMillis(), builder.build()); //todo
    }
}

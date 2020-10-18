package com.product.tabletmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

public class AlarmReceiver extends BroadcastReceiver {

    public static final String KEY_DRUG_NAME = "drugName";
    public static final String KEY_DRAG_TIME = "drugTime";
    public static final String KEY_DRAG_FORM = "drugForm";
    public static final String KEY_DRAG_DOSAGE = "drugDosage"; //todo parcelable

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

/*        if (extras == null) {
            Log.d(LOG_TAG, "onReceive: extras are null");
            return;
        }

        Drug drug = extras.getParcelable(KEY_DRUG);
        long time = extras.getLong(KEY_DRAG_TIME);
        Log.d(LOG_TAG, "onReceive: " + (drug == null ? drug : drug.toString()));
        if (drug == null) {
            return;
        }*/

        showNotification(context, name, form, dosage, timeInMiles);
    }

    @SuppressLint("DefaultLocale")
    private void showNotification(Context context, String name, String form, int dosage, long drugTime) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "3000";
            CharSequence channelName = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, channelName, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            manager.createNotificationChannel(mChannel);
            builder = new NotificationCompat.Builder(context, chanel_id);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        Intent doneIntent = new Intent(context, NotificationActionDoneReceiver.class);
        doneIntent.putExtra(KEY_DRUG_NAME, name);
        doneIntent.putExtra(KEY_DRAG_TIME, drugTime);
        PendingIntent donePendingIntent =
                PendingIntent.getBroadcast(context, 2, doneIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder
                .setContentTitle(name)
                .setContentText(String.format("%s    %s x %d",
                        CommonUtils.getInstance().getDateString(drugTime),
                        form, dosage))
                .setSmallIcon(R.drawable.ic_plus)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_plus, "Done", donePendingIntent);

        manager.notify((int) System.currentTimeMillis(), builder.build()); //todo
    }
}

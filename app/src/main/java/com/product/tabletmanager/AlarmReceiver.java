package com.product.tabletmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = AlarmReceiver.class.getSimpleName();

    public static final String TITLE_KEY = "title";
    public static final String CONTENT_KEY = "content";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive: " + intent.getStringExtra(TITLE_KEY)
                + intent.getStringExtra(CONTENT_KEY));
        showNotification(context, intent);
    }

    private void showNotification(Context context, Intent intent) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "3000";
            CharSequence name = "Channel Name";
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
                .setContentTitle(intent.getStringExtra(TITLE_KEY))
                .setContentText(intent.getStringExtra(CONTENT_KEY))
                .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        manager.notify((int) System.currentTimeMillis(), builder.build()); //todo
    }
}

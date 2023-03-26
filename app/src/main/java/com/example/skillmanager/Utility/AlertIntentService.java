package com.example.skillmanager.Utility;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertIntentService extends BroadcastReceiver {
    public static int numAlert = 0;
    private static int notificationId = 0;
    public static final String EXTRA_ALERT_TITLE = "AlertTitle";
    private static final String NOTIFICATION_CHANNEL = "channel_notifications";


    @Override
    public void onReceive(Context context, Intent intent) {
        String alertTitle = intent.getStringExtra(EXTRA_ALERT_TITLE);
        createNotificationChannel(context);
        createNotification(context, alertTitle);
    }


    public void createNotificationChannel(Context context) {
        CharSequence name = "Upcoming Assignment/ProjectAssignmentCrossRef";
        String description = "Opt-in notifications for upcoming assignments and assessments.";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void createNotification(Context context, String text) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Upcoming Date")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(++notificationId, notification);
    }
}
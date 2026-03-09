package com.lubolabao.todolist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class TaskReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "TASK_REMINDER_CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskTitle = intent.getStringExtra("task_title");
        if (taskTitle == null) taskTitle = "Legendary Task Reminder!";

        showNotification(context, taskTitle);
        playAlarmSound(context);
    }

    private void showNotification(Context context, String title) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Quest Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for upcoming legendary tasks");
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 
                0, 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0)
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Legend-Tasker Quest!")
                .setContentText("Reminder: " + title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    private void playAlarmSound(Context context) {
        try {
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            RingtoneManager.getRingtone(context, alarmUri).play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

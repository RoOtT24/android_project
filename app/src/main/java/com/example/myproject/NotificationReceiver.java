package com.example.myproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the notification title, message, and email from the intent
        String title = intent.getStringExtra("notification_title");
        String message = intent.getStringExtra("notification_message");
        String email = intent.getStringExtra("notification_email");

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setContentTitle(title)
                .setContentText(message + " Email: " + email)  // Include the email in the notification message
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }
}


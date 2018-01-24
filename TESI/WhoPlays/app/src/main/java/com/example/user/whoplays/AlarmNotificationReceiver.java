package com.example.user.whoplays;

    import android.app.Notification;
    import android.app.NotificationManager;
    import android.content.BroadcastReceiver;
    import android.content.Context;
    import android.content.Intent;
    import android.support.v4.app.NotificationCompat;

/**
 * Created by io on 24/01/2018.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.whistle)
                .setContentTitle("Partita tra 30 minuti!")
                .setContentText("Ricordati che hai una partita tra 30 minuti")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Reminder");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());



    }
}

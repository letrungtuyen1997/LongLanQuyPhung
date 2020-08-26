package com.mygdx.game;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.NotificationCompat;


public class AlarmReceiver extends BroadcastReceiver {
    public static final String CUSTOM_INTENT = "com.jkstudiogroup.popcat.intent.action.ALARM";

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Gift Notification";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        Intent launchIntent = new Intent(context, AndroidLauncher.class);
        int id = intent.getIntExtra("id",0);
        launchIntent.putExtra("id",id);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        stackBuilder.addNextIntent(launchIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TEST", "TEST");

        String title = intent.getStringExtra("header");
        String message = intent.getStringExtra("content");

        showNotification(context, title, message, intent);

    }
//    public static void cancelAlarm() {
//
//    }
//    public static void setAlarm(boolean force, Context ctx) {
//        cancelAlarm();
//        AlarmManager alarm = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//
//        // EVERY X MINUTES
//        int X = 1;
//        long delay = (1000 * 60 * X);
//        long when = System.currentTimeMillis();
//        //if (!force) {
//            when += delay;
//        //}
//
//        /* fire the broadcast */
//       // alarms.set(AlarmManager.RTC_WAKEUP, when, getPendingIntent());
//
//        PendingIntent pendingIntent = getPendingIntent(ctx);
//        int SDK_INT = Build.VERSION.SDK_INT;
//        if (SDK_INT < Build.VERSION_CODES.KITKAT)
//            alarm.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
//        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
//            alarm.setExact(AlarmManager.RTC_WAKEUP, when, pendingIntent);
//        else if (SDK_INT >= Build.VERSION_CODES.M) {
//            alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when, pendingIntent);
//        }
//
//    }
//    private static PendingIntent getPendingIntent(Context ctx) {
//        //Context ctx;   /* get the application context */
//        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
//        alarmIntent.putExtra("content", "test1");
//        alarmIntent.putExtra("header", "test2");
//
//        alarmIntent.setAction(CUSTOM_INTENT);
//
//        return PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//    }


}
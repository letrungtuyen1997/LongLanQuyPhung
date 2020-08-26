package com.mygdx.game;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;


public class DailyNotification {
    public static final String CUSTOM_INTENT = "com.jkstudiogroup.popcat.intent.action.ALARM";

    public static void PendingRestartApplication(Intent intent, Context android, int id){
        try {
            @SuppressLint("WrongConstant") AlarmManager alarmManager = (AlarmManager) android.getSystemService(Context.ALARM_SERVICE);
            PendingRestart(intent, android, alarmManager, id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void SetDailyNotification(Context android, int id, String header, String content, int days, int hours){
        try {
            @SuppressLint("WrongConstant") AlarmManager alarmManager = (AlarmManager) android.getSystemService(Context.ALARM_SERVICE);
            PendingIntent(android, id, header, content, alarmManager, days, hours);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void CancelDailyNotification(Context android, int id){
        try {
            @SuppressLint("WrongConstant") AlarmManager alarmManager = (AlarmManager) android.getSystemService(Context.ALARM_SERVICE);
            CancelIntent(android, id, alarmManager);
        }
        catch(Exception e){

        }
    }

    private static Calendar GetCalendar(int addDay, int hour) {
        Calendar calendar = Calendar.getInstance();
        int minute = 1;
       // hour = 15;
//        minute = 50;
        addDay = 0;
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 10);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, addDay);

        return calendar;
    }


    private static void PendingRestart(Intent intent, Context android, AlarmManager alarmManager, int id) {
        Log.d("TEST", "PendingRestart");
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(android, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 100, pendingIntent);
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 100, pendingIntent);
        else if (SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 100, pendingIntent);
        else alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 100, pendingIntent);
    }

    private static void PendingIntent(Context android, int id, String header, String content,  AlarmManager alarmManager, int addDay, int hour) {
        Intent intent = new Intent(android, AlarmReceiver.class);
        intent.putExtra("content", content);
        intent.putExtra("header", header);
        intent.putExtra("id", id);
        intent.setAction(AlarmReceiver.CUSTOM_INTENT);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(android, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, GetCalendar(addDay, hour).getTimeInMillis(), pendingIntent);
        else if (Build.VERSION_CODES.KITKAT <= SDK_INT && SDK_INT < Build.VERSION_CODES.M)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, GetCalendar(addDay, hour).getTimeInMillis(), pendingIntent);
        else if (SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, GetCalendar(addDay, hour).getTimeInMillis(), pendingIntent);
       else
           alarmManager.set(AlarmManager.RTC_WAKEUP, GetCalendar(addDay, hour).getTimeInMillis(), pendingIntent);
    }

    private static void CancelIntent(Context android, int id, AlarmManager alarmManager) {
        Intent intent = new Intent(android, AlarmReceiver.class);
        intent.setAction(AlarmReceiver.CUSTOM_INTENT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(android, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }


}
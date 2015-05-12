package com.xplete.lingoz.utils;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.xplete.lingoz.receivers.WordOfTheDayBroadcastReceiver;

import java.util.Calendar;

public class AlarmUtils {
    public static void setWordOfTheDayAlarm(Context context) {
        Calendar updateTime = Calendar.getInstance();
        updateTime.set(Calendar.HOUR_OF_DAY, 9);
        updateTime.set(Calendar.MINUTE, 00);

        Intent wordOfTheDaydNotification = new Intent(context, WordOfTheDayBroadcastReceiver.class);
        PendingIntent recurringNotification = PendingIntent.getBroadcast(context, 0, wordOfTheDaydNotification, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarms.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, recurringNotification);
    }

    public static void cancelWordOfTheDayAlarm(Context context) {

        Intent wordOfTheDaydNotification = new Intent(context, WordOfTheDayBroadcastReceiver.class);
        PendingIntent recurringNotification = PendingIntent.getBroadcast(context, 0, wordOfTheDaydNotification, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarms.cancel(recurringNotification);
    }
}

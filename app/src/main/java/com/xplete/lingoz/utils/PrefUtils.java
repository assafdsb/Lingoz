package com.xplete.lingoz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xplete.lingoz.consts.CONSTS_PREFERENCES;

public class PrefUtils {

    public static boolean getPrefWordOfTheDayValue(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CONSTS_PREFERENCES.PREF_APP_ID, Context.MODE_PRIVATE);
        return prefs.getBoolean(CONSTS_PREFERENCES.PREFS_WORD_OF_THE_DAY, true);
    }

    public static boolean wordOfTheDayKeyExists(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CONSTS_PREFERENCES.PREF_APP_ID, Context.MODE_PRIVATE);
        return prefs.contains(CONSTS_PREFERENCES.PREFS_WORD_OF_THE_DAY);
    }

    public static void setWordOfTheDay(boolean wordOfTheDay, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CONSTS_PREFERENCES.PREF_APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(CONSTS_PREFERENCES.PREFS_WORD_OF_THE_DAY, wordOfTheDay);
        editor.commit();
    }

    public static int getAppVolume(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CONSTS_PREFERENCES.PREF_APP_ID, Context.MODE_PRIVATE);
        int notificationVolume = prefs.getInt(CONSTS_PREFERENCES.PREFS_NOTIFICATION_VOLUME, -1);
        if (notificationVolume == -1) {
            setAppVolume(CONSTS_PREFERENCES.APP_DEFAULT_VOLUME, context);
            notificationVolume = CONSTS_PREFERENCES.APP_DEFAULT_VOLUME;
        }
        return notificationVolume;
    }

    public static void setAppVolume(int notificationVolume, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(CONSTS_PREFERENCES.PREF_APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(CONSTS_PREFERENCES.PREFS_NOTIFICATION_VOLUME, notificationVolume);
        editor.commit();
    }
}

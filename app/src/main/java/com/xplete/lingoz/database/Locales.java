package com.xplete.lingoz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.xplete.lingoz.models.LocaleModel;
import com.xplete.lingoz.provider.LingozContract;

import java.util.ArrayList;
import java.util.List;

public class Locales {

    public static void bulkInsert(Context context, LocaleModel[] locales) {
        ContentValues[] localesValues = getContentValues(locales);
        context.getContentResolver().bulkInsert(LingozContract.LocaleEntry.CONTENT_URI_LOCALES_BULK_INSERT, localesValues);
    }

    public static ContentValues[] getContentValues(LocaleModel[] locales) {
        ContentValues[] localesValues = new ContentValues[locales.length];
        for (int i = 0; i < locales.length; i++) {
            localesValues[i] = new ContentValues();

            localesValues[i].put(LingozContract.LocaleEntry.LOCALE_CODE, locales[i].getCode());
            localesValues[i].put(LingozContract.LocaleEntry.LOCALE_CAPTION, locales[i].getCaption());
            localesValues[i].put(LingozContract.LocaleEntry.LOCALE_USER_PREFERENCE, locales[i].isUser_preference());
            localesValues[i].put(LingozContract.LocaleEntry.LOCALE_AVAILABLE, locales[i].isAvailable());
        }
        return localesValues;
    }

    public static void setUserPreference(Context context, int code, boolean val) {
        ContentValues values = new ContentValues();
        values.put(LingozContract.LocaleEntry.LOCALE_USER_PREFERENCE, val ? 1 : 0);

        context.getContentResolver().update(LingozContract.LocaleEntry.CONTENT_URI_LOCALES_UPDATE_USER_PREFERENCE, values, LingozContract.LocaleEntry.LOCALE_CODE + " = " + String.valueOf(code), null);
    }

    public static List<LocaleModel> getLocales(Context context) {
        List<LocaleModel> indexArray = new ArrayList<LocaleModel>();
        Cursor cursor = context.getContentResolver().query(LingozContract.LocaleEntry.CONTENT_URI_LOCALES_GET_LIST, null, null, null, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();

            if (count > 0) {
                int code_idx = cursor.getColumnIndex(LingozContract.LocaleEntry.LOCALE_CODE);
                int caption_idx = cursor.getColumnIndex(LingozContract.LocaleEntry.LOCALE_CAPTION);
                int user_preference_idx = cursor.getColumnIndex(LingozContract.LocaleEntry.LOCALE_USER_PREFERENCE);
                for (int i = 0; i < count; i++) {
                    LocaleModel locale = new LocaleModel(cursor.getInt(code_idx), cursor.getString(caption_idx), cursor.getInt(user_preference_idx), 1);
                    indexArray.add(locale);
                    cursor.moveToNext();
                }
            }
        }

        return indexArray;
    }
}

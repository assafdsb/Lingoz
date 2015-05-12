package com.xplete.lingoz.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.xplete.lingoz.models.TranslationModel;
import com.xplete.lingoz.provider.LingozContract;

import java.util.ArrayList;
import java.util.List;

public class Translations {

    public static void bulkInsert(Context context, TranslationModel[] translations) {
        ContentValues[] translationsValues = getContentValues(translations);
        context.getContentResolver().bulkInsert(LingozContract.TranslationEntry.CONTENT_URI_TRANSLATIONS_BULK_INSERT, translationsValues);
    }

    public static ContentValues[] getContentValues(TranslationModel[] translations) {
        ContentValues[] translationsValues = new ContentValues[translations.length];
        for (int i = 0; i < translations.length; i++) {
            translationsValues[i] = new ContentValues();

            translationsValues[i].put(LingozContract.TranslationEntry.LEMMA_ID, translations[i].getLemma_id());
            translationsValues[i].put(LingozContract.TranslationEntry.LOCALE_CODE, translations[i].getLocale_code());
            translationsValues[i].put(LingozContract.TranslationEntry.TRANSLATION, translations[i].getTranslation());
        }
        return translationsValues;
    }

    public static List<TranslationModel> getTranslation(Context context, int lemma_id) {
        List<TranslationModel> translationsArray = new ArrayList<TranslationModel>();
        Uri uri = ContentUris.withAppendedId(LingozContract.TranslationEntry.CONTENT_URI_TRANSLATIONS_GET_BY_LEMMA_ID, Long.valueOf(lemma_id));
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
            if (count > 0) {

                int locale_code_idx = cursor.getColumnIndex(LingozContract.TranslationEntry.LOCALE_CODE);
                int translation_idx = cursor.getColumnIndex(LingozContract.TranslationEntry.TRANSLATION);
                int locale_caption_idx = cursor.getColumnIndex(LingozContract.LocaleEntry.LOCALE_CAPTION);

                for (int i = 0; i < count; i++) {
                    TranslationModel translation = new TranslationModel(lemma_id, cursor.getInt(locale_code_idx),
                            cursor.getString(translation_idx), cursor.getString(locale_caption_idx));
                    translationsArray.add(translation);
                    cursor.moveToNext();
                }
            }
        }

        return translationsArray;
    }

}

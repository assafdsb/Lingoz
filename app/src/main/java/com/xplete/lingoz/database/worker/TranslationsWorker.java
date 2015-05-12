package com.xplete.lingoz.database.worker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xplete.lingoz.database.LingozDbHelper;
import com.xplete.lingoz.provider.LingozContract;

/**
 * Created by ASSAF on 4/28/2015.
 */
public class TranslationsWorker extends DbWorkerBase {

    public TranslationsWorker(LingozDbHelper dbHelper) {
        super(dbHelper);
    }

    public int bulkInert(ContentValues[] valuesArray) {
        setWritableDatabase();

        mDb.beginTransaction();
        try {
            for (ContentValues values : valuesArray) {
                mDb.insertWithOnConflict(LingozContract.TranslationEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                mDb.yieldIfContendedSafely(); // temporarily releases the transaction so the UI is not blocked
            }
            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
            closeDatabase();
        }

        return valuesArray.length;
    }

    public Cursor getTranslation(String lemma_id) {
        setReadableDatabase();

        String query = "SELECT " + LingozContract.TranslationEntry.LEMMA_ID + " , "
                + "L." + LingozContract.TranslationEntry.LOCALE_CODE + " , "
                + LingozContract.TranslationEntry.TRANSLATION + " , "
                + LingozContract.LocaleEntry.LOCALE_CAPTION
                + " FROM " + LingozContract.TranslationEntry.TABLE_NAME + " T," + LingozContract.LocaleEntry.TABLE_NAME + " L"
                + " WHERE " + LingozContract.TranslationEntry.LEMMA_ID + " = " + lemma_id
                + " AND T." + LingozContract.TranslationEntry.LOCALE_CODE + " = L." + LingozContract.LocaleEntry.LOCALE_CODE
                + " AND L." + LingozContract.LocaleEntry.LOCALE_USER_PREFERENCE + " = 1 "
                + " AND L." + LingozContract.LocaleEntry.LOCALE_AVAILABLE + " = 1 ";

        return mDb.rawQuery(query, null);
    }
}

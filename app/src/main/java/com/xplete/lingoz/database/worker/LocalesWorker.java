package com.xplete.lingoz.database.worker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xplete.lingoz.database.LingozDbHelper;
import com.xplete.lingoz.provider.LingozContract;

/**
 * Created by ASSAF on 4/28/2015.
 */
public class LocalesWorker extends DbWorkerBase {

    public LocalesWorker(LingozDbHelper dbHelper) {
        super(dbHelper);
    }

    public int bulkInert(ContentValues[] valuesArray) {
        setWritableDatabase();

        mDb.beginTransaction();
        try {
            for (ContentValues values : valuesArray) {
                mDb.insertWithOnConflict(LingozContract.LocaleEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                mDb.yieldIfContendedSafely(); // temporarily releases the transaction so the UI is not blocked
            }
            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
            closeDatabase();
        }

        return valuesArray.length;
    }

    public int updateUserPreference(ContentValues values, String selection) {
        setWritableDatabase();
        int updatedNo = mDb.update(LingozContract.LocaleEntry.TABLE_NAME, values, selection, null);
        closeDatabase();

        return updatedNo;
    }

    public Cursor getList() {
        setReadableDatabase();

        String query = "SELECT " + LingozContract.LocaleEntry.LOCALE_CODE + ","
                + LingozContract.LocaleEntry.LOCALE_CAPTION + ","
                + LingozContract.LocaleEntry.LOCALE_USER_PREFERENCE
                + " FROM " + LingozContract.LocaleEntry.TABLE_NAME
                + " WHERE " +LingozContract.LocaleEntry.LOCALE_AVAILABLE + " = 1"
                + " ORDER BY " + LingozContract.LocaleEntry.LOCALE_CAPTION ;

        return mDb.rawQuery(query, null);

    }
}

package com.xplete.lingoz.database.worker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xplete.lingoz.database.LingozDbHelper;
import com.xplete.lingoz.provider.LingozContract;

/**
 * Created by ASSAF on 4/28/2015.
 */
public class LemmasWorker extends DbWorkerBase {


    public LemmasWorker(LingozDbHelper dbHelper) {
        super(dbHelper);
    }

    public int bulkInert(ContentValues[] valuesArray) {
        setWritableDatabase();

        mDb.beginTransaction();
        try {
            for (ContentValues values : valuesArray) {
                mDb.insertWithOnConflict(LingozContract.LemmaEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                mDb.yieldIfContendedSafely(); // temporarily releases the transaction so the UI is not blocked
            }
            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
            closeDatabase();
        }

        return valuesArray.length;
    }

    public Cursor getIndex() {
        setReadableDatabase();

        String query = "SELECT " + LingozContract.LemmaEntry.LEMMA_ID + ","
                + LingozContract.LemmaEntry.LEMMA_POS + ","
                + LingozContract.LemmaEntry.LEMMA_CAPTION
                + " FROM " + LingozContract.LemmaEntry.TABLE_NAME;
        //+ " ORDER BY " + LingozContract.LemmaEntry.LEMMA_CAPTION;

        return mDb.rawQuery(query, null);

    }

    public Cursor getLemma(String lemma_id) {
        setReadableDatabase();

        String query = "SELECT " + LingozContract.LemmaEntry.LEMMA_ID + ","
                + LingozContract.LemmaEntry.LEMMA_POS + ","
                + LingozContract.LemmaEntry.LEMMA_CAPTION + ","
                + LingozContract.LemmaEntry.LEMMA_DEFINITION + ","
                + LingozContract.LemmaEntry.LEMMA_EXAMPLE
                + " FROM " + LingozContract.LemmaEntry.TABLE_NAME
                + " WHERE " + LingozContract.LemmaEntry.LEMMA_ID + " = " + lemma_id;

        return mDb.rawQuery(query, null);

    }

    public Cursor getRandomRows(String count) {
        setReadableDatabase();

        String query = "SELECT " + LingozContract.LemmaEntry.LEMMA_ID + ","
                + LingozContract.LemmaEntry.LEMMA_POS + ","
                + LingozContract.LemmaEntry.LEMMA_CAPTION + ","
                + LingozContract.LemmaEntry.LEMMA_DEFINITION
                + " FROM " + LingozContract.LemmaEntry.TABLE_NAME
                + " ORDER BY RANDOM() LIMIT " + count;

        return mDb.rawQuery(query, null);

    }


}

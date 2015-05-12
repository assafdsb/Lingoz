package com.xplete.lingoz.database.worker;

import android.database.sqlite.SQLiteDatabase;

import com.xplete.lingoz.database.LingozDbHelper;

/**
 * Created by ASSAF on 4/28/2015.
 */
public class DbWorkerBase {
    protected LingozDbHelper mDbHelper;
    protected SQLiteDatabase mDb;

    public DbWorkerBase(LingozDbHelper dbHelper) { mDbHelper = dbHelper; }

    public void setWritableDatabase() {
        if (mDb == null) {
            mDb = mDbHelper.getWritableDatabase();
        }
    }

    public void setReadableDatabase() {
        if (mDb == null) {
            mDb = mDbHelper.getReadableDatabase();
        }
    }

    public void closeDatabase() {
        if (mDb != null) {
            mDb.close();
            mDb = null;
        }
    }

}

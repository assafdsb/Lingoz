package com.xplete.lingoz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xplete.lingoz.provider.LingozContract;

public class LingozDbHelper extends SQLiteOpenHelper {

    private static final String TAG = LingozDbHelper.class.getSimpleName();

    public static String DB_FILENAME = "Lingoz.db";
    public static int DB_VERSION = 15;

    private Context mContext;

    public LingozDbHelper(Context context) {
        super(context, DB_FILENAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_LEMMAS = "CREATE TABLE " + LingozContract.LemmaEntry.TABLE_NAME + "("
                + LingozContract.LemmaEntry.LEMMA_ID + " INTEGER PRIMARY KEY,"
                + LingozContract.LemmaEntry.LEMMA_CAPTION + " TEXT,"
                + LingozContract.LemmaEntry.LEMMA_POS + " INTEGER NOT NULL,"
                + LingozContract.LemmaEntry.LEMMA_DEFINITION + " TEXT,"
                + LingozContract.LemmaEntry.LEMMA_EXAMPLE + " TEXT); COMMIT;";
        db.execSQL(CREATE_TABLE_LEMMAS);

        final String CREATE_TABLE_TRANSLATIONS = "CREATE TABLE " + LingozContract.TranslationEntry.TABLE_NAME + "("
                + LingozContract.TranslationEntry.LEMMA_ID + " INTEGER NOT NULL,"
                + LingozContract.TranslationEntry._ID + " INTEGER,"
                + LingozContract.TranslationEntry.LOCALE_CODE + " INTEGER NOT NULL,"
                + LingozContract.TranslationEntry.TRANSLATION + " TEXT,"
                + "PRIMARY KEY (" + LingozContract.TranslationEntry.LEMMA_ID + "," + LingozContract.TranslationEntry._ID + ")); COMMIT;";
        db.execSQL(CREATE_TABLE_TRANSLATIONS);

        final String CREATE_TABLE_LOCALES = "CREATE TABLE " + LingozContract.LocaleEntry.TABLE_NAME + "("
                + LingozContract.LocaleEntry.LOCALE_CODE + " INTEGER PRIMARY KEY,"
                + LingozContract.LocaleEntry.LOCALE_CAPTION + " TEXT,"
                + LingozContract.LocaleEntry.LOCALE_USER_PREFERENCE + " INTEGER DEFAULT 1,"
                + LingozContract.LocaleEntry.LOCALE_AVAILABLE + " INTEGER DEFAULT 1); COMMIT;";
        db.execSQL(CREATE_TABLE_LOCALES);

        LingozDbInit.initDb(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LingozContract.LemmaEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LingozContract.TranslationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LingozContract.LocaleEntry.TABLE_NAME);
        onCreate(db);
    }

}

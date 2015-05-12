package com.xplete.lingoz.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.xplete.lingoz.database.LingozDbHelper;
import com.xplete.lingoz.database.worker.LemmasWorker;
import com.xplete.lingoz.database.worker.LocalesWorker;
import com.xplete.lingoz.database.worker.TranslationsWorker;

public class LingozProvider extends ContentProvider {

    private static final String TAG = LingozProvider.class.getSimpleName();
    private Context mContext;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // lemmas
    private static final int LEMMAS_BULK_INSERT = 100;
    private static final int LEMMAS_GET_INDEX = 101;
    private static final int LEMMAS_GET_LEMMA_DETAILS = 102;
    private static final int LEMMAS_GET_RANDOM_ROWS = 103;

    // locales
    private static final int LOCALES_BULK_INSERT = 200;
    private static final int LOCALES_UPDATE_USER_PREFERENCE = 201;
    private static final int LOCALES_GET_LIST = 202;

    // translations
    private static final int TRANSLATIONS_BULK_INSERT = 300;
    private static final int TRANSLATIONS_GET_BY_LEMMA_ID = 301;

    private LingozDbHelper mDbHelper;


    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDbHelper = new LingozDbHelper(mContext);
        return true;
    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = LingozContract.CONTENT_AUTHORITY;

        // Lemmas
        matcher.addURI(authority, LingozContract.PATH_LEMMAS + "/" + LingozContract.PATH_BULK_INSERT, LEMMAS_BULK_INSERT);
        matcher.addURI(authority, LingozContract.PATH_LEMMAS + "/" + LingozContract.PATH_LEMMAS_GET_INDEX, LEMMAS_GET_INDEX);
        matcher.addURI(authority, LingozContract.PATH_LEMMAS + "/" + LingozContract.PATH_LEMMAS_GET_LEMMA_DETAILS + "/#", LEMMAS_GET_LEMMA_DETAILS);
        matcher.addURI(authority, LingozContract.PATH_LEMMAS + "/" + LingozContract.PATH_LEMMAS_GET_RANDOM_ROWS + "/#", LEMMAS_GET_RANDOM_ROWS);

        // Locales
        matcher.addURI(authority, LingozContract.PATH_LOCALES + "/" + LingozContract.PATH_BULK_INSERT, LOCALES_BULK_INSERT);
        matcher.addURI(authority, LingozContract.PATH_LOCALES + "/" + LingozContract.PATH_LOCALES_UPDATE_USER_PREFERENCE, LOCALES_UPDATE_USER_PREFERENCE);
        matcher.addURI(authority, LingozContract.PATH_LOCALES + "/" + LingozContract.PATH_LOCALES_GET_LIST, LOCALES_GET_LIST);

        // Translations
        matcher.addURI(authority, LingozContract.PATH_TRANSLATIONS + "/" + LingozContract.PATH_BULK_INSERT, TRANSLATIONS_BULK_INSERT);
        matcher.addURI(authority, LingozContract.PATH_TRANSLATIONS + "/" + LingozContract.PATH_TRANSLATIONS_GET_BY_LEMMA_ID + "/#", TRANSLATIONS_GET_BY_LEMMA_ID);


        return matcher;
    }

    @Override
    public String getType(Uri uri) {
        String returnedType = null;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Lemmas
            case LEMMAS_BULK_INSERT:
                returnedType = LingozContract.LemmaEntry.CONTENT_ITEM_TYPE;
                break;
            case LEMMAS_GET_INDEX:
                returnedType = LingozContract.LemmaEntry.CONTENT_TYPE;
                break;
            case LEMMAS_GET_LEMMA_DETAILS:
                returnedType = LingozContract.LemmaEntry.CONTENT_ITEM_TYPE;
                break;
            case LEMMAS_GET_RANDOM_ROWS:
                returnedType = LingozContract.LemmaEntry.CONTENT_TYPE;
                break;

            // Locales
            case LOCALES_BULK_INSERT:
                returnedType = LingozContract.LocaleEntry.CONTENT_ITEM_TYPE;
                break;
            case LOCALES_UPDATE_USER_PREFERENCE:
                returnedType = LingozContract.LocaleEntry.CONTENT_ITEM_TYPE;
                break;
            case LOCALES_GET_LIST:
                returnedType = LingozContract.LocaleEntry.CONTENT_TYPE;
                break;

            // Translations
            case TRANSLATIONS_BULK_INSERT:
                returnedType = LingozContract.TranslationEntry.CONTENT_ITEM_TYPE;
                break;
            case TRANSLATIONS_GET_BY_LEMMA_ID:
                returnedType = LingozContract.TranslationEntry.CONTENT_TYPE;
                break;
            default:
                Log.d(TAG, "Unhandled by UriMatcher: " + uri);
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return returnedType;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            // Lemmas
            case LEMMAS_GET_INDEX: {
                LemmasWorker workerLemmas = new LemmasWorker(mDbHelper);
                cursor = workerLemmas.getIndex();
                break;
            }
            case LEMMAS_GET_LEMMA_DETAILS: {
                LemmasWorker workerLemmas = new LemmasWorker(mDbHelper);
                cursor = workerLemmas.getLemma(uri.getLastPathSegment());
                break;
            }
            case LEMMAS_GET_RANDOM_ROWS: {
                LemmasWorker workerLemmas = new LemmasWorker(mDbHelper);
                cursor = workerLemmas.getRandomRows(uri.getLastPathSegment());
                break;
            }

            // Locales
            case LOCALES_GET_LIST: {
                LocalesWorker workerLocales = new LocalesWorker(mDbHelper);
                cursor = workerLocales.getList();
                break;
            }

            // Translations
            case TRANSLATIONS_GET_BY_LEMMA_ID: {
                TranslationsWorker workerTranslations = new TranslationsWorker(mDbHelper);
                cursor = workerTranslations.getTranslation(uri.getLastPathSegment());
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int numInserted = -1;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // lemmas
            case LEMMAS_BULK_INSERT: {
                LemmasWorker lemmas = new LemmasWorker(mDbHelper);
                numInserted = lemmas.bulkInert(values);
                break;
            }

            // locales
            case LOCALES_BULK_INSERT: {
                LocalesWorker locales = new LocalesWorker(mDbHelper);
                numInserted = locales.bulkInert(values);
                break;
            }

            // translations
            case TRANSLATIONS_BULK_INSERT: {
                TranslationsWorker translations = new TranslationsWorker(mDbHelper);
                numInserted = translations.bulkInert(values);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return numInserted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = -1;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            // locales
            case LOCALES_UPDATE_USER_PREFERENCE: {
                LocalesWorker workerLocales = new LocalesWorker(mDbHelper);
                rowsUpdated = workerLocales.updateUserPreference(values, selection);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}

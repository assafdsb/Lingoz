package com.xplete.lingoz.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class LingozContract {

    public static final String CONTENT_AUTHORITY = "com.xplete.lingoz.providers.LingozProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // lemmas
    public static final String PATH_LEMMAS = "lemmas";
    public static final String PATH_LEMMAS_GET_INDEX = "getIndex";
    public static final String PATH_LEMMAS_GET_LEMMA_DETAILS = "getLemmaDetails";
    public static final String PATH_LEMMAS_GET_RANDOM_ROWS = "getRandomRows";

    // Translations
    public static final String PATH_TRANSLATIONS = "translations";
    public static final String PATH_TRANSLATIONS_GET_BY_LEMMA_ID = "getByLemmaId";

    // Locales
    public static final String PATH_LOCALES = "locales";
    public static final String PATH_LOCALES_UPDATE_USER_PREFERENCE = "updateUserPreference";
    public static final String PATH_LOCALES_GET_LIST = "getList";

    public static final String PATH_BULK_INSERT = "bulk_insert";

    interface LemmaColumns {
        String LEMMA_ID = "lemma_id";
        String LEMMA_CAPTION = "lemma_caption";
        String LEMMA_POS = "lemma_pos";
        String LEMMA_DEFINITION = "lemma_definition";
        String LEMMA_EXAMPLE = "lemma_example";
    }

    interface ExampleTranslationColumns {
        String LEMMA_ID = "lemma_id";
        String LOCALE_CODE = "locale_code";
        String EXAMPLE_TRANSLATION = "example_translation";
    }

    interface TranslationColumns {
        String LEMMA_ID = "lemma_id";
        String LOCALE_CODE = "locale_code";
        String TRANSLATION = "translation";
    }

    interface LocaleColumns {
        String LOCALE_CODE = "locale_code";
        String LOCALE_CAPTION = "locale_caption";
        String LOCALE_USER_PREFERENCE = "locale_user_preference";
        String LOCALE_AVAILABLE = "locale_available";
    }

    public static final class LemmaEntry implements LemmaColumns {
        public static final String TABLE_NAME = PATH_LEMMAS;

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LEMMAS).build();
        public static final Uri CONTENT_URI_LEMMAS_BULK_INSERT = CONTENT_URI.buildUpon().appendPath(PATH_BULK_INSERT).build();
        public static final Uri CONTENT_URI_LEMMAS_GET_INDEX = CONTENT_URI.buildUpon().appendPath(PATH_LEMMAS_GET_INDEX).build();
        public static final Uri CONTENT_URI_LEMMAS_GET_LEMMA_DETAILS = CONTENT_URI.buildUpon().appendPath(PATH_LEMMAS_GET_LEMMA_DETAILS).build();
        public static final Uri CONTENT_URI_LEMMAS_GET_RANDOM_ROWS = CONTENT_URI.buildUpon().appendPath(PATH_LEMMAS_GET_RANDOM_ROWS).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LEMMAS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LEMMAS;
    }

    public static final class TranslationEntry implements TranslationColumns, BaseColumns {
        public static final String TABLE_NAME = PATH_TRANSLATIONS;

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRANSLATIONS).build();
        public static final Uri CONTENT_URI_TRANSLATIONS_BULK_INSERT = CONTENT_URI.buildUpon().appendPath(PATH_BULK_INSERT).build();
        public static final Uri CONTENT_URI_TRANSLATIONS_GET_BY_LEMMA_ID = CONTENT_URI.buildUpon().appendPath(PATH_TRANSLATIONS_GET_BY_LEMMA_ID).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSLATIONS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSLATIONS;
    }

    public static final class LocaleEntry implements LocaleColumns {
        public static final String TABLE_NAME = PATH_LOCALES;

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCALES).build();
        public static final Uri CONTENT_URI_LOCALES_BULK_INSERT = CONTENT_URI.buildUpon().appendPath(PATH_BULK_INSERT).build();
        public static final Uri CONTENT_URI_LOCALES_UPDATE_USER_PREFERENCE = CONTENT_URI.buildUpon().appendPath(PATH_LOCALES_UPDATE_USER_PREFERENCE).build();
        public static final Uri CONTENT_URI_LOCALES_GET_LIST = CONTENT_URI.buildUpon().appendPath(PATH_LOCALES_GET_LIST).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCALES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCALES;

    }

}

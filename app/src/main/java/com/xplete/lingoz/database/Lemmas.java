package com.xplete.lingoz.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.xplete.lingoz.models.LemmaModel;
import com.xplete.lingoz.provider.LingozContract;

import java.util.ArrayList;
import java.util.List;

public class Lemmas {

    public static void bulkInsert(Context context, LemmaModel[] lemmas) {
        ContentValues[] lemmasValues = getContentValues(lemmas);
        context.getContentResolver().bulkInsert(LingozContract.LemmaEntry.CONTENT_URI_LEMMAS_BULK_INSERT, lemmasValues);
    }

    public static ContentValues[] getContentValues(LemmaModel[] lemmas) {
        ContentValues[] lemmasValues = new ContentValues[lemmas.length];
        for (int i = 0; i < lemmas.length; i++) {
            lemmasValues[i] = new ContentValues();

            lemmasValues[i].put(LingozContract.LemmaEntry.LEMMA_ID, lemmas[i].getId());
            lemmasValues[i].put(LingozContract.LemmaEntry.LEMMA_CAPTION, lemmas[i].getCaption());
            lemmasValues[i].put(LingozContract.LemmaEntry.LEMMA_POS, lemmas[i].getPos());
            lemmasValues[i].put(LingozContract.LemmaEntry.LEMMA_DEFINITION, lemmas[i].getDefinition());
            lemmasValues[i].put(LingozContract.LemmaEntry.LEMMA_EXAMPLE, lemmas[i].getExample());
        }
        return lemmasValues;
    }

    public static List<LemmaModel> getIndex(Context context) {
        List<LemmaModel> indexArray = new ArrayList<LemmaModel>();
        Cursor cursor = context.getContentResolver().query(LingozContract.LemmaEntry.CONTENT_URI_LEMMAS_GET_INDEX, null, null, null, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
            if (count > 0) {
                int id_idx = cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_ID);
                int caption_idx = cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_CAPTION);
                int pos_idx = cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_POS);
                String new_letter;
                String current_letter = cursor.getString(caption_idx).substring(0, 1);
                LemmaModel letter_line = new LemmaModel(-1, current_letter.toUpperCase(), 0, null, null);
                indexArray.add(letter_line);
                for (int i = 0; i < count; i++) {
                    new_letter = cursor.getString(caption_idx).substring(0, 1);
                    if (!current_letter.equals(new_letter)) {
                        letter_line = new LemmaModel(-1, new_letter.toUpperCase(), 0, null, null);
                        indexArray.add(letter_line);
                        current_letter = new_letter;
                    }
                    LemmaModel lemma = new LemmaModel(cursor.getInt(id_idx), cursor.getString(caption_idx), cursor.getInt(pos_idx), null, null);
                    indexArray.add(lemma);
                    cursor.moveToNext();
                }
            }
        }

        return indexArray;
    }

    public static LemmaModel getLemmaDetails(Context context, long lemma_id) {
        LemmaModel lemma_details = null;
        Uri uri = ContentUris.withAppendedId(LingozContract.LemmaEntry.CONTENT_URI_LEMMAS_GET_LEMMA_DETAILS, lemma_id);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            lemma_details = new LemmaModel(
                    cursor.getInt(cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_ID)),
                    cursor.getString(cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_CAPTION)),
                    cursor.getInt(cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_POS)),
                    cursor.getString(cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_DEFINITION)),
                    cursor.getString(cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_EXAMPLE))
            );
        }
        return lemma_details;
    }


    public static List<LemmaModel> getRandomRows(Context context, long rowsCount) {
        List<LemmaModel> indexArray = new ArrayList<LemmaModel>();

        Uri uri = ContentUris.withAppendedId(LingozContract.LemmaEntry.CONTENT_URI_LEMMAS_GET_RANDOM_ROWS, rowsCount);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
            if (count > 0) {
                int id_idx = cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_ID);
                int caption_idx = cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_CAPTION);
                int definition_idx = cursor.getColumnIndex(LingozContract.LemmaEntry.LEMMA_DEFINITION);
                for (int i = 0; i < count; i++) {
                    LemmaModel lemma = new LemmaModel(cursor.getInt(id_idx), cursor.getString(caption_idx), -1, cursor.getString(definition_idx), null);
                    indexArray.add(lemma);
                    cursor.moveToNext();
                }
            }
        }

        return indexArray;
    }


}

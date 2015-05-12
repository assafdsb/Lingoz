package com.xplete.lingoz.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.xplete.lingoz.database.Translations;
import com.xplete.lingoz.models.TranslationModel;

import java.util.List;

public class TranslationsLoader extends AsyncTaskLoader<List<TranslationModel>> {

    private Context mContext;
    private int mLemma_id;

    public TranslationsLoader(Context context, int lemma_id) {
        super(context);
        mContext = context;
        mLemma_id = lemma_id;
    }

    @Override
    public List<TranslationModel> loadInBackground() {
        List<TranslationModel> translations = Translations.getTranslation(mContext, mLemma_id);
        return translations;
    }

    @Override
    public void deliverResult(List<TranslationModel> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }
}

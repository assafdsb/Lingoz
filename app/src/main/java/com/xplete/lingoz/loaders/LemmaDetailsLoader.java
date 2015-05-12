package com.xplete.lingoz.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.xplete.lingoz.database.Lemmas;
import com.xplete.lingoz.models.LemmaModel;

public class LemmaDetailsLoader extends AsyncTaskLoader<LemmaModel> {

    private Context mContext;
    private int mLemma_id;

    public LemmaDetailsLoader(Context context, int lemma_id) {
        super(context);
        mContext = context;
        mLemma_id = lemma_id;
    }

    @Override
    public LemmaModel loadInBackground() {
        LemmaModel lemma = Lemmas.getLemmaDetails(mContext, mLemma_id);
        return lemma;
    }

    @Override
    public void deliverResult(LemmaModel data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }
}

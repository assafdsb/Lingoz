package com.xplete.lingoz.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.xplete.lingoz.database.Lemmas;
import com.xplete.lingoz.models.LemmaModel;

import java.util.List;

public class RandomLemmasLoader extends AsyncTaskLoader<List<LemmaModel>> {

    private Context mContext;
    private long mRowsCount;

    public RandomLemmasLoader(Context context, long rowsCount) {
        super(context);
        mContext = context;
        mRowsCount = rowsCount;
    }

    @Override
    public List<LemmaModel> loadInBackground() {
        List<LemmaModel> lemmas = Lemmas.getRandomRows(mContext, mRowsCount);
        return lemmas;
    }

    @Override
    public void deliverResult(List<LemmaModel> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }
}

package com.xplete.lingoz.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.xplete.lingoz.database.Lemmas;
import com.xplete.lingoz.models.LemmaModel;

import java.util.List;

public class LemmasIndexLoader extends AsyncTaskLoader<List<LemmaModel>> {

    private Context mContext;

    public LemmasIndexLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<LemmaModel> loadInBackground() {
        List<LemmaModel> lemmasIndex = Lemmas.getIndex(mContext);
        return lemmasIndex;
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

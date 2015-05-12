package com.xplete.lingoz.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.xplete.lingoz.database.Locales;
import com.xplete.lingoz.models.LocaleModel;

import java.util.List;

public class LocalesLoader extends AsyncTaskLoader<List<LocaleModel>> {

    private Context mContext;

    public LocalesLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public List<LocaleModel> loadInBackground() {
        List<LocaleModel> locales = Locales.getLocales(mContext);
        return locales;
    }

    @Override
    public void deliverResult(List<LocaleModel> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }
}

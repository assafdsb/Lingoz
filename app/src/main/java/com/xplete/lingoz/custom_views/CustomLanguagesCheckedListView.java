package com.xplete.lingoz.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xplete.lingoz.adapters.CheckedLanguagesAdapter;
import com.xplete.lingoz.database.Locales;
import com.xplete.lingoz.models.LocaleModel;

import java.util.ArrayList;
import java.util.List;

public class CustomLanguagesCheckedListView extends ListView implements android.widget.AdapterView.OnItemClickListener {

    private static Context mContext;
    private CheckedLanguagesAdapter mAdapter;
    private List<LocaleModel> mCheckedLanguagesArray = new ArrayList<LocaleModel>();

    public CustomLanguagesCheckedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAdapter = new CheckedLanguagesAdapter(mContext);
        mAdapter.setData(mCheckedLanguagesArray);
        setAdapter(mAdapter);
        setOnItemClickListener(this);
    }

    public void setData(List<LocaleModel> languagesArray) {
        mCheckedLanguagesArray = languagesArray;
        mAdapter.setData(mCheckedLanguagesArray);
        mAdapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LocaleModel locale = mCheckedLanguagesArray.get(position);
        boolean newCheckedVal = !locale.isUser_preference();
        mAdapter.setChecked(position, newCheckedVal);
        Locales.setUserPreference(mContext, locale.getCode(),newCheckedVal);
    }

}

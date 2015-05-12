package com.xplete.lingoz.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_VISUAL;

public class CustomTranslationBox extends LinearLayout {

    private Context mContext;
    private TextView mTvLocale;
    private TextView mTvTranslation;

    public CustomTranslationBox(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_translation_box, this, true);
        setViewClassMembers();
        changeFonts();
    }

    private void setViewClassMembers() {
        mTvLocale = (TextView) findViewById(R.id.tvLocale);
        mTvTranslation = (TextView) findViewById(R.id.tvTranslation);
    }

    private void changeFonts() {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), CONSTS_VISUAL.FONT_PATH);
        mTvLocale.setTypeface(tf);
        mTvTranslation.setTypeface(tf);
    }

    public void setValues(String locale, String translation) {
        mTvLocale.setText(locale);
        mTvTranslation.setText(translation);
    }
}

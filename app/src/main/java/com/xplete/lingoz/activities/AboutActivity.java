package com.xplete.lingoz.activities;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_VISUAL;

public class AboutActivity extends Activity {

    private TextView mTvVersion;
    private TextView mTvBuild;
    private TextView mTvCourtesy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setViewsClassMembers();
        changeFonts();
        setViewsValues();
    }

    private void setViewsClassMembers() {
        mTvVersion = (TextView) findViewById(R.id.tvAboutVersion);
        mTvBuild = (TextView) findViewById(R.id.tvAboutBuild);
        mTvCourtesy = (TextView) findViewById(R.id.tvCourtesy);
    }

    private void changeFonts() {
        Typeface tf = Typeface.createFromAsset(getAssets(), CONSTS_VISUAL.FONT_PATH);
        mTvVersion.setTypeface(tf);
        mTvBuild.setTypeface(tf);
        mTvCourtesy.setTypeface(tf);
    }

    private void setViewsValues() {
        PackageInfo pInfo;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mTvVersion.setText(getString(R.string.about_version_title) + ": " + pInfo.versionName);
            mTvBuild.setText(getString(R.string.about_build_title) + ": " + pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}

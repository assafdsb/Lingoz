package com.xplete.lingoz.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_PREFERENCES;
import com.xplete.lingoz.consts.CONSTS_VISUAL;
import com.xplete.lingoz.custom_views.CustomLanguagesCheckedListView;
import com.xplete.lingoz.loaders.LocalesLoader;
import com.xplete.lingoz.models.LocaleModel;
import com.xplete.lingoz.utils.AlarmUtils;
import com.xplete.lingoz.utils.PrefUtils;

import java.util.List;

public class PreferencesActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<LocaleModel>> {

    private static final int LANGUAGES_LOADER_ID = 2;

    private TextView mTvPreferredLanguage;
    private TextView mTvWordOfTheDay;
    private TextView mTvNotificationVolume;
    private SeekBar mSeekbarNotificationVolume;
    private Switch mSwWordOfTheDay;
    private CustomLanguagesCheckedListView mLvLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setViewClassMembers();
        changeFonts();
        setSeekbarBounderies();
        loadValues();
        setOnSeekBarNotificationVolumeChangeListener();
        setWordOfTheDaySwitchOnCheckedChangeListener();
        getSupportLoaderManager().initLoader(LANGUAGES_LOADER_ID, null, this);
    }

    private void setViewClassMembers() {
        mTvPreferredLanguage = (TextView) findViewById(R.id.tvPreferredLanguage);
        mTvWordOfTheDay = (TextView) findViewById(R.id.tvWordOfTheDay);
        mTvNotificationVolume = (TextView) findViewById(R.id.tvNotificationVolume);
        mSeekbarNotificationVolume = (SeekBar) findViewById(R.id.prefsSeekBarNotificationVolume);
        mSwWordOfTheDay = (Switch) findViewById(R.id.swWordOfTheDay);
        mLvLanguages = (CustomLanguagesCheckedListView) findViewById(R.id.lvLanguages);
    }

    private void changeFonts() {
        Typeface tf = Typeface.createFromAsset(getAssets(), CONSTS_VISUAL.FONT_PATH);
        mTvPreferredLanguage.setTypeface(tf);
        mTvWordOfTheDay.setTypeface(tf);
        mTvNotificationVolume.setTypeface(tf);
    }

    private void loadValues() {
        // switch word of the day
        mSwWordOfTheDay.setChecked(PrefUtils.getPrefWordOfTheDayValue(this));

        // notifications volume
        mSeekbarNotificationVolume.setProgress(PrefUtils.getAppVolume(this));
    }


    // word of the day
    private void setWordOfTheDaySwitchOnCheckedChangeListener() {
        mSwWordOfTheDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefUtils.setWordOfTheDay(isChecked, PreferencesActivity.this);
                if (isChecked) {
                    // set Alarm
                    AlarmUtils.setWordOfTheDayAlarm(PreferencesActivity.this);
                } else {
                    // cancel Alarm
                    AlarmUtils.cancelWordOfTheDayAlarm(PreferencesActivity.this);
                }
            }
        });
    }

    ///  Seekbar
    private void setOnSeekBarNotificationVolumeChangeListener() {
        mSeekbarNotificationVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                PrefUtils.setAppVolume(progress, PreferencesActivity.this);
            }
        });
    }

    private void setSeekbarBounderies() {
        mSeekbarNotificationVolume.setMax(CONSTS_PREFERENCES.APP_MAX_VOLUME - 1);
    }


    @Override
    public Loader<List<LocaleModel>> onCreateLoader(int id, Bundle args) {
        LocalesLoader loader = new LocalesLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<LocaleModel>> loader, List<LocaleModel> data) {
        mLvLanguages.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<LocaleModel>> loader) {

    }
}

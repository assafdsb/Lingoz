package com.xplete.lingoz.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_MAIN_MENU_MESSAGES;
import com.xplete.lingoz.consts.CONSTS_VISUAL;
import com.xplete.lingoz.custom_views.CustomMainMenuButton;
import com.xplete.lingoz.utils.AlarmUtils;
import com.xplete.lingoz.utils.PrefUtils;
import com.xplete.lingoz.utils.Utils;

public class MainActivity extends Activity {

    private TextView mTvTitle;
    private CustomMainMenuButton mCustomButtonPlay;
    private CustomMainMenuButton mCustomButtonDictionary;
    private CustomMainMenuButton mCustomButtonPreferences;
    private CustomMainMenuButton mCustomButtonShare;
    private CustomMainMenuButton mCustomButtonAbout;

    private Handler mHandlerMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setViewClassMembers();
        changeFonts();
        prepareHandler();
        setButtonsValues();
        setWordOfTheDayAlarm();

    }

    private void setViewClassMembers() {
        mTvTitle = (TextView) findViewById(R.id.tvMainMenuTitle);
        mCustomButtonPlay = (CustomMainMenuButton) findViewById(R.id.btnMainMenuPlay);
        mCustomButtonDictionary = (CustomMainMenuButton) findViewById(R.id.btnMainMenuDictionary);
        mCustomButtonPreferences = (CustomMainMenuButton) findViewById(R.id.btnMainMenuPreferences);
        mCustomButtonShare = (CustomMainMenuButton) findViewById(R.id.btnMainMenuShare);
        mCustomButtonAbout = (CustomMainMenuButton) findViewById(R.id.btnMainMenuAbout);
    }

    private void setButtonsValues() {
        mCustomButtonPlay.setButtonValues(getResources().getDrawable(R.drawable.icon_verbs), getString(R.string.main_menu_btn_play), mHandlerMenu, CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_PLAY);
        mCustomButtonDictionary.setButtonValues(getResources().getDrawable(R.drawable.icon_dictionary), getString(R.string.main_menu_btn_dictionary), mHandlerMenu, CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_DICTIONARY);
        mCustomButtonPreferences.setButtonValues(getResources().getDrawable(R.drawable.icon_settings), getString(R.string.main_menu_btn_preferences), mHandlerMenu, CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_PREFERENCES);
        mCustomButtonShare.setButtonValues(getResources().getDrawable(R.drawable.icon_share), getString(R.string.main_menu_btn_share), mHandlerMenu, CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_SHARE);
        mCustomButtonAbout.setButtonValues(getResources().getDrawable(R.drawable.icon_about), getString(R.string.main_menu_btn_about), mHandlerMenu, CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_ABOUT);
    }

    private void setWordOfTheDayAlarm() {
        if (!PrefUtils.wordOfTheDayKeyExists(this)) {
            AlarmUtils.setWordOfTheDayAlarm(this);
            PrefUtils.setWordOfTheDay(true, this);
        }
    }

    private void changeFonts() {
        Typeface tf = Typeface.createFromAsset(getAssets(), CONSTS_VISUAL.FONT_PATH);
        mTvTitle.setTypeface(tf);
    }

    private void prepareHandler() {
        mHandlerMenu = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_PLAY: {
                        Intent i = new Intent(MainActivity.this, PlayActivity.class);
                        startActivity(i);
                        break;
                    }
                    case CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_DICTIONARY: {
                        Intent i = new Intent(MainActivity.this, HeadwordListActivity.class);
                        startActivity(i);
                        break;
                    }
                    case CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_PREFERENCES: {
                        Intent i = new Intent(MainActivity.this, PreferencesActivity.class);
                        startActivity(i);
                        break;
                    }
                    case CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_SHARE:
                        Utils.share(MainActivity.this);
                        break;
                    case CONSTS_MAIN_MENU_MESSAGES.MENU_ITEM_ABOUT: {
                        Intent i = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(i);
                        break;
                    }
                }
            }

        };
    }
}

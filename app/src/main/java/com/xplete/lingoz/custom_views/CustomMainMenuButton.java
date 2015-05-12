package com.xplete.lingoz.custom_views;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_VISUAL;

public class CustomMainMenuButton extends LinearLayout {

    private Context mContext;
    private TextView mTvCaption;
    private ImageView mIvIcon;
    private LinearLayout mLlCustomButton;
    private Handler mRefToMainMenuHandler;
    private int mMenuItemMessage;

    public CustomMainMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_main_menu_button, this, true);
        setViewClassMembers();
        changeFonts();
        setListener();
    }

    private void setViewClassMembers() {
        mTvCaption = (TextView) findViewById(R.id.tvMainMenuButtonCaption);
        mIvIcon = (ImageView) findViewById(R.id.ivMainMenuButtonIcon);
        mLlCustomButton = (LinearLayout) findViewById(R.id.llCustomMainMenuButton);
    }

    private void changeFonts() {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), CONSTS_VISUAL.FONT_PATH);
        mTvCaption.setTypeface(tf);
    }

    public void setButtonValues(Drawable icon, String caption, Handler refToMainMenuHandler, int menuItemMessage) {
        mIvIcon.setImageDrawable(icon);
        mTvCaption.setText(caption);
        mRefToMainMenuHandler = refToMainMenuHandler;
        mMenuItemMessage = menuItemMessage;
    }

    private void setListener() {
        mLlCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefToMainMenuHandler.sendEmptyMessage(mMenuItemMessage);
            }
        });
    }
}

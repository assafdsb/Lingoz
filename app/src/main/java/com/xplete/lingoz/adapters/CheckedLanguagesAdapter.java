package com.xplete.lingoz.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_VISUAL;
import com.xplete.lingoz.models.LocaleModel;

import java.util.ArrayList;
import java.util.List;

public class CheckedLanguagesAdapter extends BaseAdapter {

    private static final int ITEM_TYPE_ROW = 0;
    private static final int NO_OF_TYPES = 1;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    List<LocaleModel> mLanguagesRows = new ArrayList<LocaleModel>();

    public CheckedLanguagesAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<LocaleModel> languagesArray) {
        mLanguagesRows = languagesArray;
    }

    public void setChecked(int position, boolean newVal) {
        mLanguagesRows.get(position).setUser_preference(newVal);
        notifyDataSetChanged();
    }

    public int getCount() {
        return mLanguagesRows.size();
    }

    @Override
    public int getViewTypeCount() {
        return NO_OF_TYPES;
    }

    public Object getItem(int position) {
        return mLanguagesRows.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ROW;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // View view = null;
        LocaleModel currentRow = mLanguagesRows.get(position);

        ViewHolderCheckedListRow holderRow = null;
        if (convertView == null) {
            holderRow = new ViewHolderCheckedListRow();
            convertView = mLayoutInflater.inflate(R.layout.checked_listview_row, null);
            holderRow.chkRow = (CheckBox) convertView.findViewById(R.id.chkRowCheckbox);
            holderRow.tvRowCaption = (TextView) convertView.findViewById(R.id.tvRowCaption);
            convertView.setTag(holderRow);

            // set typeface
            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), CONSTS_VISUAL.FONT_PATH);
            holderRow.tvRowCaption.setTypeface(tf);
            holderRow.tvRowCaption.setTextColor(mContext.getResources().getColor(R.color.preference_value));

        } else {
            holderRow = (ViewHolderCheckedListRow) convertView.getTag();
        }

        holderRow.chkRow.setChecked(currentRow.isUser_preference());
        holderRow.tvRowCaption.setText(currentRow.getCaption());

        return convertView;
    }

    public static class ViewHolderCheckedListRow {
        public CheckBox chkRow;
        public TextView tvRowCaption;
    }

}


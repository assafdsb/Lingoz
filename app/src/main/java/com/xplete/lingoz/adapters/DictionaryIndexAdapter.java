package com.xplete.lingoz.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_POS;
import com.xplete.lingoz.consts.CONSTS_VISUAL;
import com.xplete.lingoz.models.LemmaModel;

import java.util.ArrayList;
import java.util.List;

public class DictionaryIndexAdapter extends BaseAdapter {

    private static final String TAG = DictionaryIndexAdapter.class.getSimpleName();

    private static final int ITEM_TYPE_LEMMA = 0;
    private static final int ITEM_TYPE_LETTER_SEPARATOR = 1;
    private static final int NO_OF_TYPES = 2;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    List<LemmaModel> mLemmas = new ArrayList<LemmaModel>();

    public DictionaryIndexAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<LemmaModel> lemmasArray) {
        mLemmas = lemmasArray;
    }

    public int getCount() {
        int count = 0;
        if (mLemmas != null) {
            count = mLemmas.size();
        }
        return count;
    }

    @Override
    public int getViewTypeCount() {
        return NO_OF_TYPES;
    }

    public Object getItem(int position) {
        return mLemmas.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        int item_type;
        // setting lemmaId == -1 indicates that this is not a lemma but a letter separator
        if (mLemmas.get(position).getId() == -1) {
            item_type = ITEM_TYPE_LETTER_SEPARATOR;
        } else {
            item_type = ITEM_TYPE_LEMMA;
        }
        return item_type;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int position) {
        if (mLemmas.get(position).getId() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public long getItemId(int position) {
        return mLemmas.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LemmaModel currentLemma = mLemmas.get(position);

        int type = getItemViewType(position);
        switch (type) {
            case ITEM_TYPE_LEMMA:
                ViewHolderLemma holderLemma;
                if (convertView == null) {
                    holderLemma = new ViewHolderLemma();
                    convertView = mLayoutInflater.inflate(R.layout.dictionary_index_lemma_row, null);
                    holderLemma.tvLemmaCaption = (TextView) convertView.findViewById(R.id.tvLemmaCaption);
                    holderLemma.tvPOS = (TextView) convertView.findViewById(R.id.tvPOS);
                    convertView.setTag(holderLemma);

                    // set typeface
                    Typeface tf = Typeface.createFromAsset(mContext.getAssets(), CONSTS_VISUAL.FONT_PATH);
                    holderLemma.tvLemmaCaption.setTypeface(tf);
                    holderLemma.tvPOS.setTypeface(tf);
                } else {
                    holderLemma = (ViewHolderLemma) convertView.getTag();
                }
                holderLemma.tvLemmaCaption.setText(currentLemma.getCaption());
                holderLemma.tvPOS.setText(CONSTS_POS.getPosCaption(currentLemma.getPos()));
                convertView.setFocusable(false);
                break;
            case ITEM_TYPE_LETTER_SEPARATOR:
                ViewHolderLetterSeparator holderSeparator;
                if (convertView == null) {
                    holderSeparator = new ViewHolderLetterSeparator();
                    convertView = mLayoutInflater.inflate(R.layout.dictionary_index_letter_row, null);
                    holderSeparator.tvLetterSeparator = (TextView) convertView.findViewById(R.id.tvIndexLetter);
                    convertView.setTag(holderSeparator);
                    // set typeface
                    Typeface tf = Typeface.createFromAsset(mContext.getAssets(), CONSTS_VISUAL.FONT_PATH);
                    holderSeparator.tvLetterSeparator.setTypeface(tf);
                } else {
                    holderSeparator = (ViewHolderLetterSeparator) convertView.getTag();
                }
                holderSeparator.tvLetterSeparator.setText(currentLemma.getCaption());
                convertView.setFocusable(false);
                convertView.setFocusableInTouchMode(false);
                break;
        }

        return convertView;
    }

    public static class ViewHolderLemma {
        public TextView tvLemmaCaption;
        public TextView tvPOS;
    }

    public static class ViewHolderLetterSeparator {
        public TextView tvLetterSeparator;
    }
}

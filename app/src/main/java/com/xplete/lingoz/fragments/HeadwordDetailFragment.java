package com.xplete.lingoz.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xplete.lingoz.R;

import com.xplete.lingoz.consts.CONSTS_POS;
import com.xplete.lingoz.consts.CONSTS_VISUAL;
import com.xplete.lingoz.custom_views.CustomTranslationBox;
import com.xplete.lingoz.loaders.LemmaDetailsLoader;
import com.xplete.lingoz.loaders.TranslationsLoader;
import com.xplete.lingoz.models.LemmaModel;
import com.xplete.lingoz.models.TranslationModel;
import com.xplete.lingoz.utils.AudioPlayer;

import java.util.List;

public class HeadwordDetailFragment extends Fragment {

    public static final String LEMMA_ID = "lemma_id";

    // VIEWS
    private TextView mTvLemma;
    private TextView mTvPOS;
    private TextView mTvDefinition;
    private TextView mTvExample;
    private ImageView mIvPlayRecording;
    private LinearLayout mLlTranslationsContainer;

    private static final int LEMMA_DETAILS_LOADER = 2;
    private static final int TRANSLATIONS_LOADER = 3;

    private int mLemmaId;

    public HeadwordDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(LEMMA_ID)) {
            mLemmaId = getArguments().getInt(LEMMA_ID);
            this.getLoaderManager().initLoader(LEMMA_DETAILS_LOADER, null, LemmaDetailsListener);
            this.getLoaderManager().initLoader(TRANSLATIONS_LOADER, null, TranslationsListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dictionary_lemma_detail, container, false);
        mTvLemma = (TextView) rootView.findViewById(R.id.tvLemma);
        mTvPOS = (TextView) rootView.findViewById(R.id.tvPOS);
        mTvDefinition = (TextView) rootView.findViewById(R.id.tvDefinition);
        mTvExample = (TextView) rootView.findViewById(R.id.tvExample);
        mLlTranslationsContainer = (LinearLayout) rootView.findViewById(R.id.llTranslationsContainer);
        mIvPlayRecording = (ImageView) rootView.findViewById(R.id.ivPlayRecording);

        // change font
        Typeface tf = Typeface.createFromAsset(rootView.getContext().getAssets(), CONSTS_VISUAL.FONT_PATH);
        mTvLemma.setTypeface(tf);
        mTvPOS.setTypeface(tf);
        mTvDefinition.setTypeface(tf);
        mTvExample.setTypeface(tf);

        setPlayButtonOnClickListener();

        return rootView;
    }

    private void setPlayButtonOnClickListener() {
        mIvPlayRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioPlayer.playLemmaRecording(getActivity(), mLemmaId);
            }
        });
    }

    private LoaderManager.LoaderCallbacks<LemmaModel> LemmaDetailsListener = new LoaderManager.LoaderCallbacks<LemmaModel>() {
        @Override
        public Loader<LemmaModel> onCreateLoader(int id, Bundle args) {
            LemmaDetailsLoader lemmaDetailsLoader = new LemmaDetailsLoader(getActivity(), mLemmaId);
            return lemmaDetailsLoader;
        }

        @Override
        public void onLoadFinished(Loader<LemmaModel> loader, LemmaModel data) {
            mLemmaId = data.getId();

            mTvLemma.setText(data.getCaption());
            mTvPOS.setText(CONSTS_POS.getPosCaption(data.getPos()));
            mTvDefinition.setText(data.getDefinition());
            mTvExample.setText(data.getExample());
        }

        @Override
        public void onLoaderReset(Loader<LemmaModel> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<List<TranslationModel>> TranslationsListener = new LoaderManager.LoaderCallbacks<List<TranslationModel>>() {
        @Override
        public Loader<List<TranslationModel>> onCreateLoader(int id, Bundle args) {
            TranslationsLoader translationLoader = new TranslationsLoader(getActivity(), mLemmaId);
            return translationLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<TranslationModel>> loader, List<TranslationModel> data) {
            mLlTranslationsContainer.removeAllViews();
            for (int i = 0; i < data.size(); i++) {
                TranslationModel translation = data.get(i);
                CustomTranslationBox transBox = new CustomTranslationBox(getActivity());
                transBox.setValues(translation.getLocale_caption(), translation.getTranslation());
                mLlTranslationsContainer.addView(transBox);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<TranslationModel>> loader) {

        }
    };
}

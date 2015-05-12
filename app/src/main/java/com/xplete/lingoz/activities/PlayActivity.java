package com.xplete.lingoz.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xplete.lingoz.R;
import com.xplete.lingoz.consts.CONSTS_PLAY;
import com.xplete.lingoz.consts.CONSTS_VISUAL;
import com.xplete.lingoz.loaders.RandomLemmasLoader;
import com.xplete.lingoz.models.LemmaModel;
import com.xplete.lingoz.utils.AudioPlayer;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.shuffle;

public class PlayActivity extends FragmentActivity {


    private final String FRAGMENT_TAG = "PLAY_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlayFragment(), FRAGMENT_TAG)
                    .commit();
        }
        else
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PlayFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }


    public static class PlayFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<LemmaModel>> {

        private TextView mTvPlayDefinition;
        private Button mBtnAnswer1;
        private Button mBtnAnswer2;
        private Button mBtnAnswer3;
        private Button mBtnAnswer4;

        private List<LemmaModel> mLemmas;
        private int mCurrLemma;

        protected static final int RANDOM_ROWS_LOADER_ID = 10;
        protected static final int RANDOM_FALSE_ROWS_LOADER_ID = 20;

        public PlayFragment() {
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (savedInstanceState == null) {
                this.getLoaderManager().initLoader(RANDOM_ROWS_LOADER_ID, null, this);
            }
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_play, container, false);
            mTvPlayDefinition = (TextView) rootView.findViewById(R.id.tvPlayDefinition);
            mBtnAnswer1 = (Button) rootView.findViewById(R.id.btnAnswer1);
            mBtnAnswer2 = (Button) rootView.findViewById(R.id.btnAnswer2);
            mBtnAnswer3 = (Button) rootView.findViewById(R.id.btnAnswer3);
            mBtnAnswer4 = (Button) rootView.findViewById(R.id.btnAnswer4);
            changeFonts();
            setButtonsOnClickEvent();
            return rootView;
        }

        private void setButtonsOnClickEvent() {

            mBtnAnswer1.setOnClickListener(btnAnswerListener);
            mBtnAnswer2.setOnClickListener(btnAnswerListener);
            mBtnAnswer3.setOnClickListener(btnAnswerListener);
            mBtnAnswer4.setOnClickListener(btnAnswerListener);
        }

        private View.OnClickListener btnAnswerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == true) {
                    AudioPlayer.correctAnswer(getActivity());
                    mCurrLemma++;
                    if (mCurrLemma == mLemmas.size()) {
                        restartLoader(RANDOM_ROWS_LOADER_ID);
                    } else {
                        setQuestionViews();
                    }
                } else {
                    AudioPlayer.wrongAnswer(getActivity());
                }
            }
        };

        private void restartLoader(int loaderId) {
            switch (loaderId) {
                case RANDOM_ROWS_LOADER_ID:
                    this.getLoaderManager().restartLoader(RANDOM_ROWS_LOADER_ID, null, this);
                    break;
                case RANDOM_FALSE_ROWS_LOADER_ID:
                    this.getLoaderManager().restartLoader(RANDOM_FALSE_ROWS_LOADER_ID, null, this);
                    break;
            }
        }

        private void changeFonts() {
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), CONSTS_VISUAL.FONT_PATH);
            mTvPlayDefinition.setTypeface(tf);
            mBtnAnswer1.setTypeface(tf);
            mBtnAnswer2.setTypeface(tf);
            mBtnAnswer3.setTypeface(tf);
            mBtnAnswer4.setTypeface(tf);
        }

        @Override
        public Loader<List<LemmaModel>> onCreateLoader(int id, Bundle args) {
            RandomLemmasLoader loader = null;
            switch (id) {
                case RANDOM_ROWS_LOADER_ID:
                    loader = new RandomLemmasLoader(getActivity(), CONSTS_PLAY.ROWS_COUNT_IN_SESSION);
                    break;
                case RANDOM_FALSE_ROWS_LOADER_ID:
                    loader = new RandomLemmasLoader(getActivity(), 3);
                    break;
            }
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<LemmaModel>> loader, List<LemmaModel> data) {
            switch (loader.getId()) {
                case RANDOM_ROWS_LOADER_ID:
                    mLemmas = data;
                    mCurrLemma = 1;
                    setQuestionViews();
                    break;
                case RANDOM_FALSE_ROWS_LOADER_ID:
                    setAnswers(data);
                    break;
            }
        }

        private void setAnswers(List<LemmaModel> falseAnswers) {
            List<Integer> list = Arrays.asList(1, 2, 3, 4);
            shuffle(list);

            setButtonCaption(list.get(0), mLemmas.get(mCurrLemma).getCaption(), true);

            for (int i = 1; i < 4; i++) {
                setButtonCaption(list.get(i), falseAnswers.get(i - 1).getCaption(), false);
            }
        }

        private void setButtonCaption(int btnNum, String caption, boolean isRightAnswer) {
            Button refToButton = null;
            switch (btnNum) {
                case 1:
                    refToButton = mBtnAnswer1;
                    break;
                case 2:
                    refToButton = mBtnAnswer2;
                    break;
                case 3:
                    refToButton = mBtnAnswer3;
                    break;
                case 4:
                    refToButton = mBtnAnswer4;
                    break;
            }

            refToButton.setText(caption);
            refToButton.setTag(isRightAnswer);
            if (isRightAnswer) {
                refToButton.setBackgroundResource(R.drawable.answer_correct_button_selector);
            } else {
                refToButton.setBackgroundResource(R.drawable.answer_fail_button_selector);
            }
        }

        private void setQuestionViews() {
            mTvPlayDefinition.setText(mLemmas.get(mCurrLemma).getDefinition());
            restartLoader(RANDOM_FALSE_ROWS_LOADER_ID);
        }

        @Override
        public void onLoaderReset(Loader<List<LemmaModel>> loader) {

        }
    }
}

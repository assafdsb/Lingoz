package com.xplete.lingoz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.xplete.lingoz.R;
import com.xplete.lingoz.fragments.LemmaDetailFragment;
import com.xplete.lingoz.fragments.LemmaListFragment;

public class LemmaListActivity extends FragmentActivity
        implements LemmaListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lemma_list);

        if (findViewById(R.id.lemma_detail_container) != null) {
            mTwoPane = true;

            ((LemmaListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.headword_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(int id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(LemmaDetailFragment.LEMMA_ID, id);
            LemmaDetailFragment fragment = new LemmaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lemma_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, LemmaDetailActivity.class);
            detailIntent.putExtra(LemmaDetailFragment.LEMMA_ID, id);
            startActivity(detailIntent);
        }
    }
}

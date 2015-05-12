package com.xplete.lingoz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.xplete.lingoz.R;
import com.xplete.lingoz.fragments.HeadwordDetailFragment;
import com.xplete.lingoz.fragments.HeadwordListFragment;

public class HeadwordListActivity extends FragmentActivity
        implements HeadwordListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headword_list);

        if (findViewById(R.id.headword_detail_container) != null) {
            mTwoPane = true;

            ((HeadwordListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.headword_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(int id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(HeadwordDetailFragment.LEMMA_ID, id);
            HeadwordDetailFragment fragment = new HeadwordDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.headword_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, HeadwordDetailActivity.class);
            detailIntent.putExtra(HeadwordDetailFragment.LEMMA_ID, id);
            startActivity(detailIntent);
        }
    }
}

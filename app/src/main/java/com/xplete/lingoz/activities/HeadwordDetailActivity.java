package com.xplete.lingoz.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.xplete.lingoz.R;
import com.xplete.lingoz.fragments.HeadwordDetailFragment;


public class HeadwordDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headword_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(HeadwordDetailFragment.LEMMA_ID, getIntent().getExtras().getInt(HeadwordDetailFragment.LEMMA_ID));

            HeadwordDetailFragment fragment = new HeadwordDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.headword_detail_container, fragment).commit();
        }
    }

}

package com.xplete.lingoz.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xplete.lingoz.R;
import com.xplete.lingoz.fragments.LemmaDetailFragment;


public class LemmaDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lemma_detail);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(LemmaDetailFragment.LEMMA_ID, getIntent().getExtras().getInt(LemmaDetailFragment.LEMMA_ID));

            LemmaDetailFragment fragment = new LemmaDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.lemma_detail_container, fragment).commit();
        }
    }

}

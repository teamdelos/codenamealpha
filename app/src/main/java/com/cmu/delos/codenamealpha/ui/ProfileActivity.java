package com.cmu.delos.codenamealpha.ui;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.cmu.delos.codenamealpha.R;


public class ProfileActivity extends AbstractAlphaActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupToolbar();
    }


    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.app_name);
//        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);
    }

}

package com.github.piasy.playground.repro.immactivityresulterror;

import com.github.piasy.handywidgets.centertitlesidebuttonbar.CenterTitleSideButtonBar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final CenterTitleSideButtonBar titleBar = (CenterTitleSideButtonBar) findViewById(
                R.id.mTitleBar);

        titleBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                titleBar.showSearchView();
            }
        }, 50);

        findViewById(R.id.mBtnSendResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        findViewById(R.id.mBtnSendResult2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBar.hideSearchView();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}

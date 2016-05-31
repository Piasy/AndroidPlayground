package com.github.piasy.keyboardeventdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(android.R.id.content, new BelowFragment(), "BelowFragment")
                .commit();
        new AboveDialogFragment().show(fragmentManager, "AboveDialogFragment");
    }
}

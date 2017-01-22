package com.github.piasy.viewdrawdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View customView = findViewById(R.id.mCustomView);

        findViewById(R.id.mInvalidate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.invalidate();
            }
        });

        findViewById(R.id.mRequestLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.requestLayout();
            }
        });
    }
}

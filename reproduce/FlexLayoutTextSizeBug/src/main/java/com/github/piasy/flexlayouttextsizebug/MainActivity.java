package com.github.piasy.flexlayouttextsizebug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv2 = (TextView) findViewById(R.id.mTv2);
        tv2.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv2.setText("Hello world!");
                tv2.setVisibility(View.VISIBLE);
            }
        }, 200);
    }
}

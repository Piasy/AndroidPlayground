package com.github.piasy.realclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private TickSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        mSurfaceView = (TickSurfaceView) findViewById(R.id.mSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSurfaceView.stopTick();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSurfaceView.startTick();
    }
}

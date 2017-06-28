package com.github.piasy.realclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private boolean mStarted;
    private TickSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSurfaceView = (TickSurfaceView) findViewById(R.id.mSurfaceView);

        final Button btnStart = (Button) findViewById(R.id.mBtnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mStarted = !mStarted;
                btnStart.setText(mStarted ? "stop" : "start");
                if (mStarted) {
                    mSurfaceView.startTick();
                } else {
                    mSurfaceView.stopTick();
                }
            }
        });
    }
}

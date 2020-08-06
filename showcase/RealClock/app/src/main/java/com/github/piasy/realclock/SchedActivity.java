package com.github.piasy.realclock;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Choreographer;

public class SchedActivity extends AppCompatActivity {

    private HandlerThread mHandlerThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sched);

        mHandlerThread = new HandlerThread("choreographer");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                    @Override
                    public void doFrame(final long frameTimeNanos) {
                        System.out.println("Choreographer doFrame " + System.currentTimeMillis());
                    }
                });
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                long lastTs = 0;
                while (true) {
                    long ts = System.currentTimeMillis();
                    System.out.println("Thread sleep " + (ts - lastTs));
                    lastTs = ts;

                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

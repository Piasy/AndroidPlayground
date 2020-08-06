package com.github.piasy.realclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Piasy{github.com/Piasy} on 11/06/2017.
 */

public class TickSurfaceView extends SurfaceView implements Runnable {
    private final SurfaceHolder mSurfaceHolder;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Thread mTickThread;
    private volatile boolean mRunning;

    public TickSurfaceView(final Context context) {
        this(context, null);
    }

    public TickSurfaceView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TickSurfaceView(final Context context, final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSurfaceHolder = getHolder();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(200);
    }

    public void startTick() {
        mRunning = true;
        mTickThread = new Thread(this);
        mTickThread.start();
    }

    public void stopTick() {
        mRunning = false;
        boolean retry = true;
        while (retry) {
            try {
                mTickThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (mRunning) {
            if (!mSurfaceHolder.getSurface().isValid()) {
                Log.d("ClockTick", "invalid surface");
                continue;
            }

            long ts = System.currentTimeMillis();
            Canvas canvas = mSurfaceHolder.lockCanvas();
            long lockCanvas = System.currentTimeMillis();
            canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
            canvas.drawText(String.valueOf(ts % 1_000_000_000), 50, 200, mPaint);
            long drawFinish = System.currentTimeMillis();
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            long unlockCanvas = System.currentTimeMillis();

            Log.d("ClockTick", String.format("tick: %d, lockCanvas: %d, draw: %d, post: %d", ts,
                    lockCanvas - ts, drawFinish - lockCanvas, unlockCanvas - drawFinish));
        }
    }
}

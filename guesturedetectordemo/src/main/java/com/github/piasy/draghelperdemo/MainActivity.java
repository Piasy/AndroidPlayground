/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.draghelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.piasy.draghelperdemo.MoveGestureDetector.SimpleOnMoveGestureListener;
import com.transitionseverywhere.TransitionManager;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mTextView2;
    private TextView mTextView3;

    private RelativeLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (RelativeLayout) findViewById(R.id.mRlContainer);
        mTextView = (TextView) findViewById(R.id.mTv);
        mTextView2 = (TextView) findViewById(R.id.mTv2);
        mTextView3 = (TextView) findViewById(R.id.mTv3);

        final MoveGestureDetector moveGestureDetector =
                new MoveGestureDetector(this, new SimpleOnMoveGestureListener() {
                    @Override
                    public boolean onMove(MoveGestureDetector detector) {
                        Log.d("MainActivity", "" + mIsShowing + ", " + detector.getFocusDelta().y);
                        if (mIsShowing) {
                            mMoveUpY += detector.getFocusDelta().y;
                            if (mMoveUpY > 0) {
                                mMoveUpY = 0;
                            }
                            if (mMoveUpY < -TRIGGER_MOVED_Y) {
                                hidePanel();
                            }
                        } else {
                            mMoveDownY += detector.getFocusDelta().y;
                            if (mMoveDownY < 0) {
                                mMoveDownY = 0;
                            }
                            if (mMoveDownY > TRIGGER_MOVED_Y) {
                                showPanel();
                            }
                        }
                        return true;
                    }
                });

        mContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return moveGestureDetector.onTouchEvent(event);
            }
        });

        mTextView.postDelayed(mHidingRunnable, 2000);
    }

    private static final int TRIGGER_MOVED_Y = 10;
    private int mMoveDownY = 0;
    private int mMoveUpY = 0;
    private boolean mIsShowing = true;

    private Runnable mHidingRunnable = new Runnable() {
        @Override
        public void run() {
            hidePanel();
        }
    };

    private void showPanel() {
        TransitionManager.beginDelayedTransition(mContainer);
        mTextView.setVisibility(View.VISIBLE);
        mTextView2.setVisibility(View.VISIBLE);
        mTextView3.setVisibility(View.VISIBLE);
        mTextView.postDelayed(mHidingRunnable, 2000);
        mIsShowing = true;
        mMoveDownY = 0;
        mMoveUpY = 0;
    }

    private void hidePanel() {
        mTextView.removeCallbacks(mHidingRunnable);
        TransitionManager.beginDelayedTransition(mContainer);
        mTextView.setVisibility(View.GONE);
        mTextView2.setVisibility(View.GONE);
        mTextView3.setVisibility(View.GONE);
        mIsShowing = false;
        mMoveDownY = 0;
        mMoveUpY = 0;
    }
}

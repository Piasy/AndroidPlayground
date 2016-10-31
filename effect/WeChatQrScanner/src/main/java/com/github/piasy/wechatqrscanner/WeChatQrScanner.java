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

package com.github.piasy.wechatqrscanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Piasy{github.com/Piasy} on 19/10/2016.
 */

public class WeChatQrScanner extends FrameLayout {
    private static final int MOVING_INTERVAL = 16;

    private final ImageView mMovingBar;
    private int mBarMargin;
    private int mMovingSpeed;

    private int mHeight;
    private int mCurPos;

    private final Runnable mMovingTask = new Runnable() {
        @Override
        public void run() {
            if (mHeight == 0) {
                mHeight = getHeight();
            }
            if (mHeight != 0) {
                mCurPos += mMovingSpeed;
                if (mCurPos >= mHeight - mBarMargin) {
                    mCurPos = mBarMargin;
                }
                LayoutParams params = (LayoutParams) mMovingBar.getLayoutParams();
                params.topMargin = mCurPos;
                mMovingBar.setLayoutParams(params);
            }

            postDelayed(this, MOVING_INTERVAL);
        }
    };

    public WeChatQrScanner(Context context) {
        this(context, null, 0);
    }

    public WeChatQrScanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeChatQrScanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.WeChatQrScanner, 0, 0);

        int sideBg = a.getResourceId(R.styleable.WeChatQrScanner_side_bg, 0);
        int movingBar = a.getResourceId(R.styleable.WeChatQrScanner_moving_bar, 0);
        mMovingSpeed = a.getInteger(R.styleable.WeChatQrScanner_moving_speed, 3);
        mBarMargin = (int) a.getDimension(R.styleable.WeChatQrScanner_bar_margin, 10);
        a.recycle();

        mMovingBar = new ImageView(context);
        addView(mMovingBar);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        mMovingBar.setLayoutParams(params);
        mMovingBar.setImageResource(movingBar);
        setBackgroundResource(sideBg);
    }

    public void setMovingBar(@DrawableRes int movingBar) {
        mMovingBar.setImageResource(movingBar);
    }

    public void setSideBg(@DrawableRes int sideBg) {
        setBackgroundResource(sideBg);
    }

    public void setMovingSpeed(int speed) {
        mMovingSpeed = speed;
    }

    public void setBarMargin(int margin) {
        mBarMargin = margin;
    }

    public void start() {
        post(mMovingTask);
    }

    public void stop() {
        removeCallbacks(mMovingTask);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHeight = getHeight();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
}

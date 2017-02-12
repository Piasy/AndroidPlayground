package com.github.piasy.viewdrawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Piasy{github.com/Piasy} on 19/01/2017.
 */

public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("CustomView", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(@Px int l, @Px int t, @Px int r, @Px int b) {
        Log.d("CustomView", "layout");
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("CustomView", "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d("CustomView", "dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d("CustomView", "draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("CustomView", "onDraw");
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        Log.d("CustomView", "onTouchEvent " + MotionEvent.actionToString(event.getAction()));
        return true;
    }
}

package com.github.piasy.viewdrawdemo;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Piasy{github.com/Piasy} on 12/02/2017.
 */

public class CustomViewGroup extends FrameLayout {
    public CustomViewGroup(@NonNull final Context context) {
        super(context);
    }

    public CustomViewGroup(@NonNull final Context context,
            @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(@NonNull final Context context,
            @Nullable final AttributeSet attrs, @AttrRes final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int count = 0;

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        count++;
        Log.d("CustomViewGroup", "onInterceptTouchEvent " + MotionEvent.actionToString(event.getAction()) + " " + (count > 3));
        return count > 3;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        Log.d("CustomViewGroup", "onTouchEvent " + MotionEvent.actionToString(event.getAction()));
        return false;
    }
}

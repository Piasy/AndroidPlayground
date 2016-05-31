package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/28.
 */
public class NoFlingNestedScrollView extends NestedScrollView {
    public NoFlingNestedScrollView(Context context) {
        super(context);
    }

    public NoFlingNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoFlingNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void fling(int velocityY) {
        //super.fling(velocityY);
    }
}

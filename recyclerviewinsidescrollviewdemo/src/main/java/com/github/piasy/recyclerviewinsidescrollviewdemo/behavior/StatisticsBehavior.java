package com.github.piasy.recyclerviewinsidescrollviewdemo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/27.
 */
public class StatisticsBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int MARGIN_TOP_MIN_DP = 15;
    private static final int MARGIN_TOP_MAX_DP = 122;

    private static final int TEXT_SIZE_DELTA_SP = 11;
    private static final int TEXT_SIZE_MIN_SP = 17;
    private static final int TEXT_SIZE_MAX_SP = 28;

    private static final int TEXT_COLOR_DELTA_ALPHA = 0x1A;
    private static final int TEXT_COLOR_MIN_ALPHA = 0xE5;
    private static final int TEXT_COLOR_MAX_ALPHA = 0xFF;

    private final int mMarginTopMinPx;
    private final int mMarginTopMaxPx;
    private final int mMarginTopDeltaPx;

    private float mCurrentTextSizeSp = TEXT_SIZE_MAX_SP;
    private int mCurrentTextColorAlpha = TEXT_COLOR_MAX_ALPHA;

    public StatisticsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMarginTopMinPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP_MIN_DP,
                        context.getResources().getDisplayMetrics());
        mMarginTopMaxPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP_MAX_DP,
                        context.getResources().getDisplayMetrics());
        mMarginTopDeltaPx = mMarginTopMaxPx - mMarginTopMinPx;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
            View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target,
            int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset(child, dyConsumed);
    }

    public void offset(View child, int dy) {
        child.offsetTopAndBottom(-dy);
    }
}

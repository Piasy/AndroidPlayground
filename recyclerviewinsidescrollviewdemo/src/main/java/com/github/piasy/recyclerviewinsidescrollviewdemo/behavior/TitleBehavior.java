package com.github.piasy.recyclerviewinsidescrollviewdemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.github.piasy.recyclerviewinsidescrollviewdemo.R;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/27.
 */
public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int STATUS_BAR_HEIGHT_DP = 25;
    // {margin_top of mTvContent}
    private static final int MARGIN_TOP_MIN_ABS_DP = 294;
    // {margin_top of mNestedScrollView} + STATUS_BAR_HEIGHT_DP - {height of mFlTitleBar}
    private static final int START_MOVE_DOWN_POSITION_DP = 137;

    private final int mStatusBarHeightPx;
    private final int mMarginTopMinAbsPx;
    private final int mStartMoveDownPositionPx;

    @IdRes
    private final int mTargetId;

    private final Rect mTargetGlobalRect = new Rect();
    private final Rect mTargetLocalRect = new Rect();

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBehavior);
        mTargetId = a.getResourceId(R.styleable.TitleBehavior_target, -1);
        a.recycle();
        if (mTargetId == -1) {
            throw new IllegalStateException("Must set target id");
        }
        mStatusBarHeightPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, STATUS_BAR_HEIGHT_DP,
                        context.getResources().getDisplayMetrics());
        mMarginTopMinAbsPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP_MIN_ABS_DP,
                        context.getResources().getDisplayMetrics());
        mStartMoveDownPositionPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                START_MOVE_DOWN_POSITION_DP, context.getResources().getDisplayMetrics());
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
            View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target,
            int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset(child, target, dyConsumed);
    }

    public void offset(View child, View target, int dy) {
        View realTarget = target.findViewById(mTargetId);
        if (realTarget != null) {
            realTarget.getGlobalVisibleRect(mTargetGlobalRect);
            realTarget.getLocalVisibleRect(mTargetLocalRect);
            if (dy > 0 || mTargetGlobalRect.top > mStartMoveDownPositionPx ||
                    mTargetLocalRect.top == 0) {
                float newY = mTargetGlobalRect.top - child.getHeight() - mStatusBarHeightPx;
                if (-mMarginTopMinAbsPx <= newY && newY <= 0) {
                    // newY is in legal range, so just follow target
                    child.setY(newY);
                } else if (newY < -mMarginTopMinAbsPx) {
                    // newY is too small, i.e. up over scroll happened, but it shouldn't
                    child.setY(-mMarginTopMinAbsPx);
                } else if (0 < newY) {
                    // newY is greater than 0, i.e. down over scroll happened, but it shouldn't
                    child.setY(0);
                }
            }
        }
    }
}

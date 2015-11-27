package com.github.piasy.recyclerviewinsidescrollviewdemo.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import com.github.piasy.recyclerviewinsidescrollviewdemo.R;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/27.
 */
public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int MARGIN_TOP_MIN_DP = 15;
    private static final int MARGIN_TOP_MAX_DP = 122;

    private static final int TEXT_SIZE_DELTA_SP = 11;
    private static final int TEXT_SIZE_MIN_SP = 17;
    private static final int TEXT_SIZE_MAX_SP = 28;

    private static final int TEXT_COLOR_DELTA_ALPHA = 0x1A;
    private static final int TEXT_COLOR_MIN_ALPHA = 0xE5;
    private static final int TEXT_COLOR_MAX_ALPHA = 0xFF;

    private static final int DEPENDENCY_INIT_TOP_DP = 400;

    private final int mMarginTopMinPx;
    private final int mMarginTopMaxPx;
    private final int mMarginTopDeltaPx;

    @IdRes
    private final int mTargetId;

    private float mCurrentTextSizeSp = TEXT_SIZE_MAX_SP;
    private int mCurrentTextColorAlpha = TEXT_COLOR_MAX_ALPHA;
    private int mCurrentDependencyTopPx;
    private int mStartMovingDownDependencyMarginTopPx;

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBehavior);
        mTargetId = a.getResourceId(R.styleable.TitleBehavior_target, -1);
        a.recycle();
        if (mTargetId == -1) {
            throw new IllegalStateException("Must set target id");
        }
        mMarginTopMinPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP_MIN_DP,
                        context.getResources().getDisplayMetrics());
        mMarginTopMaxPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP_MAX_DP,
                        context.getResources().getDisplayMetrics());
        mMarginTopDeltaPx = mMarginTopMaxPx - mMarginTopMinPx;
        mCurrentDependencyTopPx =
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEPENDENCY_INIT_TOP_DP,
                        context.getResources().getDisplayMetrics());
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
            View directTargetChild, View target, int nestedScrollAxes) {
        return child instanceof TextView;
    }

    private final Rect mGlobalRect = new Rect();

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target,
            int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        target.findViewById(mTargetId).getGlobalVisibleRect(mGlobalRect);
        mCurrentDependencyTopPx = mGlobalRect.top;
        offset(child, target, dyConsumed);
    }

    public void offset(View child, View target, int dy) {
        TextView textView = (TextView) child;
        int top = textView.getTop();
        if (dy > 0) {
            // moving up
            if (top - dy >= mMarginTopMinPx) {
                textView.setTop(top - dy);
                if (top - dy == mMarginTopMinPx) {
                    target.findViewById(mTargetId).getGlobalVisibleRect(mGlobalRect);
                    mStartMovingDownDependencyMarginTopPx = mGlobalRect.top;
                }

                mCurrentTextColorAlpha -= (float) dy / mMarginTopDeltaPx * TEXT_COLOR_DELTA_ALPHA;
                if (TEXT_COLOR_MIN_ALPHA <= mCurrentTextColorAlpha &&
                        mCurrentTextColorAlpha <= TEXT_COLOR_MAX_ALPHA) {
                    textView.setTextColor(mCurrentTextColorAlpha << 24 | 0xFFFFFF);
                } else {
                    mCurrentTextColorAlpha = TEXT_COLOR_MIN_ALPHA;
                }

                ////float scale = 1 -
                ////        (float) dy / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP / mCurrentTextSizeSp;
                //mCurrentTextSizeSp -= (float) dy / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP;
                //if (TEXT_SIZE_MIN_SP <= mCurrentTextSizeSp &&
                //        mCurrentTextSizeSp <= TEXT_SIZE_MAX_SP) {
                //    //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCurrentTextSizeSp);
                //
                //    //textView.setScaleX(scale);
                //    //textView.setScaleY(scale);
                //} else {
                //    mCurrentTextSizeSp = TEXT_SIZE_MIN_SP;
                //}
            } else if (top > mMarginTopMinPx) {
                textView.setTop(mMarginTopMinPx);
                target.findViewById(mTargetId).getGlobalVisibleRect(mGlobalRect);
                mStartMovingDownDependencyMarginTopPx = mGlobalRect.top + top - mMarginTopMinPx;

                mCurrentTextColorAlpha -= (float) (top - mMarginTopMinPx) / mMarginTopDeltaPx *
                        TEXT_COLOR_DELTA_ALPHA;
                if (TEXT_COLOR_MIN_ALPHA <= mCurrentTextColorAlpha &&
                        mCurrentTextColorAlpha <= TEXT_COLOR_MAX_ALPHA) {
                    textView.setTextColor(mCurrentTextColorAlpha << 24 | 0xFFFFFF);
                } else {
                    mCurrentTextColorAlpha = TEXT_COLOR_MIN_ALPHA;
                }

                ////float scale = 1 -
                ////        (float) (top - mMarginTopMinPx) / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP /
                ////                mCurrentTextSizeSp;
                //mCurrentTextSizeSp -=
                //        (float) (top - mMarginTopMinPx) / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP;
                //if (TEXT_SIZE_MIN_SP <= mCurrentTextSizeSp &&
                //        mCurrentTextSizeSp <= TEXT_SIZE_MAX_SP) {
                //    //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCurrentTextSizeSp);
                //
                //    //textView.setScaleX(scale);
                //    //textView.setScaleY(scale);
                //} else {
                //    mCurrentTextSizeSp = TEXT_SIZE_MIN_SP;
                //}
            }
        } else if (mCurrentDependencyTopPx >= mStartMovingDownDependencyMarginTopPx) {
            // moving down
            if (top - dy <= mMarginTopMaxPx) {
                textView.setTop(top - dy);

                mCurrentTextColorAlpha -= (float) dy / mMarginTopDeltaPx * TEXT_COLOR_DELTA_ALPHA;
                if (TEXT_COLOR_MIN_ALPHA <= mCurrentTextColorAlpha &&
                        mCurrentTextColorAlpha <= TEXT_COLOR_MAX_ALPHA) {
                    textView.setTextColor(mCurrentTextColorAlpha << 24 | 0xFFFFFF);
                } else {
                    mCurrentTextColorAlpha = TEXT_COLOR_MAX_ALPHA;
                }

                ////float scale = 1 -
                ////        (float) dy / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP / mCurrentTextSizeSp;
                //mCurrentTextSizeSp -= (float) dy / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP;
                //if (TEXT_SIZE_MIN_SP <= mCurrentTextSizeSp &&
                //        mCurrentTextSizeSp <= TEXT_SIZE_MAX_SP) {
                //    //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCurrentTextSizeSp);
                //
                //    //textView.setScaleX(scale);
                //    //textView.setScaleY(scale);
                //} else {
                //    mCurrentTextSizeSp = TEXT_SIZE_MAX_SP;
                //}
            } else if (top < mMarginTopMaxPx) {
                textView.setTop(mMarginTopMaxPx);

                mCurrentTextColorAlpha -= (float) (top - mMarginTopMaxPx) / mMarginTopDeltaPx *
                        TEXT_COLOR_DELTA_ALPHA;
                if (TEXT_COLOR_MIN_ALPHA <= mCurrentTextColorAlpha &&
                        mCurrentTextColorAlpha <= TEXT_COLOR_MAX_ALPHA) {
                    textView.setTextColor(mCurrentTextColorAlpha << 24 | 0xFFFFFF);
                } else {
                    mCurrentTextColorAlpha = TEXT_COLOR_MAX_ALPHA;
                }

                ////float scale = 1 -
                ////        (float) (top - mMarginTopMaxPx) / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP /
                ////                mCurrentTextSizeSp;
                //mCurrentTextSizeSp -=
                //        (float) (top - mMarginTopMaxPx) / mMarginTopDeltaPx * TEXT_SIZE_DELTA_SP;
                //if (TEXT_SIZE_MIN_SP <= mCurrentTextSizeSp &&
                //        mCurrentTextSizeSp <= TEXT_SIZE_MAX_SP) {
                //    //textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCurrentTextSizeSp);
                //
                //    //textView.setScaleX(scale);
                //    //textView.setScaleY(scale);
                //} else {
                //    mCurrentTextSizeSp = TEXT_SIZE_MAX_SP;
                //}
            }
        }
    }
}

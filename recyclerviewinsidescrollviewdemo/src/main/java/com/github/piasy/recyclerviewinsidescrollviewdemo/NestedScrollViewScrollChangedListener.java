package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/28.
 */
public class NestedScrollViewScrollChangedListener
        implements ViewTreeObserver.OnScrollChangedListener {

    private final HeaderScrollOffPercentListener mHeaderScrollOffPercentListener;

    private final View mHeaderView;

    private final float mScrollOffStartRatio;

    private int mStartScrollOffBottom = -1;

    private int mHeaderViewBottom;

    private final Rect mLocalRect = new Rect();

    private final Rect mGlobalRect = new Rect();

    public NestedScrollViewScrollChangedListener(
            HeaderScrollOffPercentListener headerScrollOffPercentListener, View headerView,
            float scrollOffStartRatio) {
        mHeaderScrollOffPercentListener = headerScrollOffPercentListener;
        mHeaderView = headerView;
        mScrollOffStartRatio = scrollOffStartRatio;
    }

    public interface HeaderScrollOffPercentListener {

        void onHeaderScrollOffPercent(float percent);
    }

    @Override
    public void onScrollChanged() {
        mHeaderView.getGlobalVisibleRect(mGlobalRect);
        mHeaderView.getLocalVisibleRect(mLocalRect);

        if (mStartScrollOffBottom == -1) {
            mStartScrollOffBottom = (int) (mHeaderView.getBottom() * mScrollOffStartRatio);
            mHeaderViewBottom = mHeaderView.getBottom();
        }
        if (mLocalRect.top > mStartScrollOffBottom && mHeaderViewBottom != mStartScrollOffBottom) {
            mHeaderScrollOffPercentListener.onHeaderScrollOffPercent(
                    ((float) mLocalRect.top - mStartScrollOffBottom) / (mHeaderViewBottom
                            - mStartScrollOffBottom));
        } else if ((mLocalRect.top <= 0 && mGlobalRect.top <= 0)
                || mHeaderViewBottom == mStartScrollOffBottom) {
            mHeaderScrollOffPercentListener.onHeaderScrollOffPercent(1);
        } else {
            mHeaderScrollOffPercentListener.onHeaderScrollOffPercent(0);
        }
    }
}

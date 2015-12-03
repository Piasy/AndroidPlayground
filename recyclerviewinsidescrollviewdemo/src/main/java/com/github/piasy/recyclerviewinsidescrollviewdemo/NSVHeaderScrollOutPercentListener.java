package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/28.
 *
 * NestedScrollView's (NSV) "header view" scroll out of screen percentage listener.
 *
 * <pre>
 * -------------  --- top position, 0
 * |           |
 * |           |  --- start "scroll out" position, its value named mStartScrollOutPosition
 * |           |
 * |   header  |  --- stop "scroll out" position, its value named mStopScrollOutPosition
 * |-----------|  --- bottom position, its value named mHeaderViewHeight
 * |           |
 * |    NSV    |
 * |           |
 * |           |
 * |           |
 * -------------
 * </pre>
 * {@code mStartScrollOutPosition = mHeaderViewHeight * scrollOutStartRatio }
 * when start "scroll out" position scroll over screen's top (move up), we call it starts scroll
 * out;
 *
 * before this position, {@link HeaderScrollOutPercentageListener} will be notified the percent as
 * 0;
 *
 * start from this position, notified percentage is (as float):
 * {@code percentage = (mLocalRect.top - mStartScrollOutPosition) / (mStopScrollOutPosition -
 * mStartScrollOutPosition) }
 *
 * when the header view's bottom edge scroll over screen's top, notified percentage is 1
 */
public class NSVHeaderScrollOutPercentListener implements ViewTreeObserver.OnScrollChangedListener {

    private final HeaderScrollOutPercentageListener mHeaderScrollOutPercentageListener;

    private final View mHeaderView;

    private final float mScrollOutStartRatio;
    private final float mScrollOutStopRatio;

    private int mHeaderViewHeight = -1;
    private int mStartScrollOutPosition;
    private int mStopScrollOutPosition;

    private final Rect mLocalRect = new Rect();

    /**
     * @param scrollOutStartRatio should in range (0, 1)
     * @param scrollOutStopRatio should in range (scrollOutStartRatio, 1]
     */
    public NSVHeaderScrollOutPercentListener(
            HeaderScrollOutPercentageListener headerScrollOutPercentageListener, View headerView,
            float scrollOutStartRatio, float scrollOutStopRatio) {
        if (scrollOutStartRatio <= 0 || 1 <= scrollOutStartRatio) {
            throw new IllegalArgumentException("scrollOutStartRatio should in range (0, 1)");
        }
        if (scrollOutStopRatio <= 0 || 1 < scrollOutStopRatio ||
                scrollOutStopRatio <= scrollOutStartRatio) {
            throw new IllegalArgumentException(
                    "scrollOutStopRatio should in range (scrollOutStartRatio, 1]");
        }
        mHeaderScrollOutPercentageListener = headerScrollOutPercentageListener;
        mHeaderView = headerView;
        mScrollOutStartRatio = scrollOutStartRatio;
        mScrollOutStopRatio = scrollOutStopRatio;
    }

    public interface HeaderScrollOutPercentageListener {

        void onHeaderScrollOffPercent(float percentage);
    }

    @Override
    public void onScrollChanged() {
        if (mHeaderViewHeight == -1) {
            mHeaderViewHeight = mHeaderView.getHeight();
            mStartScrollOutPosition = (int) (mHeaderViewHeight * mScrollOutStartRatio);
            mStopScrollOutPosition = (int) (mHeaderViewHeight * mScrollOutStopRatio);
        }

        mHeaderView.getLocalVisibleRect(mLocalRect);

        // at 5.1.1, mLocalRect.top will be minus when scroll over screen
        if (0 <= mLocalRect.top && mLocalRect.top < mStartScrollOutPosition) {
            mHeaderScrollOutPercentageListener.onHeaderScrollOffPercent(0);
        } else if (mStartScrollOutPosition <= mLocalRect.top &&
                mLocalRect.top <= mStopScrollOutPosition) {
            mHeaderScrollOutPercentageListener.onHeaderScrollOffPercent(
                    ((float) mLocalRect.top - mStartScrollOutPosition) /
                            (mStopScrollOutPosition - mStartScrollOutPosition));
        } else {
            mHeaderScrollOutPercentageListener.onHeaderScrollOffPercent(1);
        }
    }
}

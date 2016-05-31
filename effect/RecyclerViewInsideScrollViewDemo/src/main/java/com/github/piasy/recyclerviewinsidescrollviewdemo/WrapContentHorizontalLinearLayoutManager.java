package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Piasy{github.com/Piasy} on 15/6/26.
 */
public class WrapContentHorizontalLinearLayoutManager extends LinearLayoutManager {

    private final Context mContext;

    private final int mItemTotalWidthDp;
    private final int mItemTotalHeightPx;
    private final int mMarginLeftPx;
    private final int mMarginRightPx;
    private final int mMarginTopPx;
    private final int mMarginBottomPx;

    public WrapContentHorizontalLinearLayoutManager(Context context, boolean reverseLayout,
            int itemTotalWidthDp, int itemTotalHeightDp, int marginLeftDp, int marginRightDp, int
            marginTopDp,
            int marginBottomDp) {
        super(context, HORIZONTAL, reverseLayout);
        mContext = context;
        mItemTotalWidthDp = itemTotalWidthDp;
        mItemTotalHeightPx = dp2px(itemTotalHeightDp);
        mMarginLeftPx = dp2px(marginLeftDp);
        mMarginRightPx = dp2px(marginRightDp);
        mMarginTopPx = dp2px(marginTopDp);
        mMarginBottomPx = dp2px(marginBottomDp);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec,
            int heightSpec) {
        int width = dp2px(state.getItemCount() * mItemTotalWidthDp);
        int maxWidth = View.MeasureSpec.getSize(widthSpec) - mMarginLeftPx - mMarginRightPx;
        int maxHeight = View.MeasureSpec.getSize(heightSpec) - mMarginTopPx - mMarginBottomPx;
        setMeasuredDimension(Math.min(width, maxWidth), Math.min(mItemTotalHeightPx, maxHeight));
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }
}

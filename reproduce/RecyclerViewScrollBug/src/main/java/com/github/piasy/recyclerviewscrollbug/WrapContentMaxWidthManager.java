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

package com.github.piasy.recyclerviewscrollbug;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Piasy{github.com/Piasy} on 15/6/26.
 */
public class WrapContentMaxWidthManager extends LinearLayoutManager {

    private final Context mContext;

    private final int mItemWidthPx;

    private final int mItemTotalHeightPx;

    private final int mItemMarginRightPx;

    private final int mMarginRightPx;

    public WrapContentMaxWidthManager(Context context, boolean reverseLayout, int itemWidthDp,
            int itemMarginRightDp, int itemTotalHeightDp, int marginRightDp) {
        super(context, LinearLayoutManager.HORIZONTAL, reverseLayout);
        mContext = context;
        mItemWidthPx = dp2px(itemWidthDp);
        mItemTotalHeightPx = dp2px(itemTotalHeightDp);
        mItemMarginRightPx = dp2px(itemMarginRightDp);
        mMarginRightPx = dp2px(marginRightDp);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec,
            int heightSpec) {
        int width = state.getItemCount() * mItemWidthPx;
        if (state.getItemCount() > 0) {
            width += (state.getItemCount() - 1) * mItemMarginRightPx;
        }
        int maxWidth = View.MeasureSpec.getSize(widthSpec) - mMarginRightPx;
        setMeasuredDimension(Math.min(width, maxWidth), mItemTotalHeightPx);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }
}

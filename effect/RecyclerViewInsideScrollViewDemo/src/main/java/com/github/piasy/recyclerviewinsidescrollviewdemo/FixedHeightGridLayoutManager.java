package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Piasy{github.com/Piasy} on 15/10/22.
 */
public class FixedHeightGridLayoutManager extends GridLayoutManager {

    public FixedHeightGridLayoutManager(Context context, int spanCount, int orientation,
            boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public FixedHeightGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    private int[] mMeasuredDimension = new int[2];

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
            int widthSpec, int heightSpec) {
        final int heightMode = View.MeasureSpec.getMode(heightSpec);

        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        int height = 0;
        for (int i = 0; i < getItemCount(); i += getSpanCount()) {
            int maxHeight = -1;
            for (int j = 0; j < getSpanCount() && i + j < getItemCount(); j++) {
                measureScrapChild(recycler, i + j,
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);
                if (maxHeight < mMeasuredDimension[1]) {
                    maxHeight = mMeasuredDimension[1];
                }
            }
            height += maxHeight;
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        }

        setMeasuredDimension(widthSize, height);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
            int heightSpec, int[] measuredDimension) {

        if (getChildCount() == 0) {
            return;
        }
        View view = recycler.getViewForPosition(position);
        if (view.getVisibility() == View.GONE) {
            measuredDimension[0] = 0;
            measuredDimension[1] = 0;
            return;
        }
        // For adding Item Decor Insets to view
        super.measureChildWithMargins(view, 0, 0);
        RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
        int childWidthSpec = ViewGroup.getChildMeasureSpec(
                widthSpec,
                getPaddingLeft() + getPaddingRight() + getDecoratedLeft(view) + getDecoratedRight(
                        view),
                p.width);
        int childHeightSpec = ViewGroup.getChildMeasureSpec(
                heightSpec,
                getPaddingTop() + getPaddingBottom() + getDecoratedTop(view) + getDecoratedBottom(view),
                p.height);
        view.measure(childWidthSpec, childHeightSpec);

        // Get decorated measurements
        measuredDimension[0] = getDecoratedMeasuredWidth(view) + p.leftMargin + p.rightMargin;
        measuredDimension[1] = getDecoratedMeasuredHeight(view) + p.bottomMargin + p.topMargin;
        recycler.recycleView(view);
    }
}

package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/28.
 */
public class RecyclerViewInsideScrollViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_inside_scroll_view);

        LinearLayout header = (LinearLayout) findViewById(R.id.mLlHeader);
        final TextView title = (TextView) findViewById(R.id.mTitle);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        final Adapter adapter = new Adapter(this);
        recyclerView.setNestedScrollingEnabled(false);

        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.mNestedScrollView);
        nestedScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new NestedScrollViewScrollChangedListener(
                        new NestedScrollViewScrollChangedListener.HeaderScrollOffPercentListener() {
                            @Override
                            public void onHeaderScrollOffPercent(float percent) {
                                int alpha = (int) (percent * 255);
                                title.setTextColor(alpha << 24 | 0xFFFFFF);
                            }
                        }, header, 0.618F));

        ImageButton imageButton = (ImageButton) findViewById(R.id.mImageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setLayoutManager(
                        new GridLayoutManager(RecyclerViewInsideScrollViewActivity.this, 3));
                recyclerView.setAdapter(adapter);
                adapter.setContentCount(3);
                adapter.notifyDataSetChanged();
                recyclerView.getLayoutParams().height =
                        (int) (getResources().getDisplayMetrics().density * 140 * 1);
            }
        });
    }

    public static class NestedScrollViewScrollChangedListener
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
            if (mLocalRect.top > mStartScrollOffBottom &&
                    mHeaderViewBottom != mStartScrollOffBottom) {
                mHeaderScrollOffPercentListener.onHeaderScrollOffPercent(
                        ((float) mLocalRect.top - mStartScrollOffBottom) /
                                (mHeaderViewBottom - mStartScrollOffBottom));
            } else if ((mLocalRect.top <= 0 && mGlobalRect.top <= 0) ||
                    mHeaderViewBottom == mStartScrollOffBottom) {
                mHeaderScrollOffPercentListener.onHeaderScrollOffPercent(1);
            } else {
                mHeaderScrollOffPercentListener.onHeaderScrollOffPercent(0);
            }
        }
    }

    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private final Context mContext;

        public Adapter(Context context) {
            mContext = context;
        }

        private int mContentCount = 0;

        public void setContentCount(int contentCount) {
            mContentCount = contentCount;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ui_recycler_view_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(String.valueOf(position));
            holder.mLlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Click: " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mContentCount;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        LinearLayout mLlContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.mNumber);
            mLlContainer = (LinearLayout) itemView.findViewById(R.id.mLlContainer);
        }
    }
}

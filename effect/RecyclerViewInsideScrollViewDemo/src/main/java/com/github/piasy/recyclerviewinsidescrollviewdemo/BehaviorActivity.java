package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static butterknife.ButterKnife.findById;

public class BehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavoir);

        final TextView title = findById(this, R.id.mTvTitle);
        LinearLayout lLStatistics = findById(this, R.id.mLlStatistics);

        NestedScrollView nestedScrollView = findById(this, R.id.mNestedScrollView);
        nestedScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new NSVHeaderScrollOutPercentListener(
                        new NSVHeaderScrollOutPercentListener.HeaderScrollOutPercentageListener() {
                            @Override
                            public void onHeaderScrollOffPercent(float percent) {
                                int alpha = (int) (percent * 191);
                                title.setTextColor(alpha << 24 | 0xFFFFFF);
                            }
                        }, lLStatistics, 0.47F, 0.64F));

        final TextView tvName = findById(this, R.id.mTvPublisherName);

        RecyclerView detailRV = findById(this, R.id.mRvDetails);
        final DetailAdapter detailAdapter = new DetailAdapter(this);
        detailRV.setNestedScrollingEnabled(false);
        detailRV.setLayoutManager(new FixedHeightGridLayoutManager(this, 4));
        detailRV.setAdapter(detailAdapter);

        RecyclerView publishersRV = findById(this, R.id.mRvPublishers);
        final PublisherAdapter publisherAdapter =
                new PublisherAdapter(new PublisherAdapter.OnClickListener() {

                    @Override
                    public void onClicked(String name) {
                        tvName.setText(name + " 的打赏详情");
                        detailAdapter.setName(name);
                    }
                });
        publishersRV.setLayoutManager(
                new WrapContentHorizontalLinearLayoutManager(this, false, 59, 53, 5, 5, 0, 0));
        publishersRV.setAdapter(publisherAdapter);

        detailRV.postDelayed(new Runnable() {
            @Override
            public void run() {
                detailAdapter.setName("A");
                detailAdapter.setContentCount(100);
                publisherAdapter.setContentCount(10);
                tvName.setText("A 的打赏详情");
            }
        }, 200);

        findById(this, R.id.mIbClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publisherAdapter.setContentCount(3);
            }
        });
    }

    public static class PublisherAdapter extends RecyclerView.Adapter<PublisherViewHolder> {

        private final OnClickListener mOnClickListener;

        public PublisherAdapter(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        interface OnClickListener {
            void onClicked(String name);
        }

        private int mContentCount = 0;

        public void setContentCount(int contentCount) {
            mContentCount = contentCount;
            notifyDataSetChanged();
        }

        @Override
        public PublisherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PublisherViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ui_recycler_view_publisher_item, parent, false));
        }

        private int mActivatedPosition = 0;

        @Override
        public void onBindViewHolder(final PublisherViewHolder holder, final int position) {
            final String name = String.valueOf((char) ('A' + (position % 26)));
            holder.mTvName.setText(name);
            if (mActivatedPosition != position) {
                holder.mIndicator.setVisibility(View.INVISIBLE);
                holder.mLlContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int oldPosition = mActivatedPosition;
                        mActivatedPosition = position;
                        notifyItemChanged(oldPosition);
                        notifyItemChanged(mActivatedPosition);

                        mOnClickListener.onClicked(name);
                    }
                });
            } else {
                holder.mIndicator.setVisibility(View.VISIBLE);
                holder.mLlContainer.setOnClickListener(null);
            }
        }

        @Override
        public int getItemCount() {
            return mContentCount;
        }
    }

    public static class PublisherViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvName;
        private final View mIndicator;
        private final LinearLayout mLlContainer;

        public PublisherViewHolder(View itemView) {
            super(itemView);
            mTvName = findById(itemView, R.id.mTvName);
            mIndicator = findById(itemView, R.id.mIndicator);
            mLlContainer = findById(itemView, R.id.mLlContainer);
        }
    }

    public class DetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {

        private final Context mContext;

        public DetailAdapter(Context context) {
            mContext = context;
        }

        private int mContentCount = 0;

        public void setContentCount(int contentCount) {
            mContentCount = contentCount;
            notifyDataSetChanged();
        }

        private String mName;

        public void setName(String name) {
            mName = name;
            notifyDataSetChanged();
        }

        @Override
        public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DetailViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ui_recycler_view_detail_item, parent, false));
        }

        @Override
        public void onBindViewHolder(DetailViewHolder holder, final int position) {
            final String text = mName + " @ " + String.valueOf(position);
            holder.mTextView.setText(text);
            holder.mLlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Click: " + text, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BehaviorActivity.this,
                            RecyclerViewInsideScrollViewActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mContentCount;
        }
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private final LinearLayout mLlContainer;

        public DetailViewHolder(View itemView) {
            super(itemView);
            mTextView = findById(itemView, R.id.mNumber);
            mLlContainer = findById(itemView, R.id.mLlContainer);
        }
    }
}

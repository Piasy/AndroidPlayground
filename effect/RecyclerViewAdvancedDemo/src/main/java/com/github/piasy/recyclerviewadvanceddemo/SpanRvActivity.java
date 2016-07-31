package com.github.piasy.recyclerviewadvanceddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SpanRvActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_rv);

        RecyclerView rv = (RecyclerView) findViewById(R.id.mRv);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        /*layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Log.d("SpanSizeLookup", "getSpanSize: " + position);
                return position % 2 == 0 ? 2 : 1;
            }

            @Override
            public int getSpanIndex(int position, int spanCount) {
                Log.d("SpanSizeLookup", "getSpanIndex: " + position + ", " + spanCount);
                return position % 2 == 0 ? (position / 2 * 3) % spanCount : (position / 2 * 3 + 1) % spanCount;
            }

            @Override
            public int getSpanGroupIndex(int adapterPosition, int spanCount) {
                Log.d("SpanSizeLookup", "getSpanGroupIndex: " + adapterPosition + ", " + spanCount);
                return super.getSpanGroupIndex(adapterPosition, spanCount);
            }
        });*/
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new Adapter());
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.VH> {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ui_grid_item, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.mTv.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 40;
        }

        static class VH extends RecyclerView.ViewHolder {
            final TextView mTv;

            VH(View itemView) {
                super(itemView);
                mTv = (TextView) itemView.findViewById(R.id.mTv);
            }
        }
    }
}

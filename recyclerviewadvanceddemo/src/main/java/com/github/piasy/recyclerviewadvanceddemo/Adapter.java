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

package com.github.piasy.recyclerviewadvanceddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 4/1/16.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.TextVH> {

    public static final Integer CHANGE_ACTION_CLICKED = 1;

    private final List<String> mMessages;

    private final RemoveHandler mRemoveHandler = new RemoveHandler(this);

    private static final int MESSAGE_VISIBLE_DURATION = 4 * 1000;

    public Adapter() {
        mMessages = new ArrayList<>();
    }

    public void addMessage(String text) {
        mMessages.add(0, text);
        notifyItemInserted(0);
        mRemoveHandler.sendEmptyMessageDelayed(0, MESSAGE_VISIBLE_DURATION);
    }

    @Override
    public TextVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextVH(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_item, parent, false),
                this);
    }

    @Override
    public void onBindViewHolder(TextVH holder, int position) {
        holder.bind(mMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public static class TextVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Adapter mAdapter;
        final TextView mTv;
        final View vBgLike;
        final ImageView ivLike;

        public TextVH(View itemView, Adapter adapter) {
            super(itemView);
            mAdapter = adapter;
            mTv = findById(itemView, R.id.mTv);
            vBgLike = findById(itemView, R.id.vBgLike);
            ivLike = findById(itemView, R.id.ivLike);
            mTv.setOnClickListener(this);
        }

        void bind(String text) {
            mTv.setText(text);
        }

        @Override
        public void onClick(View v) {
            mAdapter.notifyItemChanged(getAdapterPosition(), Adapter.CHANGE_ACTION_CLICKED);
        }
    }

    static class RemoveHandler extends Handler {

        WeakReference<Adapter> mAdapter;

        public RemoveHandler(Adapter adapter) {
            mAdapter = new WeakReference<>(adapter);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mAdapter.get() != null && mAdapter.get().mMessages != null) {
                int position = mAdapter.get().mMessages.size() - 1;
                mAdapter.get().mMessages.remove(position);
                mAdapter.get().notifyItemRemoved(position);
            }
        }
    }
}

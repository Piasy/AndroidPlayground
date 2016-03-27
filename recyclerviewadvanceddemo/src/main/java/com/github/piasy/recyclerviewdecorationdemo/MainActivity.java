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

package com.github.piasy.recyclerviewdecorationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int OUT_SET_LEFT = 0;
    public static final int OUT_SET_TOP = 0;
    public static final int OUT_SET_RIGHT = 0;
    public static final int OUT_SET_BOTTOM = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView) findViewById(R.id.mRv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerView.Adapter<VH>() {
            @Override
            public VH onCreateViewHolder(ViewGroup parent, int viewType) {
                return new VH(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ui_item, parent, false));
            }

            @Override
            public void onBindViewHolder(VH holder, int position) {
                holder.mTv.setText(String.valueOf(position));
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
        rv.addItemDecoration(new ListDividerDecoration(this, ListDividerDecoration.VERTICAL_LIST));

        ((TextView) findViewById(R.id.mTv)).setText(
                String.format("outRect.set(%d, %d, %d, %d)", OUT_SET_LEFT, OUT_SET_TOP,
                        OUT_SET_RIGHT, OUT_SET_BOTTOM));
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView mTv;

        public VH(View itemView) {
            super(itemView);
            mTv = (TextView) itemView;
        }
    }
}

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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static butterknife.ButterKnife.findById;

public class MainActivity extends AppCompatActivity {

    static final int OUT_SET_LEFT = 0;
    static final int OUT_SET_TOP = 0;
    static final int OUT_SET_RIGHT = 0;
    static final int OUT_SET_BOTTOM = 25;

    private static final String[] MSGS =
            new String[] { "One", "Two", "Three", "Four", "Five", "Six", "Seven" };
    private static final Random RANDOM = new Random();
    private RecyclerView mRv;
    private Adapter mAdapter;

    private static String randomText() {
        StringBuilder builder = new StringBuilder();
        int words = RANDOM.nextInt(15) + 10;
        for (int i = 0; i < words; i++) {
            builder.append(MSGS[RANDOM.nextInt(7)]).append(" ");
        }
        return builder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SpanRvActivity.class));
            }
        });

        mRv = findById(this, R.id.mRv);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        mAdapter = new Adapter();
        mRv.setAdapter(mAdapter);
        mRv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRv.setItemAnimator(new MessageAnimator());

        Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long times) {
                        mAdapter.addMessage("" + times + ": " + randomText());
                        mRv.smoothScrollToPosition(0);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}

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

package com.github.piasy.retrolambdachaosrepro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.trello.navi.Event;
import com.trello.navi.Listener;
import com.trello.navi.NaviComponent;
import com.trello.navi.internal.NaviEmitter;

public class MainActivity extends AppCompatActivity implements NaviComponent {

    private final NaviEmitter mNaviEmitter = NaviEmitter.createActivityEmitter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNaviEmitter.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YLQrCodeDecoder decoder = new YLQrCodeDecoder(this, false);

        startActivity(new Intent(this, SecondActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNaviEmitter.onStart();
    }

    @Override
    protected void onStop() {
        mNaviEmitter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mNaviEmitter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mNaviEmitter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNaviEmitter.onResume();
    }

    @Override
    public final boolean handlesEvents(Event... events) {
        return mNaviEmitter.handlesEvents(events);
    }

    @Override
    public final <T> void addListener(Event<T> event, Listener<T> listener) {
        mNaviEmitter.addListener(event, listener);
    }

    @Override
    public final <T> void removeListener(Listener<T> listener) {
        mNaviEmitter.removeListener(listener);
    }
}

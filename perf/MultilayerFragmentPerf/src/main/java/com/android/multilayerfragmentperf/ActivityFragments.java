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

package com.android.multilayerfragmentperf;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.android.multilayerfragmentperf.fragments.FragmentLayer1;
import com.android.multilayerfragmentperf.fragments.FragmentLayer2;
import com.android.multilayerfragmentperf.fragments.FragmentLayer3;
import com.android.multilayerfragmentperf.fragments.FragmentLayer4;
import com.android.multilayerfragmentperf.fragments.FragmentLayer5;

public class ActivityFragments extends AppCompatActivity
        implements FragmentLayer1.OnFragmentInteractionListener,
        FragmentLayer2.OnFragmentInteractionListener, FragmentLayer3.OnFragmentInteractionListener,
        FragmentLayer4.OnFragmentInteractionListener, FragmentLayer5.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mLayer1, FragmentLayer1.newInstance("p1", "p2"), "FragmentLayer1")
                .add(R.id.mLayer2, FragmentLayer2.newInstance("p1", "p2"), "FragmentLayer2")
                .add(R.id.mLayer3, FragmentLayer3.newInstance("p1", "p2"), "FragmentLayer3")
                .add(R.id.mLayer4, FragmentLayer4.newInstance("p1", "p2"), "FragmentLayer4")
                .add(R.id.mLayer5, FragmentLayer5.newInstance("p1", "p2"), "FragmentLayer5")
                .commit();

        Handler handler = new Handler(Looper.getMainLooper());
        final long start = System.nanoTime();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("FragmentAddPerf", "fragments time elapse: " + (System.nanoTime() - start));
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

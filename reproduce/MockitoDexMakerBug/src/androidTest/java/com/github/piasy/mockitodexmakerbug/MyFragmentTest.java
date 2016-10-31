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

package com.github.piasy.mockitodexmakerbug;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(AndroidJUnit4.class)
public class MyFragmentTest {
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Rule
    public ActivityTestRule<MyActivity> mActivityTestRule =
            new ActivityTestRule<>(MyActivity.class, true, false);

    @Mock
    private MyPresenter mTestPresenter;

    private MyFragment mTestFragment;

    @Before
    public void setUp() throws Exception {
        MyActivity activity = mActivityTestRule.launchActivity(new Intent());
        activity.startTestFragment(mTestPresenter);
        mTestFragment = activity.getTestFragment();
        Thread.sleep(5_000);
    }

    @Test
    public void onDestroy() throws Exception {
        mTestFragment.onStop();
        mTestFragment.onDestroy();
        verify(mTestPresenter, times(1)).attachView(mTestFragment);
        verify(mTestPresenter, times(1)).detachView();
        verify(mTestPresenter, times(1)).onDestroy();
        verifyNoMoreInteractions(mTestPresenter);
    }
}
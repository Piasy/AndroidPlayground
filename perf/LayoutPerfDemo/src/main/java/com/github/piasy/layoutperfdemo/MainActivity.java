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

package com.github.piasy.layoutperfdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.mBtnSimpleOther)
    public void otherLayout() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new SimpleOtherFragment())
                .addToBackStack("SimpleOtherFragment")
                .commit();
    }

    @OnClick(R.id.mBtnSimpleRelative)
    public void relativeLayout() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new SimpleRelativeFragment())
                .addToBackStack("SimpleRelativeFragment")
                .commit();
    }

    @OnClick(R.id.mBtnSimpleFlex)
    public void flexLayout() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new SimpleFlexFragment())
                .addToBackStack("SimpleFlexFragment")
                .commit();
    }

    @OnClick(R.id.mBtnComplexOther)
    public void complexOtherLayout() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new ComplexOtherFragment())
                .addToBackStack("ComplexOtherFragment")
                .commit();
    }

    @OnClick(R.id.mBtnComplexRelative)
    public void complexRelativeLayout() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new ComplexRelativeFragment())
                .addToBackStack("ComplexRelativeFragment")
                .commit();
    }

    @OnClick(R.id.mBtnComplexFlex)
    public void complexFlexLayout() {
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new ComplexFlexFragment())
                .addToBackStack("ComplexFlexFragment")
                .commit();
    }
}

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

package com.github.piasy.dagger2scopeinstancedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    DemoNewDependency mDemoNewDependency;
    @Inject
    DemoInjectDependency mDemoInjectDependency;
    @Inject
    DemoDirectInjectDependency mDemoDirectInjectDependency;

    private DemoComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());

        setContentView(R.layout.activity_main);

        Timber.d("MainActivity before inject, mDemoNewDependency: " + mDemoNewDependency);
        Timber.d("MainActivity before inject, mDemoInjectDependency: " + mDemoInjectDependency);
        Timber.d("MainActivity before inject, mDemoDirectInjectDependency: " + mDemoDirectInjectDependency);

        mComponent = DaggerDemoComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
        Timber.d("MainActivity inject, component: " + mComponent);
        mComponent.inject(this);

        Timber.d("MainActivity after inject, mDemoNewDependency: " + mDemoNewDependency);
        Timber.d("MainActivity after inject, mDemoInjectDependency: " + mDemoInjectDependency);
        Timber.d("MainActivity after inject, mDemoDirectInjectDependency: " + mDemoDirectInjectDependency);

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new BlankFragment())
                .commit();
    }

    public DemoComponent getComponent() {
        return mComponent;
    }
}

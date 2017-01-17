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

package com.github.piasy.taskdemo.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.github.piasy.taskdemo.R;

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d("TaskDemo", "SimpleActivity onCreate");
        
        setContentView(R.layout.activity_simple);

        findViewById(R.id.mButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SimpleActivity.this,
                        SingleInstanceWithDifferentTaskAffinity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TaskDemo", "SimpleActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TaskDemo", "SimpleActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TaskDemo", "SimpleActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("TaskDemo", "SimpleActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TaskDemo", "SimpleActivity onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d("TaskDemo", "SimpleActivity onNewIntent");
    }
}

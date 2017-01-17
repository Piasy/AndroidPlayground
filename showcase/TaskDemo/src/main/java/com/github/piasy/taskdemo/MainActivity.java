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

package com.github.piasy.taskdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.github.piasy.taskdemo.launch_mode.SimpleActivity;
import com.github.piasy.taskdemo.launch_mode.SimpleWithDifferentTaskAffinity;
import com.github.piasy.taskdemo.launch_mode.SingleInstanceWithDifferentTaskAffinity;
import com.github.piasy.taskdemo.launch_mode.SingleInstanceWithSameTaskAffinity;
import com.github.piasy.taskdemo.launch_mode.SingleInstanceWithoutTaskAffinity;
import com.github.piasy.taskdemo.launch_mode.SingleTaskWithDifferentTaskAffinity;
import com.github.piasy.taskdemo.launch_mode.SingleTaskWithDifferentTaskAffinity2;
import com.github.piasy.taskdemo.launch_mode.SingleTaskWithSameTaskAffinity;
import com.github.piasy.taskdemo.launch_mode.SingleTaskWithoutTaskAffinity;
import com.github.piasy.taskdemo.launch_mode.SingleTop;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TaskDemo", "Main onCreate");

        setContentView(R.layout.activity_main);

        findViewById(R.id.mSingleTop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SingleTop.class));
            }
        });

        findViewById(R.id.mSingleTask1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SingleTaskWithoutTaskAffinity.class));
            }
        });

        findViewById(R.id.mSingleTask2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SingleTaskWithSameTaskAffinity.class));
            }
        });

        findViewById(R.id.mSingleTask3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this, SingleTaskWithDifferentTaskAffinity.class));
            }
        });

        findViewById(R.id.mSingleTask4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this, SingleTaskWithDifferentTaskAffinity2.class));
            }
        });

        findViewById(R.id.mSingleInstance1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this, SingleInstanceWithoutTaskAffinity.class));
            }
        });

        findViewById(R.id.mSingleInstance2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this, SingleInstanceWithSameTaskAffinity.class));
            }
        });

        findViewById(R.id.mSingleInstance3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(MainActivity.this,
                                SingleInstanceWithDifferentTaskAffinity.class));
            }
        });

        findViewById(R.id.mFlagNewTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SimpleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.mFlagNewTask2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        SimpleWithDifferentTaskAffinity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        findViewById(R.id.mFlagNewTask3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SimpleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TaskDemo", "Main onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("TaskDemo", "Main onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TaskDemo", "Main onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TaskDemo", "Main onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TaskDemo", "Main onResume");
    }
}

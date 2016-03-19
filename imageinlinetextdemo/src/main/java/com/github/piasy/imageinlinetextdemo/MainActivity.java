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

package com.github.piasy.imageinlinetextdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.mTextView);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("效率挺高图都出好了啊效率挺高图都出好了啊效率挺高图都出好了啊效率挺高图都出好了啊");
        builder.append("   拍照留念   ");
        builder.setSpan(new ImageSpan(this, R.drawable.iv_screenshot), builder.length() - 1,
                builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);

        TextView textView2 = (TextView) findViewById(R.id.mTextView2);
        SpannableStringBuilder builder2 = new SpannableStringBuilder();
        builder2.append("效率挺高图都出好了啊效率挺高图都出好了啊效率挺高图都出好了啊效率挺高图都出好了啊");
        builder2.append("   拍照留念   ");
        builder2.setSpan(new ImageSpan(this, R.drawable.iv_screenshot2), builder.length() - 1,
                builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(builder2);
    }
}

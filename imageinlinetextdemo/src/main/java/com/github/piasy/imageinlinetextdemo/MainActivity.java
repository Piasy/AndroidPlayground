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
import android.text.style.ForegroundColorSpan;
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

        SpannableStringBuilder message = new SpannableStringBuilder();
        StringBuilder namesBuilder = new StringBuilder();
        namesBuilder.append("初涛、胖胖、A-红族-剋; K、Yan.G、红烧、琨君、张瑞圣、优络技术、燕晨、潘涛");
        int missedGroupLiveCount = 28;
        message.append(
                String.format(getString(R.string.chat_too_many_missed_group_live_message_formatter),
                        namesBuilder, missedGroupLiveCount));
        message.setSpan(new ImageSpan(this, R.drawable.iv_missed_group_live), 0, 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int nameStartPos = 6;
        message.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.yolo_yellow)),
                nameStartPos, nameStartPos + namesBuilder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int countStartPos = nameStartPos + namesBuilder.length() + 4;
        message.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.yolo_yellow)),
                countStartPos, countStartPos + String.valueOf(missedGroupLiveCount).length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView2.setText(message);
    }
}

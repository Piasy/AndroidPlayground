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

package piasy.github.com.voiceprocessing;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.piasy.rxandroidaudio.StreamAudioPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    StreamAudioPlayer mStreamAudioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mStreamAudioPlayer = StreamAudioPlayer.getInstance();
        mStreamAudioPlayer.init();
    }

    @OnClick(R.id.mBtnStart)
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory(),
                        "before.pcm");
                byte[] in = new byte[1024 * 2];
                byte[] out = new byte[1024 * 2];
                float[] floatIn = new float[1024];
                float[] floatOut = new float[1024];
                FileInputStream inputStream;
                try {
                    inputStream = new FileInputStream(file);
                    while (inputStream.read(in) > 0) {
                        long start = System.nanoTime();
                        VoiceProcessor.process(in, out, in.length, floatIn, floatOut);
                        System.out.println("" + (System.nanoTime() - start) + " ns");
                        mStreamAudioPlayer.play(out, out.length);
                    }
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

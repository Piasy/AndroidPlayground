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

import android.util.Log;
import com.trello.navi.Event;
import com.trello.navi.NaviComponent;
import com.trello.navi.rx.RxNavi;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Piasy{github.com/Piasy} on 11/11/2016.
 */

public class YLQrCodeDecoder extends BaseNaviPlugin {

    private Subject<Boolean, Boolean> mScanResult = PublishSubject.<Boolean>create().toSerialized();

    public YLQrCodeDecoder(NaviComponent naviComponent, boolean scanFromCamera) {
        super(naviComponent);

        if (scanFromCamera) {
            // 注意！！！ 下面的几个 action 不要改成 lambda 表达式，retrolambda 已经踩到坑了，会乱套
            // 但其他地方呢？细思极恐。。。
            addUtilDestroy(Observable.just(true)
                    .subscribe(mScanResult));
            addUtilDestroy(RxNavi.observe(mNaviComponent, Event.RESUME)
                    .subscribe(aVoid -> Log.d("YLQrCodeDecoder", "RESUME")));
            addUtilDestroy(RxNavi.observe(mNaviComponent, Event.PAUSE)
                    .subscribe(aVoid -> Log.d("YLQrCodeDecoder", "PAUSE")));
        }
    }
}

package com.github.piasy.wechatluckymoneyeffect;

import android.util.Log;
import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 16/1/12.
 */
public class RxUtils {

    public static Action1<Throwable> IgnoreErrorProcessor = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            Log.e("IgnoreErrorProcessor", "Rx onError: ", throwable);
        }
    };
}

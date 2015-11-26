package com.github.piasy.fancytransitiondemo;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class CountdownAnimationTextView extends TextView {

    private int mStartCount;
    private int mStopCount;
    private int mCurrentCount;

    private final CountdownSpringListener mSpringListener = new CountdownSpringListener(this);
    private final Spring mCountdownSpring;
    private Subscription mCountdownSubscription;
    private CountdownCallback mCountdownCallback;

    public CountdownAnimationTextView(Context context) {
        this(context, null);
    }

    public CountdownAnimationTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownAnimationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        BaseSpringSystem springSystem = SpringSystem.create();
        mCountdownSpring = springSystem.createSpring();
        mCountdownSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(600, 9));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mCountdownSubscription != null && !mCountdownSubscription.isUnsubscribed()) {
            mCountdownSubscription.unsubscribe();
        }
        mCountdownSpring.removeAllListeners();
    }

    public void setCountdown(int startCount, int stopCount) {
        mStartCount = startCount;
        mStopCount = stopCount;
    }

    public void startCountdown() {
        mCountdownSpring.addListener(mSpringListener);
        mCurrentCount = mStartCount;
        play(mCurrentCount);
        if (mCountdownCallback != null) {
            mCountdownCallback.onStart();
        }
        mCountdownSubscription = Observable.interval(100, TimeUnit.MILLISECONDS)
                .take((mStartCount - mStopCount + 1) * 10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        countdown();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long count) {
                        if (count > 0 && count % 10 == 0) {
                            countdown();
                        } else if (count % 10 == 8) {
                            hide();
                        }
                    }
                });
    }

    private void countdown() {
        if (mCurrentCount <= mStopCount) {
            mCountdownSpring.removeAllListeners();
            if (mCountdownCallback != null) {
                mCountdownCallback.onComplete();
            }
        } else {
            mCurrentCount--;
            play(mCurrentCount);
            if (mCountdownCallback != null) {
                mCountdownCallback.onCountdown();
            }
        }
    }

    private void play(int count) {
        setText(String.valueOf(count));
        mCountdownSpring.setCurrentValue(0.01);
        mCountdownSpring.setEndValue(1);
        show();
    }

    private void show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            animate().alpha(1).setDuration(100).withLayer().start();
        } else {
            animate().alpha(1).setDuration(100).start();
        }
    }

    private void hide() {
        animate().alpha(0).setDuration(100).start();
    }

    public void setCountdownCallback(CountdownCallback countdownCallback) {
        mCountdownCallback = countdownCallback;
    }

    private static class CountdownSpringListener extends SimpleSpringListener {

        public static final double TO_HIGH = 1.5;

        private final View mView;

        private CountdownSpringListener(View view) {
            mView = view;
        }

        @Override
        public void onSpringUpdate(Spring spring) {
            if (mView != null) {
                float mappedValue =
                        (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1,
                                1, TO_HIGH);
                mView.setScaleX(mappedValue);
                mView.setScaleY(mappedValue);
            }
        }
    }

    public interface CountdownCallback {
        void onStart();

        void onCountdown();

        void onComplete();
    }

    public abstract static class LazyCountdownCallback implements CountdownCallback {

        @Override
        public void onStart() {

        }

        @Override
        public void onCountdown() {

        }

        @Override
        public void onComplete() {

        }
    }
}

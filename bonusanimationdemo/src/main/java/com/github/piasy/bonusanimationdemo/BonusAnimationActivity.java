package com.github.piasy.bonusanimationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import java.lang.ref.WeakReference;

/**
 * Created by Piasy{github.com/Piasy} on 15/10/10.
 */
public class BonusAnimationActivity extends Activity {
    @Bind(R.id.mBonusAnimationView)
    BonusAnimationView mBonusAnimationView;

    private static final int QC_TENSION = 100;

    private static final int QC_FRICTION = 9;

    private final BaseSpringSystem mSpringSystem = SpringSystem.create();
    private final BonusMsgSpringListener mBonusMsgSpringListener = new BonusMsgSpringListener(this);
    private Spring mScaleSpring;

    private static final class BonusMsgSpringListener extends SimpleSpringListener {

        private final WeakReference<BonusAnimationActivity> mReference;

        private BonusMsgSpringListener(BonusAnimationActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void onSpringUpdate(Spring spring) {
            float mappedValue =
                    (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1,
                            1.5);
            if (mReference.get() != null && mReference.get().mBonusAnimationView != null) {
                mReference.get().mBonusAnimationView.setScaleX(mappedValue);
                mReference.get().mBonusAnimationView.setScaleY(mappedValue);
            }
            Log.d("BonusAnimTest", "mappedValue: " + mappedValue);
        }
    }

    private boolean mIsShowing = false;
    private int mShowTimes = 0;

    @OnClick(R.id.mBtnPlay)
    public void onPlay() {
        if (mIsShowing) {
            playBonusMsgEndAnimation();
            mIsShowing = false;
        } else {
            playBonusMsgAnimation(mShowTimes % 2 == 0);
            mShowTimes++;
            mIsShowing = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_animation);
        ButterKnife.bind(this);
        mScaleSpring = mSpringSystem.createSpring();
        mScaleSpring.addListener(mBonusMsgSpringListener);
        mScaleSpring.setCurrentValue(0.07);
        mScaleSpring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(QC_TENSION,
                QC_FRICTION));

        new Thread(new Runnable() {
            @Override
            public void run() {
                mBonusAnimationView.initResources(BonusAnimationActivity.this);
            }
        }).start();
    }

    private void playBonusMsgAnimation(boolean self) {
        String content = "优 张瑞圣\uD83D\uDE04";
        String bonusMessage = "我在吃吃吃吃\uD83D\uDE04";
        String money = "￥1.00";
        mBonusAnimationView.setContent(content, money, bonusMessage, self);
        mBonusAnimationView.setVisibility(View.VISIBLE);

        mScaleSpring.setEndValue(1);
    }

    private void playBonusMsgEndAnimation() {
        mScaleSpring.setCurrentValue(0.07);

        mBonusAnimationView.setVisibility(View.GONE);
    }
}

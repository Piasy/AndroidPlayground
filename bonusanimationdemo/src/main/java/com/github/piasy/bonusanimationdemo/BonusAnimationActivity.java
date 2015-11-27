package com.github.piasy.bonusanimationdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import java.lang.ref.WeakReference;

/**
 * Created by Piasy{github.com/Piasy} on 15/10/10.
 */
public class BonusAnimationActivity extends Activity {
    @Bind(R.id.mLlBonusDetailContainer)
    View mLlBonusDetailContainer;

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
            if (mReference.get() != null && mReference.get().mLlBonusDetailContainer != null) {
                mReference.get().mLlBonusDetailContainer.setScaleX(mappedValue);
                mReference.get().mLlBonusDetailContainer.setScaleY(mappedValue);
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {
            super.onSpringAtRest(spring);
            /*if (mReference.get() != null) {
                mReference.get().playBonusMsgEndAnimation();
            }*/
        }
    }

    private boolean mIsShowing = false;
    private int mShowTimes = 0;

    @OnClick(R.id.mBtnPlay)
    public void onPlay() {
        if (mIsShowing) {
            playBonusMsgEndAnimationHidden();
            mIsShowing = false;
        } else {
            playBonusMsgAnimationHidden(mShowTimes % 2 == 0);
            mShowTimes++;
            mIsShowing = true;
        }
    }

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_animation);
        ButterKnife.bind(this);
        mLlBonusDetailContainer.setVisibility(View.GONE);
        mScaleSpring = mSpringSystem.createSpring();
        mScaleSpring.addListener(mBonusMsgSpringListener);

        mBonusDetailBgWidth = (int) (170 * getResources().getDisplayMetrics().density);
        mBonusDetailBgHeight = (int) (151 * getResources().getDisplayMetrics().density);
        mMarginBig = (int) (114 * getResources().getDisplayMetrics().density);
        mMarginSmall = (int) (100 * getResources().getDisplayMetrics().density);
        mBitmap = Bitmap.createBitmap(mBonusDetailBgWidth, mBonusDetailBgHeight,
                Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    private int mBonusDetailBgWidth;
    private int mBonusDetailBgHeight;
    private int mMarginSmall;
    private int mMarginBig;

    private Bitmap createBonusDetailBitmap(boolean self) {
        if (self) {
            String content = "YOLO的打赏" + mShowTimes;
            mCanvas.drawBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.iv_bonus_detail_bg), 0,
                    0, mPaint);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14,
                    getResources().getDisplayMetrics()));
            int width = (int) mPaint.measureText(content);
            mCanvas.drawText(content, (mBonusDetailBgWidth - width) / 2, mMarginSmall, mPaint);
            String money = "￥1234.5" + mShowTimes;
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24,
                    getResources().getDisplayMetrics()));
            width = (int) mPaint.measureText(money);
            mCanvas.drawText(money, (mBonusDetailBgWidth - width) / 2, mMarginSmall +
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14,
                            getResources().getDisplayMetrics()) * 2, mPaint);
        } else {
            String content = "YOLO的打赏" + mShowTimes;
            mCanvas.drawBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.iv_bonus_detail_bg), 0,
                    0, mPaint);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                    getResources().getDisplayMetrics()));
            int width = (int) mPaint.measureText(content);
            mCanvas.drawText(content, (mBonusDetailBgWidth - width) / 2, mMarginBig, mPaint);
        }
        return mBitmap;
    }

    private void playBonusMsgAnimationHidden(boolean self) {
        Bitmap bitmap = createBonusDetailBitmap(self);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
        mLlBonusDetailContainer.setBackgroundDrawable(drawable);
        mLlBonusDetailContainer.setVisibility(View.VISIBLE);

        mScaleSpring.setEndValue(26);
    }

    private void playBonusMsgEndAnimationHidden() {
        mScaleSpring.setCurrentValue(1);
        mLlBonusDetailContainer.setVisibility(View.GONE);
        mLlBonusDetailContainer.setScaleX(1.5F);
        mLlBonusDetailContainer.setScaleY(1.5F);
    }
}

package com.github.piasy.bonusanimationdemo;

/**
 * Created by Piasy{github.com/Piasy} on 16/1/5.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.squareup.picasso.Picasso;
import java.io.IOException;

public class BonusAnimationView extends View {

    public BonusAnimationView(Context context) {
        this(context, null);
    }

    public BonusAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Paint mPaint;
    private Bitmap mBgBitmapSelf;
    private Bitmap mBgBitmapOther;

    private int mBonusDetailBgWidth;

    private int mMargin;
    private Rect mRectSelf;
    private Rect mRectOther;

    private boolean mIsInitiated = false;

    public BonusAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @WorkerThread
    public boolean initResources(Context context) {
        try {
            mBgBitmapOther = Picasso.with(context).load(R.drawable.iv_bonus_detail_bg_other).get();
            mBgBitmapSelf = Picasso.with(context).load(R.drawable.iv_bonus_detail_bg).get();
            mRectOther = new Rect(0, 0, mBgBitmapOther.getWidth(), mBgBitmapOther.getHeight());
            mRectSelf = new Rect(0, 0, mBgBitmapSelf.getWidth(), mBgBitmapSelf.getHeight());
            mBonusDetailBgWidth = mRectSelf.width();
            mMargin = (int) (68 * context.getResources().getDisplayMetrics().density);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mIsInitiated = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String mTitle;
    private String mMoney;
    private String mContent;
    private boolean mIsSelf;

    public void setContent(String title, String money, String content, boolean isSelf) {
        mTitle = title;
        mMoney = money;
        mContent = content;
        mIsSelf = isSelf;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsInitiated) {
            return;
        }

        if (mIsSelf) {
            mPaint.setColor(Color.parseColor("#FFFFFFFF"));
            canvas.drawBitmap(mBgBitmapSelf, null, mRectSelf, mPaint);

            String content = mTitle;
            if (content.length() > 7) {
                content = content.substring(0, 6) + "...";
            }
            String bonusMessage = mContent;
            String money = mMoney;

            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.7F,
                    getResources().getDisplayMetrics()));
            mPaint.setColor(Color.parseColor("#F0FFFFFF"));
            int width = (int) mPaint.measureText(content);
            canvas.drawText(content, (mBonusDetailBgWidth - width) / 2, mMargin, mPaint);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                    getResources().getDisplayMetrics()));
            mPaint.setColor(Color.parseColor("#FFFFF3A3"));
            width = (int) mPaint.measureText(money);
            canvas.drawText(money, (mBonusDetailBgWidth - width) / 2, mMargin +
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.7F,
                            getResources().getDisplayMetrics()) * 1.5F, mPaint);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8.7F,
                    getResources().getDisplayMetrics()));
            mPaint.setColor(Color.parseColor("#D6FFFFFF"));
            width = (int) mPaint.measureText(bonusMessage);
            canvas.drawText(bonusMessage, (mBonusDetailBgWidth - width) / 2, mMargin +
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.7F,
                            getResources().getDisplayMetrics()) * 1.5F +
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                            getResources().getDisplayMetrics()), mPaint);
        } else {
            mPaint.setColor(Color.parseColor("#FFFFFFFF"));
            canvas.drawBitmap(mBgBitmapOther, null, mRectOther, mPaint);

            String content = mTitle;
            if (content.length() > 7) {
                content = content.substring(0, 6) + "...";
            }
            String bonusMessage = mContent;

            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.7F,
                    getResources().getDisplayMetrics()));
            mPaint.setColor(Color.parseColor("#F0FFFFFF"));
            int width = (int) mPaint.measureText(content);
            canvas.drawText(content, (mBonusDetailBgWidth - width) / 2, mMargin, mPaint);
            mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8.7F,
                    getResources().getDisplayMetrics()));
            mPaint.setColor(Color.parseColor("#D6FFFFFF"));
            width = (int) mPaint.measureText(bonusMessage);
            canvas.drawText(bonusMessage, (mBonusDetailBgWidth - width) / 2, mMargin +
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.7F,
                            getResources().getDisplayMetrics()) * 1.5F, mPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        safelyRecycleBitmap(mBgBitmapSelf);
        safelyRecycleBitmap(mBgBitmapOther);
    }

    private void safelyRecycleBitmap(@Nullable Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}

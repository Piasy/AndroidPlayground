package com.github.piasy.ViewCompatAnimate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.piasy.playground.ViewCompatAnimate.R;

/**
 * Created by Piasy{github.com/Piasy} on 2020/8/6.
 */
public class HoleMaskView extends View {
    private static final int TYPE_ROUND_CORNER = 1;

    private Paint mHolePaint;
    private Path mHolePath;

    private int mType;
    private int mForegroundColor;
    private int mRoundCornerRadius;

    public HoleMaskView(final Context context) {
        this(context, null);
    }

    public HoleMaskView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoleMaskView(final Context context, @Nullable final AttributeSet attrs,
            final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = getContext().getTheme()
                    .obtainStyledAttributes(attrs, R.styleable.HoleMaskView, defStyleAttr, 0);

            try {
                mType = a.getInteger(R.styleable.HoleMaskView_holeType, TYPE_ROUND_CORNER);
                mForegroundColor = a.getColor(R.styleable.HoleMaskView_foregroundColor,
                        Color.WHITE);

                switch (mType) {
                    case TYPE_ROUND_CORNER:
                        mRoundCornerRadius = a.getDimensionPixelSize(
                                R.styleable.HoleMaskView_roundCornerRadius, 5);
                        break;
                    default:
                        break;
                }
            } finally {
                a.recycle();
            }
        }

        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);

        mHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(mForegroundColor);

        if (mHolePath == null) {
            mHolePath = new Path();
            switch (mType) {
                case TYPE_ROUND_CORNER:
                    mHolePath.addRoundRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()),
                            mRoundCornerRadius, mRoundCornerRadius, Path.Direction.CW);
                    break;
                default:
                    break;
            }
        }
        canvas.drawPath(mHolePath, mHolePaint);
    }
}

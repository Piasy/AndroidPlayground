package com.github.piasy.playground.ylposter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Piasy{github.com/Piasy} on 29/11/2016.
 */

abstract class YLPoster extends FrameLayout {

    protected final int mDesiredWidth;
    protected final int mDesiredHeight;

    private final Handler mMainThreadHandler;
    private final Runnable mAdjustSize = new Runnable() {
        @Override
        public void run() {
            adjustSize();
            setVisibility(VISIBLE);
        }
    };

    public YLPoster(@NonNull Context context) {
        this(context, null);
    }

    public YLPoster(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YLPoster(@NonNull Context context,
            @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Resources resources = getResources();
        mDesiredWidth = resources.getDimensionPixelSize(R.dimen.poster_width);
        mDesiredHeight = resources.getDimensionPixelSize(R.dimen.poster_height);

        mMainThreadHandler = new Handler(Looper.getMainLooper());
        mMainThreadHandler.postDelayed(mAdjustSize, 50);
        setVisibility(INVISIBLE);
    }

    @CallSuper
    protected float adjustSize() {
        int width = getWidth();
        int height = getHeight();
        if (width <= 0 || height <= 0) {
            mMainThreadHandler.postDelayed(mAdjustSize, 50);
            return -1;
        }
        float ratioW = (float) width / mDesiredWidth;
        float ratioH = (float) height / mDesiredHeight;
        float ratio = (float) mDesiredWidth / mDesiredHeight;

        float scale;
        ViewGroup.LayoutParams params = getLayoutParams();
        if (ratioW > ratioH) {
            params.width = (int) (height * ratio);
            params.height = height;
            scale = (float) height / mDesiredHeight;
        } else {
            params.width = width;
            params.height = (int) (width / ratio);
            scale = (float) width / mDesiredWidth;
        }
        setLayoutParams(params);

        return scale;
    }
}

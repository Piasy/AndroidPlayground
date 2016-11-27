package com.github.piasy.playground.ylposter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Piasy{github.com/Piasy} on 27/11/2016.
 */

public class YLPosterPreviewPager extends ViewPager {
    public YLPosterPreviewPager(Context context) {
        this(context, null);
    }

    public YLPosterPreviewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                adjustSize();
            }
        }, 50);
        setPageTransformer(false, new PosterTransformer());
    }

    private void adjustSize() {
        int width = getWidth();
        int height = getHeight();
        int desiredWidth = getResources().getDimensionPixelSize(R.dimen.poster_width);
        int desiredHeight = getResources().getDimensionPixelSize(R.dimen.poster_height);
        int properWidth = (int) ((float) desiredWidth / desiredHeight * height);
        int padding = (width - properWidth) / 2;
        setPadding(padding, 0, padding, 0);
        setClipToPadding(false);
        setPageTransformer(false, new PosterTransformer());
    }
}

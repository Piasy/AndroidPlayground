package com.github.piasy.playground.ylposter;

import android.support.v4.view.ViewPager;
import android.view.View;

public class PosterTransformer implements ViewPager.PageTransformer {

    private ViewPager mViewPager;

    public void transformPage(View view, float position) {
        if (mViewPager == null) {
            mViewPager = (ViewPager) view.getParent();
        }

        int leftInScreen = view.getLeft() - mViewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - mViewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.85F / mViewPager.getMeasuredWidth();
        float alpha = 1 - Math.abs(offsetRate);
        if (alpha > 0) {
            view.setAlpha(alpha);
        }
    }
}

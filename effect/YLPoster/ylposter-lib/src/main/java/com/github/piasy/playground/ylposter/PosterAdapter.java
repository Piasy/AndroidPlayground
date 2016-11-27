package com.github.piasy.playground.ylposter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 27/11/2016.
 */

public class PosterAdapter extends PagerAdapter {
    private final List<View> mPosters;

    public PosterAdapter(List<? extends View> posters) {
        mPosters = new ArrayList<>(posters);
    }

    @Override
    public int getCount() {
        return mPosters.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View poster = mPosters.get(position);
        if (container.equals(poster.getParent())) {
            container.removeView(poster);
        }
        container.addView(poster);
        return poster;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

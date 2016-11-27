package com.github.piasy.playground.ylposter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 24/11/2016.
 */

public class YLDefaultPoster extends FrameLayout {

    private final int mDesiredWidth;
    private final int mDesiredHeight;

    private SimpleDraweeView mAvatar;
    private TextView mName;
    private SimpleDraweeView mQrCode;

    public YLDefaultPoster(Context context) {
        this(context, null);
    }

    public YLDefaultPoster(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YLDefaultPoster(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.ui_poster_default, this, true);
        bindView();
        Resources resources = getResources();
        mDesiredWidth = resources.getDimensionPixelSize(R.dimen.poster_width);
        mDesiredHeight = resources.getDimensionPixelSize(R.dimen.poster_height);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                adjustSize();
                setVisibility(VISIBLE);
            }
        }, 50);
        setVisibility(INVISIBLE);
    }

    @CallSuper
    protected void bindView() {
        mAvatar = findById(this, R.id.mAvatar);
        mName = findById(this, R.id.mName);
        mQrCode = findById(this, R.id.mQrCode);
    }

    private void adjustSize() {
        int desiredWidth = getWidth();
        int desiredHeight = getHeight();
        float ratioW = (float) desiredWidth / mDesiredWidth;
        float ratioH = (float) desiredHeight / mDesiredHeight;
        float ratio = (float) mDesiredWidth / mDesiredHeight;

        ViewGroup.LayoutParams params = getLayoutParams();
        if (ratioW > ratioH) {
            params.width = (int) (desiredHeight * ratio);
            params.height = desiredHeight;
        } else {
            params.width = desiredWidth;
            params.height = (int) (desiredWidth / ratio);
        }
        setLayoutParams(params);
    }

    public void showInfo(String groupName, Uri groupAvatar, Uri qrCode) {
        mName.setText(groupName);

        mQrCode.setImageURI(qrCode);
        YLPosterBase.loadWithSize(mAvatar, groupAvatar, mDesiredWidth, mDesiredHeight);
    }
}

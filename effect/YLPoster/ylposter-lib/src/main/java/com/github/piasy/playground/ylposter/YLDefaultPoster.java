package com.github.piasy.playground.ylposter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 24/11/2016.
 */

public class YLDefaultPoster extends YLPoster {

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
    }

    @CallSuper
    protected void bindView() {
        mAvatar = findById(this, R.id.mAvatar);
        mName = findById(this, R.id.mName);
        mQrCode = findById(this, R.id.mQrCode);
    }

    public void showInfo(String groupName, Uri groupAvatar, Uri qrCode) {
        mName.setText(groupName);

        mQrCode.setImageURI(qrCode);
        YLPosterBase.loadWithSize(mAvatar, groupAvatar, mDesiredWidth, mDesiredHeight);
    }
}

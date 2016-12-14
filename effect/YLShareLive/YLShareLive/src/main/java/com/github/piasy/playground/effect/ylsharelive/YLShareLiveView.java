package com.github.piasy.playground.effect.ylsharelive;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jakewharton.rxbinding.view.RxView;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 14/12/2016.
 */

public class YLShareLiveView extends LinearLayout implements View.OnClickListener {
    public static final int PLATFORM_NONE = -1;
    public static final int PLATFORM_WE_CHAT = 1;
    public static final int PLATFORM_WE_CHAT_MOMENTS = 2;
    public static final int PLATFORM_WEIBO = 3;
    public static final int PLATFORM_QQ = 4;
    public static final int PLATFORM_QZONE = 5;

    private final RadioButton mSelectedPlatform;

    private final RadioGroup mPlatforms;

    private final ImageButton mIndicator;

    private final Subscription mIndicatorClick;

    private boolean mExpanded;
    @IdRes
    private int mSelectedPlatformId = -1;

    public YLShareLiveView(Context context) {
        this(context, null);
    }

    public YLShareLiveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YLShareLiveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundResource(R.drawable.bg_yl_share_live_view);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.ui_yl_share_live_view, this, true);

        mSelectedPlatform = (RadioButton) findViewById(R.id.mSelectedPlatform);

        mPlatforms = (RadioGroup) findViewById(R.id.mPlatforms);

        RadioButton platformWeChat = (RadioButton) findViewById(R.id.mPlatformWeChat);
        RadioButton platformWeChatMoments = (RadioButton) findViewById(R.id.mPlatformWeChatMoments);
        RadioButton platformWeibo = (RadioButton) findViewById(R.id.mPlatformWeibo);
        RadioButton platformQQ = (RadioButton) findViewById(R.id.mPlatformQQ);
        RadioButton platformQzone = (RadioButton) findViewById(R.id.mPlatformQzone);

        platformWeChat.setOnClickListener(this);
        platformWeChatMoments.setOnClickListener(this);
        platformWeibo.setOnClickListener(this);
        platformQQ.setOnClickListener(this);
        platformQzone.setOnClickListener(this);

        mIndicator = (ImageButton) findViewById(R.id.mIndicator);

        mIndicatorClick = RxView.clicks(mIndicator)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (mExpanded) {
                            mExpanded = false;
                            hidePlatforms();
                        } else {
                            mExpanded = true;
                            showPlatforms();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIndicatorClick.unsubscribe();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof RadioButton) {
            if (v.getId() == mSelectedPlatformId) {
                mPlatforms.clearCheck();
                mSelectedPlatformId = -1;
            } else {
                mSelectedPlatformId = v.getId();
            }
        }
    }

    @Platform
    public int selectedPlatform() {
        if (mSelectedPlatformId == R.id.mPlatformWeChat) {
            return PLATFORM_WE_CHAT;
        } else if (mSelectedPlatformId == R.id.mPlatformWeChatMoments) {
            return PLATFORM_WE_CHAT_MOMENTS;
        } else if (mSelectedPlatformId == R.id.mPlatformWeibo) {
            return PLATFORM_WEIBO;
        } else if (mSelectedPlatformId == R.id.mPlatformQQ) {
            return PLATFORM_QQ;
        } else if (mSelectedPlatformId == R.id.mPlatformQzone) {
            return PLATFORM_QZONE;
        } else {
            return PLATFORM_NONE;
        }
    }

    private void showPlatforms() {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet
                .addTransition(new Fade(Fade.OUT)
                        .addTarget(mSelectedPlatform)
                        .setDuration(150))
                .addTransition(new Slide(Gravity.LEFT)
                        .addTarget(mPlatforms)
                        .setDuration(400))
                .addTransition(new Rotate()
                        .addTarget(mIndicator)
                        .setDuration(500));
        TransitionManager.beginDelayedTransition(this, transitionSet);

        mIndicator.setRotation(180);
        mPlatforms.setVisibility(VISIBLE);
        mSelectedPlatform.setVisibility(GONE);
    }

    private void hidePlatforms() {
        TransitionManager.beginDelayedTransition(this);

        mPlatforms.setVisibility(GONE);
        refreshSelectedPlatform();
        mIndicator.setRotation(0);
    }

    private void refreshSelectedPlatform() {
        if (mSelectedPlatformId == R.id.mPlatformWeChat) {
            showSelectedPlatform(R.drawable.bg_yl_share_live_view_platform_we_chat_checked);
        } else if (mSelectedPlatformId == R.id.mPlatformWeChatMoments) {
            showSelectedPlatform(R.drawable.bg_yl_share_live_view_platform_we_chat_moments_checked);
        } else if (mSelectedPlatformId == R.id.mPlatformWeibo) {
            showSelectedPlatform(R.drawable.bg_yl_share_live_view_platform_weibo_checked);
        } else if (mSelectedPlatformId == R.id.mPlatformQQ) {
            showSelectedPlatform(R.drawable.bg_yl_share_live_view_platform_qq_checked);
        } else if (mSelectedPlatformId == R.id.mPlatformQzone) {
            showSelectedPlatform(R.drawable.bg_yl_share_live_view_platform_qzone_checked);
        } else {
            mSelectedPlatform.setVisibility(GONE);
        }
    }

    private void showSelectedPlatform(@DrawableRes int platform) {
        mSelectedPlatform.setBackgroundResource(platform);
        mSelectedPlatform.setVisibility(VISIBLE);
    }

    @IntDef({
            PLATFORM_NONE, PLATFORM_WE_CHAT, PLATFORM_WE_CHAT_MOMENTS, PLATFORM_WEIBO, PLATFORM_QQ,
            PLATFORM_QZONE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Platform {
    }
}

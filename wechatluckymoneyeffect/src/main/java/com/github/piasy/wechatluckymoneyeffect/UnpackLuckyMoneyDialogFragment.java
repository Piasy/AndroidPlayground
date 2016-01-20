package com.github.piasy.wechatluckymoneyeffect;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/7.
 */
public class UnpackLuckyMoneyDialogFragment extends BaseDialogFragment {

    private static final int WIDTH = 270;
    private static final int HEIGHT = 360;
    private static final int TO_Y_DEGREES = 360;
    private static final int DURATION_MILLIS = 500;
    private static final int UNPACK_CHANGE_BG_DELAY = DURATION_MILLIS / 4;
    private static final int GOT_LUCKY_MONEY_HINT_TEXT_SIZE_SP = 16;
    private static final int HUNDRED = 100;
    private static final float TEXT_SIZE_PROPORTION = 2.69F;
    private static final float DIM_AMOUNT = 0.5F;
    public static final int TWO = 2;
    public static final int BG_MOVE_UP_DIP = -195;
    public static final int BG_MOVE_DOWN_DIP = 130;

    private ImageButton mIbClose;

    private SimpleDraweeView mAvatar;

    private TextView mTvUsername;

    private TextView mTvHint;

    private TextView mTvWish;

    private TextView mTvGotMoney;

    private ImageView mIvLuckyMoneyAboveHalf;

    private ImageView mIvLuckyMoneyBelowHalf;

    private ImageView mIvUnpack;

    private Resources mResources;

    private Rotate3dAnimation mRotate3dAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResources = getResources();
        mRotate3dAnimation = new Rotate3dAnimation(0, 0, 0, TO_Y_DEGREES, 0, 0);
        mRotate3dAnimation.setDuration(DURATION_MILLIS);
        mRotate3dAnimation.setInterpolator(new LinearInterpolator());
        mRotate3dAnimation.setRepeatCount(Animation.INFINITE);
    }

    @Override
    protected void injectDependencies() {

    }

    @LayoutRes
    @Override
    protected int getLayoutRes() {
        return R.layout.ui_unpack_lucky_monry_dialog_layout;
    }

    @Override
    protected int getWidth() {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH,
                mResources.getDisplayMetrics());
    }

    @Override
    protected int getHeight() {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT,
                mResources.getDisplayMetrics());
    }

    @Override
    protected float getDimAmount() {
        return DIM_AMOUNT;
    }

    @Override
    protected void bindViews(View view) {
        mIbClose = ButterKnife.findById(view, R.id.mIbClose);
        mAvatar = ButterKnife.findById(view, R.id.mAvatar);
        mTvUsername = ButterKnife.findById(view, R.id.mTvUsername);
        mTvHint = ButterKnife.findById(view, R.id.mTvHint);
        mTvWish = ButterKnife.findById(view, R.id.mTvWish);
        mTvGotMoney = ButterKnife.findById(view, R.id.mTvGotMoney);
        mIvUnpack = ButterKnife.findById(view, R.id.mIvUnpack);
        mIvLuckyMoneyAboveHalf = ButterKnife.findById(view, R.id.mIvLuckyMoneyAboveHalf);
        mIvLuckyMoneyBelowHalf = ButterKnife.findById(view, R.id.mIvLuckyMoneyBelowHalf);

        mTvUsername.setText("YOLO");
        listenOnClickRxy(mIbClose, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                dismiss();
            }
        });
        listenOnClickRxy(mIvUnpack, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mIvUnpack.startAnimation(mRotate3dAnimation);

                addSubscribe(Observable.timer(UNPACK_CHANGE_BG_DELAY, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                mIvUnpack.setImageResource(R.drawable.iv_unpack_animated);
                            }
                        }, RxUtils.IgnoreErrorProcessor));
                unpack();
            }
        });
    }

    @Override
    protected void unbindViews() {
        mIbClose = null;
        mAvatar = null;
        mTvUsername = null;
        mTvHint = null;
        mTvWish = null;
        mTvGotMoney = null;
        mIvUnpack = null;
        mIvLuckyMoneyAboveHalf = null;
        mIvLuckyMoneyBelowHalf = null;

    }

    Random mRandom = new Random();

    private void unpack() {
        addSubscribe(Observable.timer(800, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (mRandom.nextBoolean()) {
                            gotLuckyMoney(mRandom.nextInt(19999) + 1);
                        } else {
                            noLuckyMoney();
                        }
                    }
                }, RxUtils.IgnoreErrorProcessor));
    }

    private void gotLuckyMoney(int moneyInCent) {
        mIvUnpack.clearAnimation();
        mIvUnpack.setVisibility(View.GONE);

        mIvLuckyMoneyAboveHalf.animate()
                .translationY(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BG_MOVE_UP_DIP,
                        getResources().getDisplayMetrics()))
                .start();
        mIvLuckyMoneyBelowHalf.animate()
                .translationY(
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BG_MOVE_DOWN_DIP,
                                getResources().getDisplayMetrics()))
                .start();
        mTvUsername.setTextColor(mResources.getColor(R.color.dark_black));
        mTvHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, GOT_LUCKY_MONEY_HINT_TEXT_SIZE_SP);
        mTvHint.setTextColor(Color.parseColor("#FF999999"));
        mTvHint.setText(R.string.unpack_lucky_money_wish);

        String amount = String.format(
                mResources.getString(R.string.unpack_lucky_money_result_text_formatter),
                (float) moneyInCent / HUNDRED);
        Spannable bonus = new SpannableString(amount);
        bonus.setSpan(new RelativeSizeSpan(TEXT_SIZE_PROPORTION), 0, amount.length() - TWO,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvGotMoney.setText(bonus);
        mTvGotMoney.setVisibility(View.VISIBLE);
    }

    private void noLuckyMoney() {
        mIvUnpack.clearAnimation();
        mIvUnpack.setImageResource(R.drawable.iv_unpack);
        mIvUnpack.setVisibility(View.GONE);
        mTvHint.setVisibility(View.INVISIBLE);
        mTvWish.setText(R.string.unpack_lucky_money_empty_hint);
    }
}

package com.github.piasy.playground.ylposter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.OnClick;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.piasy.biv.view.BigImageView;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 24/11/2016.
 */

public abstract class YLPosterBase extends FrameLayout {

    private static final int MAX_YOLO_ID_SHOW_LENGTH = 20;
    private static final int MODE_DISPLAY = 0;
    private static final int MODE_PREVIEW = 1;
    private static final int MODE_EDIT = 2;
    private final Resources mResources;
    private final int mMode;

    private float mRatio;
    private ChangeBgListener mChangeBgListener;

    private BigImageView mPosterEditBg;
    private SimpleDraweeView mPosterViewBg;

    private EditText mEtTitle;
    private View mTitleEditHint;

    private TextView mTvYoloId;

    private EditText mEtDesc;
    private View mDescEditHint;

    private View mChangePosterBg;

    private SimpleDraweeView mQrCode;
    private TextView mScanQrCodeHint;

    private EditText mEtSlogan;
    private View mSloganEditHint;

    public YLPosterBase(Context context) {
        this(context, null);
    }

    public YLPosterBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YLPosterBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.yl_poster, defStyleAttr, 0);
        mMode = typedArray.getInteger(R.styleable.yl_poster_mode, MODE_DISPLAY);
        typedArray.recycle();

        switch (mMode) {
            case MODE_EDIT:
                LayoutInflater.from(context).inflate(R.layout.ui_poster_edit_bg, this, true);
                break;
            case MODE_PREVIEW:
            case MODE_DISPLAY:
            default:
                LayoutInflater.from(context).inflate(R.layout.ui_poster_view_bg, this, true);
                break;
        }

        LayoutInflater.from(context).inflate(content(), this, true);
        bindView();
        mResources = getResources();

        initView();

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
        mPosterEditBg = findById(this, R.id.mPosterEditBg);
        mPosterViewBg = findById(this, R.id.mPosterViewBg);

        mEtTitle = findById(this, R.id.mEtTitle);
        mTitleEditHint = findById(this, R.id.mTitleEditHint);

        mTvYoloId = findById(this, R.id.mTvYoloId);

        mEtDesc = findById(this, R.id.mEtDesc);
        mDescEditHint = findById(this, R.id.mDescEditHint);

        mChangePosterBg = findById(this, R.id.mChangePosterBg);

        mQrCode = findById(this, R.id.mQrCode);
        mScanQrCodeHint = findById(this, R.id.mScanQrCodeHint);

        mEtSlogan = findById(this, R.id.mEtSlogan);
        mEtSlogan.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
        mSloganEditHint = findById(this, R.id.mSloganEditHint);
    }

    private void initView() {
        switch (mMode) {
            case MODE_PREVIEW:
                hideEditable();
                break;
            case MODE_DISPLAY:
                hideAll();
                break;
            case MODE_EDIT:
            default:
                break;
        }
    }

    public void setChangeBgListener(ChangeBgListener changeBgListener) {
        mChangeBgListener = changeBgListener;
    }

    @OnClick(R2.id.mChangePosterBg)
    void changeBg() {
        if (mChangeBgListener != null) {
            mChangeBgListener.changeBg();
        }
    }

    public void showBg(Uri uri) {
        if (mPosterEditBg != null) {
            mPosterEditBg.showImage(uri);
        } else if (mPosterViewBg != null) {
            mPosterViewBg.setImageURI(uri);
        }
    }

    public void startSave() {
        switch (mMode) {
            case MODE_PREVIEW:
                mTvYoloId.setVisibility(GONE);
                mQrCode.setVisibility(GONE);
                break;
            case MODE_EDIT:
                hideEditable();

                mTvYoloId.setVisibility(GONE);
                mQrCode.setVisibility(GONE);
                break;
            case MODE_DISPLAY:
            default:
                break;
        }
    }

    private void hideEditable() {
        mTitleEditHint.setVisibility(View.GONE);
        mDescEditHint.setVisibility(View.GONE);
        mChangePosterBg.setVisibility(View.GONE);
        mSloganEditHint.setVisibility(View.GONE);
        mEtTitle.setBackground(null);
        mEtTitle.setCursorVisible(false);
        mEtTitle.setEnabled(false);
        mEtDesc.setBackground(null);
        mEtDesc.setCursorVisible(false);
        mEtDesc.setEnabled(false);
        mEtSlogan.setBackground(null);
        mEtSlogan.setCursorVisible(false);
        mEtSlogan.setEnabled(false);
        if (TextUtils.isEmpty(mEtDesc.getText())) {
            mEtDesc.setVisibility(View.GONE);
        }
    }

    @CallSuper
    protected void hideAll() {
        mEtTitle.setVisibility(View.GONE);
        mTitleEditHint.setVisibility(View.GONE);

        mEtDesc.setVisibility(View.GONE);
        mDescEditHint.setVisibility(View.GONE);

        mChangePosterBg.setVisibility(View.GONE);

        mEtSlogan.setVisibility(View.GONE);
        mSloganEditHint.setVisibility(View.GONE);

        mScanQrCodeHint.setVisibility(View.GONE);
    }

    private void adjustSize() {
        int desiredWidth = getWidth();
        int desiredHeight = getHeight();
        int width = mResources.getDimensionPixelSize(R.dimen.poster_width);
        int height = mResources.getDimensionPixelSize(R.dimen.poster_height);
        float ratioW = (float) desiredWidth / width;
        float ratioH = (float) desiredHeight / height;
        mRatio = (float) width / height;

        ViewGroup.LayoutParams params = getLayoutParams();
        if (ratioW > ratioH) {
            params.width = (int) (desiredHeight * mRatio);
            params.height = desiredHeight;
        } else {
            params.width = desiredWidth;
            params.height = (int) (desiredWidth / mRatio);
        }
        setLayoutParams(params);

        adjustTextSize(mEtTitle, titleTextSize());
        adjustTextSize(mTvYoloId, yoloIdTextSize());
        adjustTextSize(mEtDesc, descTextSize());
        adjustTextSize(mScanQrCodeHint, qrCodeScanHintTextSize());
    }

    public void showInfo(String yoloId, String name, Uri qrCode) {
        mEtTitle.setHint(name);

        String text;
        if (yoloId.length() > MAX_YOLO_ID_SHOW_LENGTH) {
            text = yoloId.substring(0, MAX_YOLO_ID_SHOW_LENGTH) + "…";
        } else {
            text = yoloId;
        }
        mTvYoloId.setText(String.format(getResources().getString(yoloIdFormatter()), text));

        mQrCode.setImageURI(qrCode);
    }

    private void adjustTextSize(TextView textView, @DimenRes int desired) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mResources.getDimension(desired) * mRatio);
    }

    @LayoutRes
    protected abstract int content();

    @StringRes
    protected abstract int yoloIdFormatter();

    @DimenRes
    protected abstract int titleTextSize();

    @DimenRes
    protected abstract int yoloIdTextSize();

    @DimenRes
    protected abstract int descTextSize();

    @DimenRes
    protected abstract int qrCodeScanHintTextSize();
}

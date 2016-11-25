package com.github.piasy.playground.ylposter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 24/11/2016.
 */

public class YLPosterAdhere extends YLPosterBase {
    private View mAdhereBg;
    private View mYoloLogo1;
    private View mYoloLogo2;

    public YLPosterAdhere(Context context) {
        super(context);
    }

    public YLPosterAdhere(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YLPosterAdhere(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void bindView() {
        super.bindView();

        mAdhereBg = findById(this, R.id.mAdhereBg);
        mYoloLogo1 = findById(this, R.id.mYoloLogo1);
        mYoloLogo2 = findById(this, R.id.mYoloLogo2);
    }

    @Override
    protected void hideAll() {
        super.hideAll();
        mAdhereBg.setVisibility(GONE);
        mYoloLogo1.setVisibility(GONE);
        mYoloLogo2.setVisibility(GONE);
    }

    @Override
    protected int content() {
        return R.layout.ui_poster_adhere;
    }

    @Override
    protected int yoloIdFormatter() {
        return R.string.poster_yolo_id_adhere_formatter;
    }

    @Override
    protected int titleTextSize() {
        return R.dimen.adhere_title_text_size;
    }

    @Override
    protected int yoloIdTextSize() {
        return R.dimen.adhere_yolo_id_text_size;
    }

    @Override
    protected int descTextSize() {
        return R.dimen.adhere_desc_text_size;
    }

    @Override
    protected int qrCodeScanHintTextSize() {
        return R.dimen.adhere_qr_code_scan_text_size;
    }
}

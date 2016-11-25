package com.github.piasy.playground.ylposter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 24/11/2016.
 */

public class YLPosterSnow extends YLPosterBase {
    private View mYoloLogo;

    public YLPosterSnow(Context context) {
        super(context);
    }

    public YLPosterSnow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YLPosterSnow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void bindView() {
        super.bindView();

        mYoloLogo = findById(this, R.id.mYoloLogo);
    }

    @Override
    protected void hideAll() {
        super.hideAll();
        mYoloLogo.setVisibility(GONE);
    }

    @Override
    protected int content() {
        return R.layout.ui_poster_snow;
    }

    @Override
    protected int yoloIdFormatter() {
        return R.string.poster_yolo_id_snow_formatter;
    }

    @Override
    protected int titleTextSize() {
        return R.dimen.snow_title_text_size;
    }

    @Override
    protected int yoloIdTextSize() {
        return R.dimen.snow_yolo_id_text_size;
    }

    @Override
    protected int descTextSize() {
        return R.dimen.snow_desc_text_size;
    }

    @Override
    protected int qrCodeScanHintTextSize() {
        return R.dimen.snow_qr_code_scan_text_size;
    }
}

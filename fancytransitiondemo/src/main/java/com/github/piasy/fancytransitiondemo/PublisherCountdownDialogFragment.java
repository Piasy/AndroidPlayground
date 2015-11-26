package com.github.piasy.fancytransitiondemo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/24.
 */
public class PublisherCountdownDialogFragment extends DialogFragment {

    private static final int WIDTH = 94;

    public PublisherCountdownDialogFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.dialog_fragment_publisher_countdown, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    private void setupView(View view) {
        final CountdownAnimationTextView countdown = findById(view, R.id.mCountDownAnim);
        countdown.setCountdown(5, 1);
        countdown.setCountdownCallback(new CountdownAnimationTextView.LazyCountdownCallback() {
            @Override
            public void onComplete() {
                super.onComplete();
                dismiss();
            }
        });
        countdown.startCountdown();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH,
                getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH,
                        getResources().getDisplayMetrics()));
        window.setGravity(Gravity.CENTER);

        // Transparent background; see http://stackoverflow.com/q/15007272/56285
        // (Needed to make dialog's alpha shadow look good)
        window.setBackgroundDrawableResource(android.R.color.transparent);

        final Resources res = getResources();
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        if (titleDividerId > 0) {
            final View titleDivider = getDialog().findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(android.R.color.transparent));
            }
        }
    }
}

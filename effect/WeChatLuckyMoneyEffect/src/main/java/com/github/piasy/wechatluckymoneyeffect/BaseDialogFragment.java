package com.github.piasy.wechatluckymoneyeffect;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.jakewharton.rxbinding.view.RxView;
import java.util.concurrent.TimeUnit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by piasy on 15/5/4.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private static final float DEFAULT_DIM_AMOUNT = 0.2F;

    private static final int WINDOW_DURATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.injectDependencies();
        bindViews(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        // without title and title divider

        // Less dimmed background; see http://stackoverflow.com/q/13822842/56285
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //CHECKSTYLE:OFF
        params.dimAmount = getDimAmount(); // dim only a little bit
        //CHECKSTYLE:ON
        window.setAttributes(params);

        window.setLayout(getWidth(), getHeight());
        window.setGravity(getGravity());

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

    public void onDestroyView() {
        super.onDestroyView();
        unbindViews();
        unSubscribeAll();
    }

    private CompositeSubscription mCompositeSubscription = null;

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            // recreate mCompositeSubscription
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    protected void listenOnClickRxy(View view, Action1<Void> action) {
        addSubscribe(RxView.clicks(view)
                .throttleFirst(WINDOW_DURATION, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, RxUtils.IgnoreErrorProcessor));
    }

    protected void unSubscribeAll() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
        mCompositeSubscription = null;
    }

    protected abstract void injectDependencies();

    @LayoutRes
    protected abstract int getLayoutRes();

    protected float getDimAmount() {
        return DEFAULT_DIM_AMOUNT;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    protected abstract void bindViews(View view);

    protected abstract void unbindViews();
}

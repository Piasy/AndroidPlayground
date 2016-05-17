package com.github.piasy.fancytransitiondemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.kogitune.activity_transition.fragment.FragmentTransition;
import com.squareup.picasso.Picasso;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static butterknife.ButterKnife.findById;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountdownFragment4Viewers extends Fragment {

    public static final String ARGS_KEY_TO_USER_AVATAR = "ARGS_KEY_TO_USER_AVATAR";
    public static final String ARGS_KEY_TO_USER_NAME = "ARGS_KEY_TO_USER_NAME";

    private String mToUserAvatar;
    private String mToUsername;

    private ScreenTransferController mScreenTransferController;

    private Subscription mCountSubscription;

    public CountdownFragment4Viewers() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ScreenTransferController) {
            mScreenTransferController = (ScreenTransferController) context;
        } else {
            throw new IllegalStateException(
                    context.getClass() + "Activity must implement ScreenTransferController");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mScreenTransferController = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
    }

    private void getArgs() {
        mToUserAvatar = getArguments().getString(ARGS_KEY_TO_USER_AVATAR);
        mToUsername = getArguments().getString(ARGS_KEY_TO_USER_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_countdown_fragment_4others,
                container, false);

        FragmentTransition.with(this)
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(300)
                .to(v.findViewById(R.id.mToAvatar))
                .start(savedInstanceState);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCountSubscription != null && mCountSubscription.isUnsubscribed()) {
            mCountSubscription.unsubscribe();
        }
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
                mScreenTransferController.screenTransferSuccess();
            }
        });

        ImageButton ibClose = findById(view, R.id.mIbClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenTransferController.quitScreenTransferAtCountdown();
            }
        });

        ImageView ivToAvatar = findById(view, R.id.mToAvatar);
        Picasso.with(getContext()).load(mToUserAvatar).into(ivToAvatar);

        final TextView tvToUsername = findById(view, R.id.mToUsername);
        tvToUsername.setText(mToUsername);

        final TextView tvCountdownHint = findById(view, R.id.mTvCountdownHint);

        mCountSubscription = Observable.interval(500, 200, TimeUnit.MILLISECONDS)
                .take(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long count) {
                        if (count == 0) {
                            tvToUsername.animate().alpha(1).setDuration(100).start();
                            tvCountdownHint.animate().alpha(1).setDuration(100).start();
                        } else if (count == 1) {
                            countdown.startCountdown();
                        }
                    }
                });
    }
}

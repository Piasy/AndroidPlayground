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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kogitune.activity_transition.fragment.FragmentTransition;
import com.squareup.picasso.Picasso;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static butterknife.ButterKnife.findById;

public class TransferringFragment extends Fragment {

    public static final String ARGS_KEY_FROM_USER_AVATAR = "ARGS_KEY_FROM_USER_AVATAR";
    public static final String ARGS_KEY_FROM_USER_NAME = "ARGS_KEY_FROM_USER_NAME";
    public static final String ARGS_KEY_TO_USER_AVATAR = "ARGS_KEY_TO_USER_AVATAR";
    public static final String ARGS_KEY_TO_USER_NAME = "ARGS_KEY_TO_USER_NAME";
    public static final String ARGS_KEY_TO_UID = "ARGS_KEY_TO_UID";

    private String mFromUserAvatar;
    private String mToUserAvatar;
    private String mFromUsername;
    private String mToUsername;
    private long mToUid;

    private Subscription mShowSubscription;
    private ImageView mToAvatar;
    private TextView mTvFromUsername;
    private TextView mTvToUsername;
    private LinearLayout mLlAnimContainer;

    private ScreenTransferController mScreenTransferController;

    public TransferringFragment() {
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
        mFromUserAvatar = getArguments().getString(ARGS_KEY_FROM_USER_AVATAR);
        mFromUsername = getArguments().getString(ARGS_KEY_FROM_USER_NAME);
        mToUserAvatar = getArguments().getString(ARGS_KEY_TO_USER_AVATAR);
        mToUsername = getArguments().getString(ARGS_KEY_TO_USER_NAME);
        mToUid = getArguments().getLong(ARGS_KEY_TO_UID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mShowSubscription != null && !mShowSubscription.isUnsubscribed()) {
            mShowSubscription.unsubscribe();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_screen_transfering, container, false);
        // TODO test uid
        if (mToUid == 0) {
            FragmentTransition.with(this)
                    .interpolator(new AccelerateDecelerateInterpolator())
                    .duration(300)
                    .to(v.findViewById(R.id.mFromAvatar))
                    .start(savedInstanceState);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    private void setupView(View view) {
        ImageView fromAvatar = findById(view, R.id.mFromAvatar);
        Picasso.with(getContext()).load(mFromUserAvatar).into(fromAvatar);
        mToAvatar = findById(view, R.id.mToAvatar);
        Picasso.with(getContext()).load(mToUserAvatar).into(mToAvatar);
        mTvFromUsername = findById(view, R.id.mFromUsername);
        mTvFromUsername.setText(mFromUsername);
        mTvToUsername = findById(view, R.id.mToUsername);
        mTvToUsername.setText(mToUsername);
        mLlAnimContainer = findById(view, R.id.mLlAnimContainer);

        ImageButton ibClose = findById(view, R.id.mIbClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScreenTransferController.quitScreenTransferAtTransfer();
            }
        });

        show();

        // TODO Simulate connected
        mLlAnimContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mScreenTransferController != null) {
                    if (mToUid == 0) {
                        mScreenTransferController.showCountdownFragment4NewPublisher();
                    } else {
                        mScreenTransferController.showCountdownFragment4NewViewers(mToUserAvatar,
                                mToUsername, mToAvatar);
                    }
                }
            }
        }, 5000);
    }

    private void show() {
        mShowSubscription = Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(3)
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
                            showFromUsername();
                        } else if (count == 1) {
                            showToUser();
                        } else if (count == 2) {
                            showCenterAnim();
                        }
                    }
                });
    }

    private void showFromUsername() {
        mTvFromUsername.animate().alpha(1).setDuration(100).start();
    }

    private void showToUser() {
        mTvToUsername.animate().alpha(1).setDuration(100).start();
        mToAvatar.animate().alpha(1).setDuration(100).start();
    }

    private void showCenterAnim() {
        mLlAnimContainer.animate().alpha(1).setDuration(100).start();
    }
}

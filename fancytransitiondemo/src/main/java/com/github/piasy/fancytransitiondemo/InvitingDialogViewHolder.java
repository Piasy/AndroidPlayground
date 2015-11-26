package com.github.piasy.fancytransitiondemo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.iwgang.countdownview.CountdownView;
import com.squareup.picasso.Picasso;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/24.
 */
public class InvitingDialogViewHolder {
    private static final int INVITE_EXPIRE_MILLIS = 30 * 1000;

    private final String mFromUserAvatar;
    private final String mFromUsername;
    private final String mToUserAvatar;
    private final String mToUsername;
    private final long mToUid;

    private final Context mContext;
    private final Controller mController;
    private final View mRootView;

    ImageView mAvatar;

    public InvitingDialogViewHolder(String fromUserAvatar, String fromUsername, String toUserAvatar,
            String toUsername, long toUid, Context context, View rootView, Controller controller) {
        mFromUserAvatar = fromUserAvatar;
        mFromUsername = fromUsername;
        mToUserAvatar = toUserAvatar;
        mToUsername = toUsername;
        mToUid = toUid;
        mContext = context;
        mRootView = rootView;
        mController = controller;
    }

    public void setupView() {
        mRootView.setVisibility(View.VISIBLE);
        mAvatar = findById(mRootView, R.id.mToAvatar);
        Picasso.with(mContext).load(mToUserAvatar).into(mAvatar);

        CountdownView countdownView = findById(mRootView, R.id.mCountDownTimber);
        countdownView.start(INVITE_EXPIRE_MILLIS);
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                if (mController != null) {
                    mController.denied();
                }
            }
        });

        TextView tvToUsername = findById(mRootView, R.id.mTvToUsername);
        tvToUsername.setText(mToUsername);

        // TODO simulate accepted
        tvToUsername.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mController != null) {
                    mController.accepted(mFromUserAvatar, mFromUsername, mToUserAvatar, mToUsername,
                            mToUid);
                }
            }
        }, 5000);
    }

    public interface Controller {
        void denied();

        void accepted(String fromUserAvatar, String fromUsername, String toUserAvatar,
                String toUsername, long uid);
    }
}

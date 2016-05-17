package com.github.piasy.fancytransitiondemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.kogitune.activity_transition.fragment.FragmentTransitionLauncher;

import static butterknife.ButterKnife.findById;

public class MainActivity extends AppCompatActivity implements ScreenTransferController {

    private static final String URL =
            "http://a.hiphotos.baidu.com/image/w%3D310/sign=762d27820d2442a7ae0efba4e142ad95" +
                    "/4bed2e738bd4b31c6f7d89ba85d6277f9e2ff86a.jpg";
    private static final String URL2 =
            "https://raw.githubusercontent.com/Piasy/Piasy.github.io/master/images/piasy_avatar" +
                    ".jpg";

    private FragmentManager mFragmentManager;
    private RelativeLayout mRlInvitingDialogContainer;
    private InvitingDialogViewHolder mInvitingDialogViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        Button invitedView = findById(this, R.id.mBtnInvitedView);
        invitedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInvitedDialog(URL, "齐天大圣", URL2, "尅; K", 0);
            }
        });

        mRlInvitingDialogContainer = findById(this, R.id.mRlInvitingDialogContainer);
        Button publisherView = findById(this, R.id.mBtnPublisherView);
        publisherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInvitingDialog(URL, "齐天大圣", URL2, "尅; K", 1);
            }
        });

        Button othersView = findById(this, R.id.mBtnOthersView);
        othersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTransferringFragment(URL, "齐天大圣", URL2, "尅; K", 1, null);
            }
        });

    }

    @Override
    public void showInvitingDialog(String fromUserAvatar, String fromUsername, String toUserAvatar,
            String toUsername, final long toUid) {
        if (mInvitingDialogViewHolder == null) {
            mInvitingDialogViewHolder =
                    new InvitingDialogViewHolder(fromUserAvatar, fromUsername, toUserAvatar,
                            toUsername, toUid, this, mRlInvitingDialogContainer,
                            new InvitingDialogViewHolder.Controller() {
                                @Override
                                public void denied() {
                                    if (mRlInvitingDialogContainer != null) {
                                        mRlInvitingDialogContainer.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void accepted(String fromUserAvatar, String fromUsername,
                                        String toUserAvatar, String toUsername, long uid) {
                                    showTransferringFragment(fromUserAvatar, fromUsername,
                                            toUserAvatar, toUsername, toUid, null);
                                    if (mRlInvitingDialogContainer != null) {
                                        mRlInvitingDialogContainer.setVisibility(View.GONE);
                                    }
                                }
                            });
        }
        mInvitingDialogViewHolder.setupView();
    }

    @Override
    public void showInvitedDialog(String fromUserAvatar, String fromUsername, String toUserAvatar,
            String toUsername, long toUid) {
        InvitedDialogFragmentBuilder.newInvitedDialogFragment(fromUserAvatar, fromUsername, toUid,
                toUserAvatar, toUsername)
                .show(mFragmentManager, InvitedDialogFragment.class.getName());
    }

    @Override
    public void showTransferringFragment(String fromUserAvatar, String fromUsername,
            String toUserAvatar, String toUsername, long uid, ImageView imageView) {
        TransferringFragment toFragment = new TransferringFragment();
        if (imageView != null) {
            FragmentTransitionLauncher.with(this).from(imageView).prepare(toFragment);
        } else {
            toFragment.setArguments(new Bundle());
        }
        Bundle args = toFragment.getArguments();
        args.putString(TransferringFragment.ARGS_KEY_FROM_USER_AVATAR, fromUserAvatar);
        args.putString(TransferringFragment.ARGS_KEY_FROM_USER_NAME, fromUsername);
        args.putString(TransferringFragment.ARGS_KEY_TO_USER_AVATAR, toUserAvatar);
        args.putString(TransferringFragment.ARGS_KEY_TO_USER_NAME, toUsername);
        args.putLong(TransferringFragment.ARGS_KEY_TO_UID, uid);
        mFragmentManager.beginTransaction()
                .replace(android.R.id.content, toFragment, TransferringFragment.class.getName())
                .commit();
    }

    @Override
    public void showCountdownFragment4NewViewers(String toUserAvatar, String toUsername,
            ImageView imageView) {
        CountdownFragment4Viewers toFragment = new CountdownFragment4Viewers();
        FragmentTransitionLauncher.with(this).from(imageView).prepare(toFragment);
        Bundle args = toFragment.getArguments();
        args.putString(CountdownFragment4Viewers.ARGS_KEY_TO_USER_AVATAR, toUserAvatar);
        args.putString(CountdownFragment4Viewers.ARGS_KEY_TO_USER_NAME, toUsername);
        mFragmentManager.beginTransaction()
                .replace(android.R.id.content, toFragment,
                        CountdownFragment4Viewers.class.getName())
                .commit();
    }

    @Override
    public void showCountdownFragment4NewPublisher() {
        mFragmentManager.beginTransaction()
                .remove(mFragmentManager.findFragmentByTag(TransferringFragment.class.getName()))
                .commit();
        new PublisherCountdownDialogFragment().show(mFragmentManager,
                PublisherCountdownDialogFragment.class.getName());
    }

    @Override
    public void screenTransferSuccess() {
        Toast.makeText(this, "screenTransferSuccess", Toast.LENGTH_SHORT).show();
        mFragmentManager.beginTransaction()
                .remove(mFragmentManager.findFragmentByTag(
                        CountdownFragment4Viewers.class.getName()))
                .commit();
    }

    @Override
    public void quitScreenTransferAtTransfer() {
        Toast.makeText(this, "quitScreenTransferAtTransfer", Toast.LENGTH_SHORT).show();
        mFragmentManager.beginTransaction()
                .remove(mFragmentManager.findFragmentByTag(TransferringFragment.class.getName()))
                .commit();
    }

    @Override
    public void quitScreenTransferAtCountdown() {
        Toast.makeText(this, "quitScreenTransferAtCountdown", Toast.LENGTH_SHORT).show();
        mFragmentManager.beginTransaction()
                .remove(mFragmentManager.findFragmentByTag(
                        CountdownFragment4Viewers.class.getName()))
                .commit();
    }

    @Override
    public void onBackPressed() {
        // omit
    }
}

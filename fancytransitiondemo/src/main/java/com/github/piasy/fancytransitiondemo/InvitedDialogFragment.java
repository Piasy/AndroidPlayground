package com.github.piasy.fancytransitiondemo;

import android.app.Activity;
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
import android.widget.ImageView;
import cn.iwgang.countdownview.CountdownView;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.squareup.picasso.Picasso;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 15/11/24.
 */
public class InvitedDialogFragment extends DialogFragment
        implements View.OnClickListener {

    private static final int WIDTH = 270;
    private static final int INVITE_EXPIRE_MILLIS = 30 * 1000;

    @Arg
    String mFromUserAvatar;
    @Arg
    String mFromUsername;
    @Arg
    String mToUserAvatar;
    @Arg
    String mToUsername;
    @Arg
    long mToUid;

    private ScreenTransferController mScreenTransferController;

    public InvitedDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ScreenTransferController) {
            mScreenTransferController = (ScreenTransferController) activity;
        } else {
            throw new IllegalStateException(
                    activity.getClass() + "Activity must implement ScreenTransferController");
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
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.dialog_fragment_invited, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    ImageView mAvatar;

    private void setupView(View view) {
        mAvatar = findById(view, R.id.mFromAvatar);
        Picasso.with(getContext()).load(mFromUserAvatar).into(mAvatar);

        CountdownView countdownView = findById(view, R.id.mCountDownTimber);
        countdownView.start(INVITE_EXPIRE_MILLIS);
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                dismiss();
            }
        });

        ImageView deny = findById(view, R.id.mIvDeny);
        deny.setOnClickListener(this);
        ImageView accept = findById(view, R.id.mIvAccept);
        accept.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH,
                getResources().getDisplayMetrics()), ViewGroup.LayoutParams.WRAP_CONTENT);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.mIvDeny) {
            dismiss();
        } else if (id == R.id.mIvAccept) {
            mScreenTransferController.showTransferringFragment(mFromUserAvatar, mFromUsername,
                    mToUserAvatar, mToUsername, mToUid, mAvatar);
            dismiss();
        }
    }
}

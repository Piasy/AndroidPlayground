package com.github.piasy.dialogfragmentdemo;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Piasy{github.com/Piasy} on 15/10/14.
 */
public class BottomPopUpWindowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_popup_window);
        ButterKnife.bind(this);
    }

    @Bind(R.id.mContentHolder)
    LinearLayout mContentHolder;

    @OnClick(R.id.mButton)
    public void onClick() {
        showToast("Click");

        View windowView = getLayoutInflater().inflate(R.layout.ui_bottom_popup_window, null);
        final PopupWindow popupWindow =
                new PopupWindow(windowView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setAnimationStyle(R.style.popup_window_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_window_bg));

        TextView cancel = ButterKnife.findById(windowView, R.id.mCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        LinearLayout holder = ButterKnife.findById(windowView, R.id.mPopupWindowHolder);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(mContentHolder, Gravity.BOTTOM, 0, 0);
    }

    @OnClick(R.id.mButton1)
    public void onClick1() {
        new TopDownDialogFragment().show(getFragmentManager(), "TopDownDialogFragment");
    }

    @OnClick(R.id.mButton2)
    public void onClick2() {
        showToast("Click2");
        View dialogView = getLayoutInflater().inflate(R.layout.ui_dialog, null);
        new MaterialDialog.Builder(this).customView(dialogView, false).show();
    }

    @OnClick(R.id.mButton3)
    public void onClick3() {
        View dialogView = getLayoutInflater().inflate(R.layout.ui_dialog, null);
        final PopupWindow popupWindow = new PopupWindow(dialogView,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250,
                        getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 186,
                        getResources().getDisplayMetrics()), true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.showAtLocation(mContentHolder, Gravity.CENTER, 0, 0);
    }

    @OnClick(R.id.mButton4)
    public void onClick4() {
        new SimpleDialogFragment().show(getFragmentManager(), "SimpleDialogFragment");
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public static class SimpleDialogFragment extends DialogFragment {

        private Context mContext;

        @Bind(R.id.mEtBonusAmount)
        EditText mEtBonusAmount;

        @OnClick(R.id.mIvClose)
        public void close() {
            dismiss();
        }

        @OnClick(R.id.mTvGiveBonus)
        void give() {
            Toast.makeText(mContext, mEtBonusAmount.getText().toString(), Toast.LENGTH_SHORT)
                    .show();
            dismiss();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View view = inflater.inflate(R.layout.ui_dialog, null);
            mContext = inflater.getContext();
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            Window window = getDialog().getWindow();
            window.setLayout((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250,
                            getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 186,
                            getResources().getDisplayMetrics()));
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    public static class TopDownDialogFragment extends DialogFragment implements View.OnClickListener {

        @OnClick(R.id.mCancel)
        public void close() {
            dismiss();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View view = inflater.inflate(R.layout.ui_bottom_dialog_fragment, null);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            Window window = getDialog().getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170,
                            getResources().getDisplayMetrics()));
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

        @Override
        public void onStart() {
            super.onStart();
            getDialog().getWindow().setWindowAnimations(R.style.popup_window_anim_style);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

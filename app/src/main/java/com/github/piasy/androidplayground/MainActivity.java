package com.github.piasy.androidplayground;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Piasy{github.com/Piasy} on 15/10/2.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.mBtnBitmapLoadMemoryTest, R.id.mBtnRecyclerViewInsideScrollView,
            R.id.mBtnCollapsingAppBar, R.id.mBtnBonusAnimation, R.id.mBtnBottomPopupWindow,
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtnBitmapLoadMemoryTest:
                startActivity(new Intent(this, BitmapLoadTestActivity.class));
                break;
            case R.id.mBtnRecyclerViewInsideScrollView:
                startActivity(new Intent(this, RecyclerViewInsideScrollViewActivity.class));
                break;
            case R.id.mBtnCollapsingAppBar:
                startActivity(new Intent(this, CollapsingAppBarActivity.class));
                break;
            case R.id.mBtnBonusAnimation:
                startActivity(new Intent(this, BonusAnimationActivity.class));
                break;
            case R.id.mBtnBottomPopupWindow:
                startActivity(new Intent(this, BottomPopUpWindowActivity.class));
                break;
        }
    }
}

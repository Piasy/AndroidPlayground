package com.github.piasy.recyclerviewinsidescrollviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static butterknife.ButterKnife.findById;

public class BehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavoir);

        final TextView title = findById(this, R.id.mTvTitle);
        LinearLayout lLStatistics = findById(this, R.id.mLlStatistics);

        NestedScrollView nestedScrollView = findById(this, R.id.mNestedScrollView);
        nestedScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new NSVHeaderScrollOutPercentListener(
                        new NSVHeaderScrollOutPercentListener.HeaderScrollOutPercentageListener() {
                            @Override
                            public void onHeaderScrollOffPercent(float percent) {
                                int alpha = (int) (percent * 191);
                                title.setTextColor(alpha << 24 | 0xFFFFFF);
                            }
                        }, lLStatistics, 0.47F, 0.64F));

        findById(this, R.id.mIbClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BehaviorActivity.this, RecyclerViewInsideScrollViewActivity.class));
            }
        });
    }
}

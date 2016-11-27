package com.github.piasy.playground.ylposter.example;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.piasy.playground.ylposter.YLPosterBase;

public class DisplayActivity extends AppCompatActivity {

    @BindView(R2.id.mPoster)
    YLPosterBase mPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainActivity.sIsSnow) {
            setContentView(R.layout.activity_display_snow);
        } else {
            setContentView(R.layout.activity_display_adhere);
        }
        ButterKnife.bind(this);
        mPoster.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPoster.showInfo("piasy", "Piasy", res2Uri(
                        R.drawable.we_chat_mp_qrcode), MainActivity.DEFAULT_STATE);
                mPoster.showBg(MainActivity.sIsSnow ? MainActivity.POSTER_URI
                        : MainActivity.POSTER_URI_ADHERE);
            }
        }, 100);
    }

    private Uri res2Uri(int resId) {
        return Uri.parse("res://" + getPackageName() + "/" + resId);
    }
}

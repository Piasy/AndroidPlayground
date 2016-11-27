package com.github.piasy.playground.ylposter.example;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.piasy.playground.ylposter.PosterAdapter;
import com.github.piasy.playground.ylposter.PosterState;
import com.github.piasy.playground.ylposter.YLPosterBase;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PreviewActivity extends AppCompatActivity {

    @BindView(R2.id.mViewPager)
    ViewPager mViewPager;

    private List<ViewGroup> mPosters = new ArrayList<>();
    private int mSelectedPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                fillViewPager();
            }
        }, 100);
    }

    /**
     * 填充ViewPager
     */
    private void fillViewPager() {
        ViewGroup posterContainer = (ViewGroup) LayoutInflater
                .from(this)
                .inflate(R.layout.preview_poster_snow, mViewPager, false)
                .findViewById(R.id.mPosterContainer);
        YLPosterBase poster = (YLPosterBase) posterContainer.getChildAt(0);
        poster.showInfo("piasy", "Piasy", res2Uri(R.drawable.we_chat_mp_qrcode),
                PosterState.read(getSharedPreferences("poster", MODE_PRIVATE),
                        MainActivity.DEFAULT_STATE));
        mPosters.add(posterContainer);

        posterContainer = (ViewGroup) LayoutInflater
                .from(this)
                .inflate(R.layout.preview_poster_adhere, mViewPager, false)
                .findViewById(R.id.mPosterContainer);
        poster = (YLPosterBase) posterContainer.getChildAt(0);
        poster.showInfo("piasy", "Piasy", res2Uri(R.drawable.we_chat_mp_qrcode),
                PosterState.read(getSharedPreferences("poster", MODE_PRIVATE),
                        MainActivity.DEFAULT_STATE));
        mPosters.add(posterContainer);

        posterContainer = (ViewGroup) LayoutInflater
                .from(this)
                .inflate(R.layout.preview_poster_snow, mViewPager, false)
                .findViewById(R.id.mPosterContainer);
        poster = (YLPosterBase) posterContainer.getChildAt(0);
        poster.showInfo("piasy", "Piasy", res2Uri(R.drawable.we_chat_mp_qrcode),
                PosterState.read(getSharedPreferences("poster", MODE_PRIVATE),
                        MainActivity.DEFAULT_STATE));
        mPosters.add(posterContainer);

        posterContainer = (ViewGroup) LayoutInflater
                .from(this)
                .inflate(R.layout.preview_poster_adhere, mViewPager, false)
                .findViewById(R.id.mPosterContainer);
        poster = (YLPosterBase) posterContainer.getChildAt(0);
        poster.showInfo("piasy", "Piasy", res2Uri(R.drawable.we_chat_mp_qrcode),
                PosterState.read(getSharedPreferences("poster", MODE_PRIVATE),
                        MainActivity.DEFAULT_STATE));
        mPosters.add(posterContainer);

        mViewPager.setAdapter(new PosterAdapter(mPosters));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mSelectedPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(1);
    }

    @OnClick(R2.id.mSave)
    public void save() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            doSave();
                        }
                    }
                });
    }

    private void doSave() {
        YLPosterBase poster = (YLPosterBase) mPosters.get(mSelectedPos).getChildAt(0);
        poster.startSave(getSharedPreferences("poster", MODE_PRIVATE));

        poster.setDrawingCacheEnabled(true);
        poster.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(poster.getDrawingCache());
        poster.setDrawingCacheEnabled(false); // clear drawing cache

        if (bitmap != null) {
            int width = 750;
            int height = 1205;
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, width, height, true);
            bitmap.recycle();

            String fileName = "yolo_" + System.currentTimeMillis();
            String result = MediaStore.Images.Media.insertImage(getContentResolver(), scaled,
                    fileName, "");
            scaled.recycle();
            if (!TextUtils.isEmpty(result)) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private Uri res2Uri(int resId) {
        return Uri.parse("res://" + getPackageName() + "/" + resId);
    }
}

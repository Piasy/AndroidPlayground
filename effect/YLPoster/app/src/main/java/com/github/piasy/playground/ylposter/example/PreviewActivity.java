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
import com.github.piasy.playground.ylposter.YLDefaultPoster;
import com.github.piasy.playground.ylposter.YLPosterAdhere;
import com.github.piasy.playground.ylposter.YLPosterBase;
import com.github.piasy.playground.ylposter.YLPosterSnow;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PreviewActivity extends AppCompatActivity {

    @BindView(R2.id.mViewPager)
    ViewPager mPosterTemplates;

    private List<ViewGroup> mPosters = new ArrayList<>();
    private int mCurrentPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        mPosterTemplates.postDelayed(new Runnable() {
            @Override
            public void run() {
                populatePosters();
            }
        }, 100);
    }

    private void populatePosters() {
        String yoloId = "piasy";
        String name = "Piasy";
        String groupName = "Rxy weekly";
        Uri groupAvatar = Uri.parse(
                "http://yolo-debug.oss-cn-beijing.aliyuncs"
                + ".com/group_headimgs/5e08e9ac-67b2-42ea-a1cc-8b90d533c660.png");
        Uri mGroupQrCode = res2Uri(R.drawable.we_chat_mp_qrcode);
        PosterState prevState = PosterState.read(getSharedPreferences("poster", MODE_PRIVATE),
                MainActivity.DEFAULT_STATE);
        Context context = this;

        ViewGroup defaultPosterContainer = (ViewGroup) LayoutInflater
                .from(context)
                .inflate(R.layout.ui_group_poster_preview_item_default, mPosterTemplates, false)
                .findViewById(R.id.mPosterContainer);
        YLDefaultPoster defaultPoster = (YLDefaultPoster) defaultPosterContainer.getChildAt(0);
        defaultPoster.showInfo(groupName, groupAvatar, mGroupQrCode);
        mPosters.add(defaultPosterContainer);

        ViewGroup snowContainer = (ViewGroup) LayoutInflater
                .from(context)
                .inflate(R.layout.ui_group_poster_preview_item_snow, mPosterTemplates, false)
                .findViewById(R.id.mPosterContainer);
        YLPosterSnow snow = (YLPosterSnow) snowContainer.getChildAt(0);
        snow.showInfo(yoloId, name, mGroupQrCode, prevState);
        mPosters.add(snowContainer);

        ViewGroup adhereContainer = (ViewGroup) LayoutInflater
                .from(context)
                .inflate(R.layout.ui_group_poster_preview_item_adhere, mPosterTemplates, false)
                .findViewById(R.id.mPosterContainer);
        YLPosterAdhere adhere = (YLPosterAdhere) adhereContainer.getChildAt(0);
        adhere.showInfo(yoloId, name, mGroupQrCode, prevState);
        mPosters.add(adhereContainer);

        mPosterTemplates.setAdapter(new PosterAdapter(mPosters));

        mPosterTemplates.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPoster = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mPosterTemplates.setCurrentItem(1);
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
        YLPosterBase poster = (YLPosterBase) mPosters.get(mCurrentPoster).getChildAt(0);
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

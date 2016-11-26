package com.github.piasy.playground.ylposter.example;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.piasy.playground.ylposter.PosterState;
import com.github.piasy.playground.ylposter.YLPosterBase;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.functions.Consumer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PreviewActivity extends AppCompatActivity {

    @BindView(R2.id.mPoster)
    YLPosterBase mPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MainActivity.sIsSnow) {
            setContentView(R.layout.activity_preview_snow);
        } else {
            setContentView(R.layout.activity_preview_adhere);
        }
        ButterKnife.bind(this);

        mPoster.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPoster.showInfo("piasy", "Piasy", res2Uri(R.drawable.we_chat_mp_qrcode),
                        PosterState.read(getSharedPreferences("poster", MODE_PRIVATE),
                                MainActivity.DEFAULT_STATE));
            }
        }, 100);
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
        mPoster.startSave(getSharedPreferences("poster", MODE_PRIVATE));

        mPoster.setDrawingCacheEnabled(true);
        mPoster.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(mPoster.getDrawingCache());
        mPoster.setDrawingCacheEnabled(false); // clear drawing cache

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

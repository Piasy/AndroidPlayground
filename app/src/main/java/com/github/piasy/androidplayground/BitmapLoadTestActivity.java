package com.github.piasy.androidplayground;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/29.
 */
public class BitmapLoadTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_load_test);

        ImageView avatar = (ImageView) findViewById(R.id.mAvatar);
        avatar.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.avatar, 65, 65));

        ImageView avatar1 = (ImageView) findViewById(R.id.mAvatar1);
        avatar1.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.avatar, 130, 130));

        ImageView avatar2 = (ImageView) findViewById(R.id.mAvatar2);
        avatar2.setImageResource(R.drawable.avatar);

        SimpleDraweeView avatar3 = (SimpleDraweeView) findViewById(R.id.mAvatar3);
        //"http://yolo-debug.oss-cn-beijing.aliyuncs
        // .com/headimgs/20c8435a-5d12-11e5-8a0b-00163e000555.jpeg"
        avatar3.setImageURI(Uri.parse(
                "http://cn.gundam.info/series/buildfighters/wp-content/uploads/sites/2/2014/09" +
                        "/wing_130x130.png"));
    }

    private int dip2px(int dip) {
        return (int) (getResources().getDisplayMetrics().density * dip);
    }

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth,
            int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
            int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

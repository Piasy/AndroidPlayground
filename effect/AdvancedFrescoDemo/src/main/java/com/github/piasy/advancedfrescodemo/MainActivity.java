package com.github.piasy.advancedfrescodemo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(getApplication(), config);

        setContentView(R.layout.activity_main);

        showGif();

        showShapedDrawee();

        showInnerDrawee();
    }

    private void showGif() {
        Uri uri = Uri.parse("http://d.hiphotos.baidu"
                + ".com/zhidao/pic/item/0d338744ebf81a4c6a74d7cdd42a6059252da66b.jpg");
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        DraweeController controller =
                Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true).build();
        draweeView.setController(controller);
    }

    private void showShapedDrawee() {
        ShapedDraweeView shapedDraweeView = (ShapedDraweeView) findViewById(R.id.mShapedDraweeView);
        FrescoUtil.loadWithSize(shapedDraweeView,
                Uri.parse("http://img0.bdstatic.com/img/image/shouye/xinshouye/chongwu16830.jpg"),
                300, 300);
    }

    private void showInnerDrawee() {
        SimpleDraweeView innerImageView = (SimpleDraweeView) findViewById(R.id.mInnerImageView);
        innerImageView.setImageURI(
                Uri.parse("http://img0.bdstatic.com/img/image/shouye/xinshouye/chongwu16830.jpg"));
    }
}

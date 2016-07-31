package com.github.piasy.orientationdemo;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private Camera mCamera;
    private boolean mIsFront;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextureView textureView = (TextureView) findViewById(R.id.mSurface);
        textureView.setSurfaceTextureListener(this);
        final TextView textView = (TextView) findViewById(R.id.mText);

        findViewById(R.id.mBtnSwitch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.stopPreview();
                mCamera.release();
                mIsFront = !mIsFront;
                mCamera = Camera.open(mIsFront ? Camera.CameraInfo.CAMERA_FACING_FRONT
                        : Camera.CameraInfo.CAMERA_FACING_BACK);
                try {
                    mCamera.setPreviewTexture(textureView.getSurfaceTexture());
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.mBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                switch (rotation) {
                    case Surface.ROTATION_0:
                        textView.setText("0°");
                        break;
                    case Surface.ROTATION_90:
                        textView.setText("90°");
                        break;
                    case Surface.ROTATION_180:
                        textView.setText("180°");
                        break;
                    case Surface.ROTATION_270:
                        textView.setText("270°");
                        break;
                    default:
                        textView.setText("unknown");
                        break;
                }
                Camera.CameraInfo info = new Camera.CameraInfo();
                Camera.getCameraInfo(mIsFront ? Camera.CameraInfo.CAMERA_FACING_FRONT
                        : Camera.CameraInfo.CAMERA_FACING_BACK, info);
                textView.setText(textView.getText() + "\n" + info.orientation);
            }
        });
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open();

        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}

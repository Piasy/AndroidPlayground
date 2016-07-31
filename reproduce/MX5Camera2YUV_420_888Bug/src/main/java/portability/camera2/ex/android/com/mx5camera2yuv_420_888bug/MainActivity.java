package portability.camera2.ex.android.com.mx5camera2yuv_420_888bug;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements ImageReader.OnImageAvailableListener {

    private TextureView mTextureView;
    private CameraManager mCameraManager;

    private String mBackCameraId;

    private CameraCaptureSession mCaptureSession;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CaptureRequest mPreviewRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextureView = (TextureView) findViewById(R.id.mTextureView);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            initCameraIds();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTextureView.isAvailable()) {
            startPreview();
        } else {
            mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                        int height) {
                    startPreview();
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                        int height) {
                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });
        }
    }

    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;
    private ImageReader mImageReader;

    private void startPreview() {
        try {
            mBackgroundThread = new HandlerThread("PreviewV21Thread");
            mBackgroundThread.start();
            mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
            mImageReader = ImageReader.newInstance(640, 480, ImageFormat.NV21, 2);
            mImageReader.setOnImageAvailableListener(this, mBackgroundHandler);
            mCameraManager.openCamera(mBackCameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(CameraDevice cameraDevice) {
                    createCameraPreviewSession(cameraDevice);
                }

                @Override
                public void onDisconnected(CameraDevice cameraDevice) {

                }

                @Override
                public void onError(CameraDevice cameraDevice, int i) {

                }
            }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void initCameraIds() throws CameraAccessException {
        for (String cameraId : mCameraManager.getCameraIdList()) {
            CameraCharacteristics characteristics =
                    mCameraManager.getCameraCharacteristics(cameraId);

            Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
            if (facing != null) {
                if (facing == CameraCharacteristics.LENS_FACING_BACK) {
                    mBackCameraId = cameraId;
                }
            }
        }
    }

    private void createCameraPreviewSession(CameraDevice cameraDevice) {
        try {
            SurfaceTexture texture = mTextureView.getSurfaceTexture();
            // fix MX5 preview not show bug: http://stackoverflow.com/a/34337226/3077508
            texture.setDefaultBufferSize(640, 480);

            // This is the output Surface we need to start preview.
            Surface surface = new Surface(texture);

            // We set up a CaptureRequest.Builder with the output Surface.
            mPreviewRequestBuilder =
                    cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(surface);
            mPreviewRequestBuilder.addTarget(mImageReader.getSurface());

            // Here, we create a CameraCaptureSession for camera preview.
            cameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(
                                @NonNull CameraCaptureSession cameraCaptureSession) {
                            // When the session is ready, we start displaying the preview.
                            mCaptureSession = cameraCaptureSession;
                            try {
                                // Auto focus should be continuous for camera preview.
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO);
                                // Finally, we start displaying the camera preview.
                                mPreviewRequest = mPreviewRequestBuilder.build();
                                mCaptureSession.setRepeatingRequest(mPreviewRequest, null, mBackgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(
                                @NonNull CameraCaptureSession cameraCaptureSession) {
                        }
                    }, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    int count;

    @Override
    public void onImageAvailable(ImageReader imageReader) {
        Log.d("MainActivity", "onImageAvailable");
        final Image image = imageReader.acquireLatestImage();
        count++;
        if (count == 100) {
            byte[] yuv = new byte[image.getWidth() * image.getHeight() * 3 / 2];
            image2yuv(image, yuv);
            saveRawYuvData(yuv, image.getWidth(), image.getHeight(), "org");
        }
        image.close();
    }

    public static void image2yuv(Image imageIn, byte[] yuvOut) {
        Image.Plane[] planes = imageIn.getPlanes();
        ByteBuffer Y = planes[0].getBuffer();
        ByteBuffer Cr = planes[2].getBuffer();
        int CrPixelStride = planes[2].getPixelStride();
        ByteBuffer Cb = planes[1].getBuffer();
        int CbPixelStride = planes[1].getPixelStride();
        for (int i = 0, size = imageIn.getWidth() * imageIn.getHeight(); i < size; i++) {
            yuvOut[i] = Y.get(i);
        }
        for (int i = 0, size = imageIn.getWidth() * imageIn.getHeight(); i < size / 4; i++) {
            yuvOut[size + i * 2] = Cr.get(i * CrPixelStride);
            yuvOut[size + i * 2 + 1] = Cb.get(i * CbPixelStride);
        }
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dump.y");
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[Y.remaining()];
            Y.get(bytes);
            outputStream.write(bytes);
            outputStream.close();

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dump.cb");
            outputStream = new FileOutputStream(file);
            bytes = new byte[Cb.remaining()];
            Cb.get(bytes);
            outputStream.write(bytes);
            outputStream.close();

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dump.cr");
            outputStream = new FileOutputStream(file);
            bytes = new byte[Cr.remaining()];
            Cr.get(bytes);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRawYuvData(byte[] buf, int width, int height, String prefix) {
        // Save the generated bitmap to a PNG so we can see what it looks like.
        String filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + prefix + "_" + width + "_" + height + ".yuv";
        Log.d("CameraCompat", "Creating " + filename);
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filename);
            outputStream.write(buf);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.github.piasy.mediacodecdemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.github.piasy.mediacodecdemo.VideoEncoderCore.FRAME_RATE;
import static com.github.piasy.mediacodecdemo.VideoEncoderCore.IFRAME_INTERVAL;
import static com.github.piasy.mediacodecdemo.VideoEncoderCore.MIME_TYPE;

@TargetApi(Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mCameraCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(MainActivity.this, CameraCaptureActivity.class));
            }
        });

        TextView tvInfo = (TextView) findViewById(R.id.mTvInfo);

        MediaFormat format = MediaFormat.createVideoFormat(MIME_TYPE, 1080, 720);

        // Set some properties.  Failing to specify some of these can cause the MediaCodec
        // configure() call to throw an unhelpful exception.
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, 100000);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL);

        MediaCodecInfo codecInfo = selectCodec(MIME_TYPE);
        MediaCodecInfo.CodecCapabilities capabilities = codecInfo.getCapabilitiesForType(MIME_TYPE);

        tvInfo.setText(
                "MaxSupportedInstances: " + capabilities.getMaxSupportedInstances() + "\n"
        );
    }

    private static MediaCodecInfo selectCodec(String mimeType) {
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);

            if (!codecInfo.isEncoder()) {
                continue;
            }

            String[] types = codecInfo.getSupportedTypes();
            for (int j = 0; j < types.length; j++) {
                if (types[j].equalsIgnoreCase(mimeType)) {
                    return codecInfo;
                }
            }
        }
        return null;
    }
}

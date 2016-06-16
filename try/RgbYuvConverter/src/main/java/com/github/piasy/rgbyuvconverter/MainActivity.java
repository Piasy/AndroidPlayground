package com.github.piasy.rgbyuvconverter;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class MainActivity extends AppCompatActivity {

    int width = 640;
    int height = 480;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 100, 100: 255, 255, 241
        // 200, 200: 12, 15, 16
        // 300, 300: 93, 107, 110

        //testRgbPngConvert();

        testYuv2Rgba();

        //testRgba2Yub();
    }

    private void testRgbPngConvert() {
        String filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/org_dump_640_480.png";
        ByteBuffer rgb = ByteBuffer.allocate(width * height * 4);
        rgb.position(0);
        RgbYuvConverter.readRgbFromPng(filename, rgb);
        rgb.rewind();

        filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/converted_dump_640_480.png";
        RgbYuvConverter.saveRgb2Png(rgb, filename, width, height);
        rgb.rewind();

        filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/converted_dump_640_480.rgb";
        RgbYuvConverter.saveRawData(rgb, filename);
    }

    private void testYuv2Rgba() {
        String input = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/dump_640_480.yuv";
        try {
            FileInputStream inputStream = new FileInputStream(input);
            ByteBuffer yuv = ByteBuffer.allocateDirect(width * height * 3 / 2);
            int size = inputStream.read(yuv.array());
            Log.d("RgbYuvConverter", "read yuv " + size + " bytes");
            inputStream.close();

            ByteBuffer rgbBuf = ByteBuffer.allocateDirect(width * height * 4);
            RgbYuvConverter.yuv2rgbaBit(yuv, width, height, rgbBuf);
            String filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/converted_dump_640_480.yuv2rgb.bit";
            RgbYuvConverter.saveRawData(rgbBuf, filename);
            yuv.rewind();
            rgbBuf.position(0);

            RgbYuvConverter.yuv2rgbaFloat(yuv, width, height, rgbBuf);
            filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/converted_dump_640_480.yuv2rgb.float";
            RgbYuvConverter.saveRawData(rgbBuf, filename);
            yuv.rewind();
            rgbBuf.position(0);

            RgbYuvConverter.yuv2rgbaJni(yuv, width, height, rgbBuf);
            filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/converted_dump_640_480.yuv2rgb.jni";
            RgbYuvConverter.saveRawData(rgbBuf, filename);
            yuv.rewind();

            IntBuffer rgbIntBuf = IntBuffer.allocate(width * height);
            RgbYuvConverter.yuv2rgbaGpuImage(yuv, width, height, rgbIntBuf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testRgba2Yub() {
        String input = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/converted_dump_640_480.rgb";
        try {
            FileInputStream inputStream = new FileInputStream(input);
            ByteBuffer rgb = ByteBuffer.allocate(width * height * 4);
            int size = inputStream.read(rgb.array());
            Log.d("RgbYuvConverter", "read rgb " + size + " bytes");
            inputStream.close();

            ByteBuffer yuvBuf = ByteBuffer.allocate(width * height * 3 / 2);
            RgbYuvConverter.rgba2yuvBit(rgb, width, height, yuvBuf);
            String filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/converted_dump_640_480.rgb2yuv.bit";
            RgbYuvConverter.saveRawData(yuvBuf, filename);
            rgb.rewind();
            yuvBuf.position(0);

            RgbYuvConverter.rgba2yuvFloat(rgb, width, height, yuvBuf);
            filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/converted_dump_640_480.rgb2yuv.float";
            RgbYuvConverter.saveRawData(yuvBuf, filename);
            rgb.rewind();
            yuvBuf.position(0);

            RgbYuvConverter.rgba2yuvJni(rgb, width, height, yuvBuf);
            filename = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/converted_dump_640_480.rgb2yuv.jni";
            RgbYuvConverter.saveRawData(yuvBuf, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

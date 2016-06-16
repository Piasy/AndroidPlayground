package com.github.piasy.rgbyuvconverter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.github.piasy.aopdemolib.Trace;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import jp.co.cyberagent.android.gpuimage.GPUImageNativeLibrary;

/**
 * Created by Piasy{github.com/Piasy} on 6/2/16.
 *
 * from http://www.equasys.de/colorconversion.html
 * https://en.wikipedia.org/wiki/YCbCr#ITU-R_BT.601_conversion
 *
 * packed mode, and Cr comes first, ref: http://stackoverflow.com/a/12610396/3077508
 */
public class RgbYuvConverter {

    static {
        System.loadLibrary("rgb-yuv-converter-library");
    }

    /**
     * {@code
     * float operation:
     * R = 1.164 * (Y-16)                    + 1.596 * (Cr-128)
     * G = 1.164 * (Y-16) - 0.392 * (Cb-128) - 0.813 * (Cr-128)
     * B = 1.164 * (Y-16) + 2.017 * (Cb-128)
     *
     * transform to bit operation:
     * 1.164 = 1 + (1 >> 3) + (1 >> 5) + (1 >> 7) ~= 1 + (1 >> 3) + (1 >> 5) = 1.15625 , 0.0078125 , 2
     * 1.596 = 1 + (1 >> 1) + (1 >> 4) + (1 >> 5)
     * 0.392 =     (1 >> 2) + (1 >> 3) + (1 >> 6) ~=     (1 >> 1) - (1 >> 3) = 0.375   , 0.017     , 4.352
     * 0.813 =     (1 >> 1) + (1 >> 2) + (1 >> 4) ~= 1 - (1 >> 3) - (1 >> 4) = 0.8125  , 0.0005    , 0.128
     * 2.017 = 2 + (1 >> 6)                       ~= 2                                 , 0.017     , 4
     *
     * Y = Y - 16, Cb = Cb - 128, Cr = Cr - 128
     * Y = Y + (Y >> 3) + (Y >> 5) + (Y >> 7)
     *
     * R = Y                                + Cr + (Cr >> 1) + (Cr >> 4) + (Cr >> 5)
     * G = Y - (Cb >> 2) - (Cb >> 3) - (Cb >> 6) - (Cr >> 1) - (Cr >> 2) - (Cr >> 4)
     * B = Y + (Cb << 1) + (Cb >> 6)
     * }
     *
     * Perf:
     * float           640*480: 458060886 ns
     * bit             640*480: 406342604 ns,   12.7 % faster than float
     * jni (bit)       640*480:  15664062 ns, 2494.1 % faster than bit
     * jni (GpuImage)  640*480:  13116771 ns,   19.4 % faster than jni, but why??
     */
    @Trace
    public static void yuv2rgbaJni(ByteBuffer yuvIn, int width, int height, ByteBuffer rgbaOut) {
        yuv2rgba(width, height, yuvIn.array(), rgbaOut.array());
    }

    @Trace
    public static void yuv2rgbaGpuImage(ByteBuffer yuvIn, int width, int height, IntBuffer rgbaOut) {
        GPUImageNativeLibrary.YUVtoRBGA(yuvIn.array(), width, height, rgbaOut.array());
    }

    @Trace
    public static void yuv2rgbaBit(ByteBuffer yuvIn, int width, int height, ByteBuffer rgbaOut) {
        yuv2rgbaBitOp(width, height, yuvIn.array(), rgbaOut.array());
    }

    @Trace
    public static void yuv2rgbaFloat(ByteBuffer yuvIn, int width, int height, ByteBuffer rgbaOut) {
        yuv2rgbaFloatOp(width, height, yuvIn.array(), rgbaOut.array());
    }

    public static native void yuv2rgba(int width, int height, byte[] yuvIn, byte[] rgbaOut);

    private static void yuv2rgbaBitOp(int width, int height, byte[] yuv, byte[] rgba) {
        int R, G, B;
        int Y, Cb = 0, Cr = 0;
        int cOffset, pixelIndex, rOffset;
        int row = 0, size = width * height;
        for (; row < height; row++) {
            int column = 0;
            int rowDiv2 = row >> 1;
            pixelIndex = row * width;
            for (; column < width; column++) {
                Y = yuv[pixelIndex];
                if (Y < 0) {
                    Y += 255;
                }
                if ((column & 0x1) == 0) {
                    cOffset = size + rowDiv2 * width + column;
                    Cr = yuv[cOffset];
                    Cr = Cr < 0 ? Cr + 127 : Cr - 128;
                    Cb = yuv[cOffset + 1];
                    Cb = Cb < 0 ? Cb + 127 : Cb - 128;
                }
                Y = Y + (Y >> 3) + (Y >> 5);
                R = Y + Cr + (Cr >> 1) + (Cr >> 4) + (Cr >> 5);
                G = Y - (Cb >> 1) + (Cb >> 3) - Cr + (Cr >> 3) + (Cr >> 4);
                B = Y + (Cb << 1);
                rOffset = pixelIndex << 2;
                rgba[rOffset] = clampByte(R);
                rgba[rOffset + 1] = clampByte(G);
                rgba[rOffset + 2] = clampByte(B);
                rgba[rOffset + 3] = clampByte(255);
                pixelIndex++;
            }
        }
    }

    private static void yuv2rgbaFloatOp(int width, int height, byte[] yuv, byte[] rgba) {
        int R, G, B;
        int Y, Cb = 0, Cr = 0;
        int cOffset, pixelIndex;
        for (int row = 0, size = width * height; row < height; row++) {
            for (int column = 0; column < width; column++) {
                pixelIndex = row * width + column;
                Y = yuv[pixelIndex];
                if (Y < 0) {
                    Y += 255;
                }
                if ((column & 0x1) == 0) {
                    cOffset = size + (row >> 1) * width + column;
                    Cr = yuv[cOffset];
                    Cr = Cr < 0 ? Cr + 127 : Cr - 128;
                    Cb = yuv[cOffset + 1];
                    Cb = Cb < 0 ? Cb + 127 : Cb - 128;
                }
                R = (int) (1.164 * Y + 1.596 * Cr);
                G = (int) (1.164 * Y - 0.392 * Cb - 0.813 * Cr);
                B = (int) (1.164 * Y + 2.017 * Cb);
                rgba[4 * pixelIndex] = clampByte(R);
                rgba[4 * pixelIndex + 1] = clampByte(G);
                rgba[4 * pixelIndex + 2] = clampByte(B);
                rgba[4 * pixelIndex + 3] = clampByte(255);
            }
        }
    }

    /**
     * {@code
     * float operation:
     * Y  =  0.257 * R + 0.504 * G + 0.098 * B + 16
     * Cb = -0.148 * R - 0.291 * G + 0.439 * B + 128
     * Cr =  0.439 * R - 0.368 * G - 0.071 * B + 128
     *
     * transform to bit operation:
     * 0.257 = (1 >> 2) + (1 >> 7)
     * 0.504 = (1 >> 1) + (1 >> 8)
     * 0.098 = (1 >> 4) + (1 >> 5) + (1 >> 8)
     * 0.148 = (1 >> 3) + (1 >> 6) + (1 >> 7)
     * 0.291 = (1 >> 2) + (1 >> 5) + (1 >> 7)
     * 0.439 = (1 >> 2) + (1 >> 3) + (1 >> 4) = (1 >> 1) - (1 >> 4)
     * 0.368 = (1 >> 2) + (1 >> 3) - (1 >> 7)
     * 0.071 = (1 >> 4) + (1 >> 7)
     *
     * Y  =  (R >> 2) + (R >> 7)            + (G >> 1) + (G >> 8)            + (B >> 4) + (B >> 5) + (B >> 8) + 16
     * Cb = -(R >> 3) - (R >> 6) - (R >> 7) - (G >> 2) - (G >> 5) - (G >> 7) + (B >> 1) - (B >> 4) + 128
     * Cr =  (R >> 1) - (R >> 4)            - (G >> 2) - (G >> 3) + (G >> 7) - (B >> 4) - (B >> 7) + 128
     * }
     *
     * Perf:
     * float       640*480: 227084323 ns
     * bit         640*480: 181100573 ns, 25.4 % faster than float
     * jni (bit)   640*480:  11113646 ns, 1529.5 % faster than bit
     */
    @Trace
    public static void rgba2yuvJni(ByteBuffer rgbaIn, int width, int height, ByteBuffer yuvOut) {
        rgba2yuv(width, height, rgbaIn.array(), yuvOut.array());
    }

    @Trace
    public static void rgba2yuvBit(ByteBuffer rgbaIn, int width, int height, ByteBuffer yuvOut) {
        rgba2yuvBitOp(width, height, rgbaIn.array(), yuvOut.array());
    }

    @Trace
    public static void rgba2yuvFloat(ByteBuffer rgbaIn, int width, int height, ByteBuffer yuvOut) {
        rgba2yuvFloatOp(width, height, rgbaIn.array(), yuvOut.array());
    }

    public static native void rgba2yuv(int width, int height, byte[] rgbaIn, byte[] yuvOut);

    private static void rgba2yuvBitOp(int width, int height, byte[] rgba, byte[] yuv) {
        int R, G, B;
        int Y, Cb, Cr;
        int cOffset, pixelIndex;
        int row = 0, size = width * height;
        for (; row < height; row++) {
            int column = 0;
            int row_M_width = row * width;
            int row_div2_M_width = (row >> 1) * width;
            for (; column < width; column++) {
                pixelIndex = row_M_width + column << 2;
                R = rgba[pixelIndex];
                if (R < 0) {
                    R += 256;
                }
                G = rgba[pixelIndex + 1];
                if (G < 0) {
                    G += 256;
                }
                B = rgba[pixelIndex + 2];
                if (B < 0) {
                    B += 256;
                }
                Y = (R >> 2) + (R >> 7) + (G >> 1) + (G >> 8) + (B >> 4) + (B >> 5) + (B >> 8) + 16;
                yuv[pixelIndex >> 2] = (byte) Y;
                if ((row & 0x1) == 0 && (column & 0x1) == 0) {
                    cOffset = size + row_div2_M_width + column;
                    Cr = (R >> 1) - (R >> 4) - (G >> 2) - (G >> 3) + (G >> 7) - (B >> 4) - (B >> 7) + 128;
                    yuv[cOffset] = (byte) Cr;
                    Cb = -(R >> 3) - (R >> 6) - (R >> 7) - (G >> 2) - (G >> 5) - (G >> 7) + (B >> 1) - (B >> 4) + 128;
                    yuv[cOffset + 1] = (byte) Cb;
                }
            }
        }
    }

    private static void rgba2yuvFloatOp(int width, int height, byte[] rgba, byte[] yuv) {
        int R, G, B;
        int Y, Cb, Cr;
        int cOffset, pixelIndex;
        for (int row = 0, size = width * height; row < height; row++) {
            for (int column = 0; column < width; column++) {
                pixelIndex = row * width + column << 2;
                R = rgba[pixelIndex];
                if (R < 0) {
                    R += 256;
                }
                G = rgba[pixelIndex + 1];
                if (G < 0) {
                    G += 256;
                }
                B = rgba[pixelIndex + 2];
                if (B < 0) {
                    B += 256;
                }
                Y  = (int) (0.257 * R + 0.504 * G + 0.098 * B + 16);
                yuv[pixelIndex >> 2] = (byte) Y;
                if ((row & 0x1) == 0 && (column & 0x1) == 0) {
                    cOffset = size + (row >> 1) * width + column;
                    Cr = (int) (0.439 * R - 0.368 * G - 0.071 * B + 128);
                    yuv[cOffset] = (byte) Cr;
                    Cb = (int) (-0.148 * R - 0.291 * G + 0.439 * B + 128);
                    yuv[cOffset + 1] = (byte) Cb;
                }
            }
        }
    }

    private static byte clampByte(int val) {
        return (byte) (val < 0 ? 0 : (val > 255 ? 255 : val));
    }

    public static void saveRgb2Png(ByteBuffer rgbIn, String filename, int width, int height) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filename));
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.copyPixelsFromBuffer(rgbIn);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bmp.recycle();
            Log.d("RgbYuvConverter", "saved " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveRawData(ByteBuffer data, String filename) {
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(data.array());
            outputStream.close();
            Log.d("RgbYuvConverter", "saved " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readRgbFromPng(String filename, ByteBuffer rgbOut) {
        Bitmap bitmap = BitmapFactory.decodeFile(filename);
        bitmap.copyPixelsToBuffer(rgbOut);
        bitmap.recycle();
        Log.d("RgbYuvConverter", "read " + filename);
    }
}

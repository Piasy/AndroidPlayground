//
// Created by Piasy on 6/4/16.
//

#include <jni.h>

JNIEXPORT void JNICALL Java_com_github_piasy_rgbyuvconverter_RgbYuvConverter_yuv2rgba(JNIEnv *env,
                                                                                      jobject obj,
                                                                                      jint width,
                                                                                      jint height,
                                                                                      jbyteArray yuvIn,
                                                                                      jbyteArray rgbaOut) {
    int R, G, B;
    int Y, Cb = 0, Cr = 0;
    int cOffset, pixelIndex, rOffset;

    jbyte *yuv = (jbyte *) (*env)->GetPrimitiveArrayCritical(env, yuvIn, 0);
    jbyte *rgba = (jbyte *) ((*env)->GetPrimitiveArrayCritical(env, rgbaOut, 0));

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
            rgba[rOffset] = (jbyte) (R < 0 ? 0 : (R > 255 ? 255 : R));
            rgba[rOffset + 1] = (jbyte) (G < 0 ? 0 : (G > 255 ? 255 : G));
            rgba[rOffset + 2] = (jbyte) (B < 0 ? 0 : (B > 255 ? 255 : B));
            rgba[rOffset + 3] = (jbyte) 0xFF;
        }
    }

    (*env)->ReleasePrimitiveArrayCritical(env, yuvIn, yuv, 0);
    (*env)->ReleasePrimitiveArrayCritical(env, rgbaOut, rgba, 0);
}

JNIEXPORT void JNICALL Java_com_github_piasy_rgbyuvconverter_RgbYuvConverter_rgba2yuv(JNIEnv *env,
                                                                                      jobject obj,
                                                                                      jint width,
                                                                                      jint height,
                                                                                      jbyteArray rgbaIn,
                                                                                      jbyteArray yuvOut) {
    int R, G, B;
    int Y, Cb = 0, Cr = 0;
    int cOffset, pixelIndex;

    jbyte *rgba = (jbyte *) ((*env)->GetPrimitiveArrayCritical(env, rgbaIn, 0));
    jbyte *yuv = (jbyte *) (*env)->GetPrimitiveArrayCritical(env, yuvOut, 0);

    int row = 0, size = width * height;
    for (; row < height; row++) {
        int column = 0;
        for (; column < width; column++) {
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
            Y = (R >> 2) + (R >> 7) + (G >> 1) + (G >> 8) + (B >> 4) + (B >> 5) + (B >> 8) + 16;
            yuv[pixelIndex >> 2] = (jbyte) Y;
            if ((row & 0x1) == 0 && (column & 0x1) == 0) {
                cOffset = size + (row >> 1) * width + column;
                Cr = (R >> 1) - (R >> 4) - (G >> 2) - (G >> 3) + (G >> 7) - (B >> 4) - (B >> 7) +
                     128;
                yuv[cOffset] = (jbyte) Cr;
                Cb = -(R >> 3) - (R >> 6) - (R >> 7) - (G >> 2) - (G >> 5) - (G >> 7) + (B >> 1) -
                     (B >> 4) + 128;
                yuv[cOffset + 1] = (jbyte) Cb;
            }
        }
    }

    (*env)->ReleasePrimitiveArrayCritical(env, rgbaIn, rgba, 0);
    (*env)->ReleasePrimitiveArrayCritical(env, yuvOut, yuv, 0);
}

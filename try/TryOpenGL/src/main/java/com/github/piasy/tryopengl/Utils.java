package com.github.piasy.tryopengl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * Created by Piasy{github.com/Piasy} on 6/7/16.
 */
public class Utils {

    static void sendImage(int width, int height) {
        ByteBuffer mRgbaBuf = ByteBuffer.allocateDirect(width * height * 4);
        mRgbaBuf.position(0);
        GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE,
                mRgbaBuf);
        saveRgb2Bitmap(mRgbaBuf, Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/gl_dump_"
                + width
                + "_"
                + height
                + ".png", width, height);
    }

    static void saveRgb2Bitmap(Buffer buf, String filename, int width, int height) {
        // Save the generated bitmap to a PNG so we can see what it looks like.
        Log.d("TryOpenGL", "Creating " + filename);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filename));
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.copyPixelsFromBuffer(buf);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bmp.recycle();
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
}

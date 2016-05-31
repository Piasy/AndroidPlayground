package com.github.piasy.tryopengl;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Piasy{github.com/Piasy} on 5/30/16.
 */
public abstract class GLRenderer implements GLSurfaceView.Renderer {

    private boolean mFirstDraw;
    private boolean mSurfaceCreated;
    private int mWidth;
    private int mHeight;

    public GLRenderer() {
        mFirstDraw = true;
        mSurfaceCreated = false;
        mWidth = -1;
        mHeight = -1;
    }

    @Override
    public void onSurfaceCreated(GL10 notUsed, EGLConfig config) {
        mSurfaceCreated = true;
        mWidth = -1;
        mHeight = -1;
    }

    @Override
    public void onSurfaceChanged(GL10 notUsed, int width, int height) {
        if (!mSurfaceCreated && width == mWidth && height == mHeight) {
            return;
        }
        mWidth = width;
        mHeight = height;

        onCreate(mWidth, mHeight, mSurfaceCreated);
        mSurfaceCreated = false;
    }

    @Override
    public void onDrawFrame(GL10 notUsed) {
        onDrawFrame(mFirstDraw);

        if (mFirstDraw) {
            mFirstDraw = false;
        }
    }

    public abstract void onCreate(int width, int height, boolean contextLost);

    public abstract void onDrawFrame(boolean firstDraw);
}

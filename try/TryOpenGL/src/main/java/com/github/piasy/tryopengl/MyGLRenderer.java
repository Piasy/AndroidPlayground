package com.github.piasy.tryopengl;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Created by Piasy{github.com/Piasy} on 6/7/16.
 */
public class MyGLRenderer extends GLBaseRenderer {

    private final float[] mMatrixBuffer = new float[16];
    private final float[] mMatrixSecondBuffer = new float[16];

    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    private final Resources mResources;

    private Triangle mTriangle;
    private GLImage mGLImage;

    public MyGLRenderer(Resources resources) {
        mResources = resources;
    }

    private int mScreenWidth;
    private int mScreenHeight;

    @Override
    public void onCreate(int width, int height, boolean contextLost) {
        mScreenWidth = width;
        mScreenHeight = height;

        mTriangle = new Triangle();
        mGLImage = new GLImage(mResources);
        GLES20.glViewport(0, 0, width, height);
        GLES20.glClearColor(0, 0, 0, 1);

        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    @Override
    public void onDrawFrame(boolean firstDraw) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0, 0, 0, 0, 1, 0);
        Matrix.multiplyMM(mMatrixBuffer, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        float angle = 0.09F * (System.currentTimeMillis() % 4000);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1);
        Matrix.multiplyMM(mMatrixSecondBuffer, 0, mMatrixBuffer, 0, mRotationMatrix, 0);

        mTriangle.draw(mMatrixSecondBuffer);
        //mGLImage.draw(mMatrixSecondBuffer);

        /*for (int i = 0; i < 16; i++) {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, mScreenWidth, 0.0f, mScreenHeight, 0, 50);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
        mGLImage.draw(mtrxProjectionAndView);*/
    }

    static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}

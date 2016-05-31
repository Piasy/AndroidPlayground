package com.github.piasy.tryopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.mGLSurfaceView);

        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new MyRenderer());
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    static class MyRenderer extends GLRenderer {

        private final float[] mMatrixBuffer = new float[16];
        private final float[] mMatrixSecondBuffer = new float[16];

        private final float[] mProjectionMatrix = new float[16];
        private final float[] mViewMatrix = new float[16];
        private final float[] mRotationMatrix = new float[16];

        private Triangle mTriangle;

        @Override
        public void onCreate(int width, int height, boolean contextLost) {
            mTriangle = new Triangle();
            GLES20.glViewport(0, 0, width, height);
            GLES20.glClearColor(0, 0, 0, 1);

            float ratio = (float) width / height;
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

        @Override
        public void onDrawFrame(boolean firstDraw) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0, 0, 0, 0, 1, 0);
            Matrix.multiplyMM(mMatrixBuffer, 0, mProjectionMatrix, 0, mViewMatrix, 0);

            float angle = 0.09F * (System.currentTimeMillis() % 4000);
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1);
            Matrix.multiplyMM(mMatrixSecondBuffer, 0, mMatrixBuffer, 0, mRotationMatrix, 0);

            mTriangle.draw(mMatrixSecondBuffer);
        }

        static int loadShader(int type, String shaderCode) {
            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            return shader;
        }
    }

    static class Triangle {
        private static final int COORD_SIZE = 4;
        private static final int COORD_PER_VERTEX = 3;
        private static final float[] COORD = {   // in counterclockwise order:
                0.0f, 0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                0.5f, -0.311004243f, 0.0f  // bottom right
        };
        private static final int VERTEX_COUNT = COORD.length / COORD_PER_VERTEX;
        private static final int VERTEX_STRIDE = COORD_PER_VERTEX * COORD_SIZE;

        private static final float[] COLOR = {
                0.63671875f, 0.76953125f, 0.22265625f, 1.0f
        };

        private static final String VERTEX_SHADER = "attribute vec4 vPosition;\n"
                + "uniform mat4 uMVPMatrix;\n"
                + "void main() {\n"
                + "  gl_Position = uMVPMatrix * vPosition;\n"
                + "}";
        private static final String FRAGMENT_SHADER = "precision mediump float;\n"
                + "uniform vec4 vColor;\n"
                + "void main() {\n"
                + "  gl_FragColor = vColor;\n"
                + "}";

        private final int mProgram;

        private final FloatBuffer mBuffer;

        private int mPositionHandle;
        private int mMatrixHandle;
        private int mColorHandle;

        Triangle() {
            mBuffer = ByteBuffer.allocateDirect(COORD.length * COORD_SIZE)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer()
                    .put(COORD);
            mBuffer.position(0);

            mProgram = GLES20.glCreateProgram();
            int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
            int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
            GLES20.glAttachShader(mProgram, vertexShader);
            GLES20.glAttachShader(mProgram, fragmentShader);
            GLES20.glLinkProgram(mProgram);
        }

        void draw(float[] matrix) {
            GLES20.glUseProgram(mProgram);

            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
            GLES20.glEnableVertexAttribArray(mPositionHandle);
            GLES20.glVertexAttribPointer(mPositionHandle, COORD_PER_VERTEX, GLES20.GL_FLOAT, false,
                    VERTEX_STRIDE, mBuffer);

            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
            GLES20.glUniform4fv(mColorHandle, 1, COLOR, 0);

            mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
            GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, matrix, 0);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);

            GLES20.glDisableVertexAttribArray(mPositionHandle);
        }
    }
}

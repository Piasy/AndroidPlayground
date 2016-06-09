package com.github.piasy.tryopengl;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Piasy{github.com/Piasy} on 6/7/16.
 */
public class Triangle {
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
    private int mColorHandle;
    private int mMatrixHandle;

    Triangle() {
        mBuffer = ByteBuffer.allocateDirect(COORD.length * COORD_SIZE)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(COORD);
        mBuffer.position(0);

        mProgram = GLES20.glCreateProgram();
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        mMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    private int count;

    void draw(float[] matrix) {
        GLES20.glUseProgram(mProgram);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORD_PER_VERTEX, GLES20.GL_FLOAT, false,
                VERTEX_STRIDE, mBuffer);

        GLES20.glUniform4fv(mColorHandle, 1, COLOR, 0);

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, matrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VERTEX_COUNT);
        count++;
        if (count == 10) {
            Utils.sendImage(1000, 1000);
        }

        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

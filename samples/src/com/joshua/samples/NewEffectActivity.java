package com.joshua.samples;

import com.joshua.glframeworklite.ShaderProgram;
import com.joshua.glframeworklite.VertexArray;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glViewport;

/**
 * @Description:
 * @Author JoshuaWong
 * @Date 2019/1/16 23:04
 * @Version 1.0
 */
public class NewEffectActivity extends BaseGLActivity {
    private static final float[] vertices = new float[]{
            -0.5f, -0.5f, 0f,
            -0.5f, 0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
    };

    private ShaderProgram mProgram;
    private VertexArray mVertexArray;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0f, 1f, 1f, 1f);

        mProgram = new ShaderProgram(this, "default.vert", "default.frag");

        mVertexArray = new VertexArray(mProgram);
        mVertexArray.addVertices(vertices);
        mVertexArray.bind(mProgram.getAttributeHandle("aPosition"), 3, 0);
        ShaderProgram.checkError();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        mProgram.useProgram();

        mVertexArray.begin();
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
        mVertexArray.end();
    }
}

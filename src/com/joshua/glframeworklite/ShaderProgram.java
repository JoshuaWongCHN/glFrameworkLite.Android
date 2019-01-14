package com.joshua.glframeworklite;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import com.joshua.glframeworklite.helper.ShaderHelper;
import com.joshua.glframeworklite.utils.Debugger;
import com.joshua.glframeworklite.utils.FileUtil;

import static android.opengl.GLES20.GL_NO_ERROR;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform2fv;
import static android.opengl.GLES20.glUseProgram;
import static com.joshua.glframeworklite.Constants.INVALID_TEXTURE;

/**
 * @Description:
 * @Author JoshuaWong
 * @Date 2019/1/13 10:23
 * @Version 1.0
 */
public class ShaderProgram {
    private static final String TAG = "ShaderProgram";

    protected final int mProgram;

    /**
     * @param context
     * @param vertexFileName   顶点着色器文件名
     * @param fragmentFileName 片段着色器文件名
     */
    public ShaderProgram(Context context, String vertexFileName, String fragmentFileName) {
        mProgram = ShaderHelper.buildProgram(FileUtil.readFileFromAssets(context, vertexFileName),
                FileUtil.readFileFromAssets(context, fragmentFileName));
    }

    public int getAttributeHandle(String name) {
        int handle = glGetAttribLocation(mProgram, name);
        if (handle < 0) {
            Log.e(TAG, "Cannot find attribute " + name + "!");
        }
        return handle;
    }

    private int getUniformHandle(String name) {
        int handle = glGetUniformLocation(mProgram, name);
        if (handle < 0) {
            Log.e(TAG, "Cannot find uniform " + name + "!");
        }
        return handle;
    }

    public void setUniform1f(String uniform, float value) {
        int handle = getUniformHandle(uniform);
        glUniform1f(handle, value);
        checkError();
    }

    public void setUniform2fv(String uniform, float[] values) {
        if (values.length < 2) {
            return;
        }
        int handle = getUniformHandle(uniform);
        glUniform2fv(handle, 1, values, 0);
        checkError();
    }

    public void setTexture2D(String uniform, int texture, int index) {
        if (texture == INVALID_TEXTURE)
            if (index < 0 || index > 31) {
                Log.e(TAG, "Texture index out of range!");
            }
        int handle = glGetUniformLocation(mProgram, uniform);
        glActiveTexture(GL_TEXTURE0 + index);
        glBindTexture(GL_TEXTURE_2D, texture);
        glUniform1i(handle, GL_TEXTURE0 + index);
    }

    public void useProgram() {
        glUseProgram(mProgram);
    }

    protected void checkError() {
        int error = GLES20.glGetError();
        if (error != GL_NO_ERROR) {
            Throwable t = new Throwable();
            Debugger.e(TAG, "GL error: " + GLU.gluErrorString(error), t);
        }
    }
}

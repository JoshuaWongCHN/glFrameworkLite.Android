package com.joshua.glframeworklite;

import com.joshua.glframeworklite.utils.Debugger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.GL_ARRAY_BUFFER;
import static android.opengl.GLES30.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES30.GL_FLOAT;
import static android.opengl.GLES30.GL_STATIC_DRAW;
import static android.opengl.GLES30.glBindBuffer;
import static android.opengl.GLES30.glBindVertexArray;
import static android.opengl.GLES30.glBufferData;
import static android.opengl.GLES30.glEnableVertexAttribArray;
import static android.opengl.GLES30.glGenBuffers;
import static android.opengl.GLES30.glGenVertexArrays;
import static android.opengl.GLES30.glVertexAttribPointer;

/**
 * @Description:
 * @Author JoshuaWong
 * @Date 2019/1/13 10:40
 * @Version 1.0
 */
public class VertexArray {
    private static final String TAG = "VertexArray";

    private int mVAOId = Constants.INVALID_BUFFER;
    private int mVerticesVBOId = Constants.INVALID_BUFFER;
    private int mIndicesVBOId = Constants.INVALID_BUFFER;
    private ShaderProgram mProgram;

    public VertexArray(ShaderProgram program) {
        mProgram = program;
    }

    public void addVertices(float[] vertices) {
        if (mProgram == null) {
            return;
        }
        mProgram.useProgram();

        if (mVerticesVBOId == Constants.INVALID_BUFFER) {
            int[] vboIds = IdPool.getBufferId();
            glGenBuffers(1, vboIds, 0);
            if (vboIds[0] == Constants.INVALID_BUFFER) {
                Debugger.e(TAG, "Generate VBO failed!");
                return;
            }
            mVerticesVBOId = vboIds[0];
            IdPool.recycle(vboIds);
        }

        FloatBuffer verticesBuffer = ByteBuffer.allocateDirect(Float.BYTES * vertices.length)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
        glBindBuffer(GL_ARRAY_BUFFER, mVerticesVBOId);

        glBufferData(GL_ARRAY_BUFFER, Float.BYTES * vertices.length, verticesBuffer, GL_STATIC_DRAW);
        ShaderProgram.checkError();

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        ShaderProgram.checkError();

    }

    public void addIndices(byte[] indices) {
        if (mProgram == null) {
            return;
        }
        mProgram.useProgram();

        if (mIndicesVBOId == Constants.INVALID_BUFFER) {
            int[] vboIds = IdPool.getBufferId();
            glGenBuffers(1, vboIds, 0);
            if (vboIds[0] == Constants.INVALID_BUFFER) {
                Debugger.e(TAG, "Generate VBO failed!");
                return;
            }
            mIndicesVBOId = vboIds[0];
            IdPool.recycle(vboIds);
        }
        ByteBuffer indicesBuffer = ByteBuffer.allocateDirect(indices.length)
                .order((ByteOrder.nativeOrder()));
        indicesBuffer.put(indices);
        indicesBuffer.position(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIndicesVBOId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer.capacity(), indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void bind(int positionHandle, int componentCount, int stride) {
        if (mProgram == null) {
            return;
        }
        mProgram.useProgram();

        if (mVAOId == Constants.INVALID_BUFFER) {
            int[] vaoIds = IdPool.getBufferId();
            glGenVertexArrays(1, vaoIds, 0);
            if (vaoIds[0] == Constants.INVALID_BUFFER) {
                Debugger.e(TAG, "Generate VAO failed!");
                return;
            }
            mVAOId = vaoIds[0];
            IdPool.recycle(vaoIds);
        }
        glBindVertexArray(mVAOId);

        glBindBuffer(GL_ARRAY_BUFFER, mVerticesVBOId);

        if (mIndicesVBOId != Constants.INVALID_BUFFER) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIndicesVBOId);
        }
        glVertexAttribPointer(positionHandle, componentCount, GL_FLOAT, false, stride * Float.BYTES, 0);
        glEnableVertexAttribArray(positionHandle);
//        glBindVertexArray(0);
    }

    public void begin() {
        if (mProgram == null) {
            return;
        }
        mProgram.useProgram();
        glBindVertexArray(mVAOId);
    }

    public void end() {
        glBindVertexArray(0);
    }
}


package com.joshua.glframeworklite;

import com.joshua.glframeworklite.utils.Debugger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES30.*;

/**
 * @Description:
 * @Author JoshuaWong
 * @Date 2019/1/13 10:40
 * @Version 1.0
 */
public class VertexArray {
    private static final String TAG = "VertexArray";

    private int mVAOId, mVerticesVBOId, mIndicesVBOId;

    public void addVertices(float[] vertices) {
        if (mVerticesVBOId != Constants.INVALID_BUFFER) {
            int[] vboIds = IdPool.getBufferId();
            glGenVertexArrays(1, vboIds, 0);
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
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer.capacity(), verticesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void addIndices(byte[] indices) {
        if (mIndicesVBOId != Constants.INVALID_BUFFER) {
            int[] vboIds = IdPool.getBufferId();
            glGenVertexArrays(1, vboIds, 0);
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

    public void bind() {
        if (mVAOId != Constants.INVALID_BUFFER) {
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
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mIndicesVBOId);
    }
}


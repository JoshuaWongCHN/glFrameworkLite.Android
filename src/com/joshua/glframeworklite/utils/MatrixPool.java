package com.joshua.glframeworklite.utils;

import android.opengl.Matrix;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Description:
 * @Author JoshuaWong
 * @Date 2019/1/14 23:18
 * @Version 1.0
 */
public class MatrixPool {
    private static final int POOL_LIMIT = 1024;
    private static final Deque<float[]> mMatrixList = new ArrayDeque<>();

    public static synchronized float[] getIdentityM4fv(){
        float[] matrix = mMatrixList.size() > 0 ? mMatrixList.remove() : new float[16];
        Matrix.setIdentityM(matrix, 0);
        return matrix;
    }

    public static synchronized void recycle(float[] matrix){
        if(matrix == null){
            return;
        }
        if(matrix.length!=16){
            return;
        }

        if (mMatrixList.size() < POOL_LIMIT) {
            mMatrixList.offer(matrix);
        }
    }

    public static void clear(){
        mMatrixList.clear();
    }
}

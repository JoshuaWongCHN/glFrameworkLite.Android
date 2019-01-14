package com.joshua.glframeworklite;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Description:
 * @Author JoshuaWong
 * @Date 2019/1/13 10:24
 * @Version 1.0
 */
public class IdPool {
    private static final int POOL_LIMIT = 1024;
    private static final Deque<int[]> mIdList = new ArrayDeque<>();

    public static synchronized int[] getTextureId() {
        int[] id = mIdList.size() > 0 ? mIdList.remove() : new int[1];
        id[0] = Constants.INVALID_TEXTURE;
        return id;
    }

    public static synchronized int[] getBufferId() {
        int[] id = mIdList.size() > 0 ? mIdList.remove() : new int[1];
        id[0] = Constants.INVALID_BUFFER;
        return id;
    }

    public static synchronized void recycle(int[] id) {
        if(id == null){
            return;
        }
        if(id.length!=1){
            return;
        }
        
        if (mIdList.size() < POOL_LIMIT) {
            mIdList.offer(id);
        }
    }

    public static synchronized void clear() {
        mIdList.clear();
    }
}

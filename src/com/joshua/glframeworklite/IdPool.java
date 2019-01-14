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
    private static final Deque<int[]> mIdsList = new ArrayDeque<>();
    private static final int POOL_LIMIT = 1024;

    public static synchronized int[] getTextureIds() {
        int[] ids = mIdsList.size() > 0 ? mIdsList.remove() : new int[1];
        ids[0] = Constants.INVALID_TEXTURE;
        return ids;
    }

    public static synchronized int[] getBufferIds() {
        int[] ids = mIdsList.size() > 0 ? mIdsList.remove() : new int[1];
        ids[0] = Constants.INVALID_BUFFER;
        return ids;
    }

    public static synchronized void recycle(int[] ids) {
        if(id == null){
            return;
        }
        if(id.length!=1){
            return;
        }
        
        if (mIdsList.size() < POOL_LIMIT) {
            mIdsList.offer(ids);
        }
    }

    public static synchronized void clear() {
        mIdsList.clear();
    }
}

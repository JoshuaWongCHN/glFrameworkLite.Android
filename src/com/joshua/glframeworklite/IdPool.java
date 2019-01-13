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
    private static final int MAX_SIZE = 128;

    public static int[] getTextureIds() {
        int[] ids = mIdsList.size() > 0 ? mIdsList.remove() : new int[1];
        ids[0] = Constants.INVALID_TEXTURE;
        return ids;
    }

    public static int[] getBufferIds() {
        int[] ids = mIdsList.size() > 0 ? mIdsList.remove() : new int[1];
        ids[0] = Constants.INVALID_BUFFER;
        return ids;
    }

    public static void recycle(int[] ids) {
        if (mIdsList.size() < MAX_SIZE) {
            mIdsList.addFirst(ids);
        }
    }

    public static void clear() {
        mIdsList.clear();
    }
}

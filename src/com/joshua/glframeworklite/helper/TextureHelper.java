package com.joshua.glframeworklite.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import com.joshua.glframeworklite.IdPool;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_MIRRORED_REPEAT;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static com.joshua.glframeworklite.Constants.INVALID_TEXTURE;

/**
 * @Description:
 * @Author JoshuaWong
 * @Date 2019/1/13 10:23
 * @Version 1.0
 */
public class TextureHelper {
    private static final String TAG = "TextureHelper";

    /**
     * 加载图形文件到OpenGL，生成纹理ID，用作纹理引用
     */
    public static int loadTexture(Context context, int resId) {
        final int[] textureObjectIds = IdPool.getTextureId();
        //创建纹理对象
        glGenTextures(1, textureObjectIds, 0);
        if (textureObjectIds[0] == INVALID_TEXTURE) {
            Log.e(TAG, "Could not generate a new OpenGL texture object.");
            return INVALID_TEXTURE;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //取图片的原始数据，非缩放版本
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        if (bitmap == null) {
            Log.e(TAG, "resource " + resId + " could not be decoded.");
            glDeleteTextures(1, textureObjectIds, 0);
            return INVALID_TEXTURE;
        }
        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
        //读入bitmap定义的位图数据，并把它复制到当前绑定的纹理对象
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        //数据已经加载进了OpenGL，立即释放数据
        bitmap.recycle();
        //生成所有必要级别的MIP贴图
        glGenerateMipmap(GL_TEXTURE_2D);
        //完成纹理加载后，解除与这个纹理的绑定，防止其它纹理方法调用意外地改变这个纹理。传递0值即为解除绑定
        glBindTexture(GL_TEXTURE_2D, 0);

        IdPool.recycle(textureObjectIds);
        return textureObjectIds[0];
    }
}

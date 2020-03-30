package com.example.glidedownload.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * author:lgh on 2019-11-08 09:17
 */
public class BitmapUtils {

    // 5. Drawable----> Bitmap
    public static Bitmap DrawableToBitmap(Drawable drawable) {

        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();

        drawable.setBounds(0, 0, width, heigh);

        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Context context,int resId, int quality) {
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(),resId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return baos.toByteArray();
    }
}

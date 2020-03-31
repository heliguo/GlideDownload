package com.example.glidedownload.utils;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.signature.EmptySignature;
import com.bumptech.glide.util.Util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.bumptech.glide.load.Key.STRING_CHARSET_NAME;

/**
 * author:lgh on 2020-03-31 14:50
 * Glide key 生成规则
 */
public class GlideKeyUitils {

    /**
     * Glide缓存存储路径：/data/data/your_packagexxxxxxx/cache/image_manager_disk_cache
     * Glide文件名生成规则函数 : 4.0+ 版本
     *
     * @param url 图片地址url
     * @return 返回图片在磁盘缓存的key值
     */
    private static String getGlide4_SafeKey(String url) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            EmptySignature signature = EmptySignature.obtain();
            signature.updateDiskCacheKey(messageDigest);
            new GlideUrl(url).updateDiskCacheKey(messageDigest);
            String safeKey = Util.sha256BytesToHex(messageDigest.digest());
            return safeKey + ".0";
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Glide缓存存储路径：/data/data/your_packagexxxxxxx/cache/image_manager_disk_cache
     * Glide文件名生成规则函数 : 3.0+ 版本
     *
     * @param url    传入您的图片地址url
     * @param width  设备屏幕分辨率的宽度 eg : 1080
     * @param height 设备屏幕分辨率的高度 eg : 1920
     * @return
     */
    private String getGlide3_SafeKey(String url, int width, int height) {
        byte[] dimensions = ByteBuffer.allocate(8)
                .putInt(width)
                .putInt(height)
                .array();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            EmptySignature signature = EmptySignature.obtain();
            signature.updateDiskCacheKey(messageDigest);
            messageDigest.update(url.getBytes(STRING_CHARSET_NAME));
            messageDigest.update(dimensions);
            messageDigest.update("".getBytes(STRING_CHARSET_NAME));
            messageDigest.update("ImageVideoBitmapDecoder.com.bumptech.glide.load.resource.bitmap".getBytes(STRING_CHARSET_NAME));
            messageDigest.update("FitCenter.com.bumptech.glide.load.resource.bitmap".getBytes(STRING_CHARSET_NAME));
            messageDigest.update("BitmapEncoder.com.bumptech.glide.load.resource.bitmap".getBytes(STRING_CHARSET_NAME));
            messageDigest.update("".getBytes(STRING_CHARSET_NAME));
            String safeKey = Util.sha256BytesToHex(messageDigest.digest());
            return safeKey + ".0";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

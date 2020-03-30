package com.example.glidedownload.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * author:lgh on 2019-11-08 11:08
 *
 * 自定义模块必须配置manifest
 * <manifest>
 *
 *     ...
 *
 *     <application>
 *
 *         <meta-data
 *             android:name="com.example.glidetest.MyGlideModule" 类全路径
 *             android:value="GlideModule" /> 固定写法
 *
 *         ...
 *
 *     </application>
 * </manifest>
 * ————————————————
 *
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    public static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, DISK_CACHE_SIZE));

    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ProgressInterceptor progressInterceptor = new ProgressInterceptor();
        builder.addInterceptor(progressInterceptor);
        OkHttpClient client = builder.build();
        OkHttpGlideUrlLoader.Factory factory = new OkHttpGlideUrlLoader.Factory(client);
        //替换通讯组件
        registry.replace(GlideUrl.class, InputStream.class, factory);
    }

    /**
     * 报错处理
     * "void com.bumptech.glide.module.RegistersComponents.registerComponents(android.content.Context, com.bumptech.glide.Glide, com.bumptech.glide.Registry)"
     *
     * @return false
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

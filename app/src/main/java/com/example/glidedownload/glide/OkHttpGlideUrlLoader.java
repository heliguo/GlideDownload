package com.example.glidedownload.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * author:lgh on 2019-11-08 13:41
 */
public class OkHttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private OkHttpClient okHttpClient;


    public OkHttpGlideUrlLoader() {
        this(null);
    }

    public OkHttpGlideUrlLoader(OkHttpClient client) {
        this.okHttpClient = client;
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int width, int height, @NonNull Options options) {
        return new LoadData<>(glideUrl, new OkHttpFetcher(okHttpClient, glideUrl));
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return true;
    }

    //工厂
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {

        private OkHttpClient client;

        public Factory(OkHttpClient client) {
            this.client = client;
        }

        private synchronized OkHttpClient getOkHttpClient() {
            if (client == null) {
                client = new OkHttpClient();
            }
            return client;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            //将OkHttpClient传入OkHttpGlideUrlLoader构造方法
            return new OkHttpGlideUrlLoader(getOkHttpClient());
        }

        @Override
        public void teardown() {

        }
    }


}
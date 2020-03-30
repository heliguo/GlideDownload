package com.example.glidedownload.glide;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

/**
 * author:lgh on 2019-11-08 10:34
 */
public class DownloadImageTarget implements Target<File> {

    private static final String TAG = "DownloadImageTarget";


    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {

    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {

    }

    @Override
    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
        Log.d(TAG, resource.getPath());//打印地址
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }

    @Override
    public void getSize(@NonNull SizeReadyCallback cb) {
        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    @Override
    public void removeCallback(@NonNull SizeReadyCallback cb) {

    }

    @Override
    public void setRequest(@Nullable Request request) {

    }

    @Nullable
    @Override
    public Request getRequest() {
        return null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}

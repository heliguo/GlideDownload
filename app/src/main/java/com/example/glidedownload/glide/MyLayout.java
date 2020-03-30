package com.example.glidedownload.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * author:lgh on 2019-11-08 10:04
 */
public class MyLayout extends FrameLayout {

    private CustomViewTarget<MyLayout, Drawable> viewTarget;

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this为当前实例
        viewTarget = new CustomViewTarget<MyLayout, Drawable>(this) {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                MyLayout myLayout = getView();
                myLayout.setImageAsBackground(resource);
            }

            @Override
            protected void onResourceCleared(@Nullable Drawable placeholder) {

            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {

            }

        };
    }

    public CustomViewTarget<MyLayout, Drawable> getTarget() {
        return viewTarget;
    }

    public void setImageAsBackground(Drawable resource) {
        setBackground(resource);
    }

}

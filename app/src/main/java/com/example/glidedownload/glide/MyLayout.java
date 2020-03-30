package com.example.glidedownload.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.glidedownload.R;
import com.example.glidedownload.utils.BitmapUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author:lgh on 2019-11-08 10:04
 */
public class MyLayout extends FrameLayout {

    private CustomViewTarget<MyLayout, Drawable> viewTarget;

    public MyLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        //this为当前实例
        final View inflate = inflate(context, R.layout.glide_view_target_layout, this);
        viewTarget = new CustomViewTarget<MyLayout, Drawable>(this) {

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                SubsamplingScaleImageView scaleImageView = inflate.findViewById(R.id.glide_subsampling_scale_imageview);
                Bitmap bitmap = BitmapUtils.DrawableToBitmap(resource);
                scaleImageView.setImage(ImageSource.bitmap(bitmap));
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

}

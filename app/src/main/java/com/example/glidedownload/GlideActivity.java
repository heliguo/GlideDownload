package com.example.glidedownload;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.glidedownload.glide.GlideApp;
import com.example.glidedownload.glide.MyLayout;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * author:lgh on 2019-11-08 08:57
 */
public class GlideActivity extends AppCompatActivity {

    private SubsamplingScaleImageView scaleImageView;
    private MyLayout myLayout;
    private String url = "http://192.168.20.147:8080/app/book.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        scaleImageView = findViewById(R.id.glide_subsampling_scale_imageview);
        myLayout = findViewById(R.id.background);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.load_failed)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new BlurTransformation(), new GrayscaleTransformation());

        CustomViewTarget<MyLayout, Drawable> target = myLayout.getTarget();

        //测试自定义模块OKHTTP
        GlideApp.with(GlideActivity.this)
                .load(url)
                .skipMemoryCache(true)
                .into(target);
    }
}

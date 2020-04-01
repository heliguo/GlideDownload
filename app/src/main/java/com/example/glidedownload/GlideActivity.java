package com.example.glidedownload;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.glidedownload.glide.DownloadImageTarget;
import com.example.glidedownload.glide.GlideApp;
import com.example.glidedownload.glide.MyLayout;
import com.example.glidedownload.glide.ProgressInterceptor;
import com.example.glidedownload.glide.ProgressListener;
import com.example.glidedownload.utils.FixFileNameUtils;
import com.example.glidedownload.utils.GlideKeyUitils;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * author:lgh on 2019-11-08 08:57
 */
public class GlideActivity extends AppCompatActivity {

    private ProgressDialog mProgressBar;
    private MyLayout myLayout;
    private String url = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        myLayout = findViewById(R.id.background);
        mProgressBar = new ProgressDialog(this);
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressBar.setMessage("加载中...");
        mProgressBar.setMax(100);
    }

    public void downloadImage(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Context context = getApplicationContext();

//                    FutureTarget<Drawable> submit = Glide.with(context)
//                            .load(url)
//                            .submit();
//                    submit.get();

                    FutureTarget<File> target = Glide.with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", "run: " + imageFile.getPath());
                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void showPic(View view) {
        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                mProgressBar.setProgress(progress);
            }
        });
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.load_failed)
                .transform(new BlurTransformation(), new GrayscaleTransformation());

        final CustomViewTarget<MyLayout, Drawable> target = myLayout.getTarget();


//        //测试自定义模块OKHTTP
//        GlideApp.with(GlideActivity.this)
//                .load(url)
////                .apply(options)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .skipMemoryCache(true)
//                .into(target);

        DownloadImageTarget downloadImageTarget = GlideApp.with(GlideActivity.this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .downloadOnly(new DownloadImageTarget() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        mProgressBar.show();
                    }

                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        super.onResourceReady(resource, transition);
                        mProgressBar.dismiss();
                        String filePath = resource.getAbsolutePath();
                        if (filePath.contains(GlideKeyUitils.getGlide4_SafeKey(url))) {
                            int loc = url.lastIndexOf("/");
                            int len = url.length();
                            String newFileName = url.substring(loc + 1, len);
                            String newFilePath = filePath.replaceAll(GlideKeyUitils.getGlide4_SafeKey(url), newFileName);
                            String s = FixFileNameUtils.copyPic(filePath, newFilePath);
                        }
                        ProgressInterceptor.removeListener(url);
                        Glide.with(GlideActivity.this)
                                .load(resource)
                                .into(target);

                    }
                });

    }

    public void target(View view) {
        DownloadImageTarget downloadImageTarget = Glide.with(this)
                .load(url)
                .downloadOnly(new DownloadImageTarget());
    }

    public void listener(View view) {
        Glide.with(this).load(url)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(myLayout.getTarget());
    }
}

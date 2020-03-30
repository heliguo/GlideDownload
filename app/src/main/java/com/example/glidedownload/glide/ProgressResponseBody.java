package com.example.glidedownload.glide;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * author:lgh on 2019-11-12 13:32
 */
public class ProgressResponseBody extends ResponseBody {

    private static final String TAG = "ProgressResponseBody";

    private BufferedSource bufferedSource;

    private ResponseBody responseBody;

    private ProgressListener progressListener;

    private String url;


    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.url = url;
        this.responseBody = responseBody;
        progressListener = ProgressInterceptor.listenerMap.get(url);
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @NotNull
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

        long totalBytesRead = 0;

        int currentProgress;

        ProgressSource(@NotNull Source delegate) {
            super(delegate);
        }

        @Override
        public long read(@NotNull Buffer sink, long byteCount) throws IOException {
            long byteRead = super.read(sink, byteCount);//正在下载量
            long fullLength = responseBody.contentLength();//下载总量
            Log.e(TAG, "responseBody.contentLength: " + fullLength);
            if (byteCount == -1) {
                totalBytesRead = fullLength;
            } else {
                totalBytesRead += byteRead;
            }
            int progress = (int) (100f * totalBytesRead / fullLength);
            Log.e(TAG, "download progress is " + progress);
            if (progressListener != null && progress != currentProgress) {
                progressListener.onProgress(progress);
            }
            if (progressListener != null && totalBytesRead == fullLength) {
                ProgressInterceptor.listenerMap.remove(url);
                progressListener = null;//下载完成
            }
            currentProgress = progress;
            return byteCount;
        }
    }

}

package com.superli.demo;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Model for MVVMActivity.
 *
 * @author SuperLi
 * @since 2019-12-05 23:16
 */
public class Model {
    public interface OnDownloadListener {
        void onDownloadSuccess();
        void onDownloading(int progress);
        void onDownloadFailed();
    }

    public static void downloadFile(final String url, final String dir, final OnDownloadListener listener) {
        final Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onDownloadFailed();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;

                FileOutputStream fos = null;
                String outPath = getStoragePath(dir);

                try {
                    long total = response.body().contentLength();
                    is = response.body().byteStream();
                    File file = new File(outPath, getDownloadName(url));
                    fos = new FileOutputStream(file);

                    byte[] buffer = new byte[1024 * 5];
                    int len = 0;
                    long count = 0;
                    int progress = 0;
                    while ((len = is.read(buffer)) != -1) {
                       fos.write(buffer, 0, len);
                       count += len;
                       progress = (int) (count / total * 100);
                       if (listener != null) {
                           listener.onDownloading(progress);
                       }
                    }
                    fos.flush();

                    if (listener != null) {
                        listener.onDownloadSuccess();
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onDownloadFailed();
                    }
                } finally {
                    if (is != null) {
                        is.close();
                    }

                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        });
    }

    private static String getDownloadName(String url) {
        if (TextUtils.isEmpty(url)) {
            return "default";
        }
        return url.substring(url.lastIndexOf("/") + 1);
    }

    private static String getStoragePath(String path) throws IOException {
        if (TextUtils.isEmpty(path)) { // 路径为null或空串
            return Environment.getExternalStorageDirectory().getCanonicalPath();
        }

        File outFile = new File(Environment.getExternalStorageDirectory(), path);
        outFile.mkdirs();
        return outFile.getCanonicalPath();
    }
}

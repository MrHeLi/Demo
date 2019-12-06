package com.superli.demo;

import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableInt;

/**
 * ViewModel of MVVMActivity.
 *
 * @author SuperLi
 * @since 2019-12-05 23:52
 */
public class ViewModel implements Model.OnDownloadListener {
    private static final String TAG = "ViewModel";

    private static final String URL = "http://172.31.6.55/test_for_live.mp4";

    public ObservableInt mDownloadProgress = new ObservableInt(0);

    public void onClick(View view) {
        Log.i(TAG, "onClick");
        Model.downloadFile(URL, "/mvvm/", this);
    }

    @Override
    public void onDownloadSuccess() {
        Log.i(TAG, "onDownloadSuccess");
    }

    @Override
    public void onDownloading(int progress) {
        mDownloadProgress.set(progress);
    }

    @Override
    public void onDownloadFailed() {
        Log.i(TAG, "onDownloadFailed");
    }
}

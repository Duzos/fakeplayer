package com.duzo.fakeplayers.util.skins;

import com.duzo.fakeplayers.util.downloader.DownloaderThread;

public class SkinDownloadCallback implements DownloaderThread.DownloaderCallback {
    public SkinDownloadCallback() {

    }
    @Override
    public void onComplete() {
        System.out.println("Download completed!");
    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void onProgress(int progress) {

    }
}

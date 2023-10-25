package com.duzo.fakeplayers.util.downloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class DownloaderThread extends Thread implements Runnable{
    private String url;
    private String filepath;
    private DownloaderCallback callback;
    public DownloaderThread() {
        this.callback = null;
    }
    public DownloaderThread(DownloaderCallback callback, String url, String filepath) {
        this.callback = callback;
        this.url = url;
        this.filepath = filepath;
    }

    public DownloaderThread setCallback(DownloaderCallback callback) {
        this.callback = callback;
        return this;
    }

    public DownloaderThread setUrl(String url){
        this.url = url;
        return this;
    }

    public DownloaderThread setFilepath(String filepath) {
        this.filepath = filepath;
        return this;
    }

    public void download() {
        super.start();
    }

    public void run() {
        try {
            URL url = new URL(this.url);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            long total = urlConnection.getContentLengthLong();
            int count;

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(filepath);

            byte data[] = new byte[4096];
            long current = 0;

            while ((count = input.read(data)) != -1) {
                current += count;
                output.write(data, 0, count);
                if(callback!=null){
                    callback.onProgress((int) ((current*100)/total));
                }
            }

            output.flush();

            output.close();
            input.close();

            if(callback!=null){
                callback.onComplete();
            }
        } catch (Exception e) {
            if(callback!=null)
                callback.onFailed(e.getMessage());
        }
    }

    public interface DownloaderCallback {
        void onComplete();
        void onFailed(String message);
        void onProgress(int progress);
    }
}

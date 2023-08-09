package com.duzo.fakeplayers.client.threads;

import com.duzo.fakeplayers.util.SkinGrabber;

import java.io.File;

public class ImageDownloaderThread  implements Runnable {
    private String filename;
    private File filepath;
    private String URL;
    private String uuid;

    /**
     * Downloads the image from the url via threads to reduce lag
     * @param filename The file name you want to save this image as
     * @param filepath The path where you want to save this image
     */
    public ImageDownloaderThread(String uuid,String filename, File filepath, String URL) {
        this.uuid = uuid;
        this.filename = filename;
        this.filepath = filepath;
        this.URL = URL;
    }
    public void run() {
        downloadAndAddSkin(this.uuid,this.filename,this.filepath,this.URL);
    }

    private static void downloadAndAddSkin(String uuid,String filename, File filepath, String URL) {
        SkinGrabber.downloadImageFromURL(filename.toLowerCase().replace(" ", ""), filepath, URL);
        SkinGrabber.addCustomNameToList(uuid);
    }
}

package com.duzo.fakeplayers.client.threads;

import com.duzo.fakeplayers.util.SkinGrabber;

import java.io.File;

public class SkinDownloaderThread implements Runnable {
    private String username;

    /**
     * Downloads the skin via threads to reduce lag
     * @param username The username to be downloaded
     */
    public SkinDownloaderThread(String username) {
        this.username = username;
    }
    public void run() {
        downloadAndAddSkin(this.username);
    }

    private static void downloadAndAddSkin(String name) {
        SkinGrabber.downloadSkinFromUsername(name.toLowerCase().replace(" ", ""), new File(SkinGrabber.DEFAULT_DIR));
        SkinGrabber.addCustomNameToList(name);
    }
}

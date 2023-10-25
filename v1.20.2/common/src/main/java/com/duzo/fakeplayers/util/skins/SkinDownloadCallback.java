package com.duzo.fakeplayers.util.skins;

import com.duzo.fakeplayers.util.SkinManager;
import com.duzo.fakeplayers.util.downloader.DownloaderThread;

import java.io.File;
import java.util.UUID;

public class SkinDownloadCallback implements DownloaderThread.DownloaderCallback {
    private String filepath;
    private Boolean custom;
    private Boolean clientSided;
    private UUID uuid;

    public SkinDownloadCallback(String filepath, Boolean custom, Boolean isClient, UUID uuid) {
        this.filepath = filepath;
        this.custom = custom;
        this.clientSided = isClient;
        this.uuid = uuid;
    }
    @Override
    public void onComplete() {
        System.out.println("Download completed!");
        File file = new File(filepath);
        Skin skin;
        if (custom) {
            skin = new Skin(file, custom, clientSided);
            SkinManager.CUSTOM_SKINNAME_TO_SKIN_HASHMAP.put(skin.getSkinName(), skin);
        } else {
            skin = new Skin(file, custom, clientSided);
            SkinManager.DEFAULT_SKINNAME_TO_SKIN_HASHMAP.put(skin.getSkinName(), skin);
        }
        SkinManager.UUID_TO_SKIN_HASHMAP.put(this.uuid, skin);
    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void onProgress(int progress) {

    }
}

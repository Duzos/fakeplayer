package com.duzo.fakeplayers.util;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.util.downloader.DownloaderThread;
import com.duzo.fakeplayers.util.skins.Skin;
import com.duzo.fakeplayers.util.skins.SkinDownloadCallback;
import net.minecraft.util.Identifier;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkinManager {
    public static final String DEFAULT_URL = "https://mineskin.eu/skin/";
    public static final String DEFAULT_DIR = "./fakeplayers/skins/names";
    public static final String DEFAULT_CUSTOM_DIR = "./fakeplayers/skins/custom";
    public static final String ERROR_SKIN = "textures/entity/humanoid/error.png";
    public static final Identifier ERROR_TEXTURE = new Identifier(FakePlayers.MOD_ID, ERROR_SKIN);

    public static final HashMap<UUID, Skin> UUID_TO_SKIN_HASHMAP = new HashMap<>();
    public static final HashMap<String, Skin> DEFAULT_SKINNAME_TO_SKIN_HASHMAP = new HashMap<>();
    public static final HashMap<String, Skin> CUSTOM_SKINNAME_TO_SKIN_HASHMAP = new HashMap<>();
    public static final List<UUID> UUIDS_WAITING_FOR_SKIN = new ArrayList<>();
    public static boolean clientSide = true;

    public static void init(boolean client) {
        clientSide = client;
        ensureDirExists();
        loadDefaultSkins();
        loadCustomSkins();
    }

    public static void ensureDirExists() {
        File defaultDirFilepath = new File(DEFAULT_DIR);
        File defaultCustomDirFilepath = new File(DEFAULT_CUSTOM_DIR);
        if (!defaultDirFilepath.exists()) {
            defaultDirFilepath.mkdirs();
        }
        if (!defaultCustomDirFilepath.exists()) {
            defaultCustomDirFilepath.mkdirs();
        }
    }

    public static void loadDefaultSkins() {
        File[] skinFiles = listFiles(DEFAULT_DIR);
        for (File file : skinFiles) {
            Skin skin = new Skin(file, false, clientSide);
            DEFAULT_SKINNAME_TO_SKIN_HASHMAP.put(skin.getSkinName(), skin);
        }
    }

    public static void loadCustomSkins() {
        File[] skinFiles = listFiles(DEFAULT_CUSTOM_DIR);
        for (File file : skinFiles) {
            Skin skin = new Skin(file, true, clientSide);
            CUSTOM_SKINNAME_TO_SKIN_HASHMAP.put(skin.getSkinName(), skin);
        }

    }

    public static File[] listFiles(String dir) {
        return new File(dir).listFiles();
    }


    public static Skin getSkin(UUID uuid, String name, Boolean isCustom, String url) {
        if (UUID_TO_SKIN_HASHMAP.containsKey(uuid)) {
            return UUID_TO_SKIN_HASHMAP.get(uuid);
        } else {
            if (isCustom && CUSTOM_SKINNAME_TO_SKIN_HASHMAP.containsKey(name)) {
                Skin skin = CUSTOM_SKINNAME_TO_SKIN_HASHMAP.get(name);
                UUID_TO_SKIN_HASHMAP.put(uuid, skin);
                return skin;
            }
            else if (!isCustom && DEFAULT_SKINNAME_TO_SKIN_HASHMAP.containsKey(name)) {
                Skin skin = DEFAULT_SKINNAME_TO_SKIN_HASHMAP.get(name);
                UUID_TO_SKIN_HASHMAP.put(uuid, skin);
                return skin;
            }
            else {
                String filepath = (isCustom ? DEFAULT_CUSTOM_DIR : DEFAULT_DIR) + File.separator + name + ".png";
                UUIDS_WAITING_FOR_SKIN.add(uuid);
                downloadSkinAsync((isCustom ? url : DEFAULT_URL), filepath, uuid);
                return null;
            }
        }
    }

    public static void downloadSkinAsync(String url, String filepath, UUID uuid) {
        boolean isCustom = (!Objects.equals(url, DEFAULT_URL));
        SkinDownloadCallback skinDownloadCallback = new SkinDownloadCallback(filepath, isCustom, clientSide, uuid);
        DownloaderThread downloaderThread = new DownloaderThread().setCallback(skinDownloadCallback).setFilepath(filepath).setUrl(url);
        downloaderThread.start();
        // maybe need to uncomment this
//        downloaderThread.run();

    }
}

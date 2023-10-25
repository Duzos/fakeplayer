package com.duzo.fakeplayers.util;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.util.downloader.DownloaderThread;
import com.duzo.fakeplayers.util.skins.Skin;
import net.minecraft.util.Identifier;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
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

    public void init() {
        loadDefaultSkins();
        loadCustomSkins();
    }

    public void loadDefaultSkins() {
        File[] skinFiles = listFiles(DEFAULT_DIR);
        for (File file : skinFiles) {
            String filename = removeExtension(file.getName());
            Skin skin = new Skin(filename, false);
            DEFAULT_SKINNAME_TO_SKIN_HASHMAP.put(filename, skin);
        }
    }

    public void loadCustomSkins() {
        File[] skinFiles = listFiles(DEFAULT_CUSTOM_DIR);
        for (File file : skinFiles) {
            String filename = removeExtension(file.getName());
            Skin skin = new Skin(filename, true);
            CUSTOM_SKINNAME_TO_SKIN_HASHMAP.put(filename, skin);
        }

    }

    public File[] listFiles(String dir) {
        return new File(dir).listFiles();
    }

    public static String removeExtension(String fname) {
        int pos = fname.lastIndexOf('.');
        if(pos > -1)
            return fname.substring(0, pos);
        else
            return fname;
    }

    public static Skin getSkinFromUUID(UUID uuid) {
        return UUID_TO_SKIN_HASHMAP.get(uuid);
    }

    public static void downloadSkinAsync(String URL, String SKIN_NAME) {

    }
}

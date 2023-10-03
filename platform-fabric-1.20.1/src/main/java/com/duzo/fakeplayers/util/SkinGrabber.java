package com.duzo.fakeplayers.util;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.client.models.renderers.FakePlayerEntityRenderer;
import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.components.MyComponents;
import com.mojang.logging.LogUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Identifier;
import net.minecraft.entity.Entity;
import org.asynchttpclient.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SkinGrabber {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String URL = "https://mineskin.eu/skin/";
    public static final String DEFAULT_DIR = "./fakeplayers/skins/";
    public static final String DEFAULT_CACHE_DIR = "./fakeplayers/cache/";
    public static final String ERROR_SKIN = "textures/entity/humanoid/error.png";
    public static final Identifier ERROR_TEXTURE = new Identifier(Fakeplayers.MOD_ID, ERROR_SKIN);
    public static final HashMap<String, String> UUID_TO_USERNAME_LIST = new HashMap<>();
    public static final HashMap<String, Identifier> UUID_SKIN_LIST = new HashMap<>();

    public static final List<String> SKINS_IN_THE_PROCESS_OF_DOWNLOADING = new ArrayList<>();

    public static final List<Entity> entities_to_update = new ArrayList<>();
    public static final HashMap<String, List<String>> USERNAME_TO_ENTITY_UUID_LIST = new HashMap<>();

    public static Identifier getEntitySkinFromList(LivingEntity entity) {
        return SkinGrabber.getSkinFromListKey(entity.getUuidAsString());
    }

    public static Identifier getCustomNameSkinFromFile(String name) {
        File file = new File(DEFAULT_CACHE_DIR + name.toLowerCase().replace(" ", "") + ".png");
        return fileToLocation(file);
    }

    public static void addEntitySkinToList(Entity entity, String playerUsername) {
        if (Objects.equals(playerUsername, "") || playerUsername == null) {
            return;
        }
        Identifier location = SkinGrabber.getCustomNameSkinFromFile(playerUsername);
        List<String> uuidList;
        if (!USERNAME_TO_ENTITY_UUID_LIST.containsKey(playerUsername)) {
            uuidList = new ArrayList<>();
        } else {
            uuidList = USERNAME_TO_ENTITY_UUID_LIST.get(playerUsername);
        }
        if (uuidList.contains(entity.getUuidAsString())) {
            return;
        }
        uuidList.add(entity.getUuidAsString());
        if (!USERNAME_TO_ENTITY_UUID_LIST.containsKey(playerUsername)) {
            USERNAME_TO_ENTITY_UUID_LIST.put(playerUsername, uuidList);
        } else {
            USERNAME_TO_ENTITY_UUID_LIST.replace(playerUsername, uuidList);
        }
        UUID_SKIN_LIST.put(entity.getUuidAsString(), location);
        UUID_TO_USERNAME_LIST.put(entity.getUuidAsString(), playerUsername);

    }

    public static Identifier getSkinFromListKey(String key) {
        if (SkinGrabber.UUID_SKIN_LIST.containsKey(key)) {
            return SkinGrabber.UUID_SKIN_LIST.get(key);
        }

        return null;
    }

    public static Identifier fileToLocation(File file) {
        NativeImage image = null;
        try {
            image = processLegacySkin(NativeImage.read(new FileInputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            return new Identifier(Fakeplayers.MOD_ID,ERROR_SKIN);
        }
        return registerImage(image);
    }

    public static Identifier registerImage(NativeImage image) {
        TextureManager manager = MinecraftClient.getInstance().getTextureManager();
        return manager.registerDynamicTexture("humanoid", new NativeImageBackedTexture(image));
    }

    public static void downloadImageFromURL(String stringURL) {
        try {
            String username = stringURL.replace(URL, "");
            File default_cache_dir_filepath = new File(DEFAULT_CACHE_DIR);
            if (!default_cache_dir_filepath.exists()) {
                default_cache_dir_filepath.mkdirs();
            }
            File cacheFile = new File(DEFAULT_CACHE_DIR + username + ".png");
//            File entityFile = new File(DEFAULT_DIR + filename + ".png");
            if (!cacheFile.exists()) {
                java.net.URL url = new URL(stringURL);
                BufferedImage image = ImageIO.read(url.openStream());
                ImageIO.write(image, "png", cacheFile);

            }

        } catch (Exception exception) {
//            exception.printStackTrace();
        }
    }

    public static void downloadImageFromURLAsync(String stringURL) {
        try {
            String username = stringURL.replace(URL, "");
            File default_cache_dir_filepath = new File(DEFAULT_CACHE_DIR);
            if (!default_cache_dir_filepath.exists()) {
                default_cache_dir_filepath.mkdirs();
            }
            File cacheFile = new File(DEFAULT_CACHE_DIR + username + ".png");
//            File entityFile = new File(DEFAULT_DIR + filename + ".png");
            if (!cacheFile.exists() || !SKINS_IN_THE_PROCESS_OF_DOWNLOADING.contains(username)) {
                SKINS_IN_THE_PROCESS_OF_DOWNLOADING.add(username);
                AsyncHttpClient client = Dsl.asyncHttpClient();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                client.prepareGet(stringURL).execute(new AsyncCompletionHandler<ByteArrayOutputStream>() {

                    @Override
                    public State onBodyPartReceived(HttpResponseBodyPart bodyPart)
                            throws Exception {
                        byteArrayOutputStream.write(bodyPart.getBodyPartBytes());
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                        ImageIO.write(image, "png", cacheFile);
                        System.out.println("Downloaded skin for " + username);
                        return State.CONTINUE;
                    }

                    @Override
                    public ByteArrayOutputStream onCompleted(Response response)
                            throws Exception {
                        SKINS_IN_THE_PROCESS_OF_DOWNLOADING.remove(username);
                        return byteArrayOutputStream;
                    }
                });

            }

        } catch (Exception exception) {
//            exception.printStackTrace();
        }
    }

    @Nullable
    private static NativeImage processLegacySkin(NativeImage image) {
        int i = image.getHeight();
        int j = image.getWidth();
        if (j == 64 && (i == 32 || i == 64)) {
            boolean flag = i == 32;
            if (flag) {
                NativeImage nativeimage = new NativeImage(64, 64, true);
                nativeimage.copyFrom(image);
                image.close();
                image = nativeimage;
                nativeimage.fillRect(0, 32, 64, 32, 0);
                nativeimage.copyRect(4, 16, 16, 32, 4, 4, true, false);
                nativeimage.copyRect(8, 16, 16, 32, 4, 4, true, false);
                nativeimage.copyRect(0, 20, 24, 32, 4, 12, true, false);
                nativeimage.copyRect(4, 20, 16, 32, 4, 12, true, false);
                nativeimage.copyRect(8, 20, 8, 32, 4, 12, true, false);
                nativeimage.copyRect(12, 20, 16, 32, 4, 12, true, false);
                nativeimage.copyRect(44, 16, -8, 32, 4, 4, true, false);
                nativeimage.copyRect(48, 16, -8, 32, 4, 4, true, false);
                nativeimage.copyRect(40, 20, 0, 32, 4, 12, true, false);
                nativeimage.copyRect(44, 20, -8, 32, 4, 12, true, false);
                nativeimage.copyRect(48, 20, -16, 32, 4, 12, true, false);
                nativeimage.copyRect(52, 20, -8, 32, 4, 12, true, false);
            }

            setNoAlpha(image, 0, 0, 32, 16);
            if (flag) {
                doNotchTransparencyHack(image, 32, 0, 64, 32);
            }

            setNoAlpha(image, 0, 16, 64, 32);
            setNoAlpha(image, 16, 48, 48, 64);
            return image;
        } else {
            image.close();
            LOGGER.warn("Discarding incorrectly sized ({}x{}) skin texture", j, i);
            return null;
        }
    }

    private static void doNotchTransparencyHack(NativeImage p_118013_, int p_118014_, int p_118015_, int p_118016_, int p_118017_) {
        for(int i = p_118014_; i < p_118016_; ++i) {
            for(int j = p_118015_; j < p_118017_; ++j) {
                int k = p_118013_.getColor(i, j);
                if ((k >> 24 & 255) < 128) {
                    return;
                }
            }
        }

        for(int l = p_118014_; l < p_118016_; ++l) {
            for(int i1 = p_118015_; i1 < p_118017_; ++i1) {
                p_118013_.setColor(l, i1, p_118013_.getColor(l, i1) & 16777215);
            }
        }

    }

    private static void setNoAlpha(NativeImage p_118023_, int p_118024_, int p_118025_, int p_118026_, int p_118027_) {
        for (int i = p_118024_; i < p_118026_; ++i) {
            for (int j = p_118025_; j < p_118027_; ++j) {
                p_118023_.setColor(i, j, p_118023_.getColor(i, j) | -16777216);
            }
        }
    }
}
package com.duzo.fakeplayers.util;

import com.duzo.fakeplayers.FakePlayers;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;
import org.w3c.dom.Text;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;

public class SkinGrabber {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String URL = "https://mineskin.eu/skin/";
    public static final String DEFAULT_DIR = "./fakeplayers/skins/";
    public static final String ERROR_SKIN = "textures/entities/humanoid/error.png";
    public static final HashMap<String, ResourceLocation> SKIN_LIST = new HashMap<>();

    public static ResourceLocation getEntitySkinFromList(Entity entity) {
        ResourceLocation location = SkinGrabber.getUsernameSkinFromList(formatEntityCustomName(entity));
        return location;
    }

    public static String formatEntityCustomName(Entity entity) {
        return entity.getName().getString().toLowerCase().replace(" ", "");
    }

    public static ResourceLocation getEntitySkinFromFile(Entity entity) {
        File file = new File(DEFAULT_DIR + formatEntityCustomName(entity) + ".png");
        ResourceLocation location = fileToLocation(file);
        return location;
    }

    public static ResourceLocation getCustomNameSkinFromFile(String name) {
        File file = new File(DEFAULT_DIR + name.toLowerCase().replace(" ", "") + ".png");
        System.out.println(file.getAbsolutePath());
        ResourceLocation location = fileToLocation(file);
        return location;
    }

    public static void addEntityToList(Entity entity) {
        ResourceLocation location = SkinGrabber.getEntitySkinFromFile(entity);
        System.out.println("Adding " + formatEntityCustomName(entity) + " " + location);
        SKIN_LIST.put(formatEntityCustomName(entity),location);
    }

    public static void addCustomNameToList(String name) {
        ResourceLocation location = SkinGrabber.getCustomNameSkinFromFile(name);
        System.out.println("Adding " + name.toLowerCase().replace(" ", "" + " " + location));
        SKIN_LIST.put(name.toLowerCase().replace(" ", ""),location);
    }

    public static void clearTextures() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            TextureManager manager = minecraft.getTextureManager();

            // Release the textures if the cache isnt empty
            if (!SkinGrabber.SKIN_LIST.isEmpty()) {
                SkinGrabber.SKIN_LIST.forEach(((uuid, resourceLocation) -> manager.release(resourceLocation)));
                SkinGrabber.SKIN_LIST.clear();
            }
        }
    }

    public static ResourceLocation getUsernameSkinFromList(String name) {
        if (SkinGrabber.SKIN_LIST.containsKey(name)) {
            return SkinGrabber.SKIN_LIST.get(name);
        }
        return null;
    }

    public static ResourceLocation fileToLocation(File file) {
        NativeImage image = null;
        try {
            image = processLegacySkin(NativeImage.read(new FileInputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            return new ResourceLocation(FakePlayers.MODID,ERROR_SKIN);
        }
        return registerImage(image);
    }

    public static ResourceLocation registerImage(NativeImage image) {
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        return manager.register("humanoid", new DynamicTexture(image));
    }



    public static void downloadSkinFromUsername(String username, File filepath) {
        try {
            URL url = new URL(URL + username);
            URLConnection connection = url.openConnection();
            connection.connect();
            connection.setConnectTimeout(0);

            BufferedImage image = ImageIO.read(connection.getInputStream());

            if (!filepath.exists()) {
                filepath.mkdirs();
            }

            ImageIO.write(image, "png", new File(filepath, username + ".png"));
            System.out.println(image);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // These functions were coped from HttpTexture by Mojang (thak you moywang(
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
                int k = p_118013_.getPixelRGBA(i, j);
                if ((k >> 24 & 255) < 128) {
                    return;
                }
            }
        }

        for(int l = p_118014_; l < p_118016_; ++l) {
            for(int i1 = p_118015_; i1 < p_118017_; ++i1) {
                p_118013_.setPixelRGBA(l, i1, p_118013_.getPixelRGBA(l, i1) & 16777215);
            }
        }

    }

    private static void setNoAlpha(NativeImage p_118023_, int p_118024_, int p_118025_, int p_118026_, int p_118027_) {
        for (int i = p_118024_; i < p_118026_; ++i) {
            for (int j = p_118025_; j < p_118027_; ++j) {
                p_118023_.setPixelRGBA(i, j, p_118023_.getPixelRGBA(i, j) | -16777216);
            }
        }
    }
}

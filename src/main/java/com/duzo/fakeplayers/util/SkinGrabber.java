package com.duzo.fakeplayers.util;

import com.duzo.fakeplayers.FakePlayers;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class SkinGrabber {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String URL = "https://mineskin.eu/skin/";
    public static final String DEFAULT_DIR = "/fakeplayers/skins/";
    public static final String ERROR_SKIN = "textures/entities/humanoid/error.png";

//    public static NativeImage fixLegacySkins(NativeImage image) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        // Check if it isnt 32x32
//        boolean is32x32 = (width == 32) && (height == 32);
//        boolean is64x64 = (width == 64) && (height == 64);
//        if ((!is32x32) && (is64x64)) {
//            return image;
//        }
//
//        NativeImage fixedImage = new NativeImage(64,64,true);
//        fixedImage.copyFrom(image);
//        image.close();
//
//        // Fill the lower half in
//        fixedImage.fillRect(0, 32, 64, 32, 0);
//
//        // Copy the legs and arms to the bottom half
//        // * Copy arms
////        fixedImage.copyRect(0,15,15,47,14,14,false,false);
//        fixedImage.fillRect(16, 48, 15, 15, 0x00ff44);
//        // * Copy legs
//        fixedImage.fillRect(32, 48, 15, 15, 0xae00ff);
////        fixedImage.copyRect(40,15,31,47,15,15,false,false);
//
//        fixedImage.fillRect(0,0,64,64,);
//        return fixedImage;
//    }

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

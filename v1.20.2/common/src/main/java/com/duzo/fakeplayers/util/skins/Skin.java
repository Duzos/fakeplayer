package com.duzo.fakeplayers.util.skins;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.util.SkinManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Skin {
    private final String SKIN_PATH;
    private final String SKIN_NAME;
    private final Boolean IS_CUSTOM_SKIN;

    private final Identifier SKIN_TEXTURE;
    private final Boolean isClientSided;

    public Skin(File file, Boolean isCustom, Boolean clientSide) {
        isClientSided = clientSide;
        IS_CUSTOM_SKIN = isCustom;
        String filename = file.getName();
        SKIN_NAME = removeExtension(filename);
        if (isCustom) {
            SKIN_PATH = SkinManager.DEFAULT_CUSTOM_DIR + "/" + SKIN_NAME + ".png";
        } else {
            SKIN_PATH = SkinManager.DEFAULT_DIR + "/" + SKIN_NAME + ".png";
        }
        SKIN_TEXTURE = createTextureFromFile(file);
    }

    public static String removeExtension(String fname) {
        int pos = fname.lastIndexOf('.');
        if(pos > -1)
            return fname.substring(0, pos);
        else
            return fname;
    }

    private Identifier createTextureFromFile(File file) {
        NativeImage image = null;
        try {
            image = processLegacySkin(NativeImage.read(new FileInputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image == null) {
            return SkinManager.ERROR_TEXTURE;
        }
        if (isClientSided) {
            TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
            return textureManager.registerDynamicTexture("humanoid", new NativeImageBackedTexture(image));
        } else {
            return new Identifier(FakePlayers.MOD_ID, file.getPath());
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

    public boolean isSkinCustom() {
        return IS_CUSTOM_SKIN;
    }

    public Identifier getTexture() {
        return SKIN_TEXTURE;
    }

    public String getSkinPath() {
        return SKIN_PATH;
    }

    public String getSkinName() {
        return SKIN_NAME;
    }
}

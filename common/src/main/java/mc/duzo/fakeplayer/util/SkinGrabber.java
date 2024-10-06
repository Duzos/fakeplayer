package mc.duzo.fakeplayer.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.mojang.logging.LogUtils;
import org.asynchttpclient.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import mc.duzo.fakeplayer.FakePlayerMod;

public class SkinGrabber {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String URL = "https://mineskin.eu/skin/";
    public static final String DEFAULT_DIR = "./fakeplayers/skins/";
    public static final String ERROR_SKIN = "textures/entity/humanoid/error.png";
    public static final Identifier ERROR_TEXTURE = new Identifier(FakePlayerMod.MOD_ID, ERROR_SKIN);
    public static final HashMap<String, Identifier> SKIN_LIST = new HashMap<>();

    public static Identifier getEntitySkinFromList(LivingEntity entity) {
        Identifier location = SkinGrabber.getSkinFromListKey(entity.getUuidAsString());
        return location;
    }

    public static String formatEntityCustomName(Entity entity) {
        return entity.getName().getString().toLowerCase().replace(" ", "");
    }

    public static Identifier getEntitySkinFromFile(Entity entity) {
        File file = new File(DEFAULT_DIR + formatEntityCustomName(entity) + ".png");
        Identifier location = fileToLocation(file);
        return location;
    }

    public static Identifier getCustomNameSkinFromFile(String name) {
        File file = new File(DEFAULT_DIR + name.toLowerCase().replace(" ", "") + ".png");
        Identifier location = fileToLocation(file);
        return location;
    }

    public static void addEntityToList(Entity entity) {
        Identifier location = SkinGrabber.getEntitySkinFromFile(entity);
//        System.out.println("Adding " + formatEntityCustomName(entity) + " " + location);
        SKIN_LIST.put(formatEntityCustomName(entity),location);
    }

    public static void addCustomNameToList(String name) {
        Identifier location = SkinGrabber.getCustomNameSkinFromFile(name);
        SKIN_LIST.put(name.toLowerCase().replace(" ", ""),location);
    }

    public static void clearTextures() {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (minecraft.world == null) {
            TextureManager manager = minecraft.getTextureManager();

            // Release the textures if the cache isnt empty
            if (!SkinGrabber.SKIN_LIST.isEmpty()) {
                SkinGrabber.SKIN_LIST.forEach(((uuid, Identifier) -> manager.destroyTexture(Identifier)));
                SkinGrabber.SKIN_LIST.clear();
            }
        }
    }

    public static Identifier getSkinFromListKey(String key) {
        if (SkinGrabber.SKIN_LIST.containsKey(key)) {
            return SkinGrabber.SKIN_LIST.get(key);
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
            return new Identifier(FakePlayerMod.MOD_ID,ERROR_SKIN);
        }
        return registerImage(image);
    }

    public static Identifier registerImage(NativeImage image) {
        TextureManager manager = MinecraftClient.getInstance().getTextureManager();
        return manager.registerDynamicTexture("humanoid", new NativeImageBackedTexture(image));
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public static void downloadImageFromURL(String filename,File filepath, String URL) {
        try {
            URL url = new URL(URL);
            URLConnection connection = url.openConnection();
            connection.connect();
            connection.setConnectTimeout(0);

            BufferedImage image = ImageIO.read(connection.getInputStream());

            if (!filepath.exists()) {
                filepath.mkdirs();
            }

            ImageIO.write(image, "png", new File(filepath, filename + ".png"));
            System.out.println(image);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void downloadSkinFromUsername(String username, File filepath) {
        downloadImageFromURL(username, filepath, SkinGrabber.URL + username);
    }

    // These functions were coped from HttpTexture by Mojang (thak you moywang(
    @Nullable private static NativeImage processLegacySkin(NativeImage image) {
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

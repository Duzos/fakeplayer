package com.duzo.fakeplayers.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

public class SkinGrabber {
    private static final String URL = "https://mineskin.eu/skin/";
//    public static final String DEFAULT_DIR = "./fakeplayers/skins/";
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
}

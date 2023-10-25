package com.duzo.fakeplayers.util;

import com.duzo.fakeplayers.FakePlayers;
import net.minecraft.util.Identifier;

public class SkinManager {
    public static final String DEFAULT_URL = "https://mineskin.eu/skin/";
    public static final String DEFAULT_DIR = "./fakeplayers/skins/names";
    public static final String DEFAULT_CUSTOM_DIR = "./fakeplayers/skins/custom";
    public static final String ERROR_SKIN = "textures/entity/humanoid/error.png";
    public static final Identifier ERROR_TEXTURE = new Identifier(FakePlayers.MOD_ID, ERROR_SKIN);
}

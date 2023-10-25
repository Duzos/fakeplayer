package com.duzo.fakeplayers.util.skins;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.util.SkinManager;
import net.minecraft.util.Identifier;

public class Skin {
    private final String SKIN_PATH;
    private final String SKIN_NAME;
    private final Boolean IS_CUSTOM_SKIN;

    private final Identifier SKIN_TEXTURE;

    public Skin(String name, Boolean isCustom) {
        IS_CUSTOM_SKIN = isCustom;
        SKIN_NAME = name;
        if (isCustom) {
            SKIN_PATH = SkinManager.DEFAULT_CUSTOM_DIR + "/" + SKIN_NAME + ".png";
        } else {
            SKIN_PATH = SkinManager.DEFAULT_DIR + "/" + SKIN_NAME + ".png";
        }
        SKIN_TEXTURE = new Identifier(FakePlayers.MOD_ID, SKIN_PATH);
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

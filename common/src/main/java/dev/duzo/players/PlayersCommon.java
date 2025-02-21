package dev.duzo.players;

import dev.duzo.players.core.FPItems;
import net.minecraft.resources.ResourceLocation;

public class PlayersCommon {
    public static void init() {
        FPItems.init();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}
package dev.duzo.players;

import dev.duzo.players.api.SkinGrabber;
import dev.duzo.players.commands.SkinUrlCommand;
import dev.duzo.players.core.FPEntities;
import dev.duzo.players.core.FPItems;
import dev.duzo.players.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

public class PlayersCommon {
    public static void init() {
        FPItems.init();
        FPEntities.init();

        Services.COMMON_REGISTRY.registerCommand(SkinUrlCommand::register);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }

    public static void tick(MinecraftServer server) {
        SkinGrabber.INSTANCE.tick(server);
    }
}
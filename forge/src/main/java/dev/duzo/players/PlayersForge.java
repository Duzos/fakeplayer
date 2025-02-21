package dev.duzo.players;

import dev.duzo.players.platform.ForgeCommonRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class PlayersForge {
    public PlayersForge() {
        PlayersCommon.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgeCommonRegistry.init(bus);
    }
}
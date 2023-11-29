package com.duzo.fakeplayers.forge;
import com.duzo.fakeplayers.FakePlayers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FakePlayers.MOD_ID)
public class FakePlayersForge {
    static IEventBus bus;

    public FakePlayersForge() {
        bus = FMLJavaModLoadingContext.get().getModEventBus();
    }

}

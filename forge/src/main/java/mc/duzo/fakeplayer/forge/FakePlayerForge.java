package mc.duzo.fakeplayer.forge;

import dev.architectury.platform.forge.EventBuses;
import mc.duzo.fakeplayer.FakePlayerMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FakePlayerMod.MOD_ID)
public class FakePlayerForge {
    public FakePlayerForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(FakePlayerMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        FakePlayerMod.init();
    }
}

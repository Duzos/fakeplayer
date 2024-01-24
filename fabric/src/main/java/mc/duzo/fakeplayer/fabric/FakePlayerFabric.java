package mc.duzo.fakeplayer.fabric;

import mc.duzo.fakeplayer.FakePlayerMod;
import net.fabricmc.api.ModInitializer;

public class FakePlayerFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FakePlayerMod.init();
    }
}

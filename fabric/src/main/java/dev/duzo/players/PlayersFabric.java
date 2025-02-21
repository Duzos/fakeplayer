package dev.duzo.players;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class PlayersFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        PlayersCommon.init();

        ServerTickEvents.END_SERVER_TICK.register(PlayersCommon::tick);
    }
}

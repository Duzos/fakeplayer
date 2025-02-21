package dev.duzo.players.client;

import dev.duzo.players.client.renderers.FakePlayerRendererWrapper;
import dev.duzo.players.core.FPEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PlayersFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		PlayersCommonClient.init();

		EntityRendererRegistry.register(FPEntities.FAKE_PLAYER.get(), FakePlayerRendererWrapper::new);
	}
}

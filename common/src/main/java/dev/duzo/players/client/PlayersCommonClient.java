package dev.duzo.players.client;

import dev.duzo.players.api.SkinGrabber;
import dev.duzo.players.client.screen.SkinSelectScreen;
import dev.duzo.players.entities.FakePlayerEntity;
import net.minecraft.client.Minecraft;

public class PlayersCommonClient {
	public static void init() {

	}

	public static void tick(Minecraft client) {
		SkinGrabber.INSTANCE.tick();
	}

	public static void openSelectScreen(FakePlayerEntity entity) {
		Minecraft.getInstance().setScreen(new SkinSelectScreen(entity));
	}
}

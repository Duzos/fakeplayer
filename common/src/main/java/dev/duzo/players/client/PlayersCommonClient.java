package dev.duzo.players.client;

import dev.duzo.players.api.SkinGrabber;
import net.minecraft.client.Minecraft;

public class PlayersCommonClient {
	public static void init() {

	}

	public static void tick(Minecraft client) {
		SkinGrabber.INSTANCE.tick();
	}
}

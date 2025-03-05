package dev.duzo.players.client;

import dev.duzo.players.Constants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ClientBusEvents {
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent e) {
		PlayersCommonClient.tick(Minecraft.getInstance());
	}

	@SubscribeEvent
	public static void onClientStopping(ClientPlayerNetworkEvent.LoggingOut e) {
		PlayersCommonClient.onClientStopping();
	}
}

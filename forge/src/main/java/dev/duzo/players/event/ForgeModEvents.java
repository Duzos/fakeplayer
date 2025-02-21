package dev.duzo.players.event;


import dev.duzo.players.Constants;
import dev.duzo.players.PlayersCommon;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static dev.duzo.players.platform.ForgeCommonRegistry.COMMANDS;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ForgeModEvents {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent e) {
		COMMANDS.forEach(command -> command.accept(e.getDispatcher()));
	}

	@SubscribeEvent
	public static void serverTick(TickEvent.ServerTickEvent e) {
		if (e.phase == TickEvent.Phase.END) {
			PlayersCommon.tick(e.getServer());
		}
	}
}

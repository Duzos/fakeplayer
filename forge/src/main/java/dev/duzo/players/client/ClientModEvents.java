package dev.duzo.players.client;

import dev.duzo.players.Constants;
import dev.duzo.players.client.renderers.FakePlayerRendererWrapper;
import dev.duzo.players.core.FPEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
	@SubscribeEvent
	public static void onEntityRenderersRegistry(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FPEntities.FAKE_PLAYER.get(), FakePlayerRendererWrapper::new);
	}
}
package com.duzo.fakeplayers.events;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.client.models.entities.FakePlayerEntityModel;
import com.duzo.fakeplayers.client.models.entities.HumanoidEntityModel;
import com.duzo.fakeplayers.client.models.renderers.FakePlayerRenderer;
import com.duzo.fakeplayers.client.models.renderers.HumanoidEntityRenderer;
import com.duzo.fakeplayers.entities.HumanoidEntity;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = FakePlayers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FakePlayersClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers renderers) {
        renderers.registerEntityRenderer(FakePlayers.HUMANOID_ENTITY.get(), HumanoidEntityRenderer::new);
        renderers.registerEntityRenderer(FakePlayers.FAKE_PLAYER_ENTITY.get(), FakePlayerRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HumanoidEntityModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,true),64,64));
        event.registerLayerDefinition(FakePlayerEntityModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,true),64,64));
    }
}

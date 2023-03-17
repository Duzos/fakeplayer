package com.duzo.fakeplayers.events;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.client.models.entities.*;
import com.duzo.fakeplayers.client.models.renderers.*;
import com.duzo.fakeplayers.core.init.FPEntities;
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
        renderers.registerEntityRenderer(FPEntities.FAKE_PLAYER_ENTITY.get(), FakePlayerRenderer::new);
        renderers.registerEntityRenderer(FPEntities.FAKE_PLAYER_SLIM_ENTITY.get(), FakePlayerSlimRenderer::new);
        renderers.registerEntityRenderer(FPEntities.TAMABLE_PLAYER.get(), TamablePlayerRenderer::new);
        renderers.registerEntityRenderer(FPEntities.TAMABLE_PLAYER_SLIM.get(), TamablePlayerSlimRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HumanoidEntityModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
        event.registerLayerDefinition(FakePlayerEntityModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
        event.registerLayerDefinition(FakePlayerSlimEntityModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,true),64,64));
        event.registerLayerDefinition(TamablePlayerSlimModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,true),64,64));
        event.registerLayerDefinition(TamablePlayerModel.LAYER_LOCATION,() -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
    }
}

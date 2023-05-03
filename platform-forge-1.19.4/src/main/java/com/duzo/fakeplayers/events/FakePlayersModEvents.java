package com.duzo.fakeplayers.events;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.core.init.FPEntities;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FakePlayers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FakePlayersModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {});
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(FPEntities.FAKE_PLAYER_ENTITY.get(), HumanoidEntity.getHumanoidAttributes().build());
        event.put(FPEntities.FAKE_PLAYER_SLIM_ENTITY.get(), HumanoidEntity.getHumanoidAttributes().build());
//        event.put(FPEntities.TAMABLE_PLAYER.get(), HumanoidEntity.getHumanoidAttributes().build());
//        event.put(FPEntities.TAMABLE_PLAYER_SLIM.get(), HumanoidEntity.getHumanoidAttributes().build());
    }
}
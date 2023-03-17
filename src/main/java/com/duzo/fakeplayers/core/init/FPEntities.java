package com.duzo.fakeplayers.core.init;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.common.entities.TamableHumanoid;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerSlimEntity;
import com.duzo.fakeplayers.common.entities.humanoids.tamables.TamablePlayer;
import com.duzo.fakeplayers.common.entities.humanoids.tamables.TamablePlayerSlim;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FakePlayers.MODID);
    public static final RegistryObject<EntityType<FakePlayerEntity>> FAKE_PLAYER_ENTITY = ENTITIES.register("fake_player_entity", () ->
            EntityType.Builder.<FakePlayerEntity>of(FakePlayerEntity::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(FakePlayers.MODID,"fake_player_entity").toString()));
    public static final RegistryObject<EntityType<FakePlayerSlimEntity>> FAKE_PLAYER_SLIM_ENTITY = ENTITIES.register("fake_player_slim_entity", () ->
            EntityType.Builder.<FakePlayerSlimEntity>of(FakePlayerSlimEntity::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(FakePlayers.MODID,"fake_player_slim_entity").toString()));
    public static final RegistryObject<EntityType<TamablePlayer>> TAMABLE_PLAYER = ENTITIES.register("tamable_player", () ->
            EntityType.Builder.<TamablePlayer>of(TamablePlayer::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(FakePlayers.MODID,"tamable_player").toString()));
    public static final RegistryObject<EntityType<TamablePlayerSlim>> TAMABLE_PLAYER_SLIM = ENTITIES.register("tamable_player_slim", () ->
            EntityType.Builder.<TamablePlayerSlim>of(TamablePlayerSlim::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(FakePlayers.MODID,"tamable_player_slim").toString()));
}

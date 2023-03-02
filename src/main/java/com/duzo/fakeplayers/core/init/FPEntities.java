package com.duzo.fakeplayers.core.init;

import com.duzo.fakeplayers.FakePlayers;
import com.duzo.fakeplayers.entities.HumanoidEntity;
import com.duzo.fakeplayers.entities.humanoids.FakePlayerEntity;
import com.duzo.fakeplayers.entities.humanoids.FakePlayerSlimEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FakePlayers.MODID);

    public static final RegistryObject<EntityType<HumanoidEntity>> HUMANOID_ENTITY = ENTITIES.register("humanoid_entity", () ->
            EntityType.Builder.<HumanoidEntity>of(HumanoidEntity::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(FakePlayers.MODID,"humanoid_entity").toString()));
    public static final RegistryObject<EntityType<FakePlayerEntity>> FAKE_PLAYER_ENTITY = ENTITIES.register("fake_player_entity", () ->
            EntityType.Builder.<FakePlayerEntity>of(FakePlayerEntity::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(FakePlayers.MODID,"fake_player_entity").toString()));
    public static final RegistryObject<EntityType<FakePlayerSlimEntity>> FAKE_PLAYER_SLIM_ENTITY = ENTITIES.register("fake_player_slim_entity", () ->
            EntityType.Builder.<FakePlayerSlimEntity>of(FakePlayerSlimEntity::new, MobCategory.CREATURE).sized(0.6f,1.8f).build(new ResourceLocation(FakePlayers.MODID,"fake_player_slim_entity").toString()));
}

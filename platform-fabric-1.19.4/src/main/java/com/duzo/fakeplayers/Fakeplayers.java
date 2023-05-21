package com.duzo.fakeplayers;

import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Fakeplayers implements ModInitializer {
    public static final String MOD_ID = "fakeplayers";

    // Entities
    public static final EntityType<FakePlayerEntity> FAKE_PLAYER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "fake_player"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FakePlayerEntity::new).dimensions(EntityDimensions.fixed(0.6f,1.8f)).build()
    );

    /**
     * Runs the mod initializer.
     */
    @java.lang.Override
    public void onInitialize() {
        // Entity attributes
        FabricDefaultAttributeRegistry.register(FAKE_PLAYER, HumanoidEntity.getHumanoidAttributes());
    }
}

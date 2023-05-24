package com.duzo.fakeplayers;

import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.FakePlayerSlimEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
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
    public static final EntityType<FakePlayerSlimEntity> FAKE_PLAYER_SLIM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "fake_player_slim"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FakePlayerSlimEntity::new).dimensions(EntityDimensions.fixed(0.6f,1.8f)).build()
    );

    // Items
    public static final Item FAKE_PLAYER_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            new Identifier(MOD_ID, "fake_player_spawn_egg"),
            new SpawnEggItem(FAKE_PLAYER,0xe0280b,0x73eb44, new Item.Settings())
    );
    public static final Item FAKE_PLAYER_SLIM_SPAWN_EGG = Registry.register(
            Registries.ITEM,
            new Identifier(MOD_ID, "fake_player_slim_spawn_egg"),
            new SpawnEggItem(FAKE_PLAYER_SLIM,0x73eb44,0xe0280b, new Item.Settings())
    );
    // Creative tabs
    private static final ItemGroup FP_TAB = FabricItemGroup.builder(new Identifier(MOD_ID, "fp_tab"))
            .icon(() -> new ItemStack(FAKE_PLAYER_SPAWN_EGG))
            .build();

    /**
     * Runs the mod initializer.
     */
    @java.lang.Override
    public void onInitialize() {
        // Entity attributes
        FabricDefaultAttributeRegistry.register(FAKE_PLAYER, HumanoidEntity.getHumanoidAttributes());
        FabricDefaultAttributeRegistry.register(FAKE_PLAYER_SLIM, HumanoidEntity.getHumanoidAttributes());
        this.addItemsToTabs();
    }
    private void addItemsToTabs() {
        ItemGroupEvents.modifyEntriesEvent(FP_TAB).register(content -> {
            content.add(FAKE_PLAYER_SPAWN_EGG);
            content.add(FAKE_PLAYER_SLIM_SPAWN_EGG);
        });
    }
}

package com.duzo.fakeplayers.core.init;

import com.duzo.fakeplayers.FakePlayers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FPItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FakePlayers.MODID);
    public static final RegistryObject<ForgeSpawnEggItem> FAKE_PLAYER_SPAWN_EGG = ITEMS.register("fake_player_spawn_egg", () ->
            new ForgeSpawnEggItem(FPEntities.FAKE_PLAYER_ENTITY,0xe0280b,0x73eb44, new Item.Properties().tab(FPCreativeTab.FP_TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> FAKE_PLAYER_SLIM_SPAWN_EGG = ITEMS.register("fake_player_slim_spawn_egg", () ->
            new ForgeSpawnEggItem(FPEntities.FAKE_PLAYER_SLIM_ENTITY,0x73eb44,0xe0280b, new Item.Properties().tab(FPCreativeTab.FP_TAB)));

    public static final RegistryObject<Item> PLAYER_SHELL = ITEMS.register("player_shell", () -> new Item(new Item.Properties().tab(FPCreativeTab.FP_TAB)));
    public static final RegistryObject<Item> PLAYER_AI = ITEMS.register("player_ai", () -> new Item(new Item.Properties().tab(FPCreativeTab.FP_TAB)));
}

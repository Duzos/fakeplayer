package com.duzo.fakeplayers.common;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.common.items.FakePlayerSpawnEggItem;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.minecraft.item.Item;

public class ItemInit implements ItemRegistryContainer {
    public static final Item FAKE_PLAYER_SPAWN_EGG = new FakePlayerSpawnEggItem(Fakeplayers.FAKE_PLAYER, 0xe0280b, 0x73eb44, new OwoItemSettings().group(Fakeplayers.FP_ITEM_GROUP));
    public static final Item FAKE_PLAYER_SLIM_SPAWN_EGG = new FakePlayerSpawnEggItem(Fakeplayers.FAKE_PLAYER_SLIM, 0x73eb44, 0xe0280b, new OwoItemSettings().group(Fakeplayers.FP_ITEM_GROUP));
    public static final Item PLAYER_AI = new Item(new OwoItemSettings().group(Fakeplayers.FP_ITEM_GROUP));
    public static final Item PLAYER_SHELL = new Item(new OwoItemSettings().group(Fakeplayers.FP_ITEM_GROUP));
}

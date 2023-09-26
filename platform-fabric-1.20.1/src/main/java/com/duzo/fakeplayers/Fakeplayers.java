package com.duzo.fakeplayers;

import com.duzo.fakeplayers.common.ItemInit;
import com.duzo.fakeplayers.common.entities.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.FakePlayerSlimEntity;
import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.networking.packets.S2CDownloadSkinAsync;
import com.duzo.fakeplayers.util.SkinGrabber;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.network.OwoNetChannel;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Fakeplayers implements ModInitializer {
    public static final String MOD_ID = "fakeplayers";
    public static List<String> urlsToDownload = new ArrayList<>();
    public static MinecraftServer minecraftServer = null;
    public static final OwoNetChannel NETWORK = OwoNetChannel.create(new Identifier(MOD_ID, "network"));
    public static final OwoItemGroup FP_ITEM_GROUP = OwoItemGroup.builder(new Identifier(Fakeplayers.MOD_ID, "item_group"),() -> Icon.of(ItemInit.FAKE_PLAYER_SPAWN_EGG)).build();

    // Entities
    public static final EntityType<FakePlayerEntity> FAKE_PLAYER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "fake_player"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FakePlayerEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build()
    );
    public static final EntityType<FakePlayerSlimEntity> FAKE_PLAYER_SLIM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "fake_player_slim"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FakePlayerSlimEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build()
    );

    // Items

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        // Entity attributes
        FP_ITEM_GROUP.initialize();
        FieldRegistrationHandler.register(ItemInit.class, MOD_ID, false);
        FabricDefaultAttributeRegistry.register(FAKE_PLAYER, HumanoidEntity.getHumanoidAttributes());
        FabricDefaultAttributeRegistry.register(FAKE_PLAYER_SLIM, HumanoidEntity.getHumanoidAttributes());
        ServerWorldEvents.LOAD.register((server, world) -> {
            if (world.getRegistryKey().equals(World.OVERWORLD)) {
                minecraftServer = server;
            }
        });
        ServerWorldEvents.UNLOAD.register((server, world) -> {
            if (world.getRegistryKey().equals(World.OVERWORLD)) {
                minecraftServer = null; // Prevents an annoying crash
            }
        });
        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            ServerPlayerEntity serverPlayerEntity = handler.getPlayer();
            PlayerInventory playerInventory = serverPlayerEntity.getInventory();
            int size = playerInventory.size();
            for (int i = 0; i < size; i++) {
                ItemStack itemStack = playerInventory.getStack(i);
                if (itemStack.getItem().equals(ItemInit.FAKE_PLAYER_SPAWN_EGG) || itemStack.getItem().equals(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG)) {
                    if (itemStack.hasCustomName()) {
                        if (!urlsToDownload.contains(itemStack.getName().getString())) {
                            urlsToDownload.add(SkinGrabber.URL + itemStack.getName().getString());
                        }
                    }

                }
            }
            sendURLsToClients(urlsToDownload.toArray(new String[0]));
        }));
    }

    public static void sendURLToClients(String url) {
        PlayerManager playerManager = minecraftServer.getPlayerManager();
        for (PlayerEntity player : playerManager.getPlayerList()) {
            NETWORK.serverHandle(player).send(new S2CDownloadSkinAsync(new String[]{url}));
        }
    }

    public void sendURLsToClients(String[] urls) {
        PlayerManager playerManager = minecraftServer.getPlayerManager();
        for (PlayerEntity player : playerManager.getPlayerList()) {
            NETWORK.serverHandle(player).send(new S2CDownloadSkinAsync(urls));
        }
    }

}

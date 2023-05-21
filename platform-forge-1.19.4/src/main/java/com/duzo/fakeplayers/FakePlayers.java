package com.duzo.fakeplayers;

import com.duzo.fakeplayers.common.containers.FPContainers;
import com.duzo.fakeplayers.configs.FPClientConfigs;
import com.duzo.fakeplayers.configs.FPCommonConfigs;
import com.duzo.fakeplayers.core.init.FPEntities;
import com.duzo.fakeplayers.core.init.FPItems;
import com.duzo.fakeplayers.networking.Network;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FakePlayers.MODID)
public class FakePlayers {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "fakeplayers";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FakePlayers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        FPItems.ITEMS.register(modEventBus);
        FPEntities.ENTITIES.register(modEventBus);
        FPContainers.MENUS.register(modEventBus);

        // Configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FPClientConfigs.SPEC, "fakeplayers-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FPCommonConfigs.SPEC, "fakeplayers-common.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Network.register();
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(FPItems.FAKE_PLAYER_SPAWN_EGG.get());
            event.accept(FPItems.FAKE_PLAYER_SLIM_SPAWN_EGG.get());
        }
        if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(FPItems.PLAYER_AI.get());
            event.accept(FPItems.PLAYER_SHELL.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code

        }
    }
}
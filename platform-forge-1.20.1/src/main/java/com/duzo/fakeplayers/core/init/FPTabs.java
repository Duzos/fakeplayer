package com.duzo.fakeplayers.core.init;

import com.duzo.fakeplayers.FakePlayers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FPTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FakePlayers.MODID);

    public static final RegistryObject<CreativeModeTab> FP_TAB = TABS.register("fp_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.fp_tab"))
            .icon(FPItems.PLAYER_AI.get()::getDefaultInstance)
            .displayItems((params,output) -> {
                for (RegistryObject<Item> reg : FPItems.ITEMS.getEntries()) {
                    output.accept(reg.get());
                }
            })
            .build()
    );
}

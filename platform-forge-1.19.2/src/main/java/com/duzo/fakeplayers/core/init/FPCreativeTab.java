package com.duzo.fakeplayers.core.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FPCreativeTab {
    public static final CreativeModeTab FP_TAB = new CreativeModeTab("fp_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(FPItems.PLAYER_AI.get());
        }
    };
}

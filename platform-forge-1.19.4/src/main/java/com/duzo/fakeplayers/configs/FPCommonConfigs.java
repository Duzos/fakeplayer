package com.duzo.fakeplayers.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class FPCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> DOES_DROP_EGGS;

    static {
        BUILDER.push("Configs for Duzo's Fake Players Mod");

        DOES_DROP_EGGS = BUILDER.comment("Defines if the fake players drog eggs on death")
                .define("Does drop eggs",true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

package com.duzo.fakeplayers.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class FPClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Configs for Duzo's Fake Players Mod");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

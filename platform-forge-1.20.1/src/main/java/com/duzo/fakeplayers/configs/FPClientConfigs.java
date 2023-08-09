package com.duzo.fakeplayers.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class FPClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> USES_FILES;

    static {
        BUILDER.push("Configs for Duzo's Fake Players Mod");

        USES_FILES = BUILDER.comment("Defines if you want to use a file instead of a link // IS CLIENT SIDE ONLY, WILL NOT SHOW FOR OTHERS!!!")
                .define("Does use files",false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}

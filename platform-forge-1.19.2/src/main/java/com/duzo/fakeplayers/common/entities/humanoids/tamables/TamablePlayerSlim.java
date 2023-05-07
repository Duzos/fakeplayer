package com.duzo.fakeplayers.common.entities.humanoids.tamables;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TamablePlayerSlim extends TamablePlayer {
    public TamablePlayerSlim(EntityType<? extends TamableHumanoid> entityType, Level level) {
        super(entityType, level);
    }

    public TamablePlayerSlim(EntityType<? extends TamableHumanoid> entityType, Level level, String customName, ResourceLocation skin) {
        super(entityType, level, customName, skin);
    }

    public TamablePlayerSlim(EntityType<? extends TamableHumanoid> entityType, Level level, String customName) {
        super(entityType, level, customName);
    }

    public TamablePlayerSlim(EntityType<? extends TamableHumanoid> entityType, Level level, ResourceLocation skin) {
        super(entityType, level, skin);
    }
}

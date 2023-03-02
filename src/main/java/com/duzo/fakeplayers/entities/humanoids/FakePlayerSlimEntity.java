package com.duzo.fakeplayers.entities.humanoids;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FakePlayerSlimEntity extends FakePlayerEntity {

    public FakePlayerSlimEntity(EntityType<? extends FakePlayerEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FakePlayerSlimEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName, ResourceLocation skin) {
        super(entityType, level, customName, skin);
    }

    public FakePlayerSlimEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName) {
        super(entityType, level, customName);
    }

    public FakePlayerSlimEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, ResourceLocation skin) {
        super(entityType, level, skin);
    }
}

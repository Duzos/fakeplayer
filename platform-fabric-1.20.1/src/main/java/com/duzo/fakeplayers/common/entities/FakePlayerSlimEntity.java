package com.duzo.fakeplayers.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class FakePlayerSlimEntity extends FakePlayerEntity {
    public FakePlayerSlimEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}

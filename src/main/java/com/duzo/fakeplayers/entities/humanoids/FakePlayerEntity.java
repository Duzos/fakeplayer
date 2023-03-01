package com.duzo.fakeplayers.entities.humanoids;

import com.duzo.fakeplayers.entities.HumanoidEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FakePlayerEntity extends HumanoidEntity {

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level) {
        super(entityType, level);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName, ResourceLocation skin) {
        super(entityType, level, customName, skin);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, String customName) {
        super(entityType, level, customName);
    }

    public FakePlayerEntity(EntityType<? extends FakePlayerEntity> entityType, Level level, ResourceLocation skin) {
        super(entityType, level, skin);
    }


    @Override
    public void setCustomName(@Nullable Component customName) {
        super.setCustomName(customName);
    }
}

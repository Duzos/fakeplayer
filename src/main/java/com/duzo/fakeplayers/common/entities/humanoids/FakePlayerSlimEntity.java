package com.duzo.fakeplayers.common.entities.humanoids;

import com.duzo.fakeplayers.core.init.FPItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
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

    @Override
    public ItemStack getEgg() {
        return FPItems.FAKE_PLAYER_SLIM_SPAWN_EGG.get().getDefaultInstance();
    }
}

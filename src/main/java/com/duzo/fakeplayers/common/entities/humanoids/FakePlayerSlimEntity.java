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
    protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
//        super.dropCustomDeathLoot(p_21385_, p_21386_, p_21387_);

        ItemStack egg = FPItems.FAKE_PLAYER_SLIM_SPAWN_EGG.get().getDefaultInstance();
        egg.setHoverName(this.getCustomName());

        this.spawnAtLocation(egg);
    }
}

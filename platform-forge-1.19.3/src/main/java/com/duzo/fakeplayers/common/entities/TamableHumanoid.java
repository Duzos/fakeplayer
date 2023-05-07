package com.duzo.fakeplayers.common.entities;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

import static com.duzo.fakeplayers.common.entities.HumanoidEntity.ERROR_TEXTURE;

public abstract class TamableHumanoid extends TamableAnimal {
    public final Item tameItem = Items.REDSTONE;
    public String customName = "Duzo"; // the default name (mine :) )
    public ResourceLocation skin;
    public TamableHumanoid(EntityType<? extends TamableHumanoid> entityType, Level level) {
        super(entityType, level);
        this.skin = ERROR_TEXTURE;
        this.setCustomName(Component.translatable(this.customName));
        this.setCustomNameVisible(true);
    }

    public TamableHumanoid(EntityType<? extends TamableHumanoid> entityType, Level level,String customName, ResourceLocation skin) {
        this(entityType,level);
        this.customName = customName;
        this.skin = skin;
        this.setCustomName(Component.translatable(this.customName));
    }

    public TamableHumanoid(EntityType<? extends TamableHumanoid> entityType, Level level,String customName) {
        this(entityType,level);
        this.customName = customName;
        this.skin = ERROR_TEXTURE;
        this.setCustomName(Component.translatable(this.customName));
    }

    public TamableHumanoid(EntityType<? extends TamableHumanoid> entityType, Level level,ResourceLocation skin) {
        this(entityType,level);
        this.skin = skin;
        this.setCustomName(Component.translatable(this.customName));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        super.registerGoals();
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        // Taming
        if (!this.isTame()) {
            if (itemstack.is(this.tameItem)) {
                if (this.random.nextInt(3) == 0) {
                    this.tame(pPlayer);
                    this.navigation.stop();
                    this.setTarget((LivingEntity) null);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                    return InteractionResult.SUCCESS;
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                    return InteractionResult.FAIL;
                }
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void setCustomName(@Nullable Component customName) {
        if (customName == null) return;
        super.setCustomName(customName);
        this.customName = ChatFormatting.stripFormatting(this.getName().getString());
    }

    public ResourceLocation getSkin() {
        return this.skin;
    }

    public static AttributeSupplier.Builder getHumanoidAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 1D);
    }
}

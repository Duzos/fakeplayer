package com.duzo.fakeplayers.common.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.HurtBySensor;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.w3c.dom.Attr;

import java.util.UUID;
import java.util.jar.Attributes;

public class HumanoidEntity extends PathAwareEntity {
    public EntityType<? extends PathAwareEntity> entityType;
    public HumanoidEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.entityType = entityType;
        this.setUuid(UUID.randomUUID());
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new WanderAroundPointOfInterestGoal(this, 0.5D,false));
        this.goalSelector.add(5, new WanderAroundGoal(this,0.5D,120,false));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8f));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.5D, 32.0f));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 0.5D,true));
        this.goalSelector.add(2, new SwimGoal(this));
    }

    public static DefaultAttributeContainer getHumanoidAttributes() {
        return DefaultAttributeContainer.builder().add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).add(EntityAttributes.GENERIC_ATTACK_DAMAGE).add(EntityAttributes.GENERIC_FOLLOW_RANGE).add(EntityAttributes.GENERIC_MAX_HEALTH).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).add(EntityAttributes.GENERIC_MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ARMOR).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).build();
    }

    @Override
    public String getUuidAsString() {
        return this.getUuid().toString();
    }
}

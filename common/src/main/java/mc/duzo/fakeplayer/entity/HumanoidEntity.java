package mc.duzo.fakeplayer.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class HumanoidEntity extends PathAwareEntity {
	protected HumanoidEntity(EntityType<? extends HumanoidEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();

		this.goalSelector.add(5, new WanderAroundPointOfInterestGoal(this, 0.5D,false));
		this.goalSelector.add(5, new WanderAroundGoal(this,0.5D,120,false));
		this.goalSelector.add(5, new LookAroundGoal(this));
		this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8f));
		this.targetSelector.add(2, new RevengeGoal(this));
		((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
		this.getNavigation().setCanSwim(true);
		this.setCanPickUpLoot(true);
		this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.5D, 32.0f));
		this.goalSelector.add(2, new MeleeAttackGoal(this, 0.5D,true));
		this.goalSelector.add(2, new SwimGoal(this));
	}

	public static DefaultAttributeContainer.Builder getHumanoidAttributes() {
		return DefaultAttributeContainer.builder().add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).add(EntityAttributes.GENERIC_ATTACK_DAMAGE).add(EntityAttributes.GENERIC_FOLLOW_RANGE).add(EntityAttributes.GENERIC_MAX_HEALTH).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).add(EntityAttributes.GENERIC_MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ARMOR).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
	}
}

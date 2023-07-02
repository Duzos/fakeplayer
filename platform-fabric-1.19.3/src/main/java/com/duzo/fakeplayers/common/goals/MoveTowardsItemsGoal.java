package com.duzo.fakeplayers.common.goals;


import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.EnumSet;
import java.util.List;

public class MoveTowardsItemsGoal extends Goal {
    protected final PathAwareEntity mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;

    public MoveTowardsItemsGoal(PathAwareEntity mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public static List<ItemEntity> getNearbyItems(PathAwareEntity entity, double radius) {
        List<ItemEntity> list = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(radius), EntityPredicates.VALID_ENTITY);
        return list;
    }

    @Override
    public boolean canStart() {
        List<ItemEntity> nearbyItems = getNearbyItems(this.mob, 4);
        if (nearbyItems.size() != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.getNavigation().isIdle();
        } else {
            return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
        }
    }
    public void start() {
        List<ItemEntity> nearbyItems = getNearbyItems(this.mob, 4);
        if (nearbyItems.size() != 0) {
            for (ItemEntity checkedItem : nearbyItems) {
                if (checkedItem.isOnGround()) {
                    this.mob.getNavigation().startMovingTo(checkedItem.getX(), checkedItem.getY(), checkedItem.getZ(), this.speedModifier);
                    break;
                }
            }
        }
    }

    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget((LivingEntity)null);
        }
        this.mob.getNavigation().stop();
    }
}

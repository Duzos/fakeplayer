package com.duzo.fakeplayers.common.goals;

import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;

public class MoveTowardsItemsGoal extends Goal {
    protected final PathfinderMob mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;

    public MoveTowardsItemsGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public static List<ItemEntity> getNearbyItems(PathfinderMob entity, double radius) {
        List<ItemEntity> list = entity.level.getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(radius));
        return list;
    }

    @Override
    public boolean canUse() {
        List<ItemEntity> nearbyItems = getNearbyItems(this.mob, 4);
        if (nearbyItems.size() != 0) {
            return true;
        }
        return false;
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.getNavigation().isDone();
        } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }
    public void start() {
        List<ItemEntity> nearbyItems = getNearbyItems(this.mob, 4);
        if (nearbyItems.size() != 0) {
            for (int i = 0; i < nearbyItems.size(); i++) {
                ItemEntity checkedItem = nearbyItems.get(i);
                if (checkedItem.isOnGround()) {
                    this.mob.getNavigation().moveTo(checkedItem.getX(), checkedItem.getY(), checkedItem.getZ(), this.speedModifier);
                    break;
                }
            }
        }
    }

    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget((LivingEntity)null);
        }
        this.mob.getNavigation().stop();
    }
}

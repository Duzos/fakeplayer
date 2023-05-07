package com.duzo.fakeplayers.common.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.List;

public class GoToNearestBedIfNightGoal extends Goal {
    protected final PathfinderMob mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;

    public GoToNearestBedIfNightGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public static List<BlockPos> getNearbyBeds(PathfinderMob entity, double radius) {
        List<BlockPos> list;
        Level level = entity.getLevel();

        // Todo: finsih this
        list = null;

        return list;
    }

    @Override
    public boolean canUse() {
        if (this.mob.level.isDay()) {return false;}

        List<BlockPos> beds = getNearbyBeds(this.mob, 4);
        if (beds.size() != 0) {
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
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
        }
    }

    public void start() {
        List<BlockPos> beds = getNearbyBeds(this.mob, 4);
        if (beds.size() == 0) return;


    }

    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget((LivingEntity) null);
        }
        this.mob.getNavigation().stop();
    }
}

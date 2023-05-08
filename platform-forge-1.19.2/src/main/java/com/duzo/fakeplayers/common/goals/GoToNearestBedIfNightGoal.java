package com.duzo.fakeplayers.common.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

import java.util.EnumSet;
import java.util.List;

public class GoToNearestBedIfNightGoal extends MoveToBlockGoal {
    private final PathfinderMob mob;

    public GoToNearestBedIfNightGoal(PathfinderMob mob, double speedMultiplier, int searchRange) {
        super(mob, speedMultiplier, searchRange, 6);
        this.mob = mob;
//        this.verticalSearchStart = -2;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    public boolean canUse() {
        return this.mob.level.isNight() && super.canUse();
    }

    public void start() {
        super.start();
        this.mob.startSleeping(this.mob.blockPosition().below());
    }

    protected int nextStartTick(PathfinderMob p_25140_) {
        return 40;
    }

    public void stop() {
        super.stop();
        this.mob.stopSleeping();
    }

    public void tick() {
//        System.out.println("RUNNING BED GOAL");
        super.tick();
        if (!this.isReachedTarget()) {
            this.mob.stopSleeping();
        } else if (!this.mob.isSleeping()) {
            System.out.println("starting sleep via goal");
            this.mob.startSleeping(this.mob.blockPosition());
        }

    }

    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return level.isEmptyBlock(pos.above()) && level.getBlockState(pos).is(BlockTags.BEDS);
    }
}
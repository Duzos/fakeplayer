package com.duzo.fakeplayers.common.goals;

import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class HumanoidWaterAvoidingRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
    public HumanoidWaterAvoidingRandomStrollGoal(HumanoidEntity p_25987_, double p_25988_) {
        super(p_25987_, p_25988_);
    }

    public HumanoidWaterAvoidingRandomStrollGoal(HumanoidEntity p_25990_, double p_25991_, float p_25992_) {
        super(p_25990_, p_25991_, p_25992_);
    }

    @Override
    public boolean canUse() {
        if (((HumanoidEntity) this.mob).isSitting()) {
            return false;
        }

        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (((HumanoidEntity) this.mob).isSitting()) {
            return false;
        }

        return super.canContinueToUse();
    }
}
package mc.duzo.fakeplayer.entity.goal;

import net.minecraft.entity.ai.goal.WanderAroundGoal;

import mc.duzo.fakeplayer.entity.HumanoidEntity;

public class HumanoidStrollGoal extends WanderAroundGoal {
    public HumanoidStrollGoal(HumanoidEntity mob, double speed) {
        super(mob, speed);
    }

    public HumanoidStrollGoal(HumanoidEntity mob, double speed, int chance) {
        super(mob, speed, chance);
    }

    public HumanoidStrollGoal(HumanoidEntity entity, double speed, int chance, boolean canDespawn) {
        super(entity, speed, chance, canDespawn);
    }

    @Override
    public boolean canStart() {
        if (((HumanoidEntity) this.mob).isSitting()) {
            return false;
        }

        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        if (((HumanoidEntity) this.mob).isSitting()) {
            return false;
        }

        return super.shouldContinue();
    }
}

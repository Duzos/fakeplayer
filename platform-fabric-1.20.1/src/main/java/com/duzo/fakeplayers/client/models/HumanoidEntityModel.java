package com.duzo.fakeplayers.client.models;

import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import com.duzo.fakeplayers.components.MyComponents;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class HumanoidEntityModel extends PlayerEntityModel<HumanoidEntity> {
    public HumanoidEntityModel(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }

    @Override
    public void animateModel(HumanoidEntity livingEntity, float f, float g, float h) {
        super.animateModel(livingEntity, f, g, h);
        if (MyComponents.FAKE_PLAYER_SITTING_COMPONENT_COMPONENT.get(livingEntity).isSitting() && !livingEntity.hasVehicle()) {
            this.rightArm.pitch += (-(float)Math.PI / 5F);
            this.rightSleeve.pitch += (-(float)Math.PI / 5F);

            this.leftArm.pitch += (-(float)Math.PI / 5F);
            this.leftSleeve.pitch += (-(float)Math.PI / 5F);

            this.rightLeg.pitch = -1.4137167F;
            this.rightLeg.yaw = ((float)Math.PI / 10F);
            this.rightLeg.roll = 0.07853982F;
            this.rightPants.pitch = -1.4137167F;
            this.rightPants.yaw = ((float)Math.PI / 10F);
            this.rightPants.roll = 0.07853982F;

            this.leftLeg.pitch = -1.4137167F;
            this.leftLeg.yaw = (-(float)Math.PI / 10F);
            this.leftLeg.roll = -0.07853982F;
            this.leftPants.pitch = -1.4137167F;
            this.leftPants.yaw = (-(float)Math.PI / 10F);
            this.leftPants.roll = -0.07853982F;
        }
    }
}
